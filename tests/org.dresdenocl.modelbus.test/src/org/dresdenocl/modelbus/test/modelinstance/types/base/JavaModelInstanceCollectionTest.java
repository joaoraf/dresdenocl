/*
Copyright (C) 2009 by Claas Wilke (info@claaswilke.de)

This file is part of the Java Model Instance Type Test Suite of Dresden 
OCL2 for Eclipse.

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
package org.dresdenocl.modelbus.test.modelinstance.types.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import org.dresdenocl.essentialocl.EssentialOclPlugin;
import org.dresdenocl.essentialocl.expressions.CollectionKind;
import org.dresdenocl.essentialocl.types.CollectionType;
import org.dresdenocl.model.ModelAccessException;
import org.dresdenocl.modelinstancetype.exception.AsTypeCastException;
import org.dresdenocl.modelinstancetype.exception.CopyForAtPreException;
import org.dresdenocl.modelinstancetype.types.IModelInstanceCollection;
import org.dresdenocl.modelinstancetype.types.IModelInstanceElement;
import org.dresdenocl.modelinstancetype.types.IModelInstanceString;
import org.dresdenocl.modelinstancetype.types.base.BasisJavaModelInstanceFactory;
import org.dresdenocl.modelinstancetype.types.base.JavaModelInstanceCollection;
import org.dresdenocl.pivotmodel.Type;

/**
 * <p>
 * Contains test cases to test the class {@link JavaModelInstanceCollection}.
 * </p>
 * 
 * @author Claas Wilke
 */
public class JavaModelInstanceCollectionTest {

	/** A {@link Type} used in this test class. */
	private static CollectionType typeBag;

	/** A {@link Type} used in this test class. */
	private static CollectionType typeOrderedSet;

	/** A {@link Type} used in this test class. */
	private static CollectionType typeSequence;

	/** A {@link Type} used in this test class. */
	private static CollectionType typeSet;

	/**
	 * <p>
	 * Loads some objects required during the tests.
	 * </p>
	 * 
	 * @throws ModelAccessException
	 */
	@BeforeClass
	public static void setUp() throws ModelAccessException {

		/* Get a collection type from the model. */
		typeBag =
				EssentialOclPlugin.getOclLibraryProvider().getOclLibrary().getBagType(
						EssentialOclPlugin.getOclLibraryProvider().getOclLibrary()
								.getOclAny());

		/* Get a collection type from the model. */
		typeOrderedSet =
				EssentialOclPlugin.getOclLibraryProvider().getOclLibrary()
						.getOrderedSetType(
								EssentialOclPlugin.getOclLibraryProvider().getOclLibrary()
										.getOclAny());

		/* Get a collection type from the model. */
		typeSequence =
				EssentialOclPlugin.getOclLibraryProvider().getOclLibrary()
						.getSequenceType(
								EssentialOclPlugin.getOclLibraryProvider().getOclLibrary()
										.getOclAny());

		/* Get a collection type from the model. */
		typeSet =
				EssentialOclPlugin.getOclLibraryProvider().getOclLibrary().getSetType(
						EssentialOclPlugin.getOclLibraryProvider().getOclLibrary()
								.getOclAny());
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#asType(Type)}.
	 * </p>
	 * 
	 * @throws AsTypeCastException
	 */
	@Test
	public void testAsType01() throws AsTypeCastException {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeBag);

		IModelInstanceElement castedElement;
		castedElement = modelInstanceCollection01.asType(typeBag);

		assertTrue(castedElement instanceof IModelInstanceCollection<?>);
		assertNotNull(castedElement.getType());

		assertTrue(castedElement.getType() instanceof CollectionType);
		assertEquals(CollectionKind.BAG, ((CollectionType) castedElement.getType())
				.getKind());
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#asType(Type)}.
	 * </p>
	 * 
	 * @throws AsTypeCastException
	 */
	@Test
	public void testAsType02() throws AsTypeCastException {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeBag);

		IModelInstanceElement castedElement;
		castedElement = modelInstanceCollection01.asType(typeOrderedSet);

		assertTrue(castedElement instanceof IModelInstanceCollection<?>);
		assertNotNull(castedElement.getType());

		assertTrue(castedElement.getType() instanceof CollectionType);
		assertEquals(CollectionKind.ORDERED_SET, ((CollectionType) castedElement
				.getType()).getKind());
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#asType(Type)}.
	 * </p>
	 * 
	 * @throws AsTypeCastException
	 */
	@Test
	public void testAsType03() throws AsTypeCastException {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeBag);

		IModelInstanceElement castedElement;
		castedElement = modelInstanceCollection01.asType(typeSequence);

		assertTrue(castedElement instanceof IModelInstanceCollection<?>);
		assertNotNull(castedElement.getType());

		assertTrue(castedElement.getType() instanceof CollectionType);
		assertEquals(CollectionKind.SEQUENCE, ((CollectionType) castedElement
				.getType()).getKind());
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#asType(Type)}.
	 * </p>
	 * 
	 * @throws AsTypeCastException
	 */
	@Test
	public void testAsType04() throws AsTypeCastException {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeBag);

		IModelInstanceElement castedElement;
		castedElement = modelInstanceCollection01.asType(typeSet);

		assertTrue(castedElement instanceof IModelInstanceCollection<?>);
		assertNotNull(castedElement.getType());

		assertTrue(castedElement.getType() instanceof CollectionType);
		assertEquals(CollectionKind.SET, ((CollectionType) castedElement.getType())
				.getKind());
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#asType(Type)}.
	 * </p>
	 * 
	 * @throws AsTypeCastException
	 */
	@Test
	public void testAsType05() throws AsTypeCastException {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeOrderedSet);

		IModelInstanceElement castedElement;
		castedElement = modelInstanceCollection01.asType(typeBag);

		assertTrue(castedElement instanceof IModelInstanceCollection<?>);
		assertNotNull(castedElement.getType());

		assertTrue(castedElement.getType() instanceof CollectionType);
		assertEquals(CollectionKind.BAG, ((CollectionType) castedElement.getType())
				.getKind());
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#asType(Type)}.
	 * </p>
	 * 
	 * @throws AsTypeCastException
	 */
	@Test
	public void testAsType06() throws AsTypeCastException {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeOrderedSet);

		IModelInstanceElement castedElement;
		castedElement = modelInstanceCollection01.asType(typeOrderedSet);

		assertTrue(castedElement instanceof IModelInstanceCollection<?>);
		assertNotNull(castedElement.getType());

		assertTrue(castedElement.getType() instanceof CollectionType);
		assertEquals(CollectionKind.ORDERED_SET, ((CollectionType) castedElement
				.getType()).getKind());
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#asType(Type)}.
	 * </p>
	 * 
	 * @throws AsTypeCastException
	 */
	@Test
	public void testAsType07() throws AsTypeCastException {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeOrderedSet);

		IModelInstanceElement castedElement;
		castedElement = modelInstanceCollection01.asType(typeSequence);

		assertTrue(castedElement instanceof IModelInstanceCollection<?>);
		assertNotNull(castedElement.getType());

		assertTrue(castedElement.getType() instanceof CollectionType);
		assertEquals(CollectionKind.SEQUENCE, ((CollectionType) castedElement
				.getType()).getKind());
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#asType(Type)}.
	 * </p>
	 * 
	 * @throws AsTypeCastException
	 */
	@Test
	public void testAsType08() throws AsTypeCastException {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeOrderedSet);

		IModelInstanceElement castedElement;
		castedElement = modelInstanceCollection01.asType(typeSet);

		assertTrue(castedElement instanceof IModelInstanceCollection<?>);
		assertNotNull(castedElement.getType());

		assertTrue(castedElement.getType() instanceof CollectionType);
		assertEquals(CollectionKind.SET, ((CollectionType) castedElement.getType())
				.getKind());
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#asType(Type)}.
	 * </p>
	 * 
	 * @throws AsTypeCastException
	 */
	@Test
	public void testAsType09() throws AsTypeCastException {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeSequence);

		IModelInstanceElement castedElement;
		castedElement = modelInstanceCollection01.asType(typeBag);

		assertTrue(castedElement instanceof IModelInstanceCollection<?>);
		assertNotNull(castedElement.getType());

		assertTrue(castedElement.getType() instanceof CollectionType);
		assertEquals(CollectionKind.BAG, ((CollectionType) castedElement.getType())
				.getKind());
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#asType(Type)}.
	 * </p>
	 * 
	 * @throws AsTypeCastException
	 */
	@Test
	public void testAsType10() throws AsTypeCastException {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeSequence);

		IModelInstanceElement castedElement;
		castedElement = modelInstanceCollection01.asType(typeOrderedSet);

		assertTrue(castedElement instanceof IModelInstanceCollection<?>);
		assertNotNull(castedElement.getType());

		assertTrue(castedElement.getType() instanceof CollectionType);
		assertEquals(CollectionKind.ORDERED_SET, ((CollectionType) castedElement
				.getType()).getKind());
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#asType(Type)}.
	 * </p>
	 * 
	 * @throws AsTypeCastException
	 */
	@Test
	public void testAsType11() throws AsTypeCastException {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeSequence);

		IModelInstanceElement castedElement;
		castedElement = modelInstanceCollection01.asType(typeSequence);

		assertTrue(castedElement instanceof IModelInstanceCollection<?>);
		assertNotNull(castedElement.getType());

		assertTrue(castedElement.getType() instanceof CollectionType);
		assertEquals(CollectionKind.SEQUENCE, ((CollectionType) castedElement
				.getType()).getKind());
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#asType(Type)}.
	 * </p>
	 * 
	 * @throws AsTypeCastException
	 */
	@Test
	public void testAsType12() throws AsTypeCastException {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeSequence);

		IModelInstanceElement castedElement;
		castedElement = modelInstanceCollection01.asType(typeSet);

		assertTrue(castedElement instanceof IModelInstanceCollection<?>);
		assertNotNull(castedElement.getType());

		assertTrue(castedElement.getType() instanceof CollectionType);
		assertEquals(CollectionKind.SET, ((CollectionType) castedElement.getType())
				.getKind());
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#asType(Type)}.
	 * </p>
	 * 
	 * @throws AsTypeCastException
	 */
	@Test
	public void testAsType13() throws AsTypeCastException {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeSet);

		IModelInstanceElement castedElement;
		castedElement = modelInstanceCollection01.asType(typeBag);

		assertTrue(castedElement instanceof IModelInstanceCollection<?>);
		assertNotNull(castedElement.getType());

		assertTrue(castedElement.getType() instanceof CollectionType);
		assertEquals(CollectionKind.BAG, ((CollectionType) castedElement.getType())
				.getKind());
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#asType(Type)}.
	 * </p>
	 * 
	 * @throws AsTypeCastException
	 */
	@Test
	public void testAsType14() throws AsTypeCastException {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeSet);

		IModelInstanceElement castedElement;
		castedElement = modelInstanceCollection01.asType(typeOrderedSet);

		assertTrue(castedElement instanceof IModelInstanceCollection<?>);
		assertNotNull(castedElement.getType());

		assertTrue(castedElement.getType() instanceof CollectionType);
		assertEquals(CollectionKind.ORDERED_SET, ((CollectionType) castedElement
				.getType()).getKind());
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#asType(Type)}.
	 * </p>
	 * 
	 * @throws AsTypeCastException
	 */
	@Test
	public void testAsType15() throws AsTypeCastException {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeSet);

		IModelInstanceElement castedElement;
		castedElement = modelInstanceCollection01.asType(typeSequence);

		assertTrue(castedElement instanceof IModelInstanceCollection<?>);
		assertNotNull(castedElement.getType());

		assertTrue(castedElement.getType() instanceof CollectionType);
		assertEquals(CollectionKind.SEQUENCE, ((CollectionType) castedElement
				.getType()).getKind());
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#asType(Type)}.
	 * </p>
	 * 
	 * @throws AsTypeCastException
	 */
	@Test
	public void testAsType16() throws AsTypeCastException {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeSet);

		IModelInstanceElement castedElement;
		castedElement = modelInstanceCollection01.asType(typeSet);

		assertTrue(castedElement instanceof IModelInstanceCollection<?>);
		assertNotNull(castedElement.getType());

		assertTrue(castedElement.getType() instanceof CollectionType);
		assertEquals(CollectionKind.SET, ((CollectionType) castedElement.getType())
				.getKind());
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#copyForAtPre()}.
	 * </p>
	 * 
	 * @throws CopyForAtPreException
	 */
	@Test
	public void testCopyForAtPre01() throws CopyForAtPreException {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeBag);

		IModelInstanceElement copy;
		copy = modelInstanceCollection01.copyForAtPre();

		assertNotNull(copy);
		assertEquals(modelInstanceCollection01, copy);
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#copyForAtPre()}.
	 * </p>
	 * 
	 * @throws CopyForAtPreException
	 */
	@Test
	public void testCopyForAtPre02() throws CopyForAtPreException {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeOrderedSet);

		IModelInstanceElement copy;
		copy = modelInstanceCollection01.copyForAtPre();

		assertNotNull(copy);
		assertEquals(modelInstanceCollection01, copy);
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#copyForAtPre()}.
	 * </p>
	 * 
	 * @throws CopyForAtPreException
	 */
	@Test
	public void testCopyForAtPre03() throws CopyForAtPreException {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeSequence);

		IModelInstanceElement copy;
		copy = modelInstanceCollection01.copyForAtPre();

		assertNotNull(copy);
		assertEquals(modelInstanceCollection01, copy);
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#copyForAtPre()}.
	 * </p>
	 * 
	 * @throws CopyForAtPreException
	 */
	@Test
	public void testCopyForAtPre04() throws CopyForAtPreException {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeSet);

		IModelInstanceElement copy;
		copy = modelInstanceCollection01.copyForAtPre();

		assertNotNull(copy);
		assertEquals(modelInstanceCollection01, copy);
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#equals(Object)}.
	 * </p>
	 */
	@Test
	public void testEquals01() {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeBag);

		List<IModelInstanceString> elements02;
		elements02 = new ArrayList<IModelInstanceString>();

		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection02;
		modelInstanceCollection02 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements02,
						typeBag);

		assertTrue(modelInstanceCollection01.equals(modelInstanceCollection02));
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#equals(Object)}.
	 * </p>
	 */
	@Test
	public void testEquals02() {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeBag);

		List<IModelInstanceString> elements02;
		elements02 = new ArrayList<IModelInstanceString>();

		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection02;
		modelInstanceCollection02 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements02,
						typeOrderedSet);

		assertFalse(modelInstanceCollection01.equals(modelInstanceCollection02));
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#equals(Object)}.
	 * </p>
	 */
	@Test
	public void testEquals03() {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeBag);

		List<IModelInstanceString> elements02;
		elements02 = new ArrayList<IModelInstanceString>();

		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection02;
		modelInstanceCollection02 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements02,
						typeSequence);

		assertFalse(modelInstanceCollection01.equals(modelInstanceCollection02));
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#equals(Object)}.
	 * </p>
	 */
	@Test
	public void testEquals04() {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeBag);

		List<IModelInstanceString> elements02;
		elements02 = new ArrayList<IModelInstanceString>();

		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection02;
		modelInstanceCollection02 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements02,
						typeSet);

		assertFalse(modelInstanceCollection01.equals(modelInstanceCollection02));
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#equals(Object)}.
	 * </p>
	 */
	@Test
	public void testEquals05() {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeBag);

		List<IModelInstanceString> elements02;
		elements02 = new ArrayList<IModelInstanceString>();

		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("four"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection02;
		modelInstanceCollection02 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements02,
						typeBag);

		assertFalse(modelInstanceCollection01.equals(modelInstanceCollection02));
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#equals(Object)}.
	 * </p>
	 */
	@Test
	public void testEquals06() {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeBag);

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection02;
		modelInstanceCollection02 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(null,
						typeBag);

		assertFalse(modelInstanceCollection01.equals(modelInstanceCollection02));
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#equals(Object)}.
	 * </p>
	 */
	@Test
	public void testEquals07() {

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(null,
						typeBag);

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection02;
		modelInstanceCollection02 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(null,
						typeBag);

		assertFalse(modelInstanceCollection01.equals(modelInstanceCollection02));
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#equals(Object)}.
	 * </p>
	 */
	@Test
	public void testEquals08() {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeOrderedSet);

		List<IModelInstanceString> elements02;
		elements02 = new ArrayList<IModelInstanceString>();

		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection02;
		modelInstanceCollection02 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements02,
						typeOrderedSet);

		assertTrue(modelInstanceCollection01.equals(modelInstanceCollection02));
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#equals(Object)}.
	 * </p>
	 */
	@Test
	public void testEquals09() {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeOrderedSet);

		List<IModelInstanceString> elements02;
		elements02 = new ArrayList<IModelInstanceString>();

		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection02;
		modelInstanceCollection02 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements02,
						typeSequence);

		assertFalse(modelInstanceCollection01.equals(modelInstanceCollection02));
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#equals(Object)}.
	 * </p>
	 */
	@Test
	public void testEquals10() {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeOrderedSet);

		List<IModelInstanceString> elements02;
		elements02 = new ArrayList<IModelInstanceString>();

		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection02;
		modelInstanceCollection02 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements02,
						typeSet);

		assertFalse(modelInstanceCollection01.equals(modelInstanceCollection02));
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#equals(Object)}.
	 * </p>
	 */
	@Test
	public void testEquals11() {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeOrderedSet);

		List<IModelInstanceString> elements02;
		elements02 = new ArrayList<IModelInstanceString>();

		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("four"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection02;
		modelInstanceCollection02 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements02,
						typeOrderedSet);

		assertFalse(modelInstanceCollection01.equals(modelInstanceCollection02));
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#equals(Object)}.
	 * </p>
	 */
	@Test
	public void testEquals12() {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeOrderedSet);

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection02;
		modelInstanceCollection02 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(null,
						typeSet);

		assertFalse(modelInstanceCollection01.equals(modelInstanceCollection02));
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#equals(Object)}.
	 * </p>
	 */
	@Test
	public void testEquals13() {

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(null,
						typeOrderedSet);

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection02;
		modelInstanceCollection02 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(null,
						typeOrderedSet);

		assertFalse(modelInstanceCollection01.equals(modelInstanceCollection02));
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#equals(Object)}.
	 * </p>
	 */
	@Test
	public void testEquals14() {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeSequence);

		List<IModelInstanceString> elements02;
		elements02 = new ArrayList<IModelInstanceString>();

		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection02;
		modelInstanceCollection02 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements02,
						typeSequence);

		assertTrue(modelInstanceCollection01.equals(modelInstanceCollection02));
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#equals(Object)}.
	 * </p>
	 */
	@Test
	public void testEquals15() {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeSequence);

		List<IModelInstanceString> elements02;
		elements02 = new ArrayList<IModelInstanceString>();

		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection02;
		modelInstanceCollection02 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements02,
						typeSet);

		assertFalse(modelInstanceCollection01.equals(modelInstanceCollection02));
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#equals(Object)}.
	 * </p>
	 */
	@Test
	public void testEquals16() {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeSequence);

		List<IModelInstanceString> elements02;
		elements02 = new ArrayList<IModelInstanceString>();

		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("four"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection02;
		modelInstanceCollection02 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements02,
						typeSequence);

		assertFalse(modelInstanceCollection01.equals(modelInstanceCollection02));
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#equals(Object)}.
	 * </p>
	 */
	@Test
	public void testEquals17() {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeSequence);

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection02;
		modelInstanceCollection02 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(null,
						typeSequence);

		assertFalse(modelInstanceCollection01.equals(modelInstanceCollection02));
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#equals(Object)}.
	 * </p>
	 */
	@Test
	public void testEquals18() {

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(null,
						typeSequence);

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection02;
		modelInstanceCollection02 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(null,
						typeSequence);

		assertFalse(modelInstanceCollection01.equals(modelInstanceCollection02));
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#equals(Object)}.
	 * </p>
	 */
	@Test
	public void testEquals19() {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeSet);

		List<IModelInstanceString> elements02;
		elements02 = new ArrayList<IModelInstanceString>();

		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection02;
		modelInstanceCollection02 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements02,
						typeSet);

		assertTrue(modelInstanceCollection01.equals(modelInstanceCollection02));
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#equals(Object)}.
	 * </p>
	 */
	@Test
	public void testEquals20() {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeSet);

		List<IModelInstanceString> elements02;
		elements02 = new ArrayList<IModelInstanceString>();

		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("four"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection02;
		modelInstanceCollection02 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements02,
						typeSet);

		assertFalse(modelInstanceCollection01.equals(modelInstanceCollection02));
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#equals(Object)}.
	 * </p>
	 */
	@Test
	public void testEquals21() {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeSet);

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection02;
		modelInstanceCollection02 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(null,
						typeSet);

		assertFalse(modelInstanceCollection01.equals(modelInstanceCollection02));
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#equals(Object)}.
	 * </p>
	 */
	@Test
	public void testEquals22() {

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(null,
						typeSet);

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection02;
		modelInstanceCollection02 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(null,
						typeSet);

		assertFalse(modelInstanceCollection01.equals(modelInstanceCollection02));
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#equals(Object)}.
	 * </p>
	 */
	@Test
	public void testEquals23() {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeBag);

		List<IModelInstanceString> elements02;
		elements02 = new ArrayList<IModelInstanceString>();

		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));
		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection02;
		modelInstanceCollection02 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements02,
						typeBag);

		assertTrue(modelInstanceCollection01.equals(modelInstanceCollection02));
	}
	
	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#equals(Object)}.
	 * </p>
	 */
	@Test
	public void testEquals24() {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeSet);

		List<IModelInstanceString> elements02;
		elements02 = new ArrayList<IModelInstanceString>();

		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));
		elements02.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection02;
		modelInstanceCollection02 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements02,
						typeSet);

		assertTrue(modelInstanceCollection01.equals(modelInstanceCollection02));
	}
	
	/**
	 * <p>
	 * Tests the method {@link IModelInstanceCollection#getCollection()}.
	 * </p>
	 */
	@Test
	public void testGetCollection01() {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeBag);

		assertNotNull(modelInstanceCollection01.getCollection());
		assertTrue(modelInstanceCollection01.getCollection().size() == 3);
	}

	/**
	 * <p>
	 * Tests the method {@link IModelInstanceCollection#getCollection()}.
	 * </p>
	 */
	@Test
	public void testGetCollection02() {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeOrderedSet);

		assertNotNull(modelInstanceCollection01.getCollection());
		assertTrue(modelInstanceCollection01.getCollection().size() == 3);
	}

	/**
	 * <p>
	 * Tests the method {@link IModelInstanceCollection#getCollection()}.
	 * </p>
	 */
	@Test
	public void testGetCollection03() {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeSequence);

		assertNotNull(modelInstanceCollection01.getCollection());
		assertTrue(modelInstanceCollection01.getCollection().size() == 3);
	}

	/**
	 * <p>
	 * Tests the method {@link IModelInstanceCollection#getCollection()}.
	 * </p>
	 */
	@Test
	public void testGetCollection04() {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeSet);

		assertNotNull(modelInstanceCollection01.getCollection());
		assertTrue(modelInstanceCollection01.getCollection().size() == 3);
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#getTypes()}.
	 * </p>
	 */
	@Test
	public void testGetTypes01() {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeBag);

		assertNotNull(modelInstanceCollection01.getType());

		assertTrue(modelInstanceCollection01.getType() instanceof CollectionType);
		assertEquals(CollectionKind.BAG,
				((CollectionType) modelInstanceCollection01.getType()).getKind());
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#getTypes()}.
	 * </p>
	 */
	@Test
	public void testGetTypes02() {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeOrderedSet);

		assertNotNull(modelInstanceCollection01.getType());

		assertTrue(modelInstanceCollection01.getType() instanceof CollectionType);
		assertEquals(CollectionKind.ORDERED_SET,
				((CollectionType) modelInstanceCollection01.getType()).getKind());
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#getTypes()}.
	 * </p>
	 */
	@Test
	public void testGetTypes03() {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeSequence);

		assertNotNull(modelInstanceCollection01.getType());

		assertTrue(modelInstanceCollection01.getType() instanceof CollectionType);
		assertEquals(CollectionKind.SEQUENCE,
				((CollectionType) modelInstanceCollection01.getType()).getKind());
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#getTypes()}.
	 * </p>
	 */
	@Test
	public void testGetTypes04() {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeSet);

		assertNotNull(modelInstanceCollection01.getType());

		assertTrue(modelInstanceCollection01.getType() instanceof CollectionType);
		assertEquals(CollectionKind.SET,
				((CollectionType) modelInstanceCollection01.getType()).getKind());
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#isOrdered()}.
	 * </p>
	 */
	@Test
	public void testIsOrdered01() {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeBag);

		assertFalse(modelInstanceCollection01.isOrdered());
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#isOrdered()}.
	 * </p>
	 */
	@Test
	public void testIsOrdered02() {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeOrderedSet);

		assertTrue(modelInstanceCollection01.isOrdered());
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#isOrdered()}.
	 * </p>
	 */
	@Test
	public void testIsOrdered03() {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeSequence);

		assertTrue(modelInstanceCollection01.isOrdered());
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#isOrdered()}.
	 * </p>
	 */
	@Test
	public void testIsOrdered04() {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeSet);

		assertFalse(modelInstanceCollection01.isOrdered());
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#isUndefined()}.
	 * </p>
	 */
	@Test
	public void testIsUndefined01() {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeBag);

		assertFalse(modelInstanceCollection01.isUndefined());
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#isUndefined()}.
	 * </p>
	 */
	@Test
	public void testIsUndefined02() {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeBag);

		assertFalse(modelInstanceCollection01.isUndefined());
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#isUndefined()}.
	 * </p>
	 */
	@Test
	public void testIsUndefined03() {

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(null,
						typeBag);

		assertTrue(modelInstanceCollection01.isUndefined());
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#isUnique()}.
	 * </p>
	 */
	@Test
	public void testIsUnique01() {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeBag);

		assertFalse(modelInstanceCollection01.isUnique());
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#isUnique()}.
	 * </p>
	 */
	@Test
	public void testIsUnique02() {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeOrderedSet);

		assertTrue(modelInstanceCollection01.isUnique());
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#isUnique()}.
	 * </p>
	 */
	@Test
	public void testIsUnique03() {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeSequence);

		assertFalse(modelInstanceCollection01.isUnique());
	}

	/**
	 * <p>
	 * Tests the method {@link JavaModelInstanceCollection#isUnique()}.
	 * </p>
	 */
	@Test
	public void testIsUnique04() {

		List<IModelInstanceString> elements01;
		elements01 = new ArrayList<IModelInstanceString>();

		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("one"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("two"));
		elements01.add(BasisJavaModelInstanceFactory
				.createModelInstanceString("three"));

		IModelInstanceCollection<IModelInstanceString> modelInstanceCollection01;
		modelInstanceCollection01 =
				BasisJavaModelInstanceFactory.createModelInstanceCollection(elements01,
						typeSet);

		assertTrue(modelInstanceCollection01.isUnique());
	}
}