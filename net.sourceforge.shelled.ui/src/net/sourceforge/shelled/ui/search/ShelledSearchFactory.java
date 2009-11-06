package net.sourceforge.shelled.ui.search;

import org.eclipse.dltk.core.search.AbstractSearchFactory;
import org.eclipse.dltk.core.search.IMatchLocatorParser;
import org.eclipse.dltk.core.search.matching.MatchLocator;

public class ShelledSearchFactory extends AbstractSearchFactory {
	public IMatchLocatorParser createMatchParser(MatchLocator locator) {
		return new ShelledMatchLocationParser(locator);
	}
}