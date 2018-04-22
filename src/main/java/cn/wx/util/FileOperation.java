package cn.wx.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

/**
 * @author WX
 *文件操作
 */
public class FileOperation {
	/**
	 * 文件读取，只能读取2^31-1个位大小，即不大于8兆的文件
	 * @param filepath 文件路径名
	 * @return 文件内容的字节数组
	 * @throws IOException 
	 */
	public static byte[] readFile(String filepath) throws IOException {
		File file=new File(filepath);
		if(!file.exists()||!file.isFile())
			return null;
		FileInputStream fileInputStream=new FileInputStream(file);
		byte[] b=new byte[(int)file.length()];
		fileInputStream.read(b);
		fileInputStream.close();
		return b;
	}
	
	/**
	 * 字节数组保存到文件
	 * @param filepath 保存路径
	 * @param bs 要保存的字节数组
	 * @throws IOException 
	 */
	public static void writeFile(String filepath,byte[] bs) throws IOException {
		File file=new File(filepath);
		if(file.exists())
			throw new FileAlreadyExistsException("filepath"+"已经存在。");
		FileOutputStream fos=new FileOutputStream(file);
		fos.write(bs);
		fos.flush();
		fos.close();
	}
	/**
	 * 返回目录下的所有文件
	 * @param directionPath 目录名
	 * @return 目录下的所有文件
	 */
	public static File[] getFileList(String directionPath){
		File file=new File(directionPath);
		if(!file.isDirectory())
			return null;
		return file.listFiles();
	}
}
