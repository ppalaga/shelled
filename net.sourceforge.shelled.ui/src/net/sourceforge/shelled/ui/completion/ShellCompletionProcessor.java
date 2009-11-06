package net.sourceforge.shelled.ui.completion;

import net.sourceforge.shelled.core.ShelledNature;
import net.sourceforge.shelled.ui.Activator;

import org.eclipse.dltk.ui.text.completion.CompletionProposalLabelProvider;
import org.eclipse.dltk.ui.text.completion.ScriptCompletionProcessor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.ui.IEditorPart;

public class ShellCompletionProcessor extends ScriptCompletionProcessor {
	public ShellCompletionProcessor(IEditorPart editor,
			ContentAssistant assistant, String partition) {
		super(editor, assistant, partition);
	}

	@Override
	protected String getNatureId() {
		return ShelledNature.SHELLED_NATURE;
	}

	@Override
	protected CompletionProposalLabelProvider getProposalLabelProvider() {
		return new CompletionProposalLabelProvider();
	}

	@Override
	protected IPreferenceStore getPreferenceStore() {
		return Activator.getDefault().getPreferenceStore();
	}
}