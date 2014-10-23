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
	 * 图形视图初始化
	 */
	public EatCountJPanel() {
		
		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout());
		lblUsername = new JLabel("姓名:");
		tfUsername = new JTextField(12);
		p1.add(lblUsername);
		p1.add(tfUsername);

//		String strnot[] ={"绝食","我不饿","减肥","珍惜粮食","吃麻辣烫去","不想次范","中秋快乐"};
		
		String str[] = { "桌餐", "小吃城", "- -" };
//		Random ran = new Random();
//		int i = ran.nextInt(7);
//		str[2] = strnot[i];
		lbllunch = new JLabel("中午:");
		lunch = new JComboBox(str);
		lbldinner = new JLabel("晚上:");
		dinner = new JComboBox(str);
		p1.add(lbllunch);
		p1.add(lunch);
		p1.add(lbldinner);
		p1.add(dinner);

		JPanel p2 = new JPanel();
		p2.setLayout(new FlowLayout());
		btnOK = new JButton("更新");
		btnOK.addActionListener(this);
		btnExit = new JButton("退出");
		btnExit.addActionListener(this);
		btnExport = new JButton("导出");
		btnExport.addActionListener(this);
		btnInsert = new JButton("新增");
		btnInsert.addActionListener(this);
		btnDelete = new JButton("删除");
		btnDelete.addActionListener(this);
		btnFlush = new JButton("刷新");
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
	 * 监听器，处理事件
	 */
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getActionCommand().equals("更新")) {

				String person_lunch = lunch.getSelectedIndex() + "";
				String person_dinner = dinner.getSelectedIndex() + "";
				String mem_name = tfUsername.getText().trim();

				if (mem_name.equals("") || mem_name == null) {
					JOptionPane.showMessageDialog(this, "姓名为空!");
				} else if (Service.QueryTable(mem_name) < 1) {
					JOptionPane.showMessageDialog(this, "姓名不存在!");
				} else {
					int updateresult = Service.UpdateTable(person_lunch,
							person_dinner, mem_name);
					System.out.println("更新结果：" + updateresult + "(1代表成功，0代表失败)");
					if (updateresult > 0) {
						this.paintJTable();
						if(person_lunch.equals("2")||person_dinner.equals("2")){
							JOptionPane.showMessageDialog(this, "已更新，祝您好运！");
						}else{
							JOptionPane.showMessageDialog(this, "已更新，祝您用餐愉快！");
						}
					} else {
						JOptionPane.showMessageDialog(this, "更新失败!");
					}
				}
			} else if (e.getActionCommand().equals("退出")) {
				System.exit(0);
			} else if (e.getActionCommand().equals("导出")){
				int exportresult=Service.ExportTable();
				System.out.println("导出结果：" + exportresult + "(1代表成功，0代表失败)");
				if(1==exportresult){
					this.paintJTable();
					JOptionPane.showMessageDialog(this, "导出成功!");
				}
				else{
					JOptionPane.showMessageDialog(this, "导出失败!");
				}
			}else if(e.getActionCommand().equals("新增")){
				String mem_name = tfUsername.getText();
				if(mem_name.length()>24){
					JOptionPane.showMessageDialog(this, "姓名过长!");
				} else if (mem_name.equals("") || mem_name == null) {
					JOptionPane.showMessageDialog(this, "姓名为空!");
				} else if (Service.QueryTable(mem_name) > 0) {
					JOptionPane.showMessageDialog(this, "姓名已存在!");
				} else {
					int insertresult=Service.InsertTable(mem_name);	
					System.out.println("新增结果：" + insertresult + "(1代表成功，0代表失败)");
					if(1==insertresult){
						this.paintJTable();
						JOptionPane.showMessageDialog(this, "新增成功!");
					}
					else{
						JOptionPane.showMessageDialog(this, "新增失败!");
					}
				}		
			}else if(e.getActionCommand().equals("删除")){
				String mem_name = tfUsername.getText();
				if (mem_name.equals("") || mem_name == null) {
					JOptionPane.showMessageDialog(this, "姓名为空!");
				} else if (Service.QueryTable(mem_name) < 1) {
					JOptionPane.showMessageDialog(this, "姓名不存在!");
				} else {
					int deleteresult=Service.DeleteTable(mem_name);			
					System.out.println("删除结果：" + deleteresult + "(1代表成功，0代表失败)");
					if(1==deleteresult){
						this.paintJTable();
						JOptionPane.showMessageDialog(this, "删除成功!");
					}
					else{
						JOptionPane.showMessageDialog(this, "删除失败!");
					}
				}
			}else if(e.getActionCommand().equals("刷新")){
				this.paintJTable();
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "错误："+ex.toString());
			ex.printStackTrace();
		}
	}
	
	/**
	 * 绘制表格
	 */
	public void paintJTable(){
		try{
			int rownumber=Service.GetCount();
			String[][] row=new String[rownumber][10];
			String[] column={"序号","姓名","午餐","桌餐","小吃城","晚餐","桌餐","小吃城","确认时间"};
			tmd=new MyTableModel(row,column);
			table = new JTable(tmd);
			
			//设置确认时间的列宽
	        TableColumn tableColumn = table.getColumnModel().getColumn(8);
	        tableColumn.setPreferredWidth(300);

			List<Map<String,Object>> show=Service.ShowTable();
			for(int i=0;i<rownumber;i++){
				table.setValueAt(show.get(i).get("序号"), i, 0);
				table.setValueAt(show.get(i).get("姓名"), i, 1);
				
				table.setValueAt(show.get(i).get("午餐"), i, 2);
				table.setValueAt(show.get(i).get("桌餐1"), i, 3);
				table.setValueAt(show.get(i).get("小吃城就餐1"), i, 4);
				
				table.setValueAt(show.get(i).get("晚餐"), i, 5);
				table.setValueAt(show.get(i).get("桌餐2"), i, 6);
				table.setValueAt(show.get(i).get("小吃城就餐2"), i, 7);
				
				table.setValueAt(show.get(i).get("确认时间"), i, 8);
			}
			p3.setViewportView(table);
			p3.paintImmediately(p3.getBounds());
		}catch(Exception ex){
			JOptionPane.showMessageDialog(this, "JTable绘制错误："+ex.toString());
			ex.printStackTrace();			
		}
	}

	/**
	 * 重写DefaultTableModel 实现表格无法修改
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
