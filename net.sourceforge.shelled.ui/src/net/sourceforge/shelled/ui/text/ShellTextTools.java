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
package net.sourceforge.shelled.ui.text;

import net.sourceforge.shelled.ui.editor.ShellSourceViewerConfiguration;

import org.eclipse.dltk.ui.text.ScriptSourceViewerConfiguration;
import org.eclipse.dltk.ui.text.ScriptTextTools;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.rules.IPartitionTokenScanner;
import org.eclipse.ui.texteditor.ITextEditor;

public class ShellTextTools extends ScriptTextTools {

	private final IPartitionTokenScanner fPartitionScanner;

	public ShellTextTools(boolean autoDisposeOnDisplayDispose) {
		super(IShellPartitions.SHELL_PARTITIONING,
				IShellPartitions.CONTENT_TYPES, autoDisposeOnDisplayDispose);
		fPartitionScanner = new ShellPartitionScanner();
	}

	@Override
	public ScriptSourceViewerConfiguration createSourceViewerConfiguraton(
			IPreferenceStore preferenceStore, ITextEditor editor,
			String partitioning) {
		return new ShellSourceViewerConfiguration(getColorManager(),
				preferenceStore, editor, partitioning);
	}

	@Override
	public IPartitionTokenScanner createPartitionScanner() {
		return fPartitionScanner;
	}

}