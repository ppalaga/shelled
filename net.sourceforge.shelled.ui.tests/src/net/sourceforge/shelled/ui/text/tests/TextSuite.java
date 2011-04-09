/*******************************************************************************
 * Copyright (c) 2009-2011 Red Hat Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Alexander Kurtakov - initial API and implementation
 *******************************************************************************/
package net.sourceforge.shelled.ui.text.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ AssignmentRuleTest.class,
		DollarBraceCountingRuleTest.class, DollarRuleTest.class, DoubleQuoteScannerTest.class,
		EvalScannerTest.class })
public class TextSuite {
}
