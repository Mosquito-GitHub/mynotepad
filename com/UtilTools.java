package com;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class UtilTools {

	/**
	 * 功能：Java读取txt文件的内容
	 * 步骤：1：先获得文件句柄
	 * 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
	 * 3：读取到输入流后，需要读取生成字节流
	 * 4：一行一行的输出。readline()。
	 * 备注：需要考虑的是异常情况
	 * @param file
	 * @param texttArea
	 */

	public void readTxtFile(File file,MyTextArea textArea) {

		try {

			String encoding = "gb2312";
			
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					int pos = textArea.getCaretPosition();
					textArea.insert(lineTxt+"\n", pos);
					//System.out.println(lineTxt);
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
	}
	
	/**
	 * 将texttArea中的文本写入文件file中
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
