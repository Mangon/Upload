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

public class DeleteFileJPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 2;

	private JLabel lblPath;
	private JTextField tfPath;
	private JLabel lblString;
	private JTextField tfString;
	private JButton btnExecute;
	private static final String METHOD 
		= "删除路径path下文件名包含字符串arg0的文件\n"
		+ "path 文件所在路径，以\\\\结尾 \n"
		+ "参考：C:\\\\Users\\\\Administrator\\\\Desktop\\\\Upload\\\\ \n"
		+ "arg0 删除的字符串 \n"
		+ "参考：.bak";
	
	public DeleteFileJPanel(){
		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout());
		
		lblPath = new JLabel("路径:");
		tfPath = new JTextField(12);
		lblString = new JLabel("字符串:");
		tfString = new JTextField(12);
		
		p1.add(lblPath);
		p1.add(tfPath);
		p1.add(lblString);
		p1.add(tfString);
		
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
		add(p2, BorderLayout.CENTER);
		add(p3, BorderLayout.SOUTH);

		setLocation(400, 150);		
		setSize(500, 550);
		setVisible(true);
		
	}
	public void actionPerformed(ActionEvent e) {
		try{
			if (e.getActionCommand().equals("执行")) {
				String path = tfPath.getText().trim();
				String arg0 = tfString.getText().trim();
				Upload.deleteFile(path,arg0);
				JOptionPane.showMessageDialog(this,"执行成功！");
				MainLogger.addLOG("执行成功！");
			}
		}catch(Exception ex){
			JOptionPane.showMessageDialog(this, "错误："+ex.toString());
			ex.printStackTrace();
		}
	}

}
