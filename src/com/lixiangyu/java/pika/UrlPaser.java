package com.lixiangyu.java.pika;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class UrlPaser {
	private String content = null;
	String urls = null;

	public UrlPaser(String urls) {
		this.urls=urls;
		urlPaser();
	}
	
	public String getContent() {
		return content;
	}

	public void urlPaser() {
		try {
			URL url =new URL(urls);
			System.out.println("URL pasering...\n");
			URLConnection connection =url.openConnection();
			InputStream is =connection.getInputStream();
			InputStreamReader isr =new InputStreamReader(is,"UTF-8");
			BufferedReader br =new BufferedReader(isr);
			String line;
			StringBuilder builder =new StringBuilder();
			while((line=br.readLine())!=null){
				builder.append(line);
			}
			br.close();
			isr.close();
			is.close();
			System.out.println("URL paser done!\n");
			content=builder.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
