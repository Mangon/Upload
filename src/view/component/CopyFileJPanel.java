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
	
	  "��·��path���ļ���fileName�е�arg0������Ϊarg0[i]������n��\n"
	  + "path �ļ�����·������\\��β \n" 
	  +	"�ο���C:\\Users\\devtao\\Desktop\\upload\\ \n"
	  + "fileName �ļ��� \n"
	  + "�ο���5880_1_code_crm_uop_crm1_P_CSM_MODIFYPRODUCT_NEW.prc \n"
	  + "arg0 ��Ҫ�滻���ַ��� "
	  + "�ο���crm1 \n"
	  + "n ��Ҫ���Ƶķ��� " 
	  + "�ο� 8 \n";

	public CopyFileJPanel(){
		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout());
		
		lblPath = new JLabel("·��:");
		tfPath = new JTextField(12);
		lblFileName = new JLabel("�ļ�����");
		tfFileName = new JTextField(6);
		lblString = new JLabel("�ַ���:");
		tfString = new JTextField(4);
		lblNum = new JLabel("������");
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
		
		btnExecute = new JButton("ִ��");
		btnExecute.setActionCommand("ִ��");
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
			if (e.getActionCommand().equals("ִ��")) {
				String path = tfPath.getText().trim();
				String fileName = tfFileName.getText().trim();
				String arg0 = tfString.getText().trim();
				int i = Integer.parseInt(tfNum.getText().trim());
				Upload.copyFile(path,fileName,arg0,i);
				JOptionPane.showMessageDialog(this,"ִ�гɹ���");
				MainLogger.addLOG("ִ�гɹ���");
			}
		}catch(Exception ex){
			JOptionPane.showMessageDialog(this, "����"+ex.toString());
			ex.printStackTrace();			
		}
	}

}
