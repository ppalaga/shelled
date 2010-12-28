package net.sourceforge.shelled.ui.wizards;

import net.sourceforge.shelled.core.ShelledNature;

import org.eclipse.dltk.ui.wizards.NewSourceModulePage;

public class NewShellScriptFilePage extends NewSourceModulePage {

	@Override
	protected String getPageTitle() {
		return "Shell Script";
	}

	@Override
	protected String getPageDescription() {
		return "Create a new Shell Script.";
	}

	@Override
	protected String getRequiredNature() {
		return ShelledNature.SHELLED_NATURE;
	}
}
