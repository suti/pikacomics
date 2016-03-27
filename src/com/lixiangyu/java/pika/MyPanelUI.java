package com.lixiangyu.java.pika;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MyPanelUI extends JPanel{
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D graphics2d = (Graphics2D) g;
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		graphics2d.setColor(new Color(255,255,255,140));
		graphics2d.fillRoundRect(2, 2, getWidth()-5, getHeight()-5, 20, 20);
		
		graphics2d.setClip(0,0,getWidth(),30);
		graphics2d.setColor(Color.WHITE);
		graphics2d.fillRoundRect(1, 2, getWidth()-2, getHeight()-1, 20, 20);
		graphics2d.setClip(null);
		
		graphics2d.setColor(Color.PINK);
		graphics2d.setStroke(new BasicStroke(4));
		graphics2d.drawRoundRect(2, 2, getWidth()-5, getHeight()-5, 20, 20);
		
		graphics2d.setFont(new Font("Arial", Font.BOLD, 16));
		graphics2d.setColor(Color.PINK);
		graphics2d.drawString("哔咔下载器", 15, 24);
		
	}

}
