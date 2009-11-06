package net.sourceforge.shelled.ui;

import net.sourceforge.shelled.core.ShelledNature;

import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.dltk.internal.debug.ui.launcher.AbstractScriptLaunchShortcut;

public class LaunchShortcut extends AbstractScriptLaunchShortcut {

	@Override
	protected ILaunchConfigurationType getConfigurationType() {
		return getLaunchManager().getLaunchConfigurationType(
				"net.sourceforge.shelled.ui.launchConfigurationType1");
	}

	@Override
	protected String getNatureId() {
		return ShelledNature.SHELLED_NATURE;
	}

}
