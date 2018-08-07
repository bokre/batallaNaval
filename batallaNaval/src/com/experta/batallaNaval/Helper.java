package com.experta.batallaNaval;

import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComponent;

public class Helper {

	protected static int getRandom(int min, int max) {
		Random num = new Random();
		return (num.nextInt((max - min) + 1) + min);
	}

	protected static void resizeProportional(JComponent c) {
		c.setSize(c.getWidth() / 2, c.getHeight() / 2);
		c.revalidate();
		c.repaint();
	}

	protected static void removeActionListener(JButton b) {

		ActionListener[] al = b.getActionListeners();
		for (int i = 0; i < al.length; i++) {
			b.removeActionListener(al[i]);
		}
	}

}
