/* $Id: WhitespaceDetector.java,v 1.1 2004/08/17 19:39:49 dougsatch Exp $ */
package net.sourceforge.shelled.ui.text;

import org.eclipse.jface.text.rules.IWhitespaceDetector;

/**
 * 
 * @author Doug Satchwell
 * @version $Id: WhitespaceDetector.java,v 1.1 2004/08/17 19:39:49 dougsatch Exp
 *          $
 */
public class WhitespaceDetector implements IWhitespaceDetector {

	/**
	 * @see IWhitespaceDetector#isWhitespace
	 */
	public boolean isWhitespace(char c) {
		return Character.isWhitespace(c);
	}
}
