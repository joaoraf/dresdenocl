/*
Copyright (C) 2008-2009 by Claas Wilke (claaswilke@gmx.net)

This file is part of the OCL 2 Java Code Generator of Dresden OCL2 for Eclipse.

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

package org.dresdenocl.tools.codegen.declarativ.ocl2sql.ui.internal.wizards;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import org.dresdenocl.pivotmodel.Constraint;
import org.dresdenocl.tools.codegen.declarativ.IOcl2DeclSettings;
import org.dresdenocl.tools.codegen.declarativ.ocl2sql.Ocl2SQLFactory;
import org.dresdenocl.tools.codegen.declarativ.ocl2sql.ui.Ocl2SQLUIPlugIn;
import org.dresdenocl.tools.codegen.declarativ.ocl2sql.ui.internal.Ocl2SqlUIMessages;
import org.dresdenocl.tools.codegen.ui.impl.wizards.CodegenJob;
import org.dresdenocl.tools.codegen.ui.impl.wizards.TransformCodeWizard;

public class SQLCodeWizard extends TransformCodeWizard implements INewWizard {

	/** A logger for this class. */
	private static final Logger logger = Ocl2SQLUIPlugIn
			.getLogger(SQLCodeWizard.class);

	/** A page to select settings for code transformation. */
	private SettingsPage settingsPage;

	/**
	 * <p>
	 * Creates a new {@link SQLCodeWizard}.
	 * </p>
	 */
	public SQLCodeWizard() {

		super(Ocl2SqlUIMessages.TransformCodeWizard_Title);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.wizard.IWizard#addPages()
	 */
	public void addPages() {

		super.addPages();
		addPage(this.settingsPage);

	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
	 * org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {

		super.init(workbench, selection);

		// this.selectDirectoryPage.getDirectoryConstraintGroup().setVisible(false);

		this.settingsPage =
				new SettingsPage((IOcl2DeclSettings) this.myCodeGenerator.getSettings());

		/* Probably log the exit from this method. */
		if (logger.isDebugEnabled()) {
			logger.debug("Exit method init(IWorkbench, IStructuredSelection).");
		}
		// no else.
	}

	protected void setCodeGenerator() {

		myCodeGenerator = Ocl2SQLFactory.getInstance().createSQLCodeGenerator();
	}

	protected CodegenJob getCodegenJob(List<Constraint> constraints) {

		CodegenSQLJob codegenJob =
				new CodegenSQLJob(constraints, this.myCodeGenerator);
		return codegenJob;

	}

}