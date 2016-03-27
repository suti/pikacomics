package com.lixiangyu.java.pika;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class JsonDM{
//	private List<Integer> listId = new ArrayList<Integer>();
	private List<List<String>> imageList = new ArrayList<List<String>>();
	private String url;
	public JsonDM(List<Integer> listId) {
//		this.listId=listId;
		for(int i = 0;i<listId.size();i++){
			url="http://picaman.picacomic.com/api/comics/"+listId.get(i).toString()+"/ep/";
			int epNum = jsonReadComicEp(
					new UrlPaser(
							"http://picaman.picacomic.com/api/comics/"+listId.get(i).toString()).getContent());
			for (int j = 1; j < epNum+1; j++) {
				String u = url+j;
				imageList.add(jsonReadComicUrl(new UrlPaser(u).getContent()));
			}
		}
	}
	
	public List<List<String>> getImageList() {
		return imageList;
	}
	
	public List<String> jsonReadComicUrl(String urlContent){
		List<String> lists = new ArrayList<String>();
		try {
			JsonParser parser = new JsonParser();
			JsonArray jsonArray = (JsonArray) parser.parse(urlContent);
			for(int i=0;i<jsonArray.size();i++){
				JsonObject jObject = jsonArray.get(i).getAsJsonObject();
				lists.add(jObject.get("url").getAsString());
			}
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		return lists;
	}

	public int jsonReadComicEp(String epContent){
		int epNum = 0;
		try {
			JsonParser parser = new JsonParser();
			JsonObject jsonObject = (JsonObject) parser.parse(epContent);
			epNum = jsonObject.get("ep_count").getAsInt();
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		System.out.println("ep："+epNum);
		return epNum;
	}

}
