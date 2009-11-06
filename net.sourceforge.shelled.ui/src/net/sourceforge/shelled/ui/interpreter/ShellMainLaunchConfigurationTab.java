package net.sourceforge.shelled.ui.interpreter;

import net.sourceforge.shelled.core.ShelledNature;

import org.eclipse.dltk.core.PreferencesLookupDelegate;
import org.eclipse.dltk.debug.ui.launchConfigurations.MainLaunchConfigurationTab;

public class ShellMainLaunchConfigurationTab extends MainLaunchConfigurationTab {

	public ShellMainLaunchConfigurationTab(String mode) {
		super(mode);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean breakOnFirstLinePrefEnabled(
			PreferencesLookupDelegate delegate) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean dbpgLoggingPrefEnabled(PreferencesLookupDelegate delegate) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected String getNatureID() {
		return ShelledNature.SHELLED_NATURE;
	}

}
