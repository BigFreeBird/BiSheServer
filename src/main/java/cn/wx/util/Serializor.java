package cn.wx.util;

import java.awt.Point;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import cn.wx.entiry.User;

/**
 * @author WX
 *	对象的序列化和反序列化
 */
public class Serializor {
	/**
	 * 序列化对象
	 * @param object 要转化的对象
	 * @return 对象转化成的序列
	 * @throws IOException
	 */
	public static byte[] serialzeTobytes(Object object) throws IOException {
		ByteArrayOutputStream os=new ByteArrayOutputStream();
		ObjectOutputStream oos=null;
		oos=new ObjectOutputStream(os);
		oos.writeObject(object);		  
		oos.close();
		return os.toByteArray();
	  }

	/**
	 * 反序列化对象
	 * @param bs 对象的序列
	 * @param clazz 对象的类型
	 * @return 由序列反序列化成的clazzlei类对象
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */

	@SuppressWarnings("unchecked")
	public static <clazz> clazz deSerialize(byte[] bs,Class<?> clazz) throws IOException, ClassNotFoundException {
		ObjectInputStream ois=null;
		clazz object=null;
		ois=new ObjectInputStream(new ByteArrayInputStream(bs));
		object=(clazz) ois.readObject();
		ois.close();		
		return object;
	}
	
	/**
	 * 序列化对象到文件
	 * @param object 
	 * @param filepath
	 * @throws IOException
	 */
	public static void serializeToFile(Object object,String filepath) throws IOException {
		FileOutputStream fos=new FileOutputStream(filepath);
		fos.write(serialzeTobytes(object));
		fos.flush();
		fos.close();
	}
	
	/**
	 * 从文件反序列化对象
	 * @param filepath 文件路径
	 * @param clazz	反序列化的对象类型
	 * @return 反序列化的对象
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static <clazz> clazz deSerialize(String filepath,Class<?> clazz) throws IOException, ClassNotFoundException {
		byte[] bs=FileOperation.readFile(filepath);
		clazz object=deSerialize(bs, clazz);
		return object;
	}
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		User user=new User("A", "12", 12, false, "123", new Point(1, 1));
		byte[] bs=Serializor.serialzeTobytes(user);
		User user2=Serializor.deSerialize(bs, User.class);
		System.out.println(user2);
	}
}
