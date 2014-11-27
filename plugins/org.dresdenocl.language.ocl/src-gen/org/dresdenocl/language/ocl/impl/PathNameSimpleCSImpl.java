/**
 */
package org.dresdenocl.language.ocl.impl;

import org.dresdenocl.language.ocl.OclPackage;
import org.dresdenocl.language.ocl.PathNameSimpleCS;
import org.dresdenocl.pivotmodel.NamedElement;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Path Name Simple CS</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.dresdenocl.language.ocl.impl.PathNameSimpleCSImpl#getNamedElement <em>Named Element</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PathNameSimpleCSImpl extends PathNameCSImpl implements PathNameSimpleCS {
	/**
	 * The cached value of the '{@link #getNamedElement() <em>Named Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamedElement()
	 * @generated
	 * @ordered
	 */
	protected NamedElement namedElement;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PathNameSimpleCSImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OclPackage.Literals.PATH_NAME_SIMPLE_CS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NamedElement getNamedElement() {
		if (namedElement != null && namedElement.eIsProxy()) {
			InternalEObject oldNamedElement = (InternalEObject)namedElement;
			namedElement = (NamedElement)eResolveProxy(oldNamedElement);
			if (namedElement != oldNamedElement) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, OclPackage.PATH_NAME_SIMPLE_CS__NAMED_ELEMENT, oldNamedElement, namedElement));
			}
		}
		return namedElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NamedElement basicGetNamedElement() {
		return namedElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNamedElement(NamedElement newNamedElement) {
		NamedElement oldNamedElement = namedElement;
		namedElement = newNamedElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OclPackage.PATH_NAME_SIMPLE_CS__NAMED_ELEMENT, oldNamedElement, namedElement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case OclPackage.PATH_NAME_SIMPLE_CS__NAMED_ELEMENT:
				if (resolve) return getNamedElement();
				return basicGetNamedElement();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case OclPackage.PATH_NAME_SIMPLE_CS__NAMED_ELEMENT:
				setNamedElement((NamedElement)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case OclPackage.PATH_NAME_SIMPLE_CS__NAMED_ELEMENT:
				setNamedElement((NamedElement)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case OclPackage.PATH_NAME_SIMPLE_CS__NAMED_ELEMENT:
				return namedElement != null;
		}
		return super.eIsSet(featureID);
	}

} //PathNameSimpleCSImpl
