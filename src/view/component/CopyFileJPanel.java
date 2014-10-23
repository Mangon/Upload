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

public class CopyFileJPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 2;

	private JLabel lblPath;
	private JTextField tfPath;
	private JLabel lblFileName;
	private JTextField tfFileName;
	private JLabel lblString;
	private JTextField tfString;
	private JLabel lblNum;
	private JTextField tfNum;
	private JButton btnExecute;
	private static final String METHOD =
	
	  "将路径path的文件名fileName中的arg0重命名为arg0[i]并复制n份\n"
	  + "path 文件所在路径，以\\结尾 \n" 
	  +	"参考：C:\\Users\\devtao\\Desktop\\upload\\ \n"
	  + "fileName 文件名 \n"
	  + "参考：5880_1_code_crm_uop_crm1_P_CSM_MODIFYPRODUCT_NEW.prc \n"
	  + "arg0 需要替换的字符串 "
	  + "参考：crm1 \n"
	  + "n 需要复制的份数 " 
	  + "参考 8 \n";

	public CopyFileJPanel(){
		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout());
		
		lblPath = new JLabel("路径:");
		tfPath = new JTextField(12);
		lblFileName = new JLabel("文件名：");
		tfFileName = new JTextField(6);
		lblString = new JLabel("字符串:");
		tfString = new JTextField(4);
		lblNum = new JLabel("份数：");
		tfNum = new JTextField(2);
		
		p1.add(lblPath);
		p1.add(tfPath);
		p1.add(lblFileName);
		p1.add(tfFileName);
		p1.add(lblString);
		p1.add(tfString);
		p1.add(lblNum);
		p1.add(tfNum);
		
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
				String arg0 = tfString.getText().trim();
				int i = Integer.parseInt(tfNum.getText().trim());
				Upload.copyFile(path,fileName,arg0,i);
				JOptionPane.showMessageDialog(this,"执行成功！");
				MainLogger.addLOG("执行成功！");
			}
		}catch(Exception ex){
			JOptionPane.showMessageDialog(this, "错误："+ex.toString());
			ex.printStackTrace();			
		}
	}

}
