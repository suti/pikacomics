package com.lixiangyu.java.pika;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.SwingWorker;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class Frame extends JFrame {

	private MyPanelUI contentPane;
	private int mX,mY,frameX,frameY;
	private JProgressBar progressBar;
	private JTextArea textArea;
	private JTextField textField;
	private JTextField textFieldSuper;
	private JScrollPane scrollPane;
	private List<List<String>> imageList = new ArrayList<List<String>>();
	private List<String> imageUrl = new ArrayList<String>();
	private List<String> imageUrls = new ArrayList<String>();
	private JFileChooser chooser = new JFileChooser("./");
	private String path = "comics";

	public JProgressBar getProgressBar() {
		return progressBar;
	}
	public JTextArea getTextArea() {
		return textArea;
	}

	/**
	 * Create the frame.
	 */
	public Frame() {
		setUndecorated(true);
		setBackground(new Color(0, 0,0,0));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 497, 381);
		contentPane = new MyPanelUI();
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mX = e.getXOnScreen();
				mY = e.getYOnScreen();
				frameX = getX();
				frameY = getY();
			}
		});
		contentPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				setLocation(frameX+(e.getXOnScreen()-mX), frameY+(e.getYOnScreen()-mY));
			}
		});
		contentPane.setOpaque(false);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setBounds(6, 30, 485, 345);
		contentPane.add(panel);

		JButton btnExit = new JButton("×");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnExit.setBounds(414, 6, 65, 29);

		JButton button = new JButton(".");
		button.setBounds(337, 6, 65, 29);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setExtendedState(JFrame.ICONIFIED);
			}
		});
		panel.setLayout(null);
		panel.add(button);
		panel.add(btnExit);

		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setValue(100);
		progressBar.setBounds(6, 319, 473, 20);
		panel.add(progressBar);

		textField = new JTextField();
		textField.setText("        0/0");
		textField.setBounds(347, 295, 107, 26);
		panel.add(textField);
		textField.setColumns(10);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 38, 309, 269);
		panel.add(scrollPane);

		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setBackground(new Color(255, 255, 255,140));

		textFieldSuper = new JTextField();
		textFieldSuper.setBounds(6, 6, 153, 26);
		panel.add(textFieldSuper);
		textFieldSuper.setColumns(10);

		JButton btnConnectdb = new JButton("ConnectDB");
		btnConnectdb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnConnectdb.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				connectDB();
			}
		});
		btnConnectdb.setBounds(337, 47, 127, 29);
		panel.add(btnConnectdb);

		JButton btnDownload = new JButton("整理表");
		btnDownload.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cleanDB();
			}
		});
		btnDownload.setBounds(337, 252, 127, 29);
		panel.add(btnDownload);

		JButton button_1 = new JButton("下载索引");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				updateDbC();
			}
		});
		button_1.setBounds(337, 129, 127, 29);
		panel.add(button_1);

		JButton button_2 = new JButton("更新数据");
		button_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String option[] = {
						"同人","SM","人妻","長篇","短篇","艦隊收藏","性轉換","全彩","百合花園","後宮閃光","扶他樂園",
						"重口地帶","非人類","Love Live","NTR","強暴","姐姐系","妹妹系","SAO 刀劍神域","東方","純愛",
						"足の恋","生肉","Fate","耽美花園","禁書目錄","偽娘哲學","CG雜圖","Cosplay","遊戲試區","英語 ENG"
				};
				String options=(String) JOptionPane.showInputDialog(Frame.this, "请选择","更新",JOptionPane.WARNING_MESSAGE,null,option,"");
				int v = 0;
				for(int i = 0;i<option.length;i++){
					if (options.equals(option[i])) {
						v=i+1;
						if (v>12) {
							v+=1;
						}
					}
				}
				updateDB(v);
			}
		});
		button_2.setBounds(337, 170, 127, 29);
		panel.add(button_2);

		JButton button_3 = new JButton("选择下载路径");
		button_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int value = chooser.showOpenDialog(Frame.this);
				if (value == JFileChooser.APPROVE_OPTION ) {
					File file = chooser.getSelectedFile();
					path = file.getAbsolutePath();
				}
			}
		});
		button_3.setBounds(337, 211, 127, 29);
		panel.add(button_3);

		JButton button_4 = new JButton("检查并创建表");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button_4.setActionCommand("");
		button_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				checkDB();
			}
		});
		button_4.setBounds(337, 88, 127, 29);
		panel.add(button_4);

		JButton btnNewButton = new JButton("search");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String content = textFieldSuper.getText();
				if (content!=null) {
					search(content);
				}
			}
		});
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(159, 6, 78, 29);
		panel.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("download");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Object temp=textFieldSuper.getText();
				if (((String)temp).matches("\\d{0,4}")) {
					int isuper = Integer.parseInt((String)temp);
					System.out.println("这是测试单个下载"+isuper);
					downLoad(isuper);
					textArea.append(new SimpleDateFormat("hh:mm:ss").format(System.currentTimeMillis())+": 开始下载"+isuper+"!\n");
				}else{
					textFieldSuper.setText("");
					textArea.append(new SimpleDateFormat("hh:mm:ss").format(System.currentTimeMillis())+": 请输入id号下载!\n");
				}
			}
		});
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setBounds(227, 6, 89, 29);
		panel.add(btnNewButton_1);
		btnExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
	}



	public void connectDB() {
		new SwingWorker<StringBuffer,StringBuffer>() {
			Connection connection;
			@Override
			protected StringBuffer doInBackground() throws Exception {
				new ConnectDB();
				connection=ConnectDB.getConnection();
				return null;
			}

			@Override
			protected void process(List<StringBuffer> chunks) {
				// TODO Auto-generated method stub
				super.process(chunks);
			}

			@Override
			protected void done() {
				if (connection!=null) {
					textArea.append(new SimpleDateFormat("hh:mm:ss").format(System.currentTimeMillis())+": 本地数据库连接成功\n");
				}else {
					textArea.append(new SimpleDateFormat("hh:mm:ss").format(System.currentTimeMillis())+": 本地数据库连接失败\n");
				}
				super.done();
			}
		}.execute();
	}

	public void updateDbC() {
		new SwingWorker<StringBuffer,Integer>() {
			int back = 0;
			@Override
			protected StringBuffer doInBackground() throws Exception {
				//				ConnectDB connectDB = new ConnectDB();
				System.out.println("bging...\n");
				JsonManager jsonManager = new JsonManager(new UrlPaser("http://picaman.picacomic.com/api/categories/").getContent());
				back =  ConnectDB.insertToDB(jsonManager.jsonReadCategories());
				return null;
			} 

			@Override
			protected void process(List<Integer> chunks) {
				// TODO Auto-generated method stub
				super.process(chunks);
			}

			@Override
			protected void done() {
				// TODO Auto-generated method stub
				textArea.append(new SimpleDateFormat("hh:mm:ss").format(System.currentTimeMillis())+": 索引已更新！ 共"+back+"条!\n");
				super.done();
			}

		}.execute();
	}

	public void updateDB(int cats) {
		new SwingWorker<StringBuffer,Integer>() {
			int counter=0;
			@Override
			protected StringBuffer doInBackground() throws Exception {
				int count = 0;
				int v=cats-1;
				if (cats>13) {
					v=cats-2;
				}
				int j = new PikaLog().getPikaProgress(v);
				for ( ; count != -1; j++) {
					count = ConnectDB.insertToDB(
							new JsonManager(
									new UrlPaser("http://picaman.picacomic.com/api/categories/"+cats+"/page/"+j+"/comics").getContent())
							.jsonReadComic());
					new PikaLog().setProgress(v, j);
					publish(count);
				}
				return null;
			}

			@Override
			protected void process(List<Integer> chunks) {
				// TODO Auto-generated method stub
				for (int count : chunks) {
					if (count != -1) {
						counter+=count;
						textField.setText(counter+"");
						textArea.append(new SimpleDateFormat("hh:mm:ss").format(System.currentTimeMillis())+": 已更新"+counter+"条!\n");
					}
				}
				super.process(chunks);
			}

			@Override
			protected void done() {
				// TODO Auto-generated method stub
				textArea.append(new SimpleDateFormat("hh:mm:ss").format(System.currentTimeMillis())+": 已更新完成!\n");
				super.done();
			}

		}.execute();
	}

	public void downLoad(int i) {

		List<Integer> count = new ArrayList<Integer>();
		new SwingWorker<StringBuffer,int[]>() {
			//			DownLoadF dF;
			@Override
			protected StringBuffer doInBackground() throws Exception {
				progressBar.setValue(0);
				if (i<33) {
					imageList=new JsonDM(ConnectDB.readFormDB(i)).getImageList();
					for (int i = 0; i < imageList.size(); i++) {
						imageUrl=(imageList.get(i));
						for (int j = 0; j < imageUrl.size(); j++) {
							imageUrls.add(imageUrl.get(j).toString());
						}
					}
				}else {
					List<Integer> listId = new ArrayList<Integer>();
					listId.add(i);
					imageList = new JsonDM(listId).getImageList();
					for (int i = 0; i < imageList.size(); i++) {
						imageUrl=imageList.get(i);
						for (int j = 0; j < imageUrl.size(); j++) {
							imageUrls.add(imageUrl.get(j).toString());
						}
					}
				}
				try {
					ExecutorService executor = Executors.newFixedThreadPool(10);
					for (int i = 0; i < imageUrls.size(); i++) {
						Callable<Integer> downLoad = new DownLoad(imageUrls.get(i).toString(),path,i);
						Future<Integer> future =executor.submit(downLoad);
						count.add(future.get());
						//						System.out.println("计数"+count.size());

						int[] temp= new int[2];
						temp[0]=0;
						temp[1]=1;
						//						while (!(temp[0]==temp[1])) {
						temp[1] = (int)count.size();
						temp[0] = (int)imageUrls.size();
						//					System.out.println("测试总"+temp[0]);
						//					System.out.println("测试进"+temp[1]);
						publish(temp);
						//						}

					}executor.shutdown();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void process(List<int[]> chunks) {
				//				System.out.println(dF.getProcess()+dF.getNum());
				for(int[] v:chunks){

					textArea.append(new SimpleDateFormat("hh:mm:ss").format(System.currentTimeMillis())+": Number."+v[1]+"start!\n");
					if (v[1] == v[0]) {
						textArea.append(new SimpleDateFormat("hh:mm:ss").format(System.currentTimeMillis())+": 下载完成!\n");
					}
					progressBar.setValue((v[1]*100)/v[0]);
					//					System.out.println((int)((v[1]/v[0])*100));
					textField.setText(v[1]+"/"+v[0]);
					//					System.out.println(v[0]+" lll  "+v[1]);
				}
				super.process(chunks);
			}

			@Override
			protected void done() {
				// TODO Auto-generated method stub
				progressBar.setValue(0);
				super.done();
			}

		}.execute();
	}

	public void search(String content) {

		new SwingWorker<StringBuffer, Integer>(){
			List<List<String>> result = new ArrayList<List<String>>();
			@Override
			protected StringBuffer doInBackground() throws Exception {
				// TODO Auto-generated method stub
				result = ConnectDB.searchDB(content.replace(" ", "%"));
				return null;
			}
			@Override
			protected void process(List<Integer> chunks) {
				// TODO Auto-generated method stub
				super.process(chunks);
			}

			@Override
			protected void done() {
				// TODO Auto-generated method stub
				if (result.size()!=0) {
					if (result.get(0).size()==1) {
						textFieldSuper.setText(result.get(0).get(0).toString());
					}
					for (int i = 0; i < result.get(0).size(); i++) {
						textArea.append("ID:"+result.get(0).get(i).toString()+" ");
						textArea.append(""+result.get(1).get(i).toString()+" ");
						textArea.append("总数:"+result.get(2).get(i).toString()+"\n");
					}

				}else {
					textArea.append("查询无果!\n");
				}
				super.done();
			}
		}.execute();
	}
	
	public void  checkDB() {
		new SwingWorker<StringBuffer, Integer>(){
			int flag;
			@Override
			protected StringBuffer doInBackground() throws Exception {
				// TODO Auto-generated method stub
				flag = ConnectDB.checkDB();
				return null;
			}
			@Override
			protected void process(List<Integer> chunks) {
				// TODO Auto-generated method stub
				super.process(chunks);
			}
			
			@Override
			protected void done() {
				// TODO Auto-generated method stub
				switch (flag) {
				case 0:
					textArea.append("数据库已存在!\n");
					break;
				case 1:
					textArea.append("comics缺失,已创建!\n");
					break;
				case -1:
					textArea.append("comics缺失，创建失败!\n");
					break;
				case 2:
					textArea.append("categories缺失，已创建!\n");
					break;
				case -2:
					textArea.append("categories缺失，创建失败!\n");
					break;
				case 3:
					textArea.append("数据库创建成功!\n");
					break;
				case -3:
					textArea.append("数据库创建失败!\n");
					break;
				default:
					textArea.append("检查并创建数据库时发生了意外!!!\n");
					break;
				}
				super.done();
			}
		}.execute();
	}
	
	public void cleanDB(){

		new SwingWorker<StringBuffer, Integer>(){
			int count = 0;
			@Override
			protected StringBuffer doInBackground() throws Exception {
				// TODO Auto-generated method stub
				String select = "select t.id,count(id) from comics t group by cid having count(*) >1";
				List<Integer> list = new ArrayList<Integer>();
				list = ConnectDB.readFormDB(select);
				System.out.println("cleanDB.....\n");
				for (int i = 0; i < list.size(); i++) {
					String delete = "DELETE FROM comics WHERE id = '"+list.get(i).toString()+"' ";
						count+=ConnectDB.insertToDB(delete);
						publish(count);
				}
				return null;
			}
			
			@Override
			protected void process(List<Integer> chunks) {
				// TODO Auto-generated method stub
				for (int v:chunks) {
					textField.setText(v+"");
				}
				super.process(chunks);
			}
			
			@Override
			protected void done() {
				// TODO Auto-generated method stub
				textArea.append(new SimpleDateFormat("hh:mm:ss").format(System.currentTimeMillis())+": 清理完成,共"+count+"条!\n");
				super.done();
			}
		}.execute();
	}
}
