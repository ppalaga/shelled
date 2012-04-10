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

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.dltk.compiler.task.TaskTagUtils;
import org.eclipse.dltk.ui.CodeFormatterConstants;
import org.eclipse.dltk.ui.PreferenceConstants;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.editors.text.EditorsUI;

public class ShelledUIPreferenceInitializer extends
		AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();

		EditorsUI.useAnnotationsPreferencePage(store);
		EditorsUI.useQuickDiffPreferencePage(store);

		// Initialize DLTK default values
		PreferenceConstants.initializeDefaultValues(store);

		// Initialize SHELL constants
		PreferenceConverter.setDefault(store,
				IShellColorConstants.SHELL_COMMENT, new RGB(136, 0, 136));
		PreferenceConverter.setDefault(store,
				IShellColorConstants.SHELL_HASHBANG, new RGB(136, 136, 136));
		PreferenceConverter.setDefault(store,
				IShellColorConstants.SHELL_KEYWORD, new RGB(144, 4, 86));
		PreferenceConverter.setDefault(store,
				IShellColorConstants.SHELL_SINGLE_QUOTE, new RGB(13, 109, 160));
		PreferenceConverter.setDefault(store,
				IShellColorConstants.SHELL_DOUBLE_QUOTE, new RGB(13, 109, 24));
		PreferenceConverter.setDefault(store, IShellColorConstants.SHELL_EVAL,
				new RGB(205, 30, 30));
		PreferenceConverter.setDefault(store,
				IShellColorConstants.SHELL_FUNCTION, new RGB(255, 127, 63));
		PreferenceConverter.setDefault(store,
				IShellColorConstants.SHELL_COMMAND, new RGB(255, 127, 63));
		PreferenceConverter.setDefault(store,
				IShellColorConstants.SHELL_VARIABLE, new RGB(0, 0, 156));

		PreferenceConverter.setDefault(store,
				IShellColorConstants.SHELL_TODO_TAG, new RGB(127, 159, 191));
		store.setDefault(IShellColorConstants.SHELL_TODO_TAG
				+ PreferenceConstants.EDITOR_BOLD_SUFFIX, true);

		store.setDefault(IShellColorConstants.SHELL_COMMENT
				+ PreferenceConstants.EDITOR_BOLD_SUFFIX, false);
		store.setDefault(IShellColorConstants.SHELL_COMMENT
				+ PreferenceConstants.EDITOR_ITALIC_SUFFIX, true);
		store.setDefault(IShellColorConstants.SHELL_HASHBANG
				+ PreferenceConstants.EDITOR_ITALIC_SUFFIX, true);

		store.setDefault(IShellColorConstants.SHELL_KEYWORD
				+ PreferenceConstants.EDITOR_BOLD_SUFFIX, true);
		store.setDefault(IShellColorConstants.SHELL_KEYWORD
				+ PreferenceConstants.EDITOR_ITALIC_SUFFIX, false);

		store.setDefault(PreferenceConstants.EDITOR_TAB_WIDTH, 8);
		store.setDefault(
				PreferenceConstants.EDITOR_SYNC_OUTLINE_ON_CURSOR_MOVE, true);

		store.setDefault(CodeFormatterConstants.FORMATTER_TAB_CHAR,
				CodeFormatterConstants.TAB);
		store.setDefault(CodeFormatterConstants.FORMATTER_TAB_SIZE, "4");
		store.setDefault(CodeFormatterConstants.FORMATTER_INDENTATION_SIZE, "4");
		store.setDefault(PreferenceConstants.EDITOR_FOLDING_ENABLED, true);

		TaskTagUtils.initializeDefaultValues(DefaultScope.INSTANCE
				.getNode(Activator.PLUGIN_ID));
	}
}