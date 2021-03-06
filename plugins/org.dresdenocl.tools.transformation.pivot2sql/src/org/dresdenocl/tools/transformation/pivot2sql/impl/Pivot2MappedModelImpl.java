/*
 * Created on 06.02.2006
 *
 */
package org.dresdenocl.tools.transformation.pivot2sql.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.dresdenocl.essentialocl.types.CollectionType;
import org.dresdenocl.model.ModelAccessException;
import org.dresdenocl.pivotmodel.AssociationProperty;
import org.dresdenocl.pivotmodel.Namespace;
import org.dresdenocl.pivotmodel.Property;
import org.dresdenocl.pivotmodel.Type;
import org.dresdenocl.tools.codegen.declarativ.IOcl2DeclSettings;
import org.dresdenocl.tools.codegen.declarativ.mapping.Guide;
import org.dresdenocl.tools.codegen.declarativ.mapping.IMappedModel;
import org.dresdenocl.tools.transformation.ITransformation;
import org.dresdenocl.tools.transformation.Transformation;
import org.dresdenocl.tools.transformation.exception.InvalidModelException;
import org.dresdenocl.tools.transformation.exception.TransformationException;
import org.dresdenocl.tools.transformation.pivot2sql.Pivot2SqlPlugin;
import org.dresdenocl.tools.transformation.pivot2sql.mapping.MappedClassImpl;
import org.dresdenocl.tools.transformation.pivot2sql.mapping.MappedModelImpl;
import org.dresdenocl.tools.transformation.pivot2sql.util.PivotModelAnalyser;

/**
 * The class Pivot2MappedModelImpl implements the transformation of an instance
 * of the pivotmodel to a MappedModel.
 * 
 * @author Christian Wende
 * @author Bjoern Freitag
 * 
 */
public class Pivot2MappedModelImpl extends
		Transformation<Namespace, IOcl2DeclSettings, IMappedModel> implements
		ITransformation<Namespace, IOcl2DeclSettings, IMappedModel> {

	private Logger LOGGER = Pivot2SqlPlugin
			.getLogger(Pivot2MappedModelImpl.class);

	private static final String PART_CLASS =
			"The partitipants of an Association must be Types.";

	private static final String ASS_ROLE =
			"The Roles at the AssociationEnds must be named.";

	private PivotModelAnalyser pivotModelAnalyser;

	/**
	 * The constructor for a Uml2MappdedModel transformation.
	 * 
	 * @param modelInName
	 *          The name of the in model.
	 * @param outname
	 *          The name of the out model.
	 * @throws ModelAccessException
	 * @throws Exception
	 */
	public Pivot2MappedModelImpl(String modelInName, String outname) {

		super(modelInName, outname);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("init() - stop");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see tudresden.ocl20.pivot.tools.transformation.Transformation#invoke()
	 */
	@Override
	public void invoke() throws InvalidModelException, TransformationException {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Starting pivot to mappedmodel transformation");
			String settings = "TablePrefix:" + this.settings.getTablePrefix() + "\n";
			settings +=
					"ObjectViewPrefix:" + this.settings.getObjectViewPrefix() + "\n";
			settings +=
					"AssociationTablePrefix:" + this.settings.getAssociationTablePrefix()
							+ "\n";
			settings +=
					"PrimaryKeyPrefix:" + this.settings.getPrimaryKeyPrefix() + "\n";
			settings +=
					"ForeignKeyPrefix:" + this.settings.getForeignKeyPrefix() + "\n";
			settings +=
					"Modus:" + this.settings.getModus() + "(1 =typed, 2=vertical)";
			LOGGER.debug(settings);
		}

		/** CHECK SETTINGS **/
		if (this.settings.getPrimaryKeyPrefix().equals("")) {
			throw new TransformationException("No primary key prefix set.", this);
		}
		if (this.settings.getPrimaryKeyPrefix().equals(
				this.settings.getForeignKeyPrefix())) {
			throw new TransformationException(
					"Primary Key and Foreign Key prefix equals", this);
		}
		if (this.settings.getTablePrefix().equals(
				this.settings.getObjectViewPrefix())) {
			throw new TransformationException("Table and ObjectView prefix equal",
					this);
		}

		pivotModelAnalyser = new PivotModelAnalyser(in);

		settings.setMappedModel(new MappedModelImpl());

		Set<Type> types = pivotModelAnalyser.getInstancesOfType(Type.class);
		for (Type type : types) {
			if (pivotModelAnalyser.isPrimitive(type))
				continue;
			map_Type2MappedClass(type);
		}

		Set<Property> properties =
				pivotModelAnalyser.getInstancesOfType(Property.class);
		for (Property property : properties) {
			map_property2Guide(property);
		}
		Set<AssociationProperty> filterAssociation =
				new HashSet<AssociationProperty>();
		Set<AssociationProperty> associations = query_allAssociations();
		for (AssociationProperty association : associations) {
			if (filterAssociation.contains(association))
				continue;
			map_association2guide(association);
			filterAssociation.addAll(association.getInverseAssociationProperties());
		}

		out = settings.getMappedModel();

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Stop Transformation");
		}

	}

	/**
	 * Create a mapped class for the  {@link Type}.
	 * 
	 * @param type
	 * 			the type for generating the class
	 * @throws InvalidModelException
	 * 			
	 */
	private void map_Type2MappedClass(Type type) throws InvalidModelException {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Mapping of Type: " + type.getName());
		}

		Set<Type> subtypes = query_subtypesForType(type);

		accessMappedClass(type);

		for (Type subType : subtypes) {
			accessMappedClass(subType);
		}
	}

	/**
	 * Generate the guide for the access the property
	 * 
	 * @param property
	 * 			the property which create the guide
	 * @throws InvalidModelException
	 */
	private void map_property2Guide(Property property)
			throws InvalidModelException {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Mapping of Property: " + property.getName());
		}
		if (property instanceof AssociationProperty)
			return;

		if (isAssociation(property)) {
			map_property2Guide(property, property.getType(), property.getOwningType());
		}
		else {
			Type type = property.getOwningType();

			List<String> pkname = query_pkNameForType(type);
			Set<Type> subtypes = query_subtypesForType(type);

			create_PropertyGuide(property, type, pkname);

			for (Type subType : subtypes) {
				create_PropertyGuide(property, subType, pkname);
			}
		}
	}

	/**
	 * 
	 * @param property
	 * @param type
	 * @param pkname
	 * @throws InvalidModelException
	 */
	private void create_PropertyGuide(Property property, Type type, List<String> pkname)
			throws InvalidModelException {

		MappedClassImpl mc = accessMappedClass(type);
		String ovname = this.settings.getObjectViewPrefix() + type.getName();
		Guide guide = new Guide(false);
		guide.add(Arrays.asList(property.getName()), ovname, pkname);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Creating Attribute Guide for Attribute: "
					+ property.getName() + " in MappedClass " + mc.getName() + "\n"
					+ guide);
		}

		mc.addAttributeGuide(property.getName(), guide);
	}

	/**
	 * Create an association guide.
	 * 
	 * @param name
	 * 			the name of the guide
	 * @param type
	 * 			the owning type of the association
	 * @param pkname
	 * 			the primary keys of this association
	 * @param fkname
	 * 			the foreign keys of this association
	 * @param ovname
	 * 			the name of the object view
	 * @return
	 * 			the generated guide
	 * @throws InvalidModelException
	 */
	private Guide create_AssociationGuide(String name, Type type, List<String> pkname,
			List<String> fkname, String ovname) throws InvalidModelException {

		MappedClassImpl mc = accessMappedClass(type);

		Guide guide = new Guide(true);

		addToGuide(guide, fkname, ovname, pkname);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Creating Association Guide for Attribute: " + name
					+ " in MappedClass " + mc.getName() + "\n" + guide);
		}
		mc.addAssociationEndGuide(name, guide);
		return guide;
	}

	
	private void addToGuide(Guide guide, List<String> select, String from, List<String> where) {

		guide.add(select, from, where);
	}

	private void map_property2Guide(Property property, Type type, Type owningType)
			throws InvalidModelException {

		if (LOGGER.isDebugEnabled()) {
			LOGGER
					.debug("Mapping association "
							+ property.getName()
							+ " between "
							+ owningType.getName()
							+ " and "
							+ type.getName()
							+ " by putting a Foreign Key to the PrimaryKey of each side into the opposite site");
		}

		String name = property.getName();
		if (type instanceof CollectionType) {
			map_MtoN_Association2Guide(((CollectionType) type).getElementType(),
					property.getName(), owningType,
					owningType.getName(),
					settings.getUniqueAssociationTableName(property));
		}
		else {
			map_1toN_Association2Guide(type, name, owningType, null);
		}
	}

	private boolean isAssociation(Property property) {

		if (property.getType() instanceof CollectionType) {
			if (settings.getMappedModel().isClass(
					((CollectionType) property.getType()).getElementType().getName())) {
				return true;
			}
		}
		else if (settings.getMappedModel().isClass(property.getType().getName())) {
			return true;
		}
		return false;
	}

	private MappedClassImpl accessMappedClass(Type type)
			throws InvalidModelException {

		String typename = type.getName();
		List<String> pkname = query_pkNameForType(type);
		String ovname = this.settings.getObjectViewPrefix() + typename;

		MappedClassImpl mc;

		if (this.settings.getMappedModel().isClass(typename)) {
			mc = (MappedClassImpl) this.settings.getMappedModel().getClass(typename);
		}
		else {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Creating MappedClass for Type " + type.getName());
			}

			mc = new MappedClassImpl(typename);
			Guide g = new Guide(false);
			((MappedModelImpl) this.settings.getMappedModel()).addMappedClass(
					typename, mc);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Creating Class Guide for Type " + type.getName() + "\n"
						+ g);
			}

			g.add(pkname, ovname, pkname);
			mc.addClassGuide(g);

		}
		return mc;
	}

	private void map_association2guide(AssociationProperty property)
			throws InvalidModelException {

		String assTableName = settings.getUniqueAssociationTableName(property);

		Type tA = property.getType();
		String nameA = property.getName();

		Property pB = property.getInverseAssociationProperties().get(0);
		Type tB = pB.getType();
		String nameB = pB.getName();

		if (nameA == null || nameA.equals("")) {
			throw new InvalidModelException(ASS_ROLE + "[" + assTableName + ", "
					+ tA.getName() + "]", this.in, this);
		}

		if (nameB == null || nameB.equals("")) {
			throw new InvalidModelException(ASS_ROLE + "[" + assTableName + ", "
					+ tB.getName() + "]", this.in, this);
		}

		if (pivotModelAnalyser.isMultiple(property)) {
			tA = ((CollectionType) tA).getElementType();
		}

		Type typeA = null;
		if (!(pivotModelAnalyser.instanceIsOfType(tA, Type.class))) {
			throw new InvalidModelException(PART_CLASS + "[" + assTableName + ", "
					+ tA.getName() + "]", this.in, this);
		}
		else {
			typeA = (Type) tA;
		}

		if (pivotModelAnalyser.isMultiple(pB)) {
			tB = ((CollectionType) tB).getElementType();
		}
		Type typeB = null;
		if (!(pivotModelAnalyser.instanceIsOfType(tB, Type.class))) {
			throw new InvalidModelException(PART_CLASS + "[" + assTableName + ", "
					+ tB.getName() + "]", this.in, this);
		}
		else {
			typeB = (Type) tB;
		}

		/** MAPPING PHASE **/

		if (!pivotModelAnalyser.isMultiple(property)
				&& !pivotModelAnalyser.isMultiple(pB)) {
			map_1to1_Association2Guide(typeA, nameA, typeB, nameB);
		}
		else if (pivotModelAnalyser.isMultiple(property)
				&& !pivotModelAnalyser.isMultiple(pB)) {
			map_1toN_Association2Guide(typeB, nameB, typeA, nameA);
		}
		else if (!pivotModelAnalyser.isMultiple(property)
				&& pivotModelAnalyser.isMultiple(pB)) {
			map_1toN_Association2Guide(typeA, nameA, typeB, nameB);
		}
		else {
			map_MtoN_Association2Guide(typeA, nameA, typeB, nameB, assTableName);
		}

	}

	private void map_1to1_Association2Guide(Type typeA, String nameA, Type typeB,
			String nameB) throws InvalidModelException {

		List<String> pknameA = query_pkNameForType(typeA);
		List<String> pknameB = query_pkNameForType(typeB);
		String ovnameA = this.settings.getObjectViewPrefix() + typeA.getName();
		String ovnameB = this.settings.getObjectViewPrefix() + typeB.getName();
		List<String> fknameA = query_fkNameForType(typeB,nameA);
		List<String> fknameB = query_fkNameForType(typeA,nameB);
		Set<Type> subtypesA = query_subtypesForType(typeA);
		Set<Type> subtypesB = query_subtypesForType(typeB);

		create_AssociationGuide(nameB, typeA, pknameA, fknameB, ovnameA);
		for (Type subType : subtypesA) {
			create_AssociationGuide(nameB, subType, pknameA, fknameB, ovnameA);
		}

		create_AssociationGuide(nameA, typeB, pknameB, fknameA, ovnameB);
		for (Type subType : subtypesB) {
			create_AssociationGuide(nameA, subType, pknameB, fknameA, ovnameB);
		}

	}

	private void map_1toN_Association2Guide(Type typeB, String nameB, Type typeA,
			String nameA) throws InvalidModelException {

		List<String> pknameA = query_pkNameForType(typeA);
		String ovnameA = this.settings.getObjectViewPrefix() + typeA.getName();
		List<String> fknameB = query_fkNameForType(typeA,nameB);

		Set<Type> subtypesA = query_subtypesForType(typeA);
		Set<Type> subtypesB = query_subtypesForType(typeB);

		create_AssociationGuide(nameB, typeA, pknameA, fknameB, ovnameA);
		for (Type subType : subtypesA) {
			if (nameA != null) {
				create_AssociationGuide(nameB, subType, pknameA, fknameB,
						this.settings.getObjectViewPrefix() + subType.getName());
			}
			else {
				create_AssociationGuide(nameB, subType, pknameA, fknameB, ovnameA);
			}
		}

		if (nameA != null) {
			create_AssociationGuide(nameA, typeB, fknameB, pknameA, ovnameA);

			for (Type subType : subtypesB) {
				create_AssociationGuide(nameA, subType, fknameB, pknameA, ovnameA);

			}
		}

	}

	private void map_MtoN_Association2Guide(Type typeA, String nameA, Type typeB,
			String nameB, String assTableName) throws InvalidModelException {

		boolean savePaths = Boolean.valueOf(this.settings.getTemplateGroup().getTemplate("check_database_references").toString()).booleanValue();
		
		List<String> pknameA = query_pkNameForType(typeA);
		List<String> pknameB = query_pkNameForType(typeB);
		String ovnameA = this.settings.getObjectViewPrefix() + typeA.getName();
		String ovnameB = this.settings.getObjectViewPrefix() + typeB.getName();
		List<String> fknameA = query_fkNameForType(typeB,nameA);
		List<String> fknameB = query_fkNameForType(typeA,nameB);
		Set<Type> subtypesA = query_subtypesForType(typeA);
		Set<Type> subtypesB = query_subtypesForType(typeB);

		if (savePaths) {
			create_AssociationGuide(nameB, typeA, fknameA, fknameB, assTableName);
		} else {
			Guide a2b = create_AssociationGuide(nameB, typeA, pknameB, pknameB, ovnameB);
			addToGuide(a2b, fknameB, assTableName, fknameA);
			addToGuide(a2b, pknameA, ovnameA, pknameA);
		}
				
		
		for (Type subType : subtypesA) {
			if (savePaths) {
				create_AssociationGuide(nameB, subType, fknameA, fknameB, assTableName);
			} else {
				Guide sa2b =
					create_AssociationGuide(nameB, subType, pknameB, pknameB, ovnameB);
			String ovnameA_sub =
					this.settings.getObjectViewPrefix() + subType.getName();

			addToGuide(sa2b, fknameB, assTableName, fknameA);
			addToGuide(sa2b, pknameA, ovnameA_sub, pknameA);
			}


		}
		if (savePaths) {
			create_AssociationGuide(nameA, typeB, fknameB, fknameA, assTableName);
		} else {
			Guide b2a =
					create_AssociationGuide(nameA, typeB, pknameA, pknameA, ovnameA);
			addToGuide(b2a, fknameA, assTableName, fknameB);
			addToGuide(b2a, pknameB, ovnameB, pknameB);
		}
		for (Type subType : subtypesB) {
			if (savePaths) {
				create_AssociationGuide(nameA, subType, fknameB, fknameA, assTableName);
			} else {
				Guide sb2a =
						create_AssociationGuide(nameA, subType, pknameA, pknameA, ovnameA);
				String ovnameB_sub =
						this.settings.getObjectViewPrefix() + subType.getName();
	
				addToGuide(sb2a, fknameA, assTableName, fknameB);
				addToGuide(sb2a, pknameB, ovnameB_sub, pknameB);
			}
		}
	}

	private Set<Type> query_subtypesForType(Type type) {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Query all subtypes for Type: " + type.getName());
		}

		return pivotModelAnalyser.query_subtypesForType(type);
	}

	private Set<AssociationProperty> query_allAssociations() {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Query all associations of input model");
		}

		return pivotModelAnalyser.getInstancesOfType(AssociationProperty.class);
	}

	private List<String> query_pkNameForType(Type type) throws InvalidModelException {
		List<String> primaryKeys = new ArrayList<String>();
		List<Type> types = pivotModelAnalyser.query_supertypesForType(type, true);
		for (Type t : types) {
			for (Property prop : t.getOwnedProperty()) {
				if (prop.isIdentifier()) primaryKeys.add(prop.getName());
			}
		}
		if (primaryKeys.size() == 0) primaryKeys.add(settings.getPrimaryKeyPrefix() + query_nameOfGenroot(type));
		return primaryKeys;
	}
	
	private List<String> query_fkNameForType(Type type,String name) throws InvalidModelException {
		List<String> primaryKeys = query_pkNameForType(type);
		List<String> foreignKeys = new ArrayList<String>();
		if (primaryKeys.size() == 1 && primaryKeys.get(0).equals(settings.getPrimaryKeyPrefix() + query_nameOfGenroot(type))) 
			foreignKeys.add(settings.getForeignKeyPrefix()+name);
		else {
			for (String value : primaryKeys) {
				foreignKeys.add(settings.getForeignKeyPrefix()+name+"_"+value);
			}
		}
		return foreignKeys;
	}

	private String query_nameOfGenroot(Type type) throws InvalidModelException {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Query the name of genroot for Type: " + type.getName());
		}

		try {
			return pivotModelAnalyser.query_nameOfGenroot(type);
		} catch (InvalidModelException ime) {
			throw new InvalidModelException(ime.getMessage(), in, this);
		}

	}

}
