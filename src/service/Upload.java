package service;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import log.MainLogger;

public class Upload {

	/**替换路径path下所有文件名中的字符串arg0为arg1
	 * @param path 文件所在路径，以\\结尾 参考：<b>C:\\Users\\devtao\\Desktop\\upload\\</b>
	 * @param arg0 需要被替换的字符串 参考：<b>_ucr_</b>
	 * @param arg1 替换的字符串 参考：<b>_uop_</b>
	 * @throws Exception 
	 */
	public static void replaceFilename(String path,String arg0,String arg1) throws Exception {
		File file = new File(path);
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			File tempFile = files[i];
			String name = tempFile.getName();
			String newName = name.replaceAll(arg0, arg1);//modify here.
			File newFile = new File(path + newName);
			boolean flag = tempFile.renameTo(newFile);
			System.out.println(newFile.getName()+" "+flag);
			MainLogger.addLOG(newFile.getName()+" "+flag);
		}
	}
	
	/**
	 * 删除路径path下文件名包含字符串arg0的文件
	 * @param path 文件所在路径，以\\结尾 参考：<b>C:\\Users\\devtao\\Desktop\\upload\\</b>
	 * @param arg0 删除的字符串 参考：<b>.bak</b>
	 */
	public static void deleteFile(String path,String arg0){
		System.out.println("path:"+path+"\n"+"arg0:"+arg0);
		MainLogger.addLOG("path:"+path+"\n"+"arg0:"+arg0);
		File file = new File(path);
		File[] files = file.listFiles();
		if(files==null){
			MainLogger.addLOG("路径"+file+"不存在！");			
			return;
		}
		for (int i = 0; i < files.length; i++) {
			File tempFile = files[i];
			String name = tempFile.getName();
			if(name.indexOf(arg0)>-1){
				tempFile.delete();
				System.out.println(name+"已删除。");
				MainLogger.addLOG(name+"已删除。");
			}
		}		
	}
	
	/**
	 * 将路径path的文件名fileName中的arg0重命名为arg0[i]并复制n份
	 * @param path 文件所在路径，以\\结尾 参考：<b>C:\\Users\\devtao\\Desktop\\upload\\</b>
	 * @param fileName 文件名 参考：<b>5880_1_code_crm_uop_crm1_P_CSM_MODIFYPRODUCT_NEW.prc</b>
	 * @param arg0 需要替换的字符串 参考：<b>crm1</b>
	 * @param n 需要复制的份数 参考 <b>8</b>
	 * @throws Exception
	 */
	public static void copyFile(String path,String fileName,String arg0,int n) throws Exception {
		for(int i=2;i<=n;i++){
			String newFileName=fileName.replaceAll(arg0, arg0.substring(0, arg0.length()-1)+i);//modify here.
			FileReader fr = new FileReader(path+fileName);
			BufferedReader br = new BufferedReader(fr);
			FileWriter fw = new FileWriter(path+newFileName);
			BufferedWriter bw = new BufferedWriter(fw);
			System.out.println(newFileName+"开始复制·······");
			MainLogger.addLOG(newFileName+"开始复制·······");
			String line;
			
			while ((line = br.readLine()) != null) {
				bw.write(line+"\r\n");
			}
			System.out.println(newFileName+"复制结束。");
			MainLogger.addLOG(newFileName+"复制结束。");
			bw.close();
			fw.close();
			br.close();
			fr.close();
		}
		
	}
	
	
	/**
	 * 将路径path的文件名fileName中的arg0重命名为arg0[i]并复制n份(Procedure专用)
	 * 注意 源存储过程的第一行需要像这个样子 
	 * <b>CREATE OR REPLACE PROCEDURE uop_crm1.P_CSM_ACTIVE_CP_TRADE_NEW(</b>
	 * @param path 文件所在路径，以\\结尾 参考：<b>C:\\Users\\devtao\\Desktop\\upload\\</b>
	 * @param fileName 文件名 参考：<b>5880_1_code_crm_uop_crm1_P_CSM_MODIFYPRODUCT_NEW.prc</b>
	 * @param arg0 需要替换的字符串 参考：<b>crm1</b>
	 * @param n 需要复制的份数 参考 <b>8</b>
	 * @throws Exception
	 */
	public static void copyProcedureFile(String path,String fileName,String arg0,int n) throws Exception {
		for(int i=2;i<=n;i++){
			String newFileName=fileName.replaceAll(arg0, arg0.substring(0, arg0.length()-1)+i);//modify here.
			FileReader fr = new FileReader(path+fileName);
			BufferedReader br = new BufferedReader(fr);
			FileWriter fw = new FileWriter(path+newFileName);
			BufferedWriter bw = new BufferedWriter(fw);
			System.out.println(newFileName+"开始复制·······");
			MainLogger.addLOG(newFileName+"开始复制·······");
			String line;
			
			//PROCEDURE特殊处理
			line = br.readLine();
			String[] x =line.split("\\.");
			line=x[0].substring(0, x[0].length()-1)+i+"."+x[1];
			bw.write(line+"\r\n");
			
			while ((line = br.readLine()) != null) {
				bw.write(line+"\r\n");
			}

			System.out.println(newFileName+"复制结束。");
			MainLogger.addLOG(newFileName+"复制结束。");
			bw.close();
			fw.close();
			br.close();
			fr.close();
		}
		
	}
		
	/**
	 * 删除SQL文件中的注释,压缩代码，让你的sql变得无人能懂O(∩_∩)O
	 * 注释种类：1 单行注释 <b>--XXXXXX</b>
	 * 2 多行注释 <b>/ *XXXXXX* /</b>
	 * @param path 文件所在路径，以\\结尾 参考：<b>C:\\Users\\devtao\\Desktop\\upload\\</b>
	 * @param fileName 文件名 参考：<b>P_CSM_MODIFYPRODUCT_NEW.prc</b>
	 * @throws Exception
	 */
	public static void cleanFile(String path,String fileName) throws Exception {
			String newFileName="clean"+fileName;
			FileReader fr = new FileReader(path+fileName);
			BufferedReader br = new BufferedReader(fr);//文件最大1M
			FileWriter fw = new FileWriter(path+newFileName);
			BufferedWriter bw = new BufferedWriter(fw);
			System.out.println(newFileName+"开始整理·······");
			MainLogger.addLOG(newFileName+"开始整理·······");
			String line;
			boolean tag = false;//表示起始是否在注释中
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				line = line.trim();
				while(true){
					if(!tag){//如果起始不是在注释中
						if(line.indexOf("--")!=-1||line.indexOf("/*")!=-1){//如果含有--或者/*
							if(line.indexOf("--")==-1){//如果没有--（只有/*）
								String lastLine = line.substring(line.indexOf("/*"),line.length());//取第一个/*之后的内容
								if(lastLine.contains("*/")){//如果/*之后的内容含有*/
									if(lastLine.indexOf("*/")==lastLine.length()-2){//如果*/是为结尾
										line = line.substring(0, line.indexOf("/*"));//去掉/**/该行结束
										break;
									}else{//*/不是结尾
										line = line.substring(0,line.indexOf("/*"))
										+lastLine.substring(lastLine.indexOf("*/")+2, lastLine.length());//去掉/**/判断剩下的内容
										continue;
									}
								}else{///*之后的内容没有*/
									tag = true;//标记下一行起始是在注释中
									line = line.substring(0, line.indexOf("/*"));//取在/*之前不是注释的内容
									break;
								}
							}else if(line.indexOf("/*")==-1){//如果不含有/*（只有--）
								line = line.substring(0, line.indexOf("--"));//取在--之前不是注释的内容
								break;
							}else{//既含有/*又含有--
								assert line.indexOf("/*")!=-1&&line.indexOf("--")!=-1;
								if(line.indexOf("/*")>line.indexOf("--")){//如果--在/*之前
									line = line.substring(0, line.indexOf("--"));//取在--之前不是注释的内容
									break;
								}else if(line.indexOf("/*")<line.indexOf("--")){//如果/*在--之前
									String lastLine = line.substring(line.indexOf("/*"),line.length());//取第一个/*之后的内容
									if(lastLine.contains("*/")){//如果/*之后的内容含有*/
										if(lastLine.indexOf("*/")==lastLine.length()-2){//如果*/是为结尾
											line = line.substring(0, line.indexOf("/*"));//去掉/**/该行结束
											break;
										}else{//*/不是结尾
											line = line.substring(0,line.indexOf("/*"))
											+lastLine.substring(lastLine.indexOf("*/")+2, lastLine.length());//去掉/**/判断剩下的内容
											continue;
										}				
									}else{///*之后的内容没有*/
										tag = true;//标记下一行起始是在注释中
										line = line.substring(0, line.indexOf("/*"));//取在/*之前不是注释的内容
										break;
									}
								}
							}
						}else{//不含--与/*
							break;
						}
					}else{//起始内容在注释中
						if(!line.contains("*/")){//如果该行不包括*/
							line = "";//该行都是注释
							break;
						}else{//该行包括*/
							if(line.indexOf("*/")==line.length()-2){//如果*/在该行结尾
								line = "";//该行都是注释
								tag = false;//下一行起始不是注释
								break;
							}else{//如果*/不在该行结尾
								line = line.substring(line.indexOf("*/")+2, line.length());//取*/直到结尾的内容
								tag = false;//起始不是注释
								continue;
							}
						}				
					}
				}
				sb.append(line+"\n");//添加到sb中，将" "改为"\n"的话不会输出为一行
			}
			bw.write(sb+"\r\n");
			System.out.println(newFileName+"整理结束。");
			MainLogger.addLOG(newFileName+"整理结束。");
			bw.close();
			fw.close();
			br.close();
			fr.close();	
	}	

}
