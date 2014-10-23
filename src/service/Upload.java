package service;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import log.MainLogger;

public class Upload {

	/**�滻·��path�������ļ����е��ַ���arg0Ϊarg1
	 * @param path �ļ�����·������\\��β �ο���<b>C:\\Users\\devtao\\Desktop\\upload\\</b>
	 * @param arg0 ��Ҫ���滻���ַ��� �ο���<b>_ucr_</b>
	 * @param arg1 �滻���ַ��� �ο���<b>_uop_</b>
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
	 * ɾ��·��path���ļ��������ַ���arg0���ļ�
	 * @param path �ļ�����·������\\��β �ο���<b>C:\\Users\\devtao\\Desktop\\upload\\</b>
	 * @param arg0 ɾ�����ַ��� �ο���<b>.bak</b>
	 */
	public static void deleteFile(String path,String arg0){
		System.out.println("path:"+path+"\n"+"arg0:"+arg0);
		MainLogger.addLOG("path:"+path+"\n"+"arg0:"+arg0);
		File file = new File(path);
		File[] files = file.listFiles();
		if(files==null){
			MainLogger.addLOG("·��"+file+"�����ڣ�");			
			return;
		}
		for (int i = 0; i < files.length; i++) {
			File tempFile = files[i];
			String name = tempFile.getName();
			if(name.indexOf(arg0)>-1){
				tempFile.delete();
				System.out.println(name+"��ɾ����");
				MainLogger.addLOG(name+"��ɾ����");
			}
		}		
	}
	
	/**
	 * ��·��path���ļ���fileName�е�arg0������Ϊarg0[i]������n��
	 * @param path �ļ�����·������\\��β �ο���<b>C:\\Users\\devtao\\Desktop\\upload\\</b>
	 * @param fileName �ļ��� �ο���<b>5880_1_code_crm_uop_crm1_P_CSM_MODIFYPRODUCT_NEW.prc</b>
	 * @param arg0 ��Ҫ�滻���ַ��� �ο���<b>crm1</b>
	 * @param n ��Ҫ���Ƶķ��� �ο� <b>8</b>
	 * @throws Exception
	 */
	public static void copyFile(String path,String fileName,String arg0,int n) throws Exception {
		for(int i=2;i<=n;i++){
			String newFileName=fileName.replaceAll(arg0, arg0.substring(0, arg0.length()-1)+i);//modify here.
			FileReader fr = new FileReader(path+fileName);
			BufferedReader br = new BufferedReader(fr);
			FileWriter fw = new FileWriter(path+newFileName);
			BufferedWriter bw = new BufferedWriter(fw);
			System.out.println(newFileName+"��ʼ���ơ�������������");
			MainLogger.addLOG(newFileName+"��ʼ���ơ�������������");
			String line;
			
			while ((line = br.readLine()) != null) {
				bw.write(line+"\r\n");
			}
			System.out.println(newFileName+"���ƽ�����");
			MainLogger.addLOG(newFileName+"���ƽ�����");
			bw.close();
			fw.close();
			br.close();
			fr.close();
		}
		
	}
	
	
	/**
	 * ��·��path���ļ���fileName�е�arg0������Ϊarg0[i]������n��(Procedureר��)
	 * ע�� Դ�洢���̵ĵ�һ����Ҫ��������� 
	 * <b>CREATE OR REPLACE PROCEDURE uop_crm1.P_CSM_ACTIVE_CP_TRADE_NEW(</b>
	 * @param path �ļ�����·������\\��β �ο���<b>C:\\Users\\devtao\\Desktop\\upload\\</b>
	 * @param fileName �ļ��� �ο���<b>5880_1_code_crm_uop_crm1_P_CSM_MODIFYPRODUCT_NEW.prc</b>
	 * @param arg0 ��Ҫ�滻���ַ��� �ο���<b>crm1</b>
	 * @param n ��Ҫ���Ƶķ��� �ο� <b>8</b>
	 * @throws Exception
	 */
	public static void copyProcedureFile(String path,String fileName,String arg0,int n) throws Exception {
		for(int i=2;i<=n;i++){
			String newFileName=fileName.replaceAll(arg0, arg0.substring(0, arg0.length()-1)+i);//modify here.
			FileReader fr = new FileReader(path+fileName);
			BufferedReader br = new BufferedReader(fr);
			FileWriter fw = new FileWriter(path+newFileName);
			BufferedWriter bw = new BufferedWriter(fw);
			System.out.println(newFileName+"��ʼ���ơ�������������");
			MainLogger.addLOG(newFileName+"��ʼ���ơ�������������");
			String line;
			
			//PROCEDURE���⴦��
			line = br.readLine();
			String[] x =line.split("\\.");
			line=x[0].substring(0, x[0].length()-1)+i+"."+x[1];
			bw.write(line+"\r\n");
			
			while ((line = br.readLine()) != null) {
				bw.write(line+"\r\n");
			}

			System.out.println(newFileName+"���ƽ�����");
			MainLogger.addLOG(newFileName+"���ƽ�����");
			bw.close();
			fw.close();
			br.close();
			fr.close();
		}
		
	}
		
	/**
	 * ɾ��SQL�ļ��е�ע��,ѹ�����룬�����sql��������ܶ�O(��_��)O
	 * ע�����ࣺ1 ����ע�� <b>--XXXXXX</b>
	 * 2 ����ע�� <b>/ *XXXXXX* /</b>
	 * @param path �ļ�����·������\\��β �ο���<b>C:\\Users\\devtao\\Desktop\\upload\\</b>
	 * @param fileName �ļ��� �ο���<b>P_CSM_MODIFYPRODUCT_NEW.prc</b>
	 * @throws Exception
	 */
	public static void cleanFile(String path,String fileName) throws Exception {
			String newFileName="clean"+fileName;
			FileReader fr = new FileReader(path+fileName);
			BufferedReader br = new BufferedReader(fr);//�ļ����1M
			FileWriter fw = new FileWriter(path+newFileName);
			BufferedWriter bw = new BufferedWriter(fw);
			System.out.println(newFileName+"��ʼ����������������");
			MainLogger.addLOG(newFileName+"��ʼ����������������");
			String line;
			boolean tag = false;//��ʾ��ʼ�Ƿ���ע����
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				line = line.trim();
				while(true){
					if(!tag){//�����ʼ������ע����
						if(line.indexOf("--")!=-1||line.indexOf("/*")!=-1){//�������--����/*
							if(line.indexOf("--")==-1){//���û��--��ֻ��/*��
								String lastLine = line.substring(line.indexOf("/*"),line.length());//ȡ��һ��/*֮�������
								if(lastLine.contains("*/")){//���/*֮������ݺ���*/
									if(lastLine.indexOf("*/")==lastLine.length()-2){//���*/��Ϊ��β
										line = line.substring(0, line.indexOf("/*"));//ȥ��/**/���н���
										break;
									}else{//*/���ǽ�β
										line = line.substring(0,line.indexOf("/*"))
										+lastLine.substring(lastLine.indexOf("*/")+2, lastLine.length());//ȥ��/**/�ж�ʣ�µ�����
										continue;
									}
								}else{///*֮�������û��*/
									tag = true;//�����һ����ʼ����ע����
									line = line.substring(0, line.indexOf("/*"));//ȡ��/*֮ǰ����ע�͵�����
									break;
								}
							}else if(line.indexOf("/*")==-1){//���������/*��ֻ��--��
								line = line.substring(0, line.indexOf("--"));//ȡ��--֮ǰ����ע�͵�����
								break;
							}else{//�Ⱥ���/*�ֺ���--
								assert line.indexOf("/*")!=-1&&line.indexOf("--")!=-1;
								if(line.indexOf("/*")>line.indexOf("--")){//���--��/*֮ǰ
									line = line.substring(0, line.indexOf("--"));//ȡ��--֮ǰ����ע�͵�����
									break;
								}else if(line.indexOf("/*")<line.indexOf("--")){//���/*��--֮ǰ
									String lastLine = line.substring(line.indexOf("/*"),line.length());//ȡ��һ��/*֮�������
									if(lastLine.contains("*/")){//���/*֮������ݺ���*/
										if(lastLine.indexOf("*/")==lastLine.length()-2){//���*/��Ϊ��β
											line = line.substring(0, line.indexOf("/*"));//ȥ��/**/���н���
											break;
										}else{//*/���ǽ�β
											line = line.substring(0,line.indexOf("/*"))
											+lastLine.substring(lastLine.indexOf("*/")+2, lastLine.length());//ȥ��/**/�ж�ʣ�µ�����
											continue;
										}				
									}else{///*֮�������û��*/
										tag = true;//�����һ����ʼ����ע����
										line = line.substring(0, line.indexOf("/*"));//ȡ��/*֮ǰ����ע�͵�����
										break;
									}
								}
							}
						}else{//����--��/*
							break;
						}
					}else{//��ʼ������ע����
						if(!line.contains("*/")){//������в�����*/
							line = "";//���ж���ע��
							break;
						}else{//���а���*/
							if(line.indexOf("*/")==line.length()-2){//���*/�ڸ��н�β
								line = "";//���ж���ע��
								tag = false;//��һ����ʼ����ע��
								break;
							}else{//���*/���ڸ��н�β
								line = line.substring(line.indexOf("*/")+2, line.length());//ȡ*/ֱ����β������
								tag = false;//��ʼ����ע��
								continue;
							}
						}				
					}
				}
				sb.append(line+"\n");//��ӵ�sb�У���" "��Ϊ"\n"�Ļ��������Ϊһ��
			}
			bw.write(sb+"\r\n");
			System.out.println(newFileName+"���������");
			MainLogger.addLOG(newFileName+"���������");
			bw.close();
			fw.close();
			br.close();
			fr.close();	
	}	

}
