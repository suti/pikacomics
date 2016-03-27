package com.lixiangyu.java.pika;

import java.io.File;

import javax.swing.SwingUtilities;

public class Main {
	Frame frame;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		if (!new File("./pika_data").exists()) {
			new PikaLog().creatPika();
		}
		
		if (!new File("./pika_config").exists()) {
			new PikaLog().creatConfig();
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame frame = new Frame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}