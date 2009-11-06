package net.sourceforge.shelled.ui.completion;

import net.sourceforge.shelled.ui.Activator;

import org.eclipse.dltk.ui.PreferenceConstants;
import org.eclipse.dltk.ui.text.completion.ScriptCompletionProposal;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.graphics.Image;

public class ShellCompletionProposal extends ScriptCompletionProposal {
	public ShellCompletionProposal(String replacementString,
			int replacementOffset, int replacementLength, Image image,
			String displayString, int relevance) {
		super(replacementString, replacementOffset, replacementLength, image,
				displayString, relevance);
	}

	public ShellCompletionProposal(String replacementString,
			int replacementOffset, int replacementLength, Image image,
			String displayString, int relevance, boolean isInDoc) {
		super(replacementString, replacementOffset, replacementLength, image,
				displayString, relevance, isInDoc);
	}

	@Override
	protected boolean isSmartTrigger(char trigger) {
		return false;
	}

	@Override
	protected boolean insertCompletion() {
		IPreferenceStore preference = Activator.getDefault()
				.getPreferenceStore();
		return preference
				.getBoolean(PreferenceConstants.CODEASSIST_INSERT_COMPLETION);
	}
}