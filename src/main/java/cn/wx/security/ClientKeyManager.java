package cn.wx.security;

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import cn.wx.util.FileOperation;
import cn.wx.util.Serializor;

/**
 * @author WX
 *客户端密钥管理
 */

public class ClientKeyManager {
	
	private SecretKey AESSecretKey;
	private KeyPair signatureKeyPair;
	private PublicKey serverPublicKey;
	
	
	public SecretKey getAESSecretKey() {
		return AESSecretKey;
	}
	public void setAESSecretKey(SecretKey aESSecretKey) {
		AESSecretKey = aESSecretKey;
	}
	public KeyPair getSignatureKeyPair() {
		return signatureKeyPair;
	}
	public void setSignatureKeyPair(KeyPair signatureKeyPair) {
		this.signatureKeyPair = signatureKeyPair;
	}
	public PublicKey getServerPublicKey() {
		return serverPublicKey;
	}
	public void setServerPublicKey(PublicKey serverPublicKey) {
		this.serverPublicKey = serverPublicKey;
	}
	/**
	 * 初始化客户端密钥
	 * @param serverPublicKeyLoction 服务器公钥的位置
	 * @throws Exception
	 */
	public void init(String serverPublicKeyLoction) throws Exception {
		this.AESSecretKey=geneAESKey();
		this.signatureKeyPair=genKeyPair();
		this.serverPublicKey=fetchServerPublicKey(serverPublicKeyLoction);
	}
	/**
	 * 
	 * @param signatureKeyPairLength 签名密钥的长度，默认1024位
	 * @param serverPublicKeyLoction 服务器加密公钥的位置
	 * @throws Exception
	 */
	public void init(int signatureKeyPairLength,String serverPublicKeyLoction) throws Exception {
		this.AESSecretKey=geneAESKey();
		this.signatureKeyPair=genKeyPair(signatureKeyPairLength);
		this.serverPublicKey=fetchServerPublicKey(serverPublicKeyLoction);
	}
	/**
	 *生成一个1024位的密钥对
	 * @return 返回一个长度是1024位的密钥
	 * @throws Exception
	 */
	public static KeyPair genKeyPair() throws Exception{  
		KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance("RSA");  
		keyPairGenerator.initialize(1024);        
		return keyPairGenerator.generateKeyPair();  
	}
	/**
	 * 生成一个keyLength的密钥对  
	 * @param keyLength 密钥长度
	 * @return	密钥对
	 * @throws Exception
	 */
	public static KeyPair genKeyPair(int keyLength) throws Exception{  
		KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance("RSA");  
		keyPairGenerator.initialize(keyLength);        
		return keyPairGenerator.generateKeyPair();  
	}
	
	/**
	 * 使用默认随机种子生成128位AES密钥的密钥
	 * @return AES密钥
	 */
    public static SecretKey geneAESKey(){  
    	KeyGenerator keyGenerator = null;
		try {
			keyGenerator =KeyGenerator.getInstance("AES");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}  
        keyGenerator.init(128);
        SecureRandom random = new SecureRandom();
        keyGenerator.init(random);  
        SecretKey secretKey = keyGenerator.generateKey();  
        return secretKey; 
    }
    /**
     * 使用seed作为随机种子生成128位的AES密钥
     * @param seed 生成密钥对使用的随机种子
     * @return AES密钥
     */
    public static SecretKey genAESKey(byte[] seed){  
    	KeyGenerator keyGenerator = null;
		try {
			keyGenerator = KeyGenerator.getInstance("AES");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}  
        keyGenerator.init(128);
        SecureRandom random = new SecureRandom(seed);
        keyGenerator.init(random);  
        SecretKey secretKey = keyGenerator.generateKey();  
        return secretKey; 
    }
    /**
     * 从文件中读取并反序列化服务器公钥
     * @param filepath
     * @return 服务器的公钥
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public static PublicKey fetchServerPublicKey(String filepath) throws IOException, ClassNotFoundException {
    	byte[] bs=FileOperation.readFile(filepath);
    	PublicKey publicKey=Serializor.deSerialize(bs, PublicKey.class);
    	return publicKey;
    }
}
