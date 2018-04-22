package cn.wx.entiry;

import java.awt.Point;
import java.io.Serializable;

public class Message implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 交通标志类数据
	 */
	public static final int SIGN=0;
	/**
	 * 突发事件类数据
	 */
	public static final int EMERGENCY=1;
	/**
	 * 速度类事件数据
	 */
	public static final int SPEED=2;
	/**
	 * 用户加入请求类数据
	 */
	public static final int LOGIN=3;
	/**
	 * 用户退出请求类数据
	 */
	public static int LOGOUT=4;
	private long time;
	private Point loction;
	private int type;
	private byte[] value;//一些带值的信息，例如速度等,根据type类型进行解析

	
	public Message(long time, Point point, int type) {
		super();
		this.time = time;
		this.loction = point;
		this.type = type;
	}
	public Message(long time, Point point, int type,byte[]value) {
		super();
		this.time = time;
		this.loction = (Point) point.clone();
		this.type = type;
		this.value=value;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public Point getPoint() {
		return loction;
	}
	public void setPoint(Point point) {
		this.loction = point;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Point getLoction() {
		return loction;
	}
	public void setLoction(Point loction) {
		this.loction = loction;
	}
	public byte[] getValue() {
		return value;
	}
	public void setValue(byte[] value) {
		this.value = value;
	}
	@Override
	public String toString() {
		String valueString="";
		if(value!=null&&value.length!=0)
			valueString=new String(value);
		
		return "Message [time=" + time + ", loction=" + loction + ", type=" + type + ", value=" + valueString
				+ "]";
	}
	
}
