package net.sourceforge.shelled.ui.wizards;

import net.sourceforge.shelled.core.ShelledNature;

import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.wizards.ProjectWizard;
import org.eclipse.dltk.ui.wizards.ProjectWizardFirstPage;
import org.eclipse.dltk.ui.wizards.ProjectWizardSecondPage;

public class ShellScriptProjectWizard extends ProjectWizard {
	private ProjectWizardFirstPage firstPage;
	private ProjectWizardSecondPage secondPage;

	public ShellScriptProjectWizard() {
		setWindowTitle("New Shell Script Project");
		setDialogSettings(DLTKUIPlugin.getDefault().getDialogSettings());
	}

	@Override
	public String getScriptNature() {
		return ShelledNature.SHELLED_NATURE;
	}

	@Override
	public void addPages() {
		super.addPages();
		firstPage = new ProjectWizardFirstPage() {

			@Override
			protected boolean interpeterRequired() {
				return false;
			}
		};

		// First page
		firstPage.setTitle("Shell Script Project");
		firstPage.setDescription("Create a new Shell Script project.");
		addPage(firstPage);

		// Second page
		secondPage = new ProjectWizardSecondPage(firstPage);
		addPage(secondPage);
	}
}
