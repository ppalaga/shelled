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
package net.sourceforge.shelled.ui.completion;

import net.sourceforge.shelled.core.parser.LexicalConstants;

import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.ui.text.completion.ScriptCompletionProposal;
import org.eclipse.dltk.ui.text.completion.ScriptCompletionProposalCollector;
import org.eclipse.swt.graphics.Image;

public class ShellCompletionProposalCollector extends
ScriptCompletionProposalCollector {

	public ShellCompletionProposalCollector(ISourceModule module) {
		super(module);
	}

	@Override
	protected ScriptCompletionProposal createOverrideCompletionProposal(
			IScriptProject scriptProject, ISourceModule compilationUnit,
			String name, String[] paramTypes, int start, int length,
			String label, String string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ScriptCompletionProposal createScriptCompletionProposal(
			String completion, int replaceStart, int length, Image image,
			String displayString, int i) {
		return new ShellCompletionProposal(completion, replaceStart, length,
				image, displayString, i);

	}

	@Override
	protected ScriptCompletionProposal createScriptCompletionProposal(
			String completion, int replaceStart, int length, Image image,
			String displayString, int i, boolean isInDoc) {
		return new ShellCompletionProposal(completion, replaceStart, length,
				image, displayString, i);
	}

	@Override
	protected char[] getVarTrigger() {
		return new char[] { LexicalConstants.DOLLAR };
	}

	@Override
	protected String getNatureId() {
		// TODO Auto-generated method stub
		return null;
	}

}
