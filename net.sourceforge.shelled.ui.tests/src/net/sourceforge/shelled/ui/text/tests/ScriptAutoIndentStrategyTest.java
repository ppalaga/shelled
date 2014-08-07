/*******************************************************************************
 * Copyright (c) 2012 Red Hat Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Peter Palaga - initial API and implementation
 *******************************************************************************/
package net.sourceforge.shelled.ui.text.tests;

import net.sourceforge.shelled.ui.Activator;
import net.sourceforge.shelled.ui.text.ScriptAutoIndentStrategy;

import org.eclipse.dltk.ui.CodeFormatterConstants;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IDocument;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 */
@SuppressWarnings("javadoc")
public class ScriptAutoIndentStrategyTest {
	enum IndentMode {
		/**  */
		TAB(CodeFormatterConstants.TAB, "\t", 4, 4),

		/**  */
		SPACES(CodeFormatterConstants.SPACE, "    ", 4, 4);
		public final String pref;
		public final String string;
		public final int indentSize;
		public final int tabDisplaySize;

		/**
		 * @param pref
		 * @param string
		 * @param indentSize
		 * @param tabDisplaySize
		 */
		private IndentMode(String pref, String string, int indentSize,
				int tabDisplaySize) {
			this.pref = pref;
			this.string = string;
			this.indentSize = indentSize;
			this.tabDisplaySize = tabDisplaySize;
		}
	}

	private static class Edit {
		private final String initialScript;

		/**
		 * @param initialScript
		 */
		public Edit(String initialScript) {
			super();
			this.initialScript = initialScript;
		}

		private String insertAfter;
		private String edit;
		private String lastScript;

		public Edit append(String edit) {
			this.insertAfter = null;
			this.edit = edit;
			return this;
		}

		/**
		 * @return {@link #command} clone
		 */
		public DocumentCommand createCommand() {
			if (insertAfter == null) {
				/* append */
				return new Cmd(lastScript.length(), 0, edit);
			} else {
				/* insert */
				int insertAt = lastScript.indexOf(insertAfter)
						+ insertAfter.length();
				return new Cmd(insertAt, 0, edit);
			}
		}

		/**
		 * @return
		 */
		public String getInitialScript(IndentMode mode) {
			this.lastScript = initialScript.replace("\t", mode.string);
			return lastScript;
		}

		/**
		 * @param string
		 * @param string2
		 * @return
		 */
		public Edit insertAfter(String edit, String insertAfter) {
			this.insertAfter = insertAfter;
			this.edit = edit;
			return this;
		}
	}

	/**
	 * We had to subclass {@link DocumentCommand} because it has no pubilc
	 * constructor.
	 *
	 * @author <a href="mailto:ppalaga@redhat.com">Peter Palaga</a>
	 */
	private static class Cmd extends DocumentCommand {

		/**
		 * @param offset
		 * @param length
		 * @param text
		 *
		 */
		public Cmd(int offset, int length, String text) {
			super();
			this.offset = offset;
			this.length = length;
			this.text = text;
		}

	}

	private ScriptAutoIndentStrategy strategy;
	private IPreferenceStore prefs;

	/**
	 *
	 */
	@Before
	public void before() {
		this.strategy = new ScriptAutoIndentStrategy();
		this.prefs = Activator.getDefault().getPreferenceStore();
	}

	private void verify(IndentMode mode, String initialScript,
			String expectedScript, DocumentCommand command)
			throws BadLocationException {
		prefs.setValue(CodeFormatterConstants.FORMATTER_TAB_CHAR, mode.pref);
		prefs.setValue(CodeFormatterConstants.FORMATTER_INDENTATION_SIZE,
				mode.indentSize);
		prefs.setValue(CodeFormatterConstants.FORMATTER_TAB_SIZE,
				mode.tabDisplaySize);
		IDocument d = new Document(initialScript);
		strategy.customizeDocumentCommand(d, command);
		d.replace(command.offset, command.length, command.text);
		Assert.assertEquals("Using " + IndentMode.class.getSimpleName() + "."
				+ mode, expectedScript, d.get());
	}

	private void verifyModes(Edit edit, String expectedScript)
			throws BadLocationException {
		for (IndentMode mode : IndentMode.values()) {
			expectedScript = expectedScript.replace("\t", mode.string);
			verify(mode, edit.getInitialScript(mode), expectedScript,
					edit.createCommand());
		}
	}

	/** Appending a newline after { should add one level indent */
	@Test
	public void testLBraceNewline() throws BadLocationException {
		/* Without initial indent */
		/* At the end of file */
		verifyModes(new Edit("#!/bin/bash\nf() {").append("\n"),
				"#!/bin/bash\nf() {\n\t");

		/* In the middle of a file */
		verifyModes(
				new Edit("f(){\n\ta='A'\n\tb='B'\n").insertAfter("\n", "f(){"),
				"f(){\n\t\n\ta='A'\n\tb='B'\n");

		/* With initial indent */
		/* At the end of file */
		verifyModes(new Edit("#!/bin/bash\n\tf() {").append("\n"),
				"#!/bin/bash\n\tf() {\n\t\t");

		/* In the middle of a file */
		verifyModes(new Edit("\tf(){\n\t\ta='A'\n\t\tb='B'\n").insertAfter(
				"\n", "f(){"), "\tf(){\n\t\t\n\t\ta='A'\n\t\tb='B'\n");

	}

	/**
	 * Closing block with } should decrease the indentation
	 *
	 * @throws BadLocationException
	 */
	public void testRBrace() throws BadLocationException {
		/* Without initial indent */
		/* At the end of file */
		verifyModes(new Edit("#!/bin/bash\nf() {\n\t").append("}"),
				"#!/bin/bash\nf() {\n}");

		/* In the middle of a file */
		verifyModes(new Edit("f(){\n\ta='A'\n\tb='B'\n").insertAfter("}",
				"a='A'\n"), "f(){\n\ta='A'\n}b='B'\n");
		verifyModes(new Edit("f(){\n\ta='A'\n\tb='B'\n").insertAfter("}",
				"a='A'\n\t"), "f(){\n\ta='A'\n}b='B'\n");
		verifyModes(new Edit("f(){\n\ta='A'\n\t\n\tb='B'\n").insertAfter("}",
				"a='A'\n\t"), "f(){\n\ta='A'\n}\n\tb='B'\n");

		/* With initial indent */
		/* At the end of file */
		verifyModes(new Edit("#!/bin/bash\n\tf() {\n\t\t").append("}"),
				"#!/bin/bash\n\tf() {\n\t}");

		/* In the middle of a file */
		verifyModes(new Edit("\tf(){\n\t\ta='A'\n\t\tb='B'\n").insertAfter("}",
				"a='A'\n"), "\tf(){\n\t\ta='A'\n\t}b='B'\n");
		verifyModes(new Edit("\tf(){\n\t\ta='A'\n\tb='B'\n").insertAfter("}",
				"a='A'\n\t"), "\tf(){\n\t\ta='A'\n\t}b='B'\n");
		verifyModes(new Edit("\tf(){\n\t\ta='A'\n\t\n\tb='B'\n").insertAfter(
				"}", "a='A'\n\t"), "\tf(){\n\t\ta='A'\n\t}\n\t\tb='B'\n");

	}

}
