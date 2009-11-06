package net.sourceforge.shelled.ui;

import net.sourceforge.shelled.core.ShelledNature;

import org.eclipse.dltk.launching.AbstractScriptLaunchConfigurationDelegate;

public class LaunchConfigurationDelegate extends
		AbstractScriptLaunchConfigurationDelegate {

	@Override
	public String getLanguageId() {
		return ShelledNature.SHELLED_NATURE;
	}

}
