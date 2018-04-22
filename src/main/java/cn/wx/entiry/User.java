package cn.wx.entiry;

import java.awt.Point;
import java.io.Serializable;

public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private int age;
	private boolean sex;
	private String phonenumber;
	private Point loction;
	
	public User(String username, String password, int age, boolean sex, String phonenumber, Point loction) {
		super();
		this.username = username;
		this.password = password;
		this.age = age;
		this.sex = sex;
		this.phonenumber = phonenumber;
		this.loction = (Point) loction.clone();
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public boolean isSex() {
		return sex;
	}
	public void setSex(boolean sex) {
		this.sex = sex;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public Point getLoction() {
		return loction;
	}
	public void setLoction(Point loction) {
		this.loction = (Point) loction.clone();
	}
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", age=" + age + ", sex=" + sex
				+ ", phonenumber=" + phonenumber + ", loction=" + loction + "]";
	}
	
}
