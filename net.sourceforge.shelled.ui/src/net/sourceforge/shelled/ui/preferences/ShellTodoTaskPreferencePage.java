/*******************************************************************************
 * Copyright (c) 2009 Red Hat Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Alexander Kurtakov - initial API and implementation
 *******************************************************************************/
package net.sourceforge.shelled.ui.preferences;

import net.sourceforge.shelled.core.ShelledNature;
import net.sourceforge.shelled.ui.Activator;

import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.ui.preferences.AbstractConfigurationBlockPropertyAndPreferencePage;
import org.eclipse.dltk.ui.preferences.AbstractOptionsBlock;
import org.eclipse.dltk.ui.preferences.TodoTaskOptionsBlock;
import org.eclipse.dltk.ui.util.IStatusChangeListener;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

public class ShellTodoTaskPreferencePage extends
		AbstractConfigurationBlockPropertyAndPreferencePage {

	@Override
	protected String getHelpId() {
		return null;
	}

	@Override
	protected void setDescription() {
		setDescription("Strings indicating tasks in Shell Script comments.");
	}

	@Override
	protected AbstractOptionsBlock createOptionsBlock(
			IStatusChangeListener newStatusChangedListener, IProject project,
			IWorkbenchPreferenceContainer container) {
		return new TodoTaskOptionsBlock(newStatusChangedListener, project,
				container, Activator.PLUGIN_ID);
	}

	@Override
	protected String getNatureId() {
		return ShelledNature.SHELLED_NATURE;
	}

	@Override
	protected String getProjectHelpId() {
		return null;
	}

	@Override
	protected void setPreferenceStore() {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}

	@Override
	protected String getPreferencePageId() {
		return "net.sourceforge.preferences.todo";
	}

	@Override
	protected String getPropertyPageId() {
		return "net.sourceforge.preferences.todo";
	}

}