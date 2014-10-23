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
	 * 更新数据库
	 * @param person_lunch 午餐标识
	 * @param person_dinner 晚餐标识
	 * @param mem_name 成员姓名
	 * @return 返回是否成功标识1代表成功0代表失败
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
	 * 查询数据库
	 * @param mem_name 成员姓名
	 * @return 成员是否存在0代表不存在
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
	 * 导出为Excel表格
	 */
	public static int ExportTable()throws Exception{
		OracleConnection oc = new OracleConnection();
		String sql = "select rownum 序号, " +
				"mem_name as 姓名, " +
				"'' 午餐,decode(lunch, '0', 'Y', '') 桌餐,decode(lunch, '1', 'Y', '') 小吃城就餐, " +
				"'' 晚餐,decode(dinner, '0', 'Y', '') 桌餐,decode(dinner, '1', 'Y', '') 小吃城就餐, " +
				"update_time 确认时间 from crm_eat order by 序号 asc ";
		ResultSet rs = oc.query(sql);
		
		GregorianCalendar ca = new GregorianCalendar();
		String filePath="";
		if(1 == ca.get(GregorianCalendar.AM_PM)){
			ca.add(GregorianCalendar.DATE, 1);
			filePath = "C:\\Users\\Administrator\\Desktop\\"
					+ new SimpleDateFormat("yyyy-MM-dd").format(ca.getTime()) + "就餐统计.xls";
		}else{
			filePath = "C:\\Users\\Administrator\\Desktop\\"
				+ new SimpleDateFormat("yyyy-MM-dd").format(ca.getTime()) + "就餐统计确认.xls";
		}
		String[] title = {"序号","姓名","午餐","桌餐","小吃城就餐","晚餐","桌餐","小吃城就餐","确认时间"};   
		OutputStream os = new FileOutputStream(filePath);
		WritableWorkbook book = Workbook.createWorkbook(os);
		WritableSheet sheet = book.createSheet("就餐统计", 0);
		Label label = null;  
        for(int i=0;i<title.length;i++){  
            // Label(x,y,z) 代表单元格的第x+1列，第y+1行, 内容z  
            // 在Label对象的子对象中指明单元格的位置和内容  
            label = new Label(i,0,title[i]);
            // 将定义好的单元格添加到工作表中  
            sheet.addCell(label);  
        }
        //遍历rs，并将数据添加到sheet中
        int count = 0;//行数
        while(rs.next()){
        	count++;
        	for(int j=0;j<rs.getMetaData().getColumnCount();j++){
        		label = new Label(j,count,rs.getString(j+1));
                sheet.addCell(label);  
        	}
        }
        System.out.println("导出行数： "+count);
        book.write();
        book.close();
        oc.closeAll();
		System.out.println("ExportTable:" +count);
		return count>0?1:0;
	}
	
	/**
	 * 新加入成员
	 * @return 插入的行数
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
	 * 删除成员
	 * @return 删除的行数
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
	 * 获取表中数据并转化为List对象
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String,Object>> ShowTable()throws Exception{
		List <Map<String,Object>> result = null;
		OracleConnection oc = new OracleConnection();
		String sql = "select rownum 序号, " +
		"mem_name as 姓名, " +
		"'' 午餐,decode(lunch, '0', 'Y', '') 桌餐1,decode(lunch, '1', 'Y', '') 小吃城就餐1, " +
		"'' 晚餐,decode(dinner, '0', 'Y', '') 桌餐2,decode(dinner, '1', 'Y', '') 小吃城就餐2, " +
		"update_time 确认时间 from crm_eat order by 序号 asc ";
		ResultSet rs = oc.query(sql);
		result =DBUtil.resultSetToList(rs);
		System.out.println("ShowTable:" +result);
		return result;
	}
	
	/**
	 * 获取表的行数
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
