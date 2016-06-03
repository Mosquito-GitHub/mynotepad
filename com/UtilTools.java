package com;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class UtilTools {

	/**
	 * ���ܣ�Java��ȡtxt�ļ�������
	 * ���裺1���Ȼ���ļ����
	 * 2������ļ��������������һ���ֽ���������Ҫ��������������ж�ȡ
	 * 3����ȡ������������Ҫ��ȡ�����ֽ���
	 * 4��һ��һ�е������readline()��
	 * ��ע����Ҫ���ǵ����쳣���
	 * @param file
	 * @param texttArea
	 */

	public void readTxtFile(File file,MyTextArea textArea) {

		try {

			String encoding = "gb2312";
			
			if (file.isFile() && file.exists()) { // �ж��ļ��Ƿ����
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// ���ǵ������ʽ
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					int pos = textArea.getCaretPosition();
					textArea.insert(lineTxt+"\n", pos);
					//System.out.println(lineTxt);
				}
				read.close();
			} else {
				System.out.println("�Ҳ���ָ�����ļ�");
			}
		} catch (Exception e) {
			System.out.println("��ȡ�ļ����ݳ���");
			e.printStackTrace();
		}
	}
	
	/**
	 * ��texttArea�е��ı�д���ļ�file��
	 * @param texttArea
	 * @param file
	 */
	public void writeTextFile(MyTextArea textArea,File file){
		try{
			String encode = "gb2312";
			String[] readerLine = textArea.getText().split("\n");
			BufferedWriter out =new BufferedWriter(new FileWriter(file,true));
			System.out.println(readerLine.length);
			for(int i=0;i<readerLine.length;i++){
				out.write(readerLine[i]);
				out.newLine();
				
			}
			
			 out.close(); 
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
