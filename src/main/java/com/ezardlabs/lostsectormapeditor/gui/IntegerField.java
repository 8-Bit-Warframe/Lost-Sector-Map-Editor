package com.ezardlabs.lostsectormapeditor.gui;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class IntegerField extends JTextField {

	public IntegerField() {
		super();
	}

	public IntegerField(int number) {
		super(String.valueOf(number));
	}

	protected Document createDefaultModel() {
		return new IntegerDocument();
	}

	private static class IntegerDocument extends PlainDocument {

		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
			if (str == null) {
				return;
			}
			char[] chars = str.toCharArray();
			boolean ok = true;
			for (char c : chars) {
				try {
					Integer.parseInt(String.valueOf(c));
				} catch (NumberFormatException exc) {
					ok = false;
					break;
				}
			}
			if (ok) super.insertString(offs, new String(chars), a);
		}
	}
}
