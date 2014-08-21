/*******************************************************************************
 * Copyright (c) 2014 Red Hat Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Peter Palaga - initial API and implementation
 *******************************************************************************/
package net.sourceforge.shelled.ui.text.folding;

import org.eclipse.dltk.ui.PreferenceConstants;
import org.eclipse.dltk.ui.preferences.OverlayPreferenceStore;
import org.eclipse.dltk.ui.preferences.PreferencesMessages;
import org.eclipse.dltk.ui.text.folding.DocumentationFoldingPreferenceBlock;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.widgets.Group;

/**
 * A preference block for folding of Shell Script comments.
 */
public class ShellCommentFoldingPreferenceBlock extends
		DocumentationFoldingPreferenceBlock {

	public ShellCommentFoldingPreferenceBlock(OverlayPreferenceStore store,
			PreferencePage page) {
		super(store, page);
	}

	/**
	 * Overridden to add just the "Comments" checkbox rather than both
	 * "Comments" and "Header comments" as it is done in the super class. It is
	 * because we do not detect header comments and therefore we cannot fold
	 * them separately.
	 * 
	 * @see org.eclipse.dltk.ui.text.folding.DocumentationFoldingPreferenceBlock#addInitiallyFoldOptions(org.eclipse.swt.widgets.Group)
	 */
	@Override
	protected void addInitiallyFoldOptions(Group group) {
		createCheckBox(
				group,
				PreferencesMessages.FoldingConfigurationBlock_initiallyFoldComments,
				PreferenceConstants.EDITOR_FOLDING_INIT_COMMENTS);
	}

}
