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
package net.sourceforge.shelled.ui.text.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.sourceforge.shelled.ui.text.DollarDetector;

import org.junit.BeforeClass;
import org.junit.Test;

public class DollarDetectorTest {

	private static DollarDetector detector;

	@BeforeClass
	public static void prepare() {
		detector = new DollarDetector();
	}

	@Test
	public void testIsWordPart() {
		assertTrue(detector.isWordPart('['));
		assertTrue(detector.isWordPart(']'));
	}

	@Test
	public void testIsWordStart() {
		assertFalse(detector.isWordStart('['));
		assertFalse(detector.isWordStart(']'));
		assertFalse(detector.isWordStart('a'));
		assertTrue(detector.isWordStart('$'));
	}

}
