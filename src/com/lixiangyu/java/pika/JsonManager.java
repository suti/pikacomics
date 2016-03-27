package com.lixiangyu.java.pika;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class JsonManager {

	private String content;
	public JsonManager(String content) {
		this.content=content;
	}


	public String jsonReadCategories(){
		String sqls=null;
		List<String> lists0=new ArrayList<String>();
		List<String> lists1=new ArrayList<String>();
		List<String> lists2=new ArrayList<String>();
		List<String> lists3=new ArrayList<String>();
		try {
			String str;
			JsonParser parser = new JsonParser();
			JsonArray jsonArray = (JsonArray) parser.parse(content);
			for(int i=0;i<jsonArray.size();i++){
				JsonObject object = jsonArray.get(i).getAsJsonObject();
				str=object.get("all_comics").getAsString();
				lists0.add(str);
				str=object.get("cover_image").getAsString();
				lists1.add(str);
				str=object.get("id").getAsString();
				lists2.add(str);
				str=object.get("name").getAsString();
				lists3.add(str);
			}
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}	
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("insert into Categories (all_comics,cover_image,id,name) values");
		for (int i = 0; i < lists0.size(); i++) {
			sBuffer.append("('"+
					lists0.get(i).toString()+
					"','"+
					lists1.get(i).toString()+
					"','"+
					lists2.get(i).toString()+
					"','"+
					lists3.get(i).toString()+
					"'),");
		}
		sBuffer.deleteCharAt(sBuffer.length()-1);
		sqls=sBuffer.toString();
		return sqls;
	}


	public String jsonReadComic(){
		String sqls=null;
		List<String> lists0=new ArrayList<String>();
		List<String> lists1=new ArrayList<String>();
		List<String> lists2=new ArrayList<String>();
		List<String> lists3=new ArrayList<String>();
		List<String> lists4=new ArrayList<String>();
		List<String> lists5=new ArrayList<String>();
		List<String> lists6=new ArrayList<String>();
		try {
			String str;
			JsonParser parser = new JsonParser();
			JsonArray jsonArray = (JsonArray) parser.parse(content);
			if (jsonArray.size()!=0) {
				for(int i=0;i<jsonArray.size();i++){
					JsonObject object = jsonArray.get(i).getAsJsonObject();
					str=(object.get("author").getAsString()).replace("\'", "\\'");
					lists0.add(str);
					str=object.get("cats").getAsString();
					lists1.add(str);
					str=object.get("cover_image").getAsString();
					lists2.add(str);
					str=object.get("id").getAsString();
					lists3.add(str);
					str=(object.get("name").getAsString()).replace("\'", "\\'");
					lists4.add(str);
					str=object.get("rank").getAsString(); 
					lists5.add(str);													
					str=object.get("total_page").getAsString();         
					lists6.add(str);
				}
				StringBuffer sBuffer = new StringBuffer();
				sBuffer.append("insert into comics (author,cats,cover_image,cid,name,rank,total_page) values");
				for (int i = 0; i < lists0.size(); i++) {
					sBuffer.append("('"+
							lists0.get(i).toString()+
							"','"+
							lists1.get(i).toString()+
							"','"+
							lists2.get(i).toString()+
							"','"+
							lists3.get(i).toString()+
							"','"+
							lists4.get(i).toString()+
							"','"+
							lists5.get(i).toString()+
							"','"+
							lists6.get(i).toString()+
							"'),");
				}
				sBuffer.deleteCharAt(sBuffer.length()-1);
				sqls=sBuffer.toString();
				System.out.println(sqls);
			}else {
				sqls=null;
			}
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}	
		return sqls;
	}

}
