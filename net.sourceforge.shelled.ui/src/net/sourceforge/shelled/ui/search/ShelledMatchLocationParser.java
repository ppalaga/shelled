/*******************************************************************************
 * Copyright (c) 2009 Red Hat Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Alexander Kurtakov - initial API and implementation
 *******************************************************************************/
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
