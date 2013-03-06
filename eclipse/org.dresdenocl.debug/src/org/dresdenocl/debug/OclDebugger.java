package org.dresdenocl.debug;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dresdenocl.debug.model.EOclDebugMessageType;
import org.dresdenocl.debug.model.OclDebugCommunicationHelper;
import org.dresdenocl.debug.model.OclDebugMessage;
import org.dresdenocl.debug.util.EStepMode;
import org.dresdenocl.debug.util.OclStringUtil;
import org.dresdenocl.essentialocl.expressions.BooleanLiteralExp;
import org.dresdenocl.essentialocl.expressions.CollectionItem;
import org.dresdenocl.essentialocl.expressions.CollectionLiteralExp;
import org.dresdenocl.essentialocl.expressions.EnumLiteralExp;
import org.dresdenocl.essentialocl.expressions.IfExp;
import org.dresdenocl.essentialocl.expressions.IntegerLiteralExp;
import org.dresdenocl.essentialocl.expressions.InvalidLiteralExp;
import org.dresdenocl.essentialocl.expressions.IterateExp;
import org.dresdenocl.essentialocl.expressions.IteratorExp;
import org.dresdenocl.essentialocl.expressions.LetExp;
import org.dresdenocl.essentialocl.expressions.OclExpression;
import org.dresdenocl.essentialocl.expressions.OperationCallExp;
import org.dresdenocl.essentialocl.expressions.PropertyCallExp;
import org.dresdenocl.essentialocl.expressions.RealLiteralExp;
import org.dresdenocl.essentialocl.expressions.StringLiteralExp;
import org.dresdenocl.essentialocl.expressions.TupleLiteralExp;
import org.dresdenocl.essentialocl.expressions.TupleLiteralPart;
import org.dresdenocl.essentialocl.expressions.TypeLiteralExp;
import org.dresdenocl.essentialocl.expressions.UndefinedLiteralExp;
import org.dresdenocl.essentialocl.expressions.Variable;
import org.dresdenocl.essentialocl.standardlibrary.OclAny;
import org.dresdenocl.essentialocl.standardlibrary.OclCollection;
import org.dresdenocl.essentialocl.standardlibrary.OclIterator;
import org.dresdenocl.interpreter.IInterpretationResult;
import org.dresdenocl.interpreter.internal.OclInterpreter;
import org.dresdenocl.language.ocl.resource.ocl.mopp.OclResource;
import org.dresdenocl.modelbus.ModelBusPlugin;
import org.dresdenocl.modelinstance.IModelInstance;
import org.dresdenocl.modelinstancetype.types.IModelInstanceElement;
import org.dresdenocl.pivotmodel.Constraint;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

public class OclDebugger extends OclInterpreter implements IOclDebuggable {

	private boolean m_debugMode;
	private boolean m_suspended;
	private boolean m_terminated;
	private boolean alreadySentStartEvent = false;
	private ServerSocket m_server;
	private PrintStream m_outputStream;
	private OclDebugCommunicationHelper m_communicationHelper =
			new OclDebugCommunicationHelper();
	private Set<Integer> m_lineBreakpointPositions = new HashSet<Integer>();
	private LinkedList<String> m_stackframes = new LinkedList<String>();
	private int m_nextId = 0;
	private Map<EObject, EObject> m_currentMappings;
	private Map<String, Map<String, Object>> m_stackVariables =
			new LinkedHashMap<String, Map<String, Object>>();
	private Integer m_lastPassedBreakpoint = Integer.valueOf(-1);
	private Set<Integer> m_invalidBreakpoints = new HashSet<Integer>();
	private EStepMode m_stepMode = EStepMode.NORMAL;

	public OclDebugger(IModelInstance aModelInstance) {

		super(aModelInstance);
	}

	@Override
	public IInterpretationResult interpretConstraint(Constraint constraint,
			IModelInstanceElement modelInstanceElement) {

		startupAndWait();
		// m_stackVariables.clear();
		// m_stackframes.clear();
		m_invalidBreakpoints.clear();
		m_lastPassedBreakpoint = Integer.valueOf(-1);

		IInterpretationResult result =
				super.interpretConstraint(constraint, modelInstanceElement);
		return result;
	}

	@Override
	public List<IInterpretationResult> interpretConstraints(
			Collection<Constraint> constraints,
			IModelInstanceElement modelInstanceElement) {

		List<IInterpretationResult> result =
				super.interpretConstraints(constraints, modelInstanceElement);

		terminate();
		return result;
	}

	@Override
	public void setEventPort(int eventPort) {

		if (isDebugMode()) {
			// check if server is already running and shut down if necessary to
			// change the port
			if (m_server != null) {
				if (!m_server.isClosed()) {
					if (m_server.getLocalPort() != eventPort) {
						stopEventSocket();
						startEventSocket(eventPort);
					}
					// no else. already listening to the port
				}
				else {
					// already closed, so create new one
					startEventSocket(eventPort);
				}
			}
			else {
				// still null
				startEventSocket(eventPort);
			}
		}
	}

	/**
	 * Computes the line of the EObject in the containing resource.
	 * 
	 * @param element
	 *          the EObject element
	 * @return the line element was defined in the resource
	 */
	protected int getLine(EObject element) {

		EObject e = m_currentMappings.get(element);
		if (e == null)
			System.out.println("getLine NULL BEI " + element);
		OclResource resource = (OclResource) e.eResource();
		int line;
		do {
			line = resource.getLocationMap().getLine(e);
			e = e.eContainer();
		} while (line == -1 && e != null);
		return line;
	}

	public boolean isLineBreakPointElement(EObject element) {

		if (!isDebugMode()) {
			return false;
		}

		// If normal mode : check if we have a breakpoint
		if (m_stepMode.equals(EStepMode.NORMAL)) {
			Integer line = Integer.valueOf(getLine(element));
			// check if the element is found in the map
			boolean result = false;

			if (line != null && line.intValue() != -1) {
				result =
						m_lineBreakpointPositions.contains(line)
								&& !m_invalidBreakpoints.contains(line);
			}
			// no else
			return result;
		}
		else if (m_stepMode.equals(EStepMode.STEP_INTO)) {
			m_stepMode = EStepMode.NORMAL;
			return true;
		}
		// no else
		return false;
	}

	private void stopOnBreakpoint(String methodName, EObject parameter) {

		pushStackFrame(methodName, parameter);
		if (isLineBreakPointElement(parameter)) {
			m_lastPassedBreakpoint = Integer.valueOf(getLine(parameter));
			setSuspend(true);
		}
		waitIfSuspended();
	}

	public void startupAndWait() {

		System.out.println("startupAndWait()");
		if (isDebugMode()) {
			if (!alreadySentStartEvent) {
				alreadySentStartEvent = true;
				sendEvent(EOclDebugMessageType.STARTED, true);
				setSuspend(true);
				waitIfSuspended();
			}
		}
		m_currentMappings =
				ModelBusPlugin.getModelRegistry().getActiveModel().getAllMappings();
	}

	public boolean isSuspended() {

		return m_suspended;
	}

	public void setSuspend(boolean suspend) {

		System.out.println("setSuspend ( " + suspend + " )");
		m_suspended = suspend;
		sendEvent(EOclDebugMessageType.SUSPENDED, true);
	}

	public void sendEvent(EOclDebugMessageType command,
			boolean sendOnlyInDebugMode, String... arguments) {

		System.out.println("OclInterpreter sendEvent " + command);
		if (isDebugMode() || !sendOnlyInDebugMode) {
			OclDebugMessage message = new OclDebugMessage(command, arguments);
			m_communicationHelper.sendEvent(message, m_outputStream);
		}
	}

	public void setDebugMode(boolean debugMode) {

		this.m_debugMode = debugMode;
	}

	public boolean isDebugMode() {

		System.out.println("isDebugMode = " + m_debugMode);
		return m_debugMode;
	}

	public void startEventSocket(final int eventPort) {

		System.out.println("OclInterpreter startEventSocket");
		try {
			m_server = new ServerSocket(eventPort);
			Socket accept = m_server.accept();
			/*
			 * try { Thread.sleep(100); } catch (InterruptedException e) {
			 * e.printStackTrace(); }
			 */
			System.out.println("OclInterpreter accepted client");
			m_outputStream = new PrintStream(accept.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void stopEventSocket() {

		try {
			m_server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void waitIfSuspended() {

		if (isSuspended()) {
			try {
				while (isSuspended()) {
					Thread.sleep(100);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sendEvent(EOclDebugMessageType.RESUMED, true);
		}
	}

	private void setTerminate(boolean terminate) {

		m_terminated = terminate;
	}

	private boolean isTerminated() {

		return m_terminated;
	}

	@Override
	public void terminate() {

		System.out.println("OclInterpreter terminate()");
		setTerminate(true);
		sendEvent(EOclDebugMessageType.TERMINATED, false);
		stopEventSocket();
	}

	@Override
	public synchronized void resume() {

		System.out.println("OclInterpreter resume()");
		m_invalidBreakpoints.add(m_lastPassedBreakpoint);
		m_stepMode = EStepMode.NORMAL;
		setSuspend(false);
		sendEvent(EOclDebugMessageType.RESUMED, true);
	}

	@Override
	public synchronized void stepOver() {

		// TODO Auto-generated method stub

	}

	@Override
	public synchronized void stepInto() {

		// make the last passed breakpoint (the one we've been suspending, thought)
		// invalid vor this model instance element
		// and stop at the next line
		m_invalidBreakpoints.add(m_lastPassedBreakpoint);
		m_stepMode = EStepMode.STEP_INTO;
		System.out.println("OclInterpreter stepInto()");
		setSuspend(false);
		sendEvent(EOclDebugMessageType.RESUMED, true);
	}

	@Override
	public synchronized void stepReturn() {

		// TODO Auto-generated method stub

	}

	@Override
	public void addLineBreakPoint(String location, int line) {

		Integer i = Integer.valueOf(line);
		m_lineBreakpointPositions.add(i);
		m_invalidBreakpoints.remove(i);
	}

	@Override
	public void removeLineBreakPoint(String location, int line) {

		Integer i = Integer.valueOf(line);
		m_lineBreakpointPositions.remove(i);
		m_invalidBreakpoints.add(i);
		m_lastPassedBreakpoint = Integer.valueOf(-1);
	}

	@Override
	public String[] getStack() {

		List<String> reverseStack = new ArrayList<String>(m_stackframes);
		Collections.reverse(reverseStack);
		return reverseStack.toArray(new String[0]);
	}

	@Override
	public Map<String, Object> getFrameVariables(String stackFrame) {

		return m_stackVariables.get(stackFrame);
	}

	private String getNextStackId() {

		return Integer.toString(++m_nextId);
	}

	@Override
	public OclAny caseBooleanLiteralExp(BooleanLiteralExp booleanLiteralExp) {

		stopOnBreakpoint("caseBooleanLiteralExp", booleanLiteralExp);
		OclAny result = super.caseBooleanLiteralExp(booleanLiteralExp);
		popStackFrame();
		stopOnBreakpoint("caseBooleanLiteralExp", booleanLiteralExp);
		popStackFrame();
		return result;
	}

	@Override
	public OclAny caseCollectionItem(CollectionItem collectionItem) {

		stopOnBreakpoint("caseCollectionItem", collectionItem);
		OclAny result = super.caseCollectionItem(collectionItem);
		popStackFrame();
		stopOnBreakpoint("caseCollectionItem", collectionItem);
		popStackFrame();
		return result;
	}

	@Override
	public OclAny caseCollectionLiteralExp(
			CollectionLiteralExp collectionLiteralExp) {

		stopOnBreakpoint("caseCollectionLiteralExp", collectionLiteralExp);
		OclAny result = super.caseCollectionLiteralExp(collectionLiteralExp);
		popStackFrame();
		stopOnBreakpoint("caseCollectionLiteralExp", collectionLiteralExp);
		popStackFrame();
		return result;
	}

	@Override
	public OclAny caseEnumLiteralExp(EnumLiteralExp enumLiteralExp) {

		stopOnBreakpoint("caseEnumLiteralExp", enumLiteralExp);
		OclAny result = super.caseEnumLiteralExp(enumLiteralExp);
		popStackFrame();
		stopOnBreakpoint("caseEnumLiteralExp", enumLiteralExp);
		popStackFrame();
		return result;
	}

	/*
	 * @Override public OclAny caseExpressionInOcl(ExpressionInOcl
	 * expressionInOcl) { stopOnBreakpoint("caseExpressionInOcl",
	 * expressionInOcl); OclAny result =
	 * super.caseExpressionInOcl(expressionInOcl); popStackFrame();
	 * stopOnBreakpoint("caseExpressionInOcl", expressionInOcl); popStackFrame();
	 * return result; }
	 */

	@Override
	public OclAny caseIfExp(IfExp ifExp) {

		stopOnBreakpoint("caseIfExp", ifExp);
		OclAny result = super.caseIfExp(ifExp);
		popStackFrame();
		stopOnBreakpoint("caseIfExp", ifExp);
		popStackFrame();
		return result;
	}

	@Override
	public OclAny caseIntegerLiteralExp(IntegerLiteralExp integerLiteralExp) {

		OclAny result = super.caseIntegerLiteralExp(integerLiteralExp);
		stopOnBreakpoint("caseIntegerLiteralExp", integerLiteralExp);
		popStackFrame();
		return result;
	}

	@Override
	public OclAny caseInvalidLiteralExp(InvalidLiteralExp invalidLiteralExp) {

		stopOnBreakpoint("caseInvalidLiteralExp", invalidLiteralExp);
		OclAny result = super.caseInvalidLiteralExp(invalidLiteralExp);
		popStackFrame();
		stopOnBreakpoint("caseInvalidLiteralExp", invalidLiteralExp);
		popStackFrame();
		return result;
	}

	@Override
	public OclAny caseIterateExp(IterateExp iterateExp) {

		stopOnBreakpoint("caseIterateExp", iterateExp);
		OclAny result = super.caseIterateExp(iterateExp);
		popStackFrame();
		stopOnBreakpoint("caseIterateExp", iterateExp);
		popStackFrame();
		return result;
	}

	@Override
	public OclAny caseIteratorExp(IteratorExp iteratorExp) {

		stopOnBreakpoint("caseIteratorExp", iteratorExp);
		OclAny result = super.caseIteratorExp(iteratorExp);
		popStackFrame();
		stopOnBreakpoint("caseIteratorExp", iteratorExp);
		popStackFrame();
		return result;
	}

	@Override
	public OclAny caseLetExp(LetExp letExp) {

		stopOnBreakpoint("caseLetExp", letExp);
		// Map<String, Object> m = m_stackVariables.get(m_stackframes.getLast());
		// m.put(letExp.getVariable().getName(), letExp.getVariable());
		OclAny result = super.caseLetExp(letExp);
		popStackFrame();
		stopOnBreakpoint("caseLetExp", letExp);
		popStackFrame();
		return result;
	}

	@Override
	public OclAny caseOperationCallExp(OperationCallExp operationCallExp) {

		OclAny result = super.caseOperationCallExp(operationCallExp);
		myEnvironment.setVariableValue("result of "
				+ operationCallExp.getReferredOperation().getName(), result);
		stopOnBreakpoint("caseOperationCallExp", operationCallExp);
		popStackFrame();
		myEnvironment.deleteVariableValue("result of "
				+ operationCallExp.getReferredOperation().getName());
		return result;
	}

	@Override
	public OclAny casePropertyCallExp(PropertyCallExp propertyCallExp) {

		stopOnBreakpoint("casePropertyCallExp", propertyCallExp);
		OclAny result = super.casePropertyCallExp(propertyCallExp);
		popStackFrame();
		stopOnBreakpoint("casePropertyCallExp", propertyCallExp);
		popStackFrame();
		return result;
	}

	@Override
	public OclAny caseRealLiteralExp(RealLiteralExp realLiteralExp) {

		stopOnBreakpoint("caseRealLiteralExp", realLiteralExp);
		OclAny result = super.caseRealLiteralExp(realLiteralExp);
		popStackFrame();
		stopOnBreakpoint("caseRealLiteralExp", realLiteralExp);
		popStackFrame();
		return result;
	}

	@Override
	public OclAny caseStringLiteralExp(StringLiteralExp stringLiteralExp) {

		stopOnBreakpoint("caseStringLiteralExp", stringLiteralExp);
		OclAny result = super.caseStringLiteralExp(stringLiteralExp);
		popStackFrame();
		stopOnBreakpoint("caseStringLiteralExp", stringLiteralExp);
		popStackFrame();
		return result;
	}

	@Override
	public OclAny caseTupleLiteralExp(TupleLiteralExp tupleLiteralExp) {

		stopOnBreakpoint("caseTupleLiteralExp", tupleLiteralExp);
		OclAny result = super.caseTupleLiteralExp(tupleLiteralExp);
		popStackFrame();
		stopOnBreakpoint("caseTupleLiteralExp", tupleLiteralExp);
		popStackFrame();
		return result;
	}

	@Override
	public OclAny caseTupleLiteralPart(TupleLiteralPart tupleLiteralPart) {

		stopOnBreakpoint("caseTupleLiteralPart", tupleLiteralPart);
		OclAny result = super.caseTupleLiteralPart(tupleLiteralPart);
		popStackFrame();
		stopOnBreakpoint("caseTupleLiteralPart", tupleLiteralPart);
		popStackFrame();
		return result;
	}

	@Override
	public OclAny caseTypeLiteralExp(TypeLiteralExp typeLiteralExp) {

		stopOnBreakpoint("caseTypeLiteralExp", typeLiteralExp);
		OclAny result = super.caseTypeLiteralExp(typeLiteralExp);
		popStackFrame();
		stopOnBreakpoint("caseTypeLiteralExp", typeLiteralExp);
		popStackFrame();
		return result;
	}

	@Override
	public OclAny caseUndefinedLiteralExp(UndefinedLiteralExp undefinedLiteralExp) {

		stopOnBreakpoint("caseUndefinedLiteralExp", undefinedLiteralExp);
		OclAny result = super.caseUndefinedLiteralExp(undefinedLiteralExp);
		popStackFrame();
		stopOnBreakpoint("caseUndefinedLiteralExp", undefinedLiteralExp);
		popStackFrame();
		return result;
	}

	/*
	 * @Override public OclAny caseVariable(Variable variable) {
	 * stopOnBreakpoint("caseVariable", variable); OclAny result =
	 * super.caseVariable(variable); popStackFrame();
	 * stopOnBreakpoint("caseVariable", variable); popStackFrame(); return result;
	 * }
	 */
	/*
	 * @Override public OclAny caseVariableExp(VariableExp variableExp) {
	 * stopOnBreakpoint("caseVariableExp", variableExp); OclAny result =
	 * super.caseVariableExp(variableExp); popStackFrame();
	 * stopOnBreakpoint("caseVariableExp", variableExp); popStackFrame(); return
	 * result; }
	 */

	@Override
	protected OclAny evaluateNonStaticOperation(OperationCallExp operationCallExp) {

		stopOnBreakpoint("evaluateNonStaticOperation "
				+ operationCallExp.getReferredOperation().getName(), operationCallExp);
		OclAny result = super.evaluateNonStaticOperation(operationCallExp);
		popStackFrame();
		stopOnBreakpoint("evaluateNonStaticOperation "
				+ operationCallExp.getReferredOperation().getName(), operationCallExp);
		popStackFrame();
		return result;
	}

	@Override
	protected OclAny evaluateStaticOperation(OperationCallExp operationCallExp) {

		stopOnBreakpoint("evaluateStaticOperation", operationCallExp);
		OclAny result = super.evaluateStaticOperation(operationCallExp);
		popStackFrame();
		stopOnBreakpoint("evaluateStaticOperation", operationCallExp);
		popStackFrame();
		return result;
	}

	@Override
	protected LinkedHashMap<String, OclAny> computeParameters(
			OperationCallExp anOperationCallExp, Constraint oclDefinedOperation) {

		stopOnBreakpoint("computeParameters", anOperationCallExp);
		LinkedHashMap<String, OclAny> result =
				super.computeParameters(anOperationCallExp, oclDefinedOperation);
		popStackFrame();
		int i = 0;
		for (String paramKey : result.keySet()) {
			myEnvironment.setVariableValue("param" + ++i, result.get(paramKey));
		}
		stopOnBreakpoint("computeParameters", anOperationCallExp);
		popStackFrame();
		i = 0;
		while (i < result.keySet().size()) {
			myEnvironment.deleteVariableValue("param" + ++i);
		}
		return result;
	}

	protected OclAny evaluateIterate(OclExpression bodyExpression,
			OclCollection<OclAny> source, List<Variable> iteratorVariables,
			OclIterator<OclAny> iterator, Variable resultVariable) {

		Map<String, Object> m = m_stackVariables.get(m_stackframes.getLast());
		m.put("source", source);
		m.put("iterator", iterator);

		OclAny result =
				super.evaluateIterate(bodyExpression, source, iteratorVariables,
						iterator, resultVariable);

		return result;
	}

	protected OclResource getOclResource(URI uri) {

		String platformString = uri.toPlatformString(true);
		org.eclipse.core.resources.IResource member =
				org.eclipse.core.resources.ResourcesPlugin.getWorkspace().getRoot()
						.findMember(platformString);
		if (member instanceof OclResource) {
			return (OclResource) member;
		}
		else
			return null;
	}

	protected void pushStackFrame(String functionName, EObject parameter) {

		int line = getLine(parameter);
		String[] data = new String[6];
		data[0] =
				functionName + " ( " + parameter.getClass().getSimpleName() + " )";
		data[1] = getNextStackId();
		data[2] = m_currentMappings.get(parameter).eResource().getURI().toString();
		data[3] = Integer.toString(line);
		data[4] = "1";
		data[5] = "2";

		OclResource resource =
				(OclResource) m_currentMappings.get(parameter).eResource();
		if (resource != null) {
			data[4] =
					Integer.toString(resource.getLocationMap().getCharStart(
							m_currentMappings.get(parameter)));
			data[5] =
					Integer.toString(resource.getLocationMap().getCharEnd(
							m_currentMappings.get(parameter)) + 1);
		}
		// no else

		String stackFrame = OclStringUtil.encode(',', data);
		m_stackframes.push(stackFrame);
		// store the mapping from current stackframe to variables
		Map<String, Object> map =
				new HashMap<String, Object>(myEnvironment.getVariableValues());
		// map.put(parameter.getClass().getSimpleName(), parameter.toString());
		/*
		 * if (!myEnvironmentStack.isEmpty()) {
		 * map.putAll(myEnvironmentStack.peek().getStoredVariableMappings()); }
		 */
		m_stackVariables.put(data[1], map);
	}

	protected String popStackFrame() {

		return m_stackframes.pop();
	}
}
