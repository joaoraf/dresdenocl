/*
 * Copyright (C) 2008-2009 by Michael Thiele & Claas Wilke (claaswilke@gmx.net)
 * This file is part of the UML2 Meta Model of Dresden OCL2 for Eclipse. Dresden
 * OCL2 for Eclipse is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version. Dresden OCL2 for Eclipse is distributed in the
 * hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See
 * the GNU Lesser General Public License for more details. You should have
 * received a copy of the GNU Lesser General Public License along with Dresden
 * OCL2 for Eclipse. If not, see <http://www.gnu.org/licenses/>.
 */
package org.dresdenocl.metamodels.uml2.internal.model;

import org.apache.log4j.Logger;
import org.eclipse.osgi.util.NLS;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.TypedElement;

import org.dresdenocl.essentialocl.EssentialOclPlugin;
import org.dresdenocl.metamodels.uml2.UML2MetamodelPlugin;
import org.dresdenocl.pivotmodel.Property;
import org.dresdenocl.pivotmodel.Type;
import org.dresdenocl.pivotmodel.base.AbstractProperty;

/**
 * <p>
 * An implementation of the Pivot Model {@link Property} concept for UML2.
 * </p>
 * 
 * @author Michael Thiele
 * 
 * @generated NOT
 */
public class UML2Association extends AbstractProperty implements Property {

	/**
	 * <p>
	 * The Logger for this class.
	 * </p>
	 * 
	 * @generated NOT
	 */
	private static final Logger LOGGER = UML2MetamodelPlugin
			.getLogger(UML2Association.class);

	/**
	 * <p>
	 * The adapted org.eclipse.uml2.uml.Property data type.
	 * </p>
	 * 
	 * @generated
	 */
	private org.eclipse.uml2.uml.Association dslProperty;

	/**
	 * <p>
	 * The {@link UML2AdapterFactory} used to create nested elements.
	 * </p>
	 */
	private UML2AdapterFactory factory;

	/**
	 * <p>
	 * Creates a new <code>UML2Association</code> instance.
	 * </p>
	 * 
	 * @param dslProperty
	 *            the {@link org.eclipse.uml2.uml.Association} that is adopted
	 *            by this class
	 * @param factory
	 *            The {@link UML2AdapterFactory} used to create nested elements.
	 * 
	 * @generated NOT
	 */
	public UML2Association(org.eclipse.uml2.uml.Association dslProperty,
			UML2AdapterFactory factory) {

		if (LOGGER.isDebugEnabled()) {
			LOGGER
					.debug("UML2Association(dslProperty = " + dslProperty + "factory = " + factory + ") - enter"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		// initialize
		this.dslProperty = dslProperty;
		this.factory = factory;

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("UML2Association() - exit"); //$NON-NLS-1$
		}
	}

	/**
	 * @see org.dresdenocl.pivotmodel.base.AbstractProperty#getName()
	 * 
	 * @generated NOT
	 */
	@Override
	public String getName() {

		return this.dslProperty.getName();
	}

	/**
	 * @see org.dresdenocl.pivotmodel.base.AbstractProperty#getType()
	 * 
	 * @generated NOT
	 */
	@Override
	public Type getType() {

		Type result;
		Type elementType;

		elementType = this.factory.createType(dslProperty.getEndTypes().get(0));

		/* Probably adapt type into a collection. */
		if (this.dslProperty.getMemberEnds().get(0).isMultivalued()) {

			if (this.dslProperty.getMemberEnds().get(0).isOrdered()) {

				/* OrderedSet. */
				if (this.dslProperty.getMemberEnds().get(0).isUnique()) {
					result = EssentialOclPlugin.getOclLibraryProvider()
							.getOclLibrary().getOrderedSetType(elementType);
				}

				/* Sequence. */
				else {
					result = EssentialOclPlugin.getOclLibraryProvider()
							.getOclLibrary().getSequenceType(elementType);
				}
			}

			else {
				/* Set. */
				if (this.dslProperty.getMemberEnds().get(0).isUnique()) {
					result = EssentialOclPlugin.getOclLibraryProvider()
							.getOclLibrary().getSetType(elementType);
				}

				/* Bag. */
				else {
					result = EssentialOclPlugin.getOclLibraryProvider()
							.getOclLibrary().getBagType(elementType);
				}
			}
		}

		else {
			result = elementType;
		}

		return result;
	}

	/**
	 * @see org.dresdenocl.pivotmodel.base.AbstractProperty#getOwningType()
	 * 
	 * @generated NOT
	 */
	@Override
	public Type getOwningType() {

		Type result;

		Element owner;

		owner = this.dslProperty.getOwner();

		/* Adapt the owner depending on its type. */
		if (owner instanceof TypedElement) {
			TypedElement aTypedElement;

			aTypedElement = (TypedElement) owner;

			result = this.factory.createType(aTypedElement.getType());
		}

		else if (owner instanceof org.eclipse.uml2.uml.Class) {
			org.eclipse.uml2.uml.Class aClass;

			aClass = (org.eclipse.uml2.uml.Class) owner;

			result = this.factory.createType(aClass);
		}

		else {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info(NLS.bind(UML2ModelMessages.UML2_GetOwningType, this
						.toString()));
			}
			// no else.

			result = null;
		}

		return result;
	}
}