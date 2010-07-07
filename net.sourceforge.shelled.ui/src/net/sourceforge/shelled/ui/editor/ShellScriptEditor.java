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
package net.sourceforge.shelled.ui.editor;

import net.sourceforge.shelled.core.ShellScriptLanguageToolkit;
import net.sourceforge.shelled.core.ShelledNature;
import net.sourceforge.shelled.ui.Activator;
import net.sourceforge.shelled.ui.text.IShellPartitions;
import net.sourceforge.shelled.ui.text.ShellTextTools;

import org.eclipse.core.runtime.ILog;
import org.eclipse.dltk.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.internal.ui.actions.FoldingActionGroup;
import org.eclipse.dltk.internal.ui.editor.ScriptEditor;
import org.eclipse.dltk.ui.text.ScriptTextTools;
import org.eclipse.dltk.ui.text.folding.AbstractASTFoldingStructureProvider;
import org.eclipse.dltk.ui.text.folding.IFoldingStructureProvider;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension3;
import org.eclipse.jface.text.rules.IPartitionTokenScanner;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.ui.IEditorInput;

public class ShellScriptEditor extends ScriptEditor {

	public static final String EDITOR_CONTEXT = "#ShellScriptEditorContext";

	public static final String EDITOR_ID = "net.sourceforge.shelled.ui.editor";

	@Override
	protected void connectPartitioningToElement(IEditorInput input,
			IDocument document) {
		if (document instanceof IDocumentExtension3) {
			IDocumentExtension3 extension = (IDocumentExtension3) document;
			if (extension
					.getDocumentPartitioner(IShellPartitions.SHELL_PARTITIONING) == null) {
				ShellTextTools tools = Activator.getDefault().getTextTools();
				tools.setupDocumentPartitioner(document,
						IShellPartitions.SHELL_PARTITIONING);
			}
		}
	}

	@Override
	public String getEditorId() {
		return EDITOR_ID;
	}

	@Override
	public IDLTKLanguageToolkit getLanguageToolkit() {
		return ShellScriptLanguageToolkit.getDefault();
	}

	@Override
	protected IPreferenceStore getScriptPreferenceStore() {
		return Activator.getDefault().getPreferenceStore();
	}

	@Override
	public ScriptTextTools getTextTools() {
		return Activator.getDefault().getTextTools();
	}

	@Override
	protected void initializeEditor() {
		super.initializeEditor();
		setEditorContextMenuId(EDITOR_CONTEXT);
	}

	@Override
	protected FoldingActionGroup createFoldingActionGroup() {
		return new FoldingActionGroup(this, getViewer(), Activator.getDefault()
				.getPreferenceStore());
	}

	@Override
	protected IFoldingStructureProvider createFoldingStructureProvider() {
		return new AbstractASTFoldingStructureProvider() {

			@Override
			protected String getCommentPartition() {
				return IShellPartitions.COMMENT_CONTENT_TYPE;
			}

			@Override
			protected ILog getLog() {
				return Activator.getDefault().getLog();
			}

			@Override
			protected String getPartition() {
				return IShellPartitions.SHELL_PARTITIONING;
			}

			@Override
			protected IPartitionTokenScanner getPartitionScanner() {
				return new RuleBasedPartitionScanner();
			}

			@Override
			protected String[] getPartitionTypes() {
				return IShellPartitions.CONTENT_TYPES;
			}

			@Override
			protected String getNatureId() {
				return ShelledNature.SHELLED_NATURE;
			}

		};
	}

}
