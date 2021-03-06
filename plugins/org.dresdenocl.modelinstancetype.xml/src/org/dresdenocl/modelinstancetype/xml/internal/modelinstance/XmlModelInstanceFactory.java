/*
Copyright (C) 2010 by Claas Wilke (claaswilke@gmx.net)

This file is part of the XML Model Instance Plug-in of Dresden OCL2 for Eclipse.

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

package org.dresdenocl.modelinstancetype.xml.internal.modelinstance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.apache.log4j.Logger;
import org.eclipse.osgi.util.NLS;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import org.dresdenocl.essentialocl.types.CollectionType;
import org.dresdenocl.model.IModel;
import org.dresdenocl.model.ModelAccessException;
import org.dresdenocl.modelinstancetype.exception.TypeNotFoundInModelException;
import org.dresdenocl.modelinstancetype.types.IModelInstanceBoolean;
import org.dresdenocl.modelinstancetype.types.IModelInstanceElement;
import org.dresdenocl.modelinstancetype.types.IModelInstanceEnumerationLiteral;
import org.dresdenocl.modelinstancetype.types.IModelInstanceFactory;
import org.dresdenocl.modelinstancetype.types.IModelInstanceInteger;
import org.dresdenocl.modelinstancetype.types.IModelInstanceObject;
import org.dresdenocl.modelinstancetype.types.IModelInstanceReal;
import org.dresdenocl.modelinstancetype.types.IModelInstanceString;
import org.dresdenocl.modelinstancetype.types.base.BasisJavaModelInstanceFactory;
import org.dresdenocl.modelinstancetype.xml.XmlModelInstanceTypePlugin;
import org.dresdenocl.modelinstancetype.xml.internal.msg.XmlModelInstanceTypeMessages;
import org.dresdenocl.pivotmodel.Enumeration;
import org.dresdenocl.pivotmodel.EnumerationLiteral;
import org.dresdenocl.pivotmodel.PrimitiveType;
import org.dresdenocl.pivotmodel.Property;
import org.dresdenocl.pivotmodel.Type;

/**
 * <p>
 * {@link IModelInstanceFactory} implementation for {@link XmlModelInstance}s.
 * </p>
 * 
 * @author Claas Wilke
 */
public class XmlModelInstanceFactory extends BasisJavaModelInstanceFactory {

	/** The {@link Logger} for this class. */
	private static final Logger LOGGER = XmlModelInstanceTypePlugin
			.getLogger(XmlModelInstanceFactory.class);

	/**
	 * The {@link IModel} for whose {@link Type}s {@link IModelInstanceElement}s
	 * shall be created.
	 */
	private IModel model;

	/**
	 * The cached {@link IModelInstanceObject}s of this
	 * {@link XmlModelInstanceFactory} identified by their adapted {@link Node}.
	 */
	private Map<Node, IModelInstanceObject> cacheModelInstanceObjects = new WeakHashMap<Node, IModelInstanceObject>();

	/**
	 * <p>
	 * Creates a new {@link XmlModelInstanceFactory}.
	 * </p>
	 * 
	 * @param model
	 *            The {@link IModel} for whose {@link Type}s
	 *            {@link IModelInstanceElement}s shall be created.
	 */
	public XmlModelInstanceFactory(IModel model) {

		if (model == null) {
			throw new IllegalArgumentException(
					"Parameter 'model' must not be null.");
		}
		// no else.

		this.model = model;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dresdenocl.modelbus.modelinstance.types.IModelInstanceFactory
	 * #createModelInstanceElement(java.lang.Object)
	 */
	@Override
	public IModelInstanceElement createModelInstanceElement(Object adapted)
			throws TypeNotFoundInModelException {

		/* Probably debug the entry of this method. */
		if (LOGGER.isDebugEnabled()) {
			String msg;

			msg = "createModelInstanceElement("; //$NON-NLS-1$
			msg += "adapted = " + adapted; //$NON-NLS-1$
			msg += ")"; //$NON-NLS-1$

			LOGGER.debug(msg);
		}
		// no else.

		IModelInstanceElement result;

		if (adapted instanceof Node) {

			Node node;
			node = (Node) adapted;

			result = this.createModelInstanceElement(node);
		}

		else {
			throw new IllegalArgumentException(
					NLS.bind(
							XmlModelInstanceTypeMessages.XmlModelInstanceFactory_UnknownClassOfAdaptee,
							adapted.getClass().getCanonicalName()));
		}

		/* Probably debug the exit of this method. */
		if (LOGGER.isDebugEnabled()) {
			String msg;

			msg = "createModelInstanceElement(Object) - exit"; //$NON-NLS-1$

			LOGGER.debug(msg);
		}
		// no else.

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dresdenocl.modelbus.modelinstance.types.IModelInstanceFactory
	 * #createModelInstanceElement(java.lang.Object,
	 * org.dresdenocl.pivotmodel.Type)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public IModelInstanceElement createModelInstanceElement(Object adapted,
			Type type) {

		/* Probably debug the entry of this method. */
		if (LOGGER.isDebugEnabled()) {
			String msg;

			msg = "createModelInstanceElement("; //$NON-NLS-1$
			msg += "adapted = " + adapted; //$NON-NLS-1$
			msg += "type = " + type; //$NON-NLS-1$
			msg += ")"; //$NON-NLS-1$

			LOGGER.debug(msg);
		}
		// no else.

		IModelInstanceElement result;

		if (adapted == null || adapted instanceof Node) {

			Node node;
			node = null;

			if (adapted != null) {
				node = (Node) adapted;
			}
			// no else.

			/* Probably adapt a literal. */
			if (type instanceof Enumeration) {

				result = this.createModelInstanceEnumerationLiteral(node,
						(Enumeration) type);
			}

			/* Else probably adapt a primitive type. */
			else if (type instanceof PrimitiveType) {

				switch (((PrimitiveType) type).getKind()) {

				case BOOLEAN:
					result = this.createModelInstanceBoolean(node, type);
					break;

				case INTEGER:
					result = this.createModelInstanceInteger(node, type);
					break;

				case REAL:
					result = this.createModelInstanceReal(node, type);
					break;

				case STRING:
					result = this.createModelInstanceString(node, type);
					break;

				default:
					throw new IllegalArgumentException(
							NLS.bind(
									XmlModelInstanceTypeMessages.XmlModelInstanceFactory_UnknownClassOfAdaptee,
									adapted.getClass().getCanonicalName()));
				}
				// end select.
			}

			else {
				/* Probably use a cached result. */
				if (this.cacheModelInstanceObjects.containsKey(node)) {
					result = this.cacheModelInstanceObjects.get(node);
				}

				else {
					result = this.createModelInstanceObject(node, type);

					/* Add the result to the cache. */
					this.cacheModelInstanceObjects.put(node,
							(IModelInstanceObject) result);
				}
				// end else.
			}
		}

		else if (type instanceof CollectionType
				&& adapted instanceof Collection<?>) {
			result = BasisJavaModelInstanceFactory
					.createModelInstanceCollection(
							(Collection<IModelInstanceElement>) adapted,
							(CollectionType) type);
		}

		else {
			throw new IllegalArgumentException(
					NLS.bind(
							XmlModelInstanceTypeMessages.XmlModelInstanceFactory_UnknownClassOfAdaptee,
							adapted.getClass().getCanonicalName()));
		}

		/* Probably debug the exit of this method. */
		if (LOGGER.isDebugEnabled()) {
			String msg;

			msg = "createModelInstanceElement(Object, Set<Type>) - exit"; //$NON-NLS-1$

			LOGGER.debug(msg);
		}
		// no else.

		return result;
	}

	/**
	 * <p>
	 * Creates an {@link IModelInstanceBoolean} for a given {@link Node} and a
	 * given {@link Type}.
	 * 
	 * @param node
	 *            The {@link Node} that shall be adapted.
	 * @param type
	 *            The {@link Type} of the {@link IModelInstanceBoolean} in the
	 *            {@link IModel}.
	 * @return The created {@link IModelInstanceBoolean}.
	 */
	private IModelInstanceBoolean createModelInstanceBoolean(Node node,
			Type type) {

		IModelInstanceBoolean result;

		/*
		 * Use the java basis types here because the adaptation of a node would
		 * not help. If you adapt a node, cast it to boolean and then to string,
		 * you have to alter the nodes' value to get the right result such as
		 * 'true', 'false' or null in all other cases!
		 */
		if (node == null || node.getTextContent() == null) {
			result = super.createModelInstanceBoolean(null);
		}

		else if (node.getTextContent().trim().equalsIgnoreCase("true")) {
			result = super.createModelInstanceBoolean(true);
		}

		else if (node.getTextContent().trim().equalsIgnoreCase("false")) {
			result = super.createModelInstanceBoolean(false);
		}

		else {
			result = super.createModelInstanceBoolean(null);
		}

		return result;
	}

	/**
	 * <p>
	 * Creates an {@link IModelInstanceElement} for a given {@link Node}.
	 * 
	 * @param node
	 *            The {@link Node} that shall be adapted.
	 * @return The created {@link IModelInstanceElement}.
	 * @throws TypeNotFoundInModelException
	 *             Thrown, if now {@link Type} in the {@link IModel} can be
	 *             found that matches to the given {@link Node}.
	 */
	private IModelInstanceElement createModelInstanceElement(Node node)
			throws TypeNotFoundInModelException {

		IModelInstanceElement result;

		Type type;
		type = this.findTypeOfNode(node);

		result = (IModelInstanceElement) this.createModelInstanceElement(node,
				type);

		return result;
	}

	/**
	 * <p>
	 * Creates an {@link IModelInstanceEnumerationLiteral} for the given
	 * {@link Node} and the given {@link Enumeration}.
	 * </p>
	 * 
	 * @param node
	 *            The {@link Node} for that an
	 *            {@link IModelInstanceEnumerationLiteral} shall be created.
	 * @param enumeration
	 *            The {@link Enumeration} type for that the
	 *            {@link IModelInstanceEnumerationLiteral} shall be created.
	 * @return The created {@link IModelInstanceEnumerationLiteral}.
	 */
	private IModelInstanceEnumerationLiteral createModelInstanceEnumerationLiteral(
			Node node, Enumeration enumeration) {

		IModelInstanceEnumerationLiteral result;

		if (node == null || node.getTextContent() == null) {
			result = super.createModelInstanceEnumerationLiteral(null);
		}

		else {
			EnumerationLiteral literal;
			literal = null;

			/* Try to find a literal that matches to the node's value. */
			for (EnumerationLiteral aLiteral : enumeration.getOwnedLiteral()) {
				if (aLiteral.getName().equalsIgnoreCase(
						node.getTextContent().trim())) {
					literal = aLiteral;
					break;
				}
				// no else.
			}
			// end for.

			result = super.createModelInstanceEnumerationLiteral(literal);
		}

		return result;
	}

	/**
	 * <p>
	 * Creates an {@link IModelInstanceInteger} for a given {@link Node} and a
	 * given {@link Type}.
	 * 
	 * @param node
	 *            The {@link Node} that shall be adapted.
	 * @param type
	 *            The {@link Type} of the {@link IModelInstanceInteger} in the
	 *            {@link IModel}.
	 * @return The created {@link IModelInstanceInteger}.
	 */
	private IModelInstanceInteger createModelInstanceInteger(Node node,
			Type type) {

		IModelInstanceInteger result;

		/*
		 * Use the java basis types here because the adaptation of a node would
		 * not help. If you adapt a node, cast it to integer and then to string,
		 * you have to alter the nodes' value to get the right result such as
		 * '1' except of '1.23', or null in many cases!
		 */
		if (node == null || node.getTextContent() == null) {
			result = super.createModelInstanceInteger(null);
		}

		else {
			Long longValue;
			try {
				longValue = new Double(
						Double.parseDouble(node.getTextContent())).longValue();
			}

			catch (NumberFormatException e) {
				longValue = null;
			}

			result = super.createModelInstanceInteger(longValue);
		}

		return result;
	}

	/**
	 * <p>
	 * Creates an {@link IModelInstanceReal} for a given {@link Node} and a
	 * given {@link Type}.
	 * 
	 * @param node
	 *            The {@link Node} that shall be adapted.
	 * @param type
	 *            The {@link Type} of the {@link IModelInstanceReal} in the
	 *            {@link IModel}.
	 * @return The created {@link IModelInstanceReal}.
	 */
	private IModelInstanceReal createModelInstanceReal(Node node, Type type) {

		IModelInstanceReal result;

		/*
		 * Use the java basis types here because the adaptation of a node would
		 * not help. If you adapt a node, cast it to real and then to string,
		 * you have to alter the nodes' value to get the right result such as
		 * '1' except of '1.0', or null in many cases!
		 */
		if (node == null || node.getTextContent() == null) {
			result = super.createModelInstanceReal(null);
		}

		else {
			Double doubleValue;

			try {
				doubleValue = new Double(Double.parseDouble(node
						.getTextContent()));
			}

			catch (NumberFormatException e) {
				doubleValue = null;
			}

			result = super.createModelInstanceReal(doubleValue);
		}

		return result;
	}

	/**
	 * <p>
	 * Creates an {@link IModelInstanceString} for a given {@link Node} and a
	 * given {@link Type}.
	 * 
	 * @param node
	 *            The {@link Node} that shall be adapted.
	 * @param type
	 *            The {@link Type} of the {@link IModelInstanceString} in the
	 *            {@link IModel}.
	 * @return The created {@link IModelInstanceString}.
	 */
	private IModelInstanceString createModelInstanceString(Node node, Type type) {

		IModelInstanceString result;

		/*
		 * Use the java basis types here because the adaptation of a node would
		 * not help. If you adapt a node, cast it to integer and then to string,
		 * you have to alter the nodes' value to get the right result such as
		 * 'truefalse' except of null!
		 */
		if (node == null || node.getTextContent() == null) {
			result = super.createModelInstanceString(null);
		}

		else {
			result = super.createModelInstanceString(node.getTextContent());
		}

		return result;
	}

	/**
	 * <p>
	 * Creates an {@link XmlModelInstanceObject} for a given {@link Node} and a
	 * given {@link Type}.
	 * 
	 * @param node
	 *            The {@link Node} that shall be adapted.
	 * @param type
	 *            The {@link Type} of the {@link XmlModelInstanceObject} in the
	 *            {@link IModel}.
	 * @return The created {@link XmlModelInstanceObject}.
	 */
	private XmlModelInstanceObject createModelInstanceObject(Node node,
			Type type) {

		XmlModelInstanceObject result;

		result = new XmlModelInstanceObject(node, type, type, this);

		return result;
	}

	private Type findTypeOfNode(Node node) throws TypeNotFoundInModelException {

		Type result;
		result = null;

		/* Collect all parent nodes. */
		List<Node> parents;
		parents = new ArrayList<Node>();

		Node aNode;
		aNode = node;

		while (aNode != null) {

			if (aNode == aNode.getParentNode() || aNode instanceof Document) {
				break;
			}

			else {
				parents.add(aNode);
				aNode = aNode.getParentNode();
			}
		}
		// end while.

		if (parents.size() != 0) {

			/* Try to find the type of the root node. */
			Type rootType;
			rootType = this
					.findTypeOfRootNode(parents.remove(parents.size() - 1));

			if (rootType != null) {

				result = rootType;

				/* Take the next node, try to find its property and its type. */
				while (parents.size() > 0) {

					List<Property> properties;
					properties = result.allProperties();

					aNode = parents.remove(parents.size() - 1);
					result = null;

					for (Property property : properties) {
						if (property.getName().equalsIgnoreCase(
								aNode.getNodeName().trim())) {

							if (property.getType() instanceof CollectionType) {
								result = ((CollectionType) property.getType())
										.getElementType();
							}

							else {
								result = property.getType();
							}

							break;
						}
						// no else.
					}
					// end for.
				}
			}
			// no else (root type not found).
		}
		// no else (no parent node found).

		if (result == null) {
			throw new TypeNotFoundInModelException(
					NLS.bind(
							XmlModelInstanceTypeMessages.XmlModelInstanceFactory_UnknownTypeOfAdaptee,
							node));
		}
		// no else.

		return result;
	}

	/**
	 * <p>
	 * A helper method that searches for the {@link Type} of a given
	 * {@link Node} in the {@link IModel}.
	 * </p>
	 * 
	 * @param node
	 *            The {@link Node} for that a {@link Type} shall be found.
	 * @return The found {@link Type}.
	 */
	private Type findTypeOfRootNode(Node node)
			throws TypeNotFoundInModelException {

		Type result;
		result = null;

		String nodeName;
		nodeName = node.getNodeName();

		List<String> pathName;
		pathName = new ArrayList<String>();
		pathName.add(nodeName);

		/* FIXME Claas: Probably handle the node's name space. */
		try {
			result = this.model.findType(pathName);

			/* FIXME Lars: Try to handle the namespace */
			if (result == null) {
				String typeName = pathName.get(pathName.size() - 1);
				while (typeName.contains(":")) {
					typeName = pathName.remove(pathName.size() - 1);
					typeName = typeName.substring(typeName.indexOf(":") + 1);

					pathName.add(typeName);
					result = this.model.findType(pathName);
					if (result != null) {
						break;
					}
				}
			}

			/*
			 * FIXME Claas: This is a very hacky dependency to the XSD
			 * meta-model and should be fixed in the Meta-model instead.
			 */
			/*
			 * change the first character into an upper case character and try
			 * again.
			 */
			if (result == null) {

				String typeName = pathName.remove(pathName.size() - 1);
				pathName.add(typeName.substring(0, 1).toUpperCase()
						+ typeName.substring(1, typeName.length()));
				result = this.model.findType(pathName);
			}
			// no else.

			/*
			 * FIXME Claas: This is a very hacky dependency to the XSD
			 * meta-model and should be fixed in the Meta-model instead.
			 */
			/* Add a 'Type' to the node name and try again. */
			if (result == null) {

				pathName.add(pathName.remove(pathName.size() - 1) + "Type");
				result = this.model.findType(pathName);
			}
			// no else.
		}
		// end try.

		catch (ModelAccessException e) {

			throw new TypeNotFoundInModelException(
					NLS.bind(
							XmlModelInstanceTypeMessages.XmlModelInstanceFactory_UnknownTypeOfAdaptee,
							node), e);
		}
		// end catch.

		return result;
	}
}