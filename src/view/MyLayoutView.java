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
		//���ô����������  
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(650,650);
		setLocation(400, 150);
		setTitle("CBSSTools");
		
		//menuBar��ʼ��
		menuBar = new JMenuBar();
		JMenu menu1 = new JMenu("��ʼ");
		JMenu menu2 = new JMenu("����");
		JMenu menu3 = new JMenu("����");
		
		menuBar.add(menu1);
		menuBar.add(menu2);
		menuBar.add(menu3);
		
		JMenuItem menuitem1 = new JMenuItem("����·��");
		JMenuItem menuitem2 = new JMenuItem("��������");
		JMenuItem menuitem3 = new JMenuItem("����");
		JMenuItem menuitem4 = new JMenuItem("�����־");
		menuitem1.setActionCommand("����·��");
		menuitem2.setActionCommand("��������");
		menuitem3.setActionCommand("����");
		menuitem4.setActionCommand("�����־");
		menuitem1.addActionListener(this);
		menuitem2.addActionListener(this);
		menuitem3.addActionListener(this);
		menuitem4.addActionListener(this);

		menu2.add(menuitem1);
		menu2.add(menuitem2);
		menu2.addSeparator();
		menu2.add(menuitem4);
		
		menu3.add(menuitem3);
				
		//toolBar�ĳ�ʼ��
		//toolBar = new JToolBar();
		
		textArea = new JTextArea();
		textArea.setColumns(24);
		textArea.setRows(5);
		textArea.setText("Welcom!");
		textArea.setVisible(false);
		MainLogger.LOG = "Welcom!\n";
//		toolBar.add(textArea);
		
		//����Ĭ���ڶ�����JTabbedPane  
		tabbedPane = new JTabbedPane(); 
		
		//��������ߵ�JTabbedPane
		tabbedPane = new JTabbedPane(JTabbedPane.TOP); 
		tabbedPane.addTab("�Ͳ�ͳ��", new ImageIcon(this.getClass().getResource("../pic/search.png")),new EatCountJPanel(),"�Ͳ�ͳ��");
		tabbedPane.addTab("����ɾ���ļ�",new ImageIcon(this.getClass().getResource("../pic/delete.png")),deleteFileJPanel,"ɾ���ļ�");
		tabbedPane.addTab("����������",new ImageIcon(this.getClass().getResource("../pic/mail.png")),new ReplaceFilenameJPanel(),"�滻�ļ���");
		tabbedPane.addTab("�����ļ�",new ImageIcon(this.getClass().getResource("../pic/search.png")),new CopyFileJPanel(),"�����ļ�");
		tabbedPane.addTab("����Proc",new ImageIcon(this.getClass().getResource("../pic/setting.png")),new CopyProcedureJPanel(),"���ƴ洢����");
		tabbedPane.addTab("ɾ��ע��",new ImageIcon(this.getClass().getResource("../pic/recycle.png")),new CleanFileJPanel(),"ɾ��ע��");
		
		tabbedPane.addChangeListener(new ChangeListener(){

			public void stateChanged(ChangeEvent e) {
				if(tabbedPane.getSelectedIndex()==0){
					textArea.setVisible(false);
				}else{
					textArea.setVisible(true);
				}				
			}
			
		});
		
		//container��ʼ��
		container = getContentPane();
		container.add(menuBar,BorderLayout.NORTH);
		container.add(tabbedPane,BorderLayout.CENTER);
		container.add(textArea,BorderLayout.SOUTH);
		setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		try{
			if (e.getActionCommand().equals("�����־")) {
				MainLogger.cleanLOG();
			}else if(e.getActionCommand().equals("����")){
				JOptionPane.showMessageDialog(this, "CBSSTools");				
			}
		}catch(Exception ex){
			JOptionPane.showMessageDialog(this, "����"+ex.toString());
			ex.printStackTrace();
		}
		
	}

}
