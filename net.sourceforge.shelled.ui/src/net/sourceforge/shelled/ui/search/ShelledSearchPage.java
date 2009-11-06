package net.sourceforge.shelled.ui.search;

import net.sourceforge.shelled.core.ShellScriptLanguageToolkit;

import org.eclipse.dltk.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.ui.search.ScriptSearchPage;

public class ShelledSearchPage extends ScriptSearchPage {
	@Override
	protected IDLTKLanguageToolkit getLanguageToolkit() {
		return ShellScriptLanguageToolkit.getDefault();
	}
}