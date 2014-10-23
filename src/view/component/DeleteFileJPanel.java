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
		= "ɾ��·��path���ļ��������ַ���arg0���ļ�\n"
		+ "path �ļ�����·������\\\\��β \n"
		+ "�ο���C:\\\\Users\\\\Administrator\\\\Desktop\\\\Upload\\\\ \n"
		+ "arg0 ɾ�����ַ��� \n"
		+ "�ο���.bak";
	
	public DeleteFileJPanel(){
		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout());
		
		lblPath = new JLabel("·��:");
		tfPath = new JTextField(12);
		lblString = new JLabel("�ַ���:");
		tfString = new JTextField(12);
		
		p1.add(lblPath);
		p1.add(tfPath);
		p1.add(lblString);
		p1.add(tfString);
		
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
		add(p2, BorderLayout.CENTER);
		add(p3, BorderLayout.SOUTH);

		setLocation(400, 150);		
		setSize(500, 550);
		setVisible(true);
		
	}
	public void actionPerformed(ActionEvent e) {
		try{
			if (e.getActionCommand().equals("ִ��")) {
				String path = tfPath.getText().trim();
				String arg0 = tfString.getText().trim();
				Upload.deleteFile(path,arg0);
				JOptionPane.showMessageDialog(this,"ִ�гɹ���");
				MainLogger.addLOG("ִ�гɹ���");
			}
		}catch(Exception ex){
			JOptionPane.showMessageDialog(this, "����"+ex.toString());
			ex.printStackTrace();
		}
	}

}
