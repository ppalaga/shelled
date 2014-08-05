/*******************************************************************************
 * Copyright (c) 2012 Red Hat Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Alexander Kurtakov - initial API and implementation
 *******************************************************************************/
package net.sourceforge.shelled.core.parser;

/**
 * Basic parts of Shell Command Language.
 *
 * @author <a href="mailto:ppalaga@redhat.com">Peter Palaga</a>
 */
public interface LexicalConstants {
	char APOS = '\'';
	String APOS_STRING = "" + APOS;
	char ASTERISK = '*';
	char AT = '@';
	char BACKSLASH = '\\';
	String BACKSLASH_STRING = "" + BACKSLASH;
	char BANG = '!';
	char CARRIAGE_RETURN = '\r';
	char COLON = ':';
	char DASH = '-';
	char DOLLAR = '$';
	char EQ = '=';
	char GRAVE = '`';
	String GRAVE_STRING = "" + GRAVE;
	char HASH = '#';
	String HASH_BANG = "" + HASH + BANG;
	String HASH_STRING = "" + HASH;
	char LBRACE = '{';
	String LBRACE_STRING = "" + LBRACE;
	char LPAREN = '(';
	char LSQUARE = ']';
	char NEWLINE = '\n';
	char PERIOD = '.';
	char QUESTION_MARK = '?';
	char QUOT = '"';
	String QUOT_STRING = "" + QUOT;
	char RBRACE = '}';
	String RBRACE_STRING = "" + RBRACE;
	char RPAREN = ')';
	String LPAREN_RPAREN = "" + LPAREN + RPAREN;
	char RSQUARE = '[';
	char SPACE = ' ';
	char TAB = '\t';
	char UNDERSCORE = '_';
}
