/*
Copyright (C) 2009 by Claas Wilke (claaswilke@gmx.net)

This file is part of the Model Bus Plug-in of Dresden OCL2 for Eclipse.

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
package org.dresdenocl.modelinstancetype.types.base;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.log4j.Logger;
import org.eclipse.osgi.util.NLS;

import org.dresdenocl.essentialocl.EssentialOclPlugin;
import org.dresdenocl.modelinstancetype.ModelInstanceTypePlugin;
import org.dresdenocl.modelinstancetype.exception.AsTypeCastException;
import org.dresdenocl.modelinstancetype.internal.ModelInstanceMessages;
import org.dresdenocl.modelinstancetype.types.IModelInstanceElement;
import org.dresdenocl.modelinstancetype.types.IModelInstanceReal;
import org.dresdenocl.pivotmodel.PrimitiveType;
import org.dresdenocl.pivotmodel.PrimitiveTypeKind;
import org.dresdenocl.pivotmodel.Type;

/**
 * <p>
 * Represents an adaptation for {@link Float}s of a {@link JavaModelInstance}.
 * </p>
 * 
 * <p>
 * This type is located in the ModelBus plug-in because the standard library and
 * the Java model instance type plug-in both require such an implementation but
 * are not allowed to know each other.
 * </p>
 * 
 * @author Claas Wilke
 */
public class JavaModelInstanceReal extends AbstractModelInstanceReal implements
		IModelInstanceReal {
	
	
	/**
	 * this is how many digits after floating point
	 * are used. It's necessary to limit the precision
	 * to avoid epsilon-caused errors when comparing floating point values
	 */
	private static final int DEFAULT_DECIMAL_SCALE = 5;

	/** The {@link Logger} for this class. */
	private static final Logger LOGGER =
			ModelInstanceTypePlugin.getLogger(JavaModelInstanceReal.class);

	/** The adapted {@link Number} of this {@link JavaModelInstanceReal}. */
	private Number myNumber;

	/**
	 * <p>
	 * Creates a new {@link JavaModelInstanceReal}.
	 * </p>
	 * 
	 * @param number
	 *          The {@link Number} that shall be adapted by this
	 *          {@link JavaModelInstanceReal}.
	 */
	protected JavaModelInstanceReal(Number number) {

		/* Eventually debug the entry of this method. */
		if (LOGGER.isDebugEnabled()) {
			String msg;

			msg = "JavaModelInstanceReal("; //$NON-NLS-1$
			msg += "number = " + number; //$NON-NLS-1$
			msg += ")"; //$NON-NLS-1$

			LOGGER.debug(msg);
		}
		// no else.

		this.myNumber = number;

		/* Initialize the type. */
		this.myType =
				EssentialOclPlugin.getOclLibraryProvider().getOclLibrary().getOclReal();

		/* Eventually debug the exit of this method. */
		if (LOGGER.isDebugEnabled()) {
			String msg;

			msg = "JavaModelInstanceReal(Number) - exit"; //$NON-NLS-1$

			LOGGER.debug(msg);
		}
		// no else.
	}

	/*
	 * (non-Javadoc)
	 * @see org.dresdenocl.modelinstancetype.types.IModelInstanceElement
	 * #asType(org.dresdenocl.pivotmodel.Type)
	 */
	public IModelInstanceElement asType(Type type) throws AsTypeCastException {

		if (type == null) {
			throw new IllegalArgumentException("Parameter 'type' must not be null.");
		}
		// no else.

		IModelInstanceElement result;

		/* By default the result is null. */
		result = null;

		/* Reals can only be casted to primitive types. */
		if (type instanceof PrimitiveType) {
			PrimitiveType primitiveType;
			primitiveType = (PrimitiveType) type;

			/* Check the given PrimitiveTypeKind. */
			if (primitiveType.getKind().equals(PrimitiveTypeKind.INTEGER)) {

				/* Create a new integer to avoid side effects. */
				result = new JavaModelInstanceInteger(this.myNumber.longValue());
			}

			else if (primitiveType.getKind().equals(PrimitiveTypeKind.REAL)) {

				/* Each integer is also a real. */
				result = new JavaModelInstanceReal(this.myNumber);
			}

			else if (primitiveType.getKind().equals(PrimitiveTypeKind.STRING)) {

				if (this.myNumber == null) {
					result = new JavaModelInstanceString(null);
				}

				else {
					result = new JavaModelInstanceString(this.myNumber.toString());
				}
			}
			// no else.
		}
		// no else.

		/* Probably throw an AsTypeCastException. */
		if (result == null) {
			String msg;

			msg = ModelInstanceMessages.IModelInstanceElement_CannotCast;
			msg = NLS.bind(msg, this.getName(), type.getName());

			throw new AsTypeCastException(msg);
		}
		// no else.

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see org.dresdenocl.modelinstancetype.types.IModelInstanceElement
	 * #deepCopy()
	 */
	public IModelInstanceElement copyForAtPre() {

		return new JavaModelInstanceReal(this.myNumber);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.dresdenocl.modelbus.modelinstance.IModelInstanceReal#getReal()
	 */
	public Double getDouble()
	{
		return this.getDouble(DEFAULT_DECIMAL_SCALE);
	}

	
	public Double getDouble(int scale) {

		Double result;

		/* Avoid null pointer exceptions. */
		if (this.myNumber != null) {
			/* 
			 * when calling myNumber.getDouble()
			 * the number is rounded oddly (e.g. 2.8 --> 2.7777777779) 
			 */
			BigDecimal dec = BigDecimal.valueOf(this.myNumber.doubleValue());
			dec = dec.setScale(scale, RoundingMode.HALF_UP);
			
			result = Double.valueOf(dec.doubleValue());
			
		}

		else {
			result = null;
		}

		return result;
	}
}