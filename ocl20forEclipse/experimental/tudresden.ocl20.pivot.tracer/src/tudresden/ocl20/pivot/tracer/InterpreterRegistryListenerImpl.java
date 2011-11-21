/*
Copyright (C) 2011 by Lars Schütze (lschuetze@gmx.net)

This file is part of the OCL 2 Interpreter of Dresden OCL2 for Eclipse.

Dresden OCL2 for Eclipse is free software: you can redistribute it and/or modify 
it under the terms of the GNU Lesser General Public License as published by the 
Free Software Foundation, either version 3 of the License, or (at your option)
any later version.

Dresden OCL2 for Eclipse is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License 
for more details.

You should have received a copy of the GNU Lesser General Public License along 
with Dresden OCL2 for Eclipse. If not, see <http://www.gnu.org/licenses/>.
 */
package tudresden.ocl20.pivot.tracer;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.WeakHashMap;

import org.eclipse.emf.ecore.EObject;

import tudresden.ocl20.pivot.interpreter.event.IInterpreterTraceListener;
import tudresden.ocl20.pivot.interpreter.event.internal.InterpreterTraceEvent;
import tudresden.ocl20.pivot.modelinstancetype.types.IModelInstanceElement;
import tudresden.ocl20.pivot.tracer.tracermodel.TracerItem;
import tudresden.ocl20.pivot.tracer.tracermodel.TracerRoot;
import tudresden.ocl20.pivot.tracer.tracermodel.TracermodelFactory;
import tudresden.ocl20.pivot.tracer.tracermodel.impl.TracermodelPackageImpl;

/**
 * @author Lars Schütze
 */
public class InterpreterRegistryListenerImpl implements
	IInterpreterTraceListener {
    /** Holding all root elements of this forest. */
    private TracerRoot roots;

    /** Pointing to the current parent */
    private TracerItem currentParent;

    /** The factory to create new instances */
    private TracermodelFactory factory;
    
    /** This map holds the TracerItems in a cache for fast access */
    private WeakHashMap<UUID, TracerItem> cachedItems;
    
    /** Holds all root elements of the forst that have been selected */
    private TracerRoot tracedRoots;

    /** Indicated whether we have to trace all or just a selection */
    private boolean isTracingSelection;

    public InterpreterRegistryListenerImpl() {
	tracedRoots = null;
	roots = null;
	currentParent = null;
	isTracingSelection = false;
	cachedItems = new WeakHashMap<UUID, TracerItem>();
	TracermodelPackageImpl.init();
	factory = TracermodelFactory.eINSTANCE;
    }

    /**
     * <p>
     * Sets the current parent of a tree after inserting a dummy entry which is
     * to be filled later by calling {@link partialInterpretationFinished}.
     * </p>
     * 
     * @param uuid
     *            The UUID which identifies this entry later.
     */
    public void interpretationTreeDepthIncreased(UUID uuid) {
	TracerItem dummy = factory.createTracerItem();
	dummy.setUUID(uuid);
	
	/* Add the dummy to the cache to be localized easier later */
	cachedItems.put(uuid, dummy);
	
	/* Add the dummy to the tree structure */
	if ((currentParent == null) && (roots == null)) {
	    // there has been no insertion before
	    // this is the first item in the tree
	    roots = factory.createTracerRoot();
	    roots.getRootItems().add(dummy);
	    currentParent = dummy;
	} else {
	    if (currentParent == null) {
		roots.getRootItems().add(dummy);
		currentParent = dummy;
	    } else {
		currentParent.getChildren().add(dummy);
		dummy.setParent(currentParent);
		currentParent = dummy;
	    }
	}
    }
    
    /**
     * <p>
     * This method overloads the
     * {@link InterpreterRegistryListenerImpl#interpretationTreeDepthIncreased(UUID)}
     * and adds the {@link IModelInstanceElement} to the resulting
     * {@link TracerItem} in the tree.
     * </p>
     */
    public void interpretationTreeDepthIncreased(UUID uuid,
	    IModelInstanceElement modelInstanceElement) {
	
	/* Call this method since it overloads this method */
	interpretationTreeDepthIncreased(uuid);
	
	/* Set the modelInstanceElement from the 
	 * just added item
	 */
	TracerItem item = cachedItems.get(uuid);
	item.setModelInstanceElement(modelInstanceElement);
    }

    /**
     * <p>
     * Sets the current parent of the tree.
     * </p>
     */
    public void interpretationTreeDepthDecreased() {
	// check if the tree has been initialized
	if (currentParent != null) {
	    // check if currentParent is the root element
	    if (currentParent.getParent() != null) {
		currentParent = currentParent.getParent();
	    } else {
		currentParent = null;
	    }
	}
	// no else
	/*
	 * this should normally not happen that {@see
	 * interpretationTreeDepthIncrease} is called when currentParent == null
	 */
    }

    public void partialInterpretationFinished(InterpreterTraceEvent event) {
	
	/* Search the corresponding TracerItem */
	TracerItem item = cachedItems.get(event.getUUID());
	
	/* Probably check for null reference */
	if(item != null) {
	    item.setExpression(event.getExpression());
	    item.setResult(event.getResult());
	    return;
	}
    }

    /**
     * 
     * @return Returns the root object {@link TracerRoot} of the tree.
     */
    public TracerRoot getTree() {
	return isTracingSelection ? tracedRoots : roots;
    }

    public void interpretationCleared() {
	Iterator<EObject> it = factory.eContents().iterator();

	// clear all saved TracerItems due to memory leaks
	while (it.hasNext()) {
	    it.remove();
	}

	isTracingSelection = false;
	roots = null;
	currentParent = null;
	tracedRoots = null;
    }
    
    public void traceSelectedConstraints(List<Object[]> constraints) {
	isTracingSelection = true;
	tracedRoots = factory.createTracerRoot();

	for (Object[] aRow : constraints) {
	    /*
	     * aRow[0] holds the ModelInstanceElement aRow[1] holds the
	     * Constraint itself aRow[2] holds the result
	     * 
	     * Now we need to find the specific element in our tree and return
	     * its subtree
	     */
    
	    for (TracerItem i : roots.getRootItems()) {
		if ((i.getExpression() == aRow[1])
			&& (i.getResult() == aRow[2])
			&& (i.getModelInstanceElement() == aRow[0]))
		{
		    tracedRoots.getRootItems().add(i);
		}
		//no else
	    }
	    // end for
	}
	// end for
    }
}
