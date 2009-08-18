/*
Copyright (C) 2009 by Claas Wilke (claaswilke@gmx.net)

This file is part of the Java Model Instance Plug-in of Dresden OCL2 for Eclipse.

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
package tudresden.ocl20.pivot.modelinstancetype.java.internal.modelinstance;

import java.util.Set;

import org.apache.log4j.Logger;

import tudresden.ocl20.pivot.modelbus.IModel;
import tudresden.ocl20.pivot.modelbus.base.AbstractModelObject;
import tudresden.ocl20.pivot.modelbus.modelinstance.IModelInstanceEnumerationLiteral;
import tudresden.ocl20.pivot.modelinstancetype.java.JavaModelInstanceTypePlugin;
import tudresden.ocl20.pivot.pivotmodel.Enumeration;
import tudresden.ocl20.pivot.pivotmodel.Type;

/**
 * <p>
 * An adapter {@link Class} for {@link JavaModelInstanceEnumerationLiteral}s of
 * {@link JavaModelInstance}s.
 * </p>
 * 
 * @author Claas Wilke
 */
public class JavaModelInstanceEnumerationLiteral extends AbstractModelObject
		implements IModelInstanceEnumerationLiteral {

	/** The {@link Logger} for this class. */
	private static final Logger LOGGER =
			JavaModelInstanceTypePlugin
					.getLogger(JavaModelInstanceEnumerationLiteral.class);

	/**
	 * <p>
	 * The adapted EnumerationLiteral.
	 */
	private Enum<?> myLiteral;

	/**
	 * <p>
	 * Creates a new {@link JavaModelInstanceEnumerationLiteral} for a given
	 * {@link Enum}.
	 * </p>
	 * 
	 * @param literal
	 *          The {@link Enum} that shall be adapted.
	 * @param types
	 *          The {@link Type}s that represent the {@link Enumeration} in the
	 *          {@link IModel}. <strong>An
	 *          {@link JavaModelInstanceEnumerationLiteral} can only have one
	 *          {@link Type}.</strong>
	 */
	public JavaModelInstanceEnumerationLiteral(Enum<?> literal, Set<Type> types) {

		/* Eventually debug the entry of this method. */
		if (LOGGER.isDebugEnabled()) {
			String msg;

			msg = "JavaModelInstanceEnumerationLiteral("; //$NON-NLS-1$
			msg += "literal = " + literal; //$NON-NLS-1$
			msg += ", types = " + types; //$NON-NLS-1$
			msg += ")"; //$NON-NLS-1$

			LOGGER.debug(msg);
		}
		// no else.

		this.myLiteral = literal;
		this.myTypes = types;

		/* Eventually debug the exit of this method. */
		if (LOGGER.isDebugEnabled()) {
			String msg;

			msg = "JavaModelInstanceEnumerationLiteral(Enum<?>, "; //$NON-NLS-1$
			msg += "Set<Type>) - exit"; //$NON-NLS-1$

			LOGGER.debug(msg);
		}
		// no else.
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * tudresden.ocl20.pivot.modelbus.modelinstance.IModelInstanceEnumerationLiteral
	 * #getLiteral()
	 */
	public Enum<?> getLiteral() {

		return this.myLiteral;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {

		String result;

		result = JavaModelInstanceEnumerationLiteral.class.getSimpleName();
		result += "[";
		result += this.myLiteral;
		result += "]";

		return result;
	}
}