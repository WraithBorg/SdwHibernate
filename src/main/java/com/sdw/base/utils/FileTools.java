package com.sdw.base.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class FileTools {

	/**
	* 创建文件
	* @param fileName 文件名称
	* @param filecontent 文件内容
	* @return 是否创建成功，成功则返回true
	*/
	public static boolean createFile(String fileName, String filecontent) {
		Boolean bool = false;

		File file = new File(fileName);
		try {
			// 如果文件存在，则删除
			if (file.exists()) delFile(fileName);

			file.createNewFile();
			// 创建文件成功后，写入内容到文件里
			writeFileContent(fileName, filecontent);
			bool = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
	}

	/**
	* 向文件中写入内容
	* @param filepath 文件路径与名称
	* @param str 写入的内容
	* @return
	* @throws IOException
	*/
	public static boolean writeFileContent(String filepath, String str) throws IOException {
		Boolean bool = false;
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		FileOutputStream fos = null;
		PrintWriter pw = null;
		try {
			File file = new File(filepath);// 文件路径(包括文件名称)
			// 将文件读入输入流
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			StringBuffer buffer = new StringBuffer();
			buffer.append(str);
			fos = new FileOutputStream(file);
			pw = new PrintWriter(fos);
			pw.write(buffer.toString().toCharArray());
			pw.flush();
			bool = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pw != null) pw.close();
			if (fos != null) fos.close();
			if (br != null) br.close();
			if (isr != null) isr.close();
			if (fis != null) fis.close();
		}
		return bool;
	}

	/**
	 * 删除文件
	 * @param fileName 文件名称
	 * @return
	 */
	public static boolean delFile(String fileName) {
		Boolean bool = false;
		File file = new File(fileName);
		try {
			if (file.exists()) {
				file.delete();
				bool = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
	}

}
