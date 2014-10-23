package service;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import databaseconnect.DBUtil;
import databaseconnect.OracleConnection;

public class Service {

	/**
	 * �������ݿ�
	 * @param person_lunch ��ͱ�ʶ
	 * @param person_dinner ��ͱ�ʶ
	 * @param mem_name ��Ա����
	 * @return �����Ƿ�ɹ���ʶ1����ɹ�0����ʧ��
	 */
	public static int UpdateTable(String person_lunch, String person_dinner,
			String mem_name) {
		OracleConnection oc = new OracleConnection();
		String sql = "update crm_eat set lunch = :1, dinner = :2, update_time = sysdate where mem_name = :3 ";
		int result = oc.update(sql, person_lunch, person_dinner, mem_name);
		oc.closeAll();
		System.out.println("UpdateTable:" + mem_name + " " + person_lunch + " "
				+ person_dinner + " " + result);
		return result;
	}

	/**
	 * ��ѯ���ݿ�
	 * @param mem_name ��Ա����
	 * @return ��Ա�Ƿ����0��������
	 */
	public static int QueryTable(String mem_name) {
		int result = 0;
		OracleConnection oc = new OracleConnection();
		String sql = "select count(1) from crm_eat where mem_name =:1 ";
		ResultSet rs = oc.query(sql, mem_name);
		try {
			rs.next();
			result = rs.getInt("COUNT(1)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		oc.closeAll();
		System.out.println("QueryTable:" + mem_name + " " + result);
		return result;
	}
	/**
	 * ����ΪExcel���
	 */
	public static int ExportTable()throws Exception{
		OracleConnection oc = new OracleConnection();
		String sql = "select rownum ���, " +
				"mem_name as ����, " +
				"'' ���,decode(lunch, '0', 'Y', '') ����,decode(lunch, '1', 'Y', '') С�ԳǾͲ�, " +
				"'' ���,decode(dinner, '0', 'Y', '') ����,decode(dinner, '1', 'Y', '') С�ԳǾͲ�, " +
				"update_time ȷ��ʱ�� from crm_eat order by ��� asc ";
		ResultSet rs = oc.query(sql);
		
		GregorianCalendar ca = new GregorianCalendar();
		String filePath="";
		if(1 == ca.get(GregorianCalendar.AM_PM)){
			ca.add(GregorianCalendar.DATE, 1);
			filePath = "C:\\Users\\Administrator\\Desktop\\"
					+ new SimpleDateFormat("yyyy-MM-dd").format(ca.getTime()) + "�Ͳ�ͳ��.xls";
		}else{
			filePath = "C:\\Users\\Administrator\\Desktop\\"
				+ new SimpleDateFormat("yyyy-MM-dd").format(ca.getTime()) + "�Ͳ�ͳ��ȷ��.xls";
		}
		String[] title = {"���","����","���","����","С�ԳǾͲ�","���","����","С�ԳǾͲ�","ȷ��ʱ��"};   
		OutputStream os = new FileOutputStream(filePath);
		WritableWorkbook book = Workbook.createWorkbook(os);
		WritableSheet sheet = book.createSheet("�Ͳ�ͳ��", 0);
		Label label = null;  
        for(int i=0;i<title.length;i++){  
            // Label(x,y,z) ����Ԫ��ĵ�x+1�У���y+1��, ����z  
            // ��Label������Ӷ�����ָ����Ԫ���λ�ú�����  
            label = new Label(i,0,title[i]);
            // ������õĵ�Ԫ����ӵ���������  
            sheet.addCell(label);  
        }
        //����rs������������ӵ�sheet��
        int count = 0;//����
        while(rs.next()){
        	count++;
        	for(int j=0;j<rs.getMetaData().getColumnCount();j++){
        		label = new Label(j,count,rs.getString(j+1));
                sheet.addCell(label);  
        	}
        }
        System.out.println("���������� "+count);
        book.write();
        book.close();
        oc.closeAll();
		System.out.println("ExportTable:" +count);
		return count>0?1:0;
	}
	
	/**
	 * �¼����Ա
	 * @return ���������
	 */
	public static  int InsertTable(String mem_name){
		int result = 0;
		OracleConnection oc = new OracleConnection();
		String sql="declare "+
 " valnew_mem_id crm_eat.mem_id%type; "+
 " begin "+
 " select max(mem_id) + 1 into valnew_mem_id from crm_eat; "+
 " insert into crm_eat "+
 "   (mem_id, mem_name, lunch, dinner, update_time) "+
 " values "+
 "   (valnew_mem_id, :1, '0', '0', sysdate); "+
 " commit; "+
 " end; ";
		result = oc.update(sql, mem_name);
		oc.closeAll();
		System.out.println("InsertTable:" +result);
		return result;
	}
	
	/**
	 * ɾ����Ա
	 * @return ɾ��������
	 */
	public static int DeleteTable(String mem_name){
		int result = 0;
		OracleConnection oc = new OracleConnection();
		String sql="delete from crm_eat where mem_name in(:1)";
		result = oc.update(sql, mem_name);
		oc.closeAll();
		System.out.println("DeleteTable:" +result);
		return result;
	}
	
	/**
	 * ��ȡ�������ݲ�ת��ΪList����
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String,Object>> ShowTable()throws Exception{
		List <Map<String,Object>> result = null;
		OracleConnection oc = new OracleConnection();
		String sql = "select rownum ���, " +
		"mem_name as ����, " +
		"'' ���,decode(lunch, '0', 'Y', '') ����1,decode(lunch, '1', 'Y', '') С�ԳǾͲ�1, " +
		"'' ���,decode(dinner, '0', 'Y', '') ����2,decode(dinner, '1', 'Y', '') С�ԳǾͲ�2, " +
		"update_time ȷ��ʱ�� from crm_eat order by ��� asc ";
		ResultSet rs = oc.query(sql);
		result =DBUtil.resultSetToList(rs);
		System.out.println("ShowTable:" +result);
		return result;
	}
	
	/**
	 * ��ȡ�������
	 * @return
	 */
	public static int GetCount()throws Exception{
		int result=0;
		OracleConnection oc = new OracleConnection();
		String sql="select count(1) from crm_eat";
		ResultSet rs = oc.query(sql);
		rs.next();
		result=Integer.parseInt(rs.getString("count(1)"));
		System.out.println("GetCount:" +result);
		return result;
	}
}
