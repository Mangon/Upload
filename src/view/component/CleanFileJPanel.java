package view.component;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import log.MainLogger;

import service.Upload;

public  class CleanFileJPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 2;

	private JLabel lblPath;
	private JTextField tfPath;
	private JLabel lblFileName;
	private JTextField tfFileName;
	private JButton btnExecute;
	private static final String METHOD =
		"删除SQL文件中的注释,压缩代码，让你的sql变得无人能懂O(∩_∩)O";	
	public CleanFileJPanel(){
		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout());
		
		lblPath = new JLabel("路径:");
		tfPath = new JTextField(12);
		lblFileName = new JLabel("文件名:");
		tfFileName = new JTextField(12);
		
		p1.add(lblPath);
		p1.add(tfPath);
		p1.add(lblFileName);
		p1.add(tfFileName);
		
		JPanel p2 = new JPanel();
		p2.setLayout(new FlowLayout());
		
		btnExecute = new JButton("执行");
		btnExecute.setActionCommand("执行");
		btnExecute.addActionListener(this);
		
		p2.add(btnExecute);

		JPanel p3 = new JPanel();
		JTextArea method = new JTextArea();

		method.setText(METHOD);
		method.setEditable(false);
		p3.add(method);
		
		add(p1, BorderLayout.NORTH);
		add(p2,BorderLayout.CENTER);
		add(p3,BorderLayout.SOUTH);
		
		setLocation(400, 150);		
		setSize(500, 550);
		setVisible(true);

	}
	
	public void actionPerformed(ActionEvent e) {
		try{
			if (e.getActionCommand().equals("执行")) {
				String path = tfPath.getText().trim();
				String fileName = tfFileName.getText().trim();
				Upload.cleanFile(path,fileName);
				JOptionPane.showMessageDialog(this,"执行成功！");
				MainLogger.addLOG("执行成功！");
			}
		}catch(Exception ex){
			JOptionPane.showMessageDialog(this, "错误："+ex.toString());
			ex.printStackTrace();			
		}
	}

}
