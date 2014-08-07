/*******************************************************************************
 * Copyright (c) 2009-2010 Red Hat Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Alexander Kurtakov - initial API and implementation
 *******************************************************************************/
package net.sourceforge.shelled.ui.editor;

import java.util.ArrayList;

import net.sourceforge.shelled.core.parser.LexicalConstants;
import net.sourceforge.shelled.core.parser.ReservedWord;
import net.sourceforge.shelled.ui.Activator;
import net.sourceforge.shelled.ui.IShellColorConstants;
import net.sourceforge.shelled.ui.ShellContentAssistPreference;
import net.sourceforge.shelled.ui.completion.ShellCompletionProcessor;
import net.sourceforge.shelled.ui.text.DoubleQuoteScanner;
import net.sourceforge.shelled.ui.text.EvalScanner;
import net.sourceforge.shelled.ui.text.IShellPartitions;
import net.sourceforge.shelled.ui.text.IndentType;
import net.sourceforge.shelled.ui.text.ScriptAutoIndentStrategy;
import net.sourceforge.shelled.ui.text.ShellCodeScanner;
import net.sourceforge.shelled.ui.text.WhitespaceDetector;

import org.eclipse.dltk.ui.text.AbstractScriptScanner;
import org.eclipse.dltk.ui.text.IColorManager;
import org.eclipse.dltk.ui.text.ScriptOutlineInformationControl;
import org.eclipse.dltk.ui.text.ScriptPresentationReconciler;
import org.eclipse.dltk.ui.text.ScriptSourceViewerConfiguration;
import org.eclipse.dltk.ui.text.SingleTokenScriptScanner;
import org.eclipse.dltk.ui.text.completion.ContentAssistPreference;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.texteditor.ITextEditor;

public class ShellSourceViewerConfiguration extends
ScriptSourceViewerConfiguration {

	private static IRule getKeywords(IToken keywordToken, final String[] words,
			IToken defaultToken) {
		WordRule wordL = new WordRule(new IWordDetector() {

			@Override
			public boolean isWordPart(char c) {
				return !Character.isWhitespace(c);
			}

			@Override
			public boolean isWordStart(char c) {
				return !Character.isWhitespace(c);
			}
		}, defaultToken);

		for (String word : words)
			wordL.addWord(word, keywordToken);
		return wordL;
	}

	// Scanners for all the various content types provided by the partitioner
	private AbstractScriptScanner fCodeScanner;
	private AbstractScriptScanner fCommentScanner;
	private AbstractScriptScanner fDoubleQuoteScanner;
	private AbstractScriptScanner fParamScanner;
	private AbstractScriptScanner fEvalScanner;
	private AbstractScriptScanner fHashbangScanner;
	private AbstractScriptScanner fFunctionScanner;
	private AbstractScriptScanner fSingleQuoteScanner;

	public ShellSourceViewerConfiguration(IColorManager colorManager,
			IPreferenceStore preferenceStore, ITextEditor editor,
			String partitioning) {
		super(colorManager, preferenceStore, editor, partitioning);
	}

	@Override
	public IAutoEditStrategy[] getAutoEditStrategies(
			ISourceViewer sourceViewer, String contentType) {
		ScriptAutoIndentStrategy strategy = new ScriptAutoIndentStrategy();

		ArrayList<IRule> rules = new ArrayList<IRule>();
		rules.add(new WhitespaceRule(new WhitespaceDetector()));
		rules.add(getKeywords(new Token(IndentType.INCREMENT), new String[] {
			ReservedWord.DO.token(), ReservedWord.CASE.token(),
			LexicalConstants.LBRACE_STRING, ReservedWord.THEN.token() },
			Token.UNDEFINED));
		rules.add(getKeywords(new Token(IndentType.DECREMENT), new String[] {
			ReservedWord.DONE.token(), ReservedWord.ESAC.token(),
				LexicalConstants.RBRACE_STRING, ReservedWord.FI.token() },
				Token.UNDEFINED));
		rules.add(getKeywords(new Token(IndentType.INFLEXION),
				new String[] { ReservedWord.ELSE.token() }, Token.UNDEFINED));

		strategy.setRules(rules.toArray(new IRule[0]));

		return new IAutoEditStrategy[] { strategy };
	}

	@Override
	protected ContentAssistPreference getContentAssistPreference() {
		return ShellContentAssistPreference.getDefault();
	}

	@Override
	protected IInformationControlCreator getOutlinePresenterControlCreator(
			ISourceViewer sourceViewer, final String commandId) {
		return new IInformationControlCreator() {
			@Override
			public IInformationControl createInformationControl(Shell parent) {
				int shellStyle = SWT.RESIZE;
				int treeStyle = SWT.V_SCROLL | SWT.H_SCROLL;
				return new ScriptOutlineInformationControl(parent, shellStyle,
						treeStyle, commandId, Activator.getDefault()
						.getPreferenceStore());
			}
		};
	}

	@Override
	public IPresentationReconciler getPresentationReconciler(
			ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new ScriptPresentationReconciler();
		reconciler.setDocumentPartitioning(this
				.getConfiguredDocumentPartitioning(sourceViewer));

		DefaultDamagerRepairer dr;

		dr = new DefaultDamagerRepairer(this.fCodeScanner);
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

		dr = new DefaultDamagerRepairer(this.fHashbangScanner);
		reconciler.setDamager(dr, IShellPartitions.HASHBANG_CONTENT_TYPE);
		reconciler.setRepairer(dr, IShellPartitions.HASHBANG_CONTENT_TYPE);

		dr = new DefaultDamagerRepairer(this.fParamScanner);
		reconciler.setDamager(dr, IShellPartitions.PARAM_CONTENT_TYPE);
		reconciler.setRepairer(dr, IShellPartitions.PARAM_CONTENT_TYPE);

		dr = new DefaultDamagerRepairer(this.fFunctionScanner);
		reconciler.setDamager(dr, IShellPartitions.FUNCTION_CONTENT_TYPE);
		reconciler.setRepairer(dr, IShellPartitions.FUNCTION_CONTENT_TYPE);

		dr = new DefaultDamagerRepairer(this.fSingleQuoteScanner);
		reconciler.setDamager(dr, IShellPartitions.SINGLE_QUOTE_CONTENT_TYPE);
		reconciler.setRepairer(dr, IShellPartitions.SINGLE_QUOTE_CONTENT_TYPE);

		dr = new DefaultDamagerRepairer(this.fDoubleQuoteScanner);
		reconciler.setDamager(dr, IShellPartitions.DOUBLE_QUOTE_CONTENT_TYPE);
		reconciler.setRepairer(dr, IShellPartitions.DOUBLE_QUOTE_CONTENT_TYPE);

		dr = new DefaultDamagerRepairer(this.fEvalScanner);
		reconciler.setDamager(dr, IShellPartitions.EVAL_CONTENT_TYPE);
		reconciler.setRepairer(dr, IShellPartitions.EVAL_CONTENT_TYPE);

		dr = new DefaultDamagerRepairer(this.fCommentScanner);
		reconciler.setDamager(dr, IShellPartitions.COMMENT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IShellPartitions.COMMENT_CONTENT_TYPE);

		return reconciler;
	}

	// This method called from base class.
	@Override
	protected void initializeScanners() {
		// This is our code scanner
		this.fCodeScanner = new ShellCodeScanner(this.getColorManager(),
				this.fPreferenceStore);
		// This is default scanners for partitions with same color.
		this.fFunctionScanner = new SingleTokenScriptScanner(
				this.getColorManager(), this.fPreferenceStore,
				IShellColorConstants.SHELL_FUNCTION);
		this.fHashbangScanner = new SingleTokenScriptScanner(
				this.getColorManager(), this.fPreferenceStore,
				IShellColorConstants.SHELL_HASHBANG);
		this.fSingleQuoteScanner = new SingleTokenScriptScanner(
				this.getColorManager(), this.fPreferenceStore,
				IShellColorConstants.SHELL_SINGLE_QUOTE);
		this.fDoubleQuoteScanner = new DoubleQuoteScanner(
				this.getColorManager(), this.fPreferenceStore);
		this.fParamScanner = new SingleTokenScriptScanner(
				this.getColorManager(), this.fPreferenceStore,
				IShellColorConstants.SHELL_VARIABLE);
		this.fEvalScanner = new EvalScanner(this.getColorManager(),
				this.fPreferenceStore);
		this.fCommentScanner = createCommentScanner(
				IShellColorConstants.SHELL_COMMENT,
				IShellColorConstants.SHELL_TODO_TAG);
	}

	@Override
	protected void alterContentAssistant(ContentAssistant assistant) {
		IContentAssistProcessor scriptProcessor = new ShellCompletionProcessor(
				getEditor(), assistant, IDocument.DEFAULT_CONTENT_TYPE);
		assistant.setContentAssistProcessor(scriptProcessor,
				IDocument.DEFAULT_CONTENT_TYPE);
	}

	@Override
	public boolean affectsTextPresentation(PropertyChangeEvent event) {
		return fCodeScanner.affectsBehavior(event)
				|| fCommentScanner.affectsBehavior(event)
				|| fDoubleQuoteScanner.affectsBehavior(event)
				|| fSingleQuoteScanner.affectsBehavior(event)
				|| fFunctionScanner.affectsBehavior(event)
				|| fParamScanner.affectsBehavior(event)
				|| fEvalScanner.affectsBehavior(event)
				|| fHashbangScanner.affectsBehavior(event);
	}

	@Override
	public void handlePropertyChangeEvent(PropertyChangeEvent event) {
		if (fCodeScanner.affectsBehavior(event))
			fCodeScanner.adaptToPreferenceChange(event);
		if (fCommentScanner.affectsBehavior(event))
			fCommentScanner.adaptToPreferenceChange(event);
		if (fDoubleQuoteScanner.affectsBehavior(event))
			fDoubleQuoteScanner.adaptToPreferenceChange(event);
		if (fSingleQuoteScanner.affectsBehavior(event))
			fSingleQuoteScanner.adaptToPreferenceChange(event);
		if (fFunctionScanner.affectsBehavior(event))
			fFunctionScanner.adaptToPreferenceChange(event);
		if (fParamScanner.affectsBehavior(event))
			fParamScanner.adaptToPreferenceChange(event);
		if (fEvalScanner.affectsBehavior(event))
			fEvalScanner.adaptToPreferenceChange(event);
		if (fHashbangScanner.affectsBehavior(event))
			fHashbangScanner.adaptToPreferenceChange(event);
	}

}
