/*
Copyright (C) 2007-2009 by Claas Wilke (claaswilke@gmx.net)

This file is part of the Ecore Meta Model of Dresden OCL2 for Eclipse.

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
package org.dresdenocl.modelinstancetype.ecore.internal.provider;

import java.net.URL;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.osgi.util.NLS;

import org.dresdenocl.model.IModel;
import org.dresdenocl.model.ModelAccessException;
import org.dresdenocl.modelinstance.IModelInstance;
import org.dresdenocl.modelinstance.IModelInstanceProvider;
import org.dresdenocl.modelinstance.base.AbstractModelInstanceProvider;
import org.dresdenocl.modelinstancetype.ecore.EcoreModelInstanceTypePlugin;
import org.dresdenocl.modelinstancetype.ecore.internal.modelinstance.EcoreModelInstance;
import org.dresdenocl.modelinstancetype.ecore.internal.msg.EcoreModelInstanceTypeMessages;

/**
 * <p>
 * Provides methods to load or get {@link IModelInstance}s of the EMF Ecore
 * model instance type.
 * 
 * @author Claas Wilke
 */
public class EcoreModelInstanceProvider extends AbstractModelInstanceProvider
		implements IModelInstanceProvider {

	/** The {@link Logger} for this class. */
	private static final Logger LOGGER =
			EcoreModelInstanceTypePlugin.getLogger(EcoreModelInstanceProvider.class);

	/**
	 * <p>
	 * Creates a new {@link EcoreModelInstanceProvider}.
	 * </p>
	 */
	public EcoreModelInstanceProvider() {

		/* Remains empty. */
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.dresdenocl.modelbus.modelinstance.IModelInstanceProvider#
	 * createEmptyModelInstance(org.dresdenocl.modelbus.IModel)
	 */
	public IModelInstance createEmptyModelInstance(IModel model) {

		if (model == null) {
			throw new IllegalArgumentException("Paramter 'model' must not be null.");
		}
		// no else.

		return new EcoreModelInstance(model);
	}

	/*
	 * (non-Javadoc)
	 * @see org.dresdenocl.modelbus.IModelInstanceProvider#getModelInstance
	 * (java.net.URL, org.dresdenocl.modelbus.IModel)
	 */
	public IModelInstance getModelInstance(URL modelInstanceUrl, IModel model)
			throws ModelAccessException {

		/* Probably debug the entry of this method. */
		if (LOGGER.isDebugEnabled()) {
			String msg;

			msg = "getModelInstance("; //$NON-NLS-1$
			msg += "modelInstanceUrl = " + modelInstanceUrl; //$NON-NLS-1$
			msg += ", model = " + model; //$NON-NLS-1$
			msg += ")"; //$NON-NLS-1$

			LOGGER.debug(msg);
		}
		// no else.

		IModelInstance result;
		URI modelURI;

		Resource resource;
		ResourceSet resourceSet;

		/* Try to create a URI. */
		try {
			modelURI = URI.createURI(modelInstanceUrl.toString());
		}

		catch (IllegalArgumentException e) {
			String msg;

			msg =
					EcoreModelInstanceTypeMessages.EcoreModelInstanceProvider_InvalidURL;
			msg = NLS.bind(msg, modelInstanceUrl, e.getMessage());

			throw new ModelAccessException(msg, e);
		}

		/* Get the resource. */
		resourceSet = new ResourceSetImpl();
		resource = resourceSet.getResource(modelURI, false);

		if (resource == null) {
			/* Only create the resource do not load it. */
			resource = resourceSet.createResource(modelURI);
		}

		/* Create the model instance. */
		result = new EcoreModelInstance(resource, model);

		/* Probably debug the exit of this method. */
		if (LOGGER.isDebugEnabled()) {
			String msg;

			msg = "getModelInstance(URL, IModel) - exit"; //$NON-NLS-1$
			msg += " return value = " + result; //$NON-NLS-1$

			LOGGER.debug(msg);
		}
		// no else.

		return result;
	}
}