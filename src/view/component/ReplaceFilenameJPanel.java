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

public class ReplaceFilenameJPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 3;

	private JLabel lblPath;
	private JTextField tfPath;
	private JLabel lblString1;
	private JTextField tfString1;
	private JLabel lblString2;
	private JTextField tfString2;	
	private JButton btnExecute;
	private static final String METHOD =
		"替换路径path下所有文件名中的字符串arg0为arg1\n"
		+ "path 文件所在路径，以\\结尾 参考：C:\\Users\\devtao\\Desktop\\upload\\ \n"
		+ "arg0 需要被替换的字符串 参考： _ucr_ \n"
		+ "arg1 替换的字符串 参考：_uop_ \n";
	public ReplaceFilenameJPanel(){
		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout());
		
		lblPath = new JLabel("路径:");
		tfPath = new JTextField(12);
		lblString1 = new JLabel("字符串1:");
		tfString1 = new JTextField(6);
		lblString2 = new JLabel("字符串2:");
		tfString2 = new JTextField(6);
		
		p1.add(lblPath);
		p1.add(tfPath);
		p1.add(lblString1);
		p1.add(tfString1);
		p1.add(lblString2);
		p1.add(tfString2);
		
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
				String arg0 = tfString1.getText().trim();
				String arg1 = tfString2.getText().trim();
				Upload.replaceFilename(path, arg0, arg1);
				JOptionPane.showMessageDialog(this,"执行成功！");
				MainLogger.addLOG("执行成功！");				
			}
		}catch(Exception ex){
			JOptionPane.showMessageDialog(this, "错误："+ex.toString());
			ex.printStackTrace();			
		}
	}

}
