package net.sourceforge.shelled.ui.wizards;

import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.wizards.NewSourceModulePage;
import org.eclipse.dltk.ui.wizards.NewSourceModuleWizard;

public class NewShellScriptFileWizard extends NewSourceModuleWizard {

	public NewShellScriptFileWizard() {
		setWindowTitle("New Shell Script");
		setDialogSettings(DLTKUIPlugin.getDefault().getDialogSettings());
	}

	@Override
	protected NewSourceModulePage createNewSourceModulePage() {
		return new NewShellScriptFilePage();
	}
}
