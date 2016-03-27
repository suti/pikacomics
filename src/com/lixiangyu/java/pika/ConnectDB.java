package com.lixiangyu.java.pika;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ConnectDB {
	public static Connection getConnection(){
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			List<String> list = new ArrayList<String>();
			list = new PikaLog().getConfig();
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/"+list.get(0).toString(),list.get(1).toString(),list.get(2).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public static int insertToDB(String sql) {
		int back = 0;
		Connection connection = getConnection();
		try {
			Statement statement = connection.createStatement();
			if (sql!=null) {
//				System.out.println(sql);
				back = statement.executeUpdate(sql);
				System.out.println("已写入了"+back+"条数据");
			}else {
				back=-1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return back;
	}
	
	public static List<Integer> readFormDB(int v) {
		List<Integer> lcid = new ArrayList<Integer>();
		Connection connection = getConnection();
		try {
			Statement statement = connection.createStatement();
			ResultSet rSet = null;
			rSet = statement.executeQuery("select * from comics");         // 需要更改！！
			while (rSet.next()) {
				lcid.add(rSet.getInt("cid"));
			}
			rSet.close();
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lcid;
	}
	
	public static List<Integer> readFormDB(String v){
		List<Integer> lid = new ArrayList<Integer>();
		Connection connection = getConnection();
		try {
			Statement statement = connection.createStatement();
			ResultSet rSet = null;
			rSet = statement.executeQuery(v);
			while (rSet.next()) {
				lid.add(rSet.getInt("id"));
			}
			rSet.close();
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lid;
	}
	
	public static int checkDB(){
		int flag = 0;
		Connection connection = getConnection();
		try {
			DatabaseMetaData meta = connection.getMetaData();
			ResultSet rsTables = meta.getTables(null , null,"comics", null);
			if (!rsTables.next()) {
				String sql = "CREATE TABLE comics (	id int(11) unsigned not null auto_increment, author VARCHAR(1000) NOT NULL DEFAULT '',cats INT(20) UNSIGNED NOT NULL,cover_image VARCHAR(150) NOT NULL DEFAULT '',cid int(11) unsigned not null,name VARCHAR(1000) NOT NULL DEFAULT '',rank int(4) unsigned not null default 0,total_page int(11) unsigned not null default 0,PRIMARY KEY (id))  ENGINE=INNODB DEFAULT CHARSET=UTF8;";
				flag = 1;
				if (insertToDB(sql)==0) {
					System.out.println("creatComicsSuccess!");
				}else {
					flag=-1;
				}
			}
			rsTables = meta.getTables(null , null,"categories", null);
			if (!rsTables.next()) {
				String sql = "CREATE TABLE categories (id INT(20) UNSIGNED NOT NULL ,cover_image VARCHAR(150) NOT NULL DEFAULT '',all_comics int(11) unsigned not null,name VARCHAR(1000) NOT NULL DEFAULT '',PRIMARY KEY (id))  ENGINE=INNODB DEFAULT CHARSET=UTF8;";
				if (flag!=0) {
					flag=3;
				}else {
					flag = 2;
				}
				if (insertToDB(sql)==0) {
					System.out.println("creatCategoriesSuccess!");
				}else {
					if (flag!=2) {
						flag = -3;
					}else {
						flag=-2;
					}
				}
			}
			rsTables.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	public static List<List<String>> searchDB(String content) {
		List<List<String>> result = new ArrayList<List<String>>();
		List<String> resultID = new ArrayList<String>();
		List<String> resultNA = new ArrayList<String>();
		List<String> resultTO = new ArrayList<String>();
		Connection connection = getConnection();
		try {
			Statement statement = connection.createStatement();
			ResultSet rSet = null;
			rSet = statement.executeQuery("select * from comics where name like '%"+content+"%'");
			while (rSet.next()) {
				resultID.add(String.valueOf(rSet.getInt("cid")));
				resultNA.add(rSet.getString("name"));
				resultTO.add(String.valueOf(rSet.getInt("total_page")));
			}
			if (resultID.size()!=0) {
				result.add(resultID);
				result.add(resultNA);
				result.add(resultTO);
			}
			rSet.close();
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
}
