/*
 * Copyright (C) 2010 by Claas Wilke (claas.wilke@tu-dresden.de)
 *
 * This file is part of the OCL 2 Java Code Generator of Dresden OCL.
 *
 * Dresden OCL2 is free software: you can redistribute it and/or modify 
 * it under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * Dresden OCL is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License 
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along 
 * with Dresden OCL. If not, see <http://www.gnu.org/licenses/>.
 */
package org.dresdenocl.tools.codegen.ocl2java.test.aspectj;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import org.dresdenocl.tools.codegen.ocl2java.test.aspectj.constraintkinds.AllConstraintKindTests;
import org.dresdenocl.tools.codegen.ocl2java.test.aspectj.expressions.AllExpressionTests;
import org.dresdenocl.tools.codegen.ocl2java.test.aspectj.standardlibrary.AllStandardLibraryTests;

/**
 * <p>
 * Provides a jUnit Test Suite containing all standard library tests of the OCL
 * 2 Java Code transformer ({@link Ocl22JavaPlugin}).
 * </p>
 * 
 * @author Claas Wilke
 * 
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ AllConstraintKindTests.class, AllExpressionTests.class,
		AllStandardLibraryTests.class })
public class AllAspectJTests {
	/*
	 * This class remains completely empty, being used only as a holder for the
	 * above annotations.
	 */
}
