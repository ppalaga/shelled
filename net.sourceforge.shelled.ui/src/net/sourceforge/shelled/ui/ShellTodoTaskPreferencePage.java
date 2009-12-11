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
package net.sourceforge.shelled.ui;

import net.sourceforge.shelled.core.ShelledNature;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.dltk.ui.PreferencesAdapter;
import org.eclipse.dltk.ui.preferences.AbstractConfigurationBlockPropertyAndPreferencePage;
import org.eclipse.dltk.ui.preferences.AbstractOptionsBlock;
import org.eclipse.dltk.ui.preferences.AbstractTodoTaskOptionsBlock;
import org.eclipse.dltk.ui.preferences.PreferenceKey;
import org.eclipse.dltk.ui.util.IStatusChangeListener;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

public class ShellTodoTaskPreferencePage extends
		AbstractConfigurationBlockPropertyAndPreferencePage {

	static final PreferenceKey CASE_SENSITIVE = AbstractTodoTaskOptionsBlock
			.createCaseSensitiveKey(Activator.PLUGIN_ID);

	static final PreferenceKey ENABLED = AbstractTodoTaskOptionsBlock
			.createEnabledKey(Activator.PLUGIN_ID);

	static final PreferenceKey TAGS = AbstractTodoTaskOptionsBlock
			.createTagKey(Activator.PLUGIN_ID);

	@Override
	protected String getHelpId() {
		return null;
	}

	@Override
	protected void setDescription() {
		setDescription("Strings indicating tasks in shell script comments.");
	}

	protected Preferences getPluginPreferences() {
		return Activator.getDefault().getPluginPreferences();
	}

	@Override
	protected AbstractOptionsBlock createOptionsBlock(
			IStatusChangeListener newStatusChangedListener, IProject project,
			IWorkbenchPreferenceContainer container) {
		return new AbstractTodoTaskOptionsBlock(newStatusChangedListener,
				project, getPreferenceKeys(), container) {
			@Override
			protected PreferenceKey getTags() {
				return TAGS;
			}

			@Override
			protected PreferenceKey getEnabledKey() {
				return ENABLED;
			}

			@Override
			protected PreferenceKey getCaseSensitiveKey() {
				return CASE_SENSITIVE;
			}
		};
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
		setPreferenceStore(new PreferencesAdapter(Activator.getDefault()
				.getPluginPreferences()));
	}

	@Override
	protected String getPreferencePageId() {
		return "net.sourceforge.preferences.todo";
	}

	@Override
	protected String getPropertyPageId() {
		return "net.sourceforge.preferences.todo";
	}

	protected PreferenceKey[] getPreferenceKeys() {
		return new PreferenceKey[] { TAGS, ENABLED, CASE_SENSITIVE };
	}
}