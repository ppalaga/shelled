/*******************************************************************************
 * Copyright (c) 2012 Red Hat Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Peter Palaga - initial API and implementation
 *******************************************************************************/
package net.sourceforge.shelled.ui.text.folding;

import org.eclipse.dltk.ui.preferences.OverlayPreferenceStore;
import org.eclipse.dltk.ui.text.folding.SourceCodeFoldingPreferenceBlock;
import org.eclipse.jface.preference.PreferencePage;

/**
 * A preference block for folding of Shell Script commands.
 */
public class ShellCodeFoldingPreferenceBlock extends
SourceCodeFoldingPreferenceBlock {
	public ShellCodeFoldingPreferenceBlock(OverlayPreferenceStore store,
			PreferencePage page) {
		super(store, page);
	}

	@Override
	protected String getInitiallyFoldMethodsText() {
		return "Functions";
	}

	@Override
	protected boolean supportsClassFolding() {
		return false;
	}

}
