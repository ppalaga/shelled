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

import java.io.InputStream;

import net.sourceforge.shelled.ui.Activator;
import net.sourceforge.shelled.ui.IShellColorConstants;
import net.sourceforge.shelled.ui.editor.ShellDocumentSetupParticipant;
import net.sourceforge.shelled.ui.text.IShellPartitions;

import org.eclipse.dltk.internal.ui.editor.ScriptSourceViewer;
import org.eclipse.dltk.ui.preferences.AbstractScriptEditorColoringConfigurationBlock;
import org.eclipse.dltk.ui.preferences.OverlayPreferenceStore;
import org.eclipse.dltk.ui.text.IColorManager;
import org.eclipse.dltk.ui.text.ScriptSourceViewerConfiguration;
import org.eclipse.dltk.ui.text.ScriptTextTools;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.IOverviewRuler;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.texteditor.ITextEditor;

public class ShellColoringConfigurationBlock extends
		AbstractScriptEditorColoringConfigurationBlock {

	private static final String PREVIEW_FILE_NAME = "PreviewFile.txt"; //$NON-NLS-1$

	private static final String[][] fSyntaxColorListModel = new String[][] {
			{ "Comments", IShellColorConstants.SHELL_COMMENT, sCommentsCategory },
			{ "Hashbang", IShellColorConstants.SHELL_HASHBANG,
					sCommentsCategory },
			{ "TODO tags", IShellColorConstants.SHELL_TODO_TAG,
					sCommentsCategory },

			{ "Eval", IShellColorConstants.SHELL_EVAL, sCoreCategory },
			{ "Double quoted text", IShellColorConstants.SHELL_DOUBLE_QUOTE,
					sCoreCategory },
			{ "Function", IShellColorConstants.SHELL_FUNCTION, sCoreCategory },

			{ "Keyword", IShellColorConstants.SHELL_KEYWORD, sCoreCategory },

			{ "Single quoted text", IShellColorConstants.SHELL_SINGLE_QUOTE,
					sCoreCategory },
			{ "Variables", IShellColorConstants.SHELL_VARIABLE, sCoreCategory },
			{ "Commands", IShellColorConstants.SHELL_COMMAND, sCoreCategory },
			{ "Default", IShellColorConstants.SHELL_DEFAULT, sCoreCategory }

	};

	public ShellColoringConfigurationBlock(OverlayPreferenceStore store) {
		super(store);
	}

	@Override
	protected String[][] getSyntaxColorListModel() {
		return fSyntaxColorListModel;
	}

	@Override
	protected ProjectionViewer createPreviewViewer(Composite parent,
			IVerticalRuler verticalRuler, IOverviewRuler overviewRuler,
			boolean showAnnotationsOverview, int styles, IPreferenceStore store) {
		return new ScriptSourceViewer(parent, verticalRuler, overviewRuler,
				showAnnotationsOverview, styles, store);
	}

	@Override
	protected ScriptSourceViewerConfiguration createSimpleSourceViewerConfiguration(
			IColorManager colorManager, IPreferenceStore preferenceStore,
			ITextEditor editor, boolean configureFormatter) {
		return new SimpleShellSourceViewerConfiguration(colorManager,
				preferenceStore, editor, IShellPartitions.SHELL_PARTITIONING,
				true);
	}

	@Override
	protected void setDocumentPartitioning(IDocument document) {
		ShellDocumentSetupParticipant participant = new ShellDocumentSetupParticipant();
		participant.setup(document);
	}

	@Override
	protected InputStream getPreviewContentReader() {
		return getClass().getResourceAsStream(PREVIEW_FILE_NAME);
	}

	@Override
	protected ScriptTextTools getTextTools() {
		return Activator.getDefault().getTextTools();
	}
}