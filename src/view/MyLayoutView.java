package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import log.MainLogger;
import view.component.CleanFileJPanel;
import view.component.CopyFileJPanel;
import view.component.CopyProcedureJPanel;
import view.component.DeleteFileJPanel;
import view.component.EatCountJPanel;
import view.component.ReplaceFilenameJPanel;

public class MyLayoutView extends JFrame implements ActionListener{

	private static final long serialVersionUID = 12345;
	private Container container = null ;  
	private JTabbedPane tabbedPane = null ; 
	private JMenuBar menuBar = null;
//	public JToolBar toolBar = null;
	public static JTextArea textArea = null;
	public static String OUTPUT = null;
	private DeleteFileJPanel deleteFileJPanel;
	public  MyLayoutView(){
		
		OUTPUT ="";
		deleteFileJPanel = new DeleteFileJPanel();
		//设置窗体基本属性  
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(650,650);
		setLocation(400, 150);
		setTitle("CBSSTools");
		
		//menuBar初始化
		menuBar = new JMenuBar();
		JMenu menu1 = new JMenu("开始");
		JMenu menu2 = new JMenu("设置");
		JMenu menu3 = new JMenu("关于");
		
		menuBar.add(menu1);
		menuBar.add(menu2);
		menuBar.add(menu3);
		
		JMenuItem menuitem1 = new JMenuItem("设置路径");
		JMenuItem menuitem2 = new JMenuItem("设置姓名");
		JMenuItem menuitem3 = new JMenuItem("关于");
		JMenuItem menuitem4 = new JMenuItem("清除日志");
		menuitem1.setActionCommand("设置路径");
		menuitem2.setActionCommand("设置姓名");
		menuitem3.setActionCommand("关于");
		menuitem4.setActionCommand("清除日志");
		menuitem1.addActionListener(this);
		menuitem2.addActionListener(this);
		menuitem3.addActionListener(this);
		menuitem4.addActionListener(this);

		menu2.add(menuitem1);
		menu2.add(menuitem2);
		menu2.addSeparator();
		menu2.add(menuitem4);
		
		menu3.add(menuitem3);
				
		//toolBar的初始化
		//toolBar = new JToolBar();
		
		textArea = new JTextArea();
		textArea.setColumns(24);
		textArea.setRows(5);
		textArea.setText("Welcom!");
		textArea.setVisible(false);
		MainLogger.LOG = "Welcom!\n";
//		toolBar.add(textArea);
		
		//创建默认在顶部的JTabbedPane  
		tabbedPane = new JTabbedPane(); 
		
		//创建在左边的JTabbedPane
		tabbedPane = new JTabbedPane(JTabbedPane.TOP); 
		tabbedPane.addTab("就餐统计", new ImageIcon(this.getClass().getResource("../pic/search.png")),new EatCountJPanel(),"就餐统计");
		tabbedPane.addTab("批量删除文件",new ImageIcon(this.getClass().getResource("../pic/delete.png")),deleteFileJPanel,"删除文件");
		tabbedPane.addTab("批量重命名",new ImageIcon(this.getClass().getResource("../pic/mail.png")),new ReplaceFilenameJPanel(),"替换文件名");
		tabbedPane.addTab("复制文件",new ImageIcon(this.getClass().getResource("../pic/search.png")),new CopyFileJPanel(),"复制文件");
		tabbedPane.addTab("复制Proc",new ImageIcon(this.getClass().getResource("../pic/setting.png")),new CopyProcedureJPanel(),"复制存储过程");
		tabbedPane.addTab("删除注释",new ImageIcon(this.getClass().getResource("../pic/recycle.png")),new CleanFileJPanel(),"删除注释");
		
		tabbedPane.addChangeListener(new ChangeListener(){

			public void stateChanged(ChangeEvent e) {
				if(tabbedPane.getSelectedIndex()==0){
					textArea.setVisible(false);
				}else{
					textArea.setVisible(true);
				}				
			}
			
		});
		
		//container初始化
		container = getContentPane();
		container.add(menuBar,BorderLayout.NORTH);
		container.add(tabbedPane,BorderLayout.CENTER);
		container.add(textArea,BorderLayout.SOUTH);
		setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		try{
			if (e.getActionCommand().equals("清除日志")) {
				MainLogger.cleanLOG();
			}else if(e.getActionCommand().equals("关于")){
				JOptionPane.showMessageDialog(this, "CBSSTools");				
			}
		}catch(Exception ex){
			JOptionPane.showMessageDialog(this, "错误："+ex.toString());
			ex.printStackTrace();
		}
		
	}

}
