/*******************************************************************************
 * Copyright (c) 2012 Red Hat Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Alexander Kurtakov - initial API and implementation
 *******************************************************************************/
package net.sourceforge.shelled.ui.text.folding;

import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.ui.PreferenceConstants;
import org.eclipse.dltk.ui.text.folding.IFoldingBlockKind;
import org.eclipse.dltk.ui.text.folding.ModelFoldingBlockProvider;
import org.eclipse.jface.preference.IPreferenceStore;

public class ShellCodeFoldingBlockProvider extends ModelFoldingBlockProvider {
	private int minimalLineCount;
	private boolean foldingEnabled;
	private boolean functionsCollapsedInitially;

	@Override
	public boolean visit(IModelElement element) {
		if (element instanceof IMethod) {
			reportElement(element);
		}
		return true;
	}

	@Override
	public void initializePreferences(IPreferenceStore preferenceStore) {
		super.initializePreferences(preferenceStore);
		minimalLineCount = preferenceStore
				.getInt(PreferenceConstants.EDITOR_FOLDING_LINES_LIMIT);
		foldingEnabled = preferenceStore
				.getBoolean(PreferenceConstants.EDITOR_FOLDING_ENABLED);
		functionsCollapsedInitially = preferenceStore
				.getBoolean(PreferenceConstants.EDITOR_FOLDING_INIT_METHODS);
	}

	@Override
	public int getMinimalLineCount() {
		return minimalLineCount;
	}

	@Override
	protected boolean isFoldedInitially(IModelElement element) {
		if (foldingEnabled) {
			if (element instanceof IMethod) {
				return functionsCollapsedInitially;
			}
		}
		return false;
	}

	@Override
	protected IFoldingBlockKind getKind(IModelElement element) {
		if (element instanceof IMethod) {
			return FoldingBlockKind.FUNCTION;
		}
		/*
		 * this should not happen. getKind() should be called for functions only
		 * because we reportElement() only for IMethod in visit()
		 */
		return null;
	}

}
