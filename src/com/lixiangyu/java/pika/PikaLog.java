package com.lixiangyu.java.pika;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class PikaLog {
	
	public void creatPika() {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("isNew", "0");
		
		JsonArray jsonArray = new JsonArray();
		for (int i = 0; i < 31; i++) {
			JsonObject jsonObjectTemp = new JsonObject();
			jsonObjectTemp.addProperty("progress", "1");
			jsonArray.add(jsonObjectTemp);
		}
		
		jsonObject.add("updatedb",jsonArray);
		
		String content = jsonObject.toString();
		
		writePika(content);
	}
	
	public void creatConfig(){
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("schema_name", "pikapika_schema");
		jsonObject.addProperty("user", "root");
		jsonObject.addProperty("passwd", "");
		try {
			File file = new File("./pika_config");
			FileOutputStream fos =new FileOutputStream(file);
			OutputStreamWriter ops =new OutputStreamWriter(fos);
			BufferedWriter bw =new BufferedWriter(ops);
			bw.write(jsonObject.toString());
			bw.close();
			ops.close();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<String> getConfig(){
		File file = new File("./pika_config");
		StringBuffer stringBuffer = new StringBuffer();
		try {
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis,"UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String line;
			while ((line = br.readLine())!= null) {
				stringBuffer.append(line);
			}
			br.close();
			isr.close();
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = (JsonObject)jsonParser.parse(stringBuffer.toString());
		List<String> list = new ArrayList<String>();
		list.add(jsonObject.get("schema_name").getAsString());
		list.add(jsonObject.get("user").getAsString());
		list.add(jsonObject.get("passwd").getAsString());
		return list;
	}
	
	public int getPikaIsNew() {
		JsonObject jsonObject = readPika();
		int v = Integer.parseInt(jsonObject.get("isNew").getAsString());
		return v;
	}
	
	public int getPikaProgress(int i){
		JsonObject jsonObject = readPika();
		JsonArray jsonArray = jsonObject.get("updatedb").getAsJsonArray();
		JsonObject jsonObject2 = jsonArray.get(i).getAsJsonObject();
		int v = jsonObject2.get("progress").getAsInt();
		return v ;
	}
	
	public void setIsNew() {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("isNew", "1");
		
		JsonArray jsonArray = new JsonArray();
		for (int i = 0; i < 31; i++) {
			JsonObject jsonObjectTemp = new JsonObject();
			jsonObjectTemp.addProperty("progress", "1");
			jsonArray.add(jsonObjectTemp);
		}
		
		jsonObject.add("updatedb",jsonArray);
		
		String content = jsonObject.toString();
		
		writePika(content);
	}
	
	public void setProgress(int x,int y) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("isNew", "1");
		
		JsonArray jsonArray = new JsonArray();
		for (int i = 0; i < 31; i++) {
			JsonObject jsonObjectTemp = new JsonObject();
			if (i == x) {
				jsonObjectTemp.addProperty("progress", y);
			}else {
				jsonObjectTemp.addProperty("progress", "1");
			}
			jsonArray.add(jsonObjectTemp);
		}
		
		jsonObject.add("updatedb",jsonArray);
		
		String content = jsonObject.toString();
		
		writePika(content);
	}
	public JsonObject readPika() {
		File file = new File("./pika_data");
		StringBuffer stringBuffer = new StringBuffer();
		try {
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis,"UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String line;
			while ((line = br.readLine())!= null) {
				stringBuffer.append(line);
			}
			br.close();
			isr.close();
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = (JsonObject)jsonParser.parse(stringBuffer.toString());
		return jsonObject;
	}
	
	public void writePika(String content) {
		try {
			File file = new File("./pika_data");
			FileOutputStream fos =new FileOutputStream(file);
			OutputStreamWriter ops =new OutputStreamWriter(fos);
			BufferedWriter bw =new BufferedWriter(ops);
			bw.write(content);
			bw.close();
			ops.close();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
