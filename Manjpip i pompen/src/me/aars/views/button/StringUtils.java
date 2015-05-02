package me.aars.views.button;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class StringUtils {
	
	public static int findWidth(String s, Graphics g) {
		FontMetrics fontMetrics = g.getFontMetrics();
		int stringWidth = fontMetrics.stringWidth(s);
		return stringWidth;
	}

	public static int findHeight(String s, Graphics g) {
		FontMetrics fontMetrics = g.getFontMetrics();
		int stringHeight = fontMetrics.getHeight();
		return stringHeight;
	}
}
