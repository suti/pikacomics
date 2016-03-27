package com.lixiangyu.java.pika;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Callable;

public class DownLoad implements Callable<Integer>{
	private String downloadUrl;
	private String path;
	private int id;
	public DownLoad(String downloadUrl,String path,int id) {
		this.downloadUrl=downloadUrl;
		this.path = path;
		this.id = id;
	}


	@Override
	public Integer call() throws Exception {
		String[] tempurls = downloadUrl.replace("http://picacomic.com/assets/", "").split("/");
		String tempurl = "/"+tempurls[0]+"/"+id;
		for (int i = 3; i < tempurls.length; i++) {
			tempurl+="/"+tempurls[i];
		}
		System.out.println(tempurl);
		File file = new File(path+tempurl);
		if(!file.exists()){
			String documt = new File(file.getAbsolutePath()).getParent();
			File filep = new File(documt);
			if (!filep.exists()) {
				filep.mkdirs();
			}
			try {
				long t= System.currentTimeMillis();
				URL url =new URL(downloadUrl);
				URLConnection connection =url.openConnection();
				InputStream is =connection.getInputStream();
				BufferedInputStream bis =new BufferedInputStream(is);
				FileOutputStream fos =new FileOutputStream(file);
				BufferedOutputStream bos  =new BufferedOutputStream(fos);
				byte input[] =new byte[1024];
				int l;
				while ((l=bis.read(input))!=-1) {
					bos.write(input,0,l);
				}
				bis.close();
				is.close();
				bos.close();
				fos.close();
				System.out.println("UsedTime:"+(System.currentTimeMillis()-t)	+"毫秒");
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("-----file:"+file.getName()+"---exists!");
		}
		return 1;
	}

}
