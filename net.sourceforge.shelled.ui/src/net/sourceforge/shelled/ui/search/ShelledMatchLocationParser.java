package net.sourceforge.shelled.ui.search;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.core.search.matching.MatchLocator;
import org.eclipse.dltk.core.search.matching.MatchLocatorParser;
import org.eclipse.dltk.core.search.matching.PatternLocator;

public class ShelledMatchLocationParser extends MatchLocatorParser {
	protected ShelledMatchLocationParser(MatchLocator locator) {
		super(locator);
	}

	@Override
	protected void processStatement(ASTNode node, PatternLocator locator) {
		super.processStatement(node, locator);
	}
}
