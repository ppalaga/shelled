package net.sourceforge.shelled.ui.completion;

import org.eclipse.dltk.ui.text.completion.ScriptCompletionProposalCollector;
import org.eclipse.dltk.ui.text.completion.ScriptCompletionProposalComputer;
import org.eclipse.dltk.ui.text.completion.ScriptContentAssistInvocationContext;
import org.eclipse.jface.text.templates.TemplateCompletionProcessor;

public class ShellCompletionProposalComputer extends
		ScriptCompletionProposalComputer {

	public ShellCompletionProposalComputer() {

	}

	@Override
	protected ScriptCompletionProposalCollector createCollector(
			ScriptContentAssistInvocationContext context) {
		return new ShellCompletionProposalCollector(context.getSourceModule());
	}

	@Override
	protected TemplateCompletionProcessor createTemplateProposalComputer(
			ScriptContentAssistInvocationContext context) {
		return null;
	}
}
