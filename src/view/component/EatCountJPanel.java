package view.component;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import service.Service;

public class EatCountJPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1;

	private JLabel lblUsername;
	private JLabel lbllunch;
	private JLabel lbldinner;
	private JTextField tfUsername;
	private JButton btnOK;
	private JButton btnExit;
	private JButton btnExport;
	private JButton btnInsert;
	private JButton btnDelete;
	private JButton btnFlush;
	private JComboBox lunch;
	private JComboBox dinner;
	
	private JScrollPane p3;
	private JTable table;
	private MyTableModel tmd;
	
	

	/**
	 * ͼ����ͼ��ʼ��
	 */
	public EatCountJPanel() {
		
		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout());
		lblUsername = new JLabel("����:");
		tfUsername = new JTextField(12);
		p1.add(lblUsername);
		p1.add(tfUsername);

//		String strnot[] ={"��ʳ","�Ҳ���","����","��ϧ��ʳ","��������ȥ","����η�","�������"};
		
		String str[] = { "����", "С�Գ�", "- -" };
//		Random ran = new Random();
//		int i = ran.nextInt(7);
//		str[2] = strnot[i];
		lbllunch = new JLabel("����:");
		lunch = new JComboBox(str);
		lbldinner = new JLabel("����:");
		dinner = new JComboBox(str);
		p1.add(lbllunch);
		p1.add(lunch);
		p1.add(lbldinner);
		p1.add(dinner);

		JPanel p2 = new JPanel();
		p2.setLayout(new FlowLayout());
		btnOK = new JButton("����");
		btnOK.addActionListener(this);
		btnExit = new JButton("�˳�");
		btnExit.addActionListener(this);
		btnExport = new JButton("����");
		btnExport.addActionListener(this);
		btnInsert = new JButton("����");
		btnInsert.addActionListener(this);
		btnDelete = new JButton("ɾ��");
		btnDelete.addActionListener(this);
		btnFlush = new JButton("ˢ��");
		btnFlush.addActionListener(this);
		p2.add(btnOK);
		p2.add(btnExit);
		p2.add(btnExport);
		p2.add(btnInsert);
		p2.add(btnDelete);
		p2.add(btnFlush);
		
		p3 = new JScrollPane();
		this.paintJTable();
	    p3.setVisible(true);
	    
		
		this.add(p1, BorderLayout.NORTH);
		this.add(p2, BorderLayout.CENTER);
		this.add(p3, BorderLayout.SOUTH);

		this.setLocation(400, 150);
		this.setSize(500, 550);
		this.setVisible(true);
		
	}

	/**
	 * �������������¼�
	 */
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getActionCommand().equals("����")) {

				String person_lunch = lunch.getSelectedIndex() + "";
				String person_dinner = dinner.getSelectedIndex() + "";
				String mem_name = tfUsername.getText().trim();

				if (mem_name.equals("") || mem_name == null) {
					JOptionPane.showMessageDialog(this, "����Ϊ��!");
				} else if (Service.QueryTable(mem_name) < 1) {
					JOptionPane.showMessageDialog(this, "����������!");
				} else {
					int updateresult = Service.UpdateTable(person_lunch,
							person_dinner, mem_name);
					System.out.println("���½����" + updateresult + "(1����ɹ���0����ʧ��)");
					if (updateresult > 0) {
						this.paintJTable();
						if(person_lunch.equals("2")||person_dinner.equals("2")){
							JOptionPane.showMessageDialog(this, "�Ѹ��£�ף�����ˣ�");
						}else{
							JOptionPane.showMessageDialog(this, "�Ѹ��£�ף���ò���죡");
						}
					} else {
						JOptionPane.showMessageDialog(this, "����ʧ��!");
					}
				}
			} else if (e.getActionCommand().equals("�˳�")) {
				System.exit(0);
			} else if (e.getActionCommand().equals("����")){
				int exportresult=Service.ExportTable();
				System.out.println("���������" + exportresult + "(1����ɹ���0����ʧ��)");
				if(1==exportresult){
					this.paintJTable();
					JOptionPane.showMessageDialog(this, "�����ɹ�!");
				}
				else{
					JOptionPane.showMessageDialog(this, "����ʧ��!");
				}
			}else if(e.getActionCommand().equals("����")){
				String mem_name = tfUsername.getText();
				if(mem_name.length()>24){
					JOptionPane.showMessageDialog(this, "��������!");
				} else if (mem_name.equals("") || mem_name == null) {
					JOptionPane.showMessageDialog(this, "����Ϊ��!");
				} else if (Service.QueryTable(mem_name) > 0) {
					JOptionPane.showMessageDialog(this, "�����Ѵ���!");
				} else {
					int insertresult=Service.InsertTable(mem_name);	
					System.out.println("���������" + insertresult + "(1����ɹ���0����ʧ��)");
					if(1==insertresult){
						this.paintJTable();
						JOptionPane.showMessageDialog(this, "�����ɹ�!");
					}
					else{
						JOptionPane.showMessageDialog(this, "����ʧ��!");
					}
				}		
			}else if(e.getActionCommand().equals("ɾ��")){
				String mem_name = tfUsername.getText();
				if (mem_name.equals("") || mem_name == null) {
					JOptionPane.showMessageDialog(this, "����Ϊ��!");
				} else if (Service.QueryTable(mem_name) < 1) {
					JOptionPane.showMessageDialog(this, "����������!");
				} else {
					int deleteresult=Service.DeleteTable(mem_name);			
					System.out.println("ɾ�������" + deleteresult + "(1����ɹ���0����ʧ��)");
					if(1==deleteresult){
						this.paintJTable();
						JOptionPane.showMessageDialog(this, "ɾ���ɹ�!");
					}
					else{
						JOptionPane.showMessageDialog(this, "ɾ��ʧ��!");
					}
				}
			}else if(e.getActionCommand().equals("ˢ��")){
				this.paintJTable();
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "����"+ex.toString());
			ex.printStackTrace();
		}
	}
	
	/**
	 * ���Ʊ��
	 */
	public void paintJTable(){
		try{
			int rownumber=Service.GetCount();
			String[][] row=new String[rownumber][10];
			String[] column={"���","����","���","����","С�Գ�","���","����","С�Գ�","ȷ��ʱ��"};
			tmd=new MyTableModel(row,column);
			table = new JTable(tmd);
			
			//����ȷ��ʱ����п�
	        TableColumn tableColumn = table.getColumnModel().getColumn(8);
	        tableColumn.setPreferredWidth(300);

			List<Map<String,Object>> show=Service.ShowTable();
			for(int i=0;i<rownumber;i++){
				table.setValueAt(show.get(i).get("���"), i, 0);
				table.setValueAt(show.get(i).get("����"), i, 1);
				
				table.setValueAt(show.get(i).get("���"), i, 2);
				table.setValueAt(show.get(i).get("����1"), i, 3);
				table.setValueAt(show.get(i).get("С�ԳǾͲ�1"), i, 4);
				
				table.setValueAt(show.get(i).get("���"), i, 5);
				table.setValueAt(show.get(i).get("����2"), i, 6);
				table.setValueAt(show.get(i).get("С�ԳǾͲ�2"), i, 7);
				
				table.setValueAt(show.get(i).get("ȷ��ʱ��"), i, 8);
			}
			p3.setViewportView(table);
			p3.paintImmediately(p3.getBounds());
		}catch(Exception ex){
			JOptionPane.showMessageDialog(this, "JTable���ƴ���"+ex.toString());
			ex.printStackTrace();			
		}
	}

	/**
	 * ��дDefaultTableModel ʵ�ֱ���޷��޸�
	 * @author gaoxiang
	 *
	 */
	public class MyTableModel extends DefaultTableModel{
		
		private static final long serialVersionUID = 2;
		
        public MyTableModel(String[][] rowData, String[] columns) {
            super(rowData, columns);
        }

		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
			
	}

}
