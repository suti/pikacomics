package com.lixiangyu.java.pika;


public class TestMain {

	public static void main(String[] args) {
		PikaLog pikaLog = new PikaLog();
//		pikaLog.creatPika();
		pikaLog.setProgress(0, 7348);
		System.out.println(pikaLog.getPikaProgress(0));
	}

}