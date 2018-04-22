package cn.wx.security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public class AES {
	private SecretKey secretKey;
	public AES(SecretKey secretKey) {
		this.secretKey=secretKey;
	}
	/**
	 * AES加密
	 * @param content 需要加密的字节数组
	 * @return 加密后的字节数组
	 */
	public byte[] encrypt(byte[] content) throws Exception{
		Cipher cipher;
		byte[] result = null;
		cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, this.secretKey);
		result=cipher.doFinal(content);//不使用update（不是一次全部解密）
		return result;
	}
	
	/**
	 * AES解密
	 * @param content
	 * @return 解密后的字节数组
	 */
	public byte[] decrypt(byte[] content) throws Exception {
		Cipher cipher;
		byte[] result=null;
		cipher=Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		result=cipher.doFinal(content);
		return result;
	}
}
