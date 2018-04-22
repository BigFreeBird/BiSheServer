package cn.wx.security;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;


public class RSA {
	  private PublicKey publicKey;
	  private PrivateKey privateKey;
	  
	  public RSA(PrivateKey privateKey) {
		  super();
		  this.privateKey = privateKey;
	  }
	  
	  public RSA(PublicKey publicKey) {
		  super();
		  this.publicKey = publicKey;
	  }

	  public RSA(PublicKey publicKey, PrivateKey privateKey) {
		super();
		this.publicKey = publicKey;
		this.privateKey = privateKey;
	}

	  /**
	   * @return 当前对象使用的公钥
	   */
	  public PublicKey getPublicKey() {
		  return publicKey;
	  }
	  
	  /**
	   * @return 返回当前使用的私钥
	   */
	  public PrivateKey getPrivateKey() {
		  return privateKey;
	  }
	 
	  /**
	   * 公钥加密,每次加密100个字节,一次加密超多117个字节出错(100个字节的密文是128个字节)
	   * @param content 需要加密的字节码
	   * @return RSA加密后的字节码
	   * @throws Exception
	   */
	  public byte[] encrypt(byte[] content) throws Exception{
		  if(publicKey==null)
			  return null;
	
		  Cipher cipher=Cipher.getInstance("RSA");
	      cipher.init(Cipher.ENCRYPT_MODE, publicKey);  

	      byte[] result=new byte[(content.length/100+1)*128];
	      byte[] group=new byte[100];
	      //加密数组前100的整数倍个字节
		  int curloc=0;
		  for(curloc=0;curloc+100<content.length;curloc=curloc+100) {
			  System.arraycopy(content, curloc, group, 0, 100);
			  byte[] doFinal=cipher.doFinal(group);
			  System.arraycopy(doFinal, 0, result, curloc, doFinal.length);
		  }
		  //加密密最后的字节
		  int slen=content.length-curloc;
		  group=new byte[slen];
		  System.arraycopy(content, curloc, group, 0, slen);
		  byte[] doFinal=cipher.doFinal(group);
		  System.arraycopy(doFinal, 0, result, curloc/100*128, doFinal.length);
		  return result;  
	   }  
	      
	  /**
	   * 私钥解密  每次解密128个字节
	   * @param content 需要解密的字节码
	   * @return 完成解密后的字节码
	   * @throws Exception
	   */
	  public byte[] decrypt(byte[] content) throws Exception{
		  if(privateKey==null)
			  return null;
		  Cipher cipher=Cipher.getInstance("RSA");  
	      cipher.init(Cipher.DECRYPT_MODE, privateKey); 
		  int initLen=content.length;
		  int actualLen=0;
		  byte[] result=new byte[initLen];
		  byte[] group=new byte[128];
		 //解密数组，128的整倍数
		  int curloc=0;
		  for(curloc=0;curloc<content.length;curloc=curloc+128) {
			  System.arraycopy(content, curloc, group, 0, 128);
			  byte []doFinal=cipher.doFinal(group);
			  System.arraycopy(doFinal, 0, result, actualLen, doFinal.length);
			  actualLen+=doFinal.length;
		  }
		  
		  byte []actualResult=new byte[actualLen];
		  System.arraycopy(result, 0, actualResult, 0, actualLen);
		  return actualResult;
       }
	 public static void main(String[] args) throws Exception {
		String msg="我是密文，我是密文，我是密文，我是密文，我是密文，我是密文，我是密文，我是密文，我是密文，我是密文，我是密文，我是密文，我是密文。";
		KeyPair keyPair=ClientKeyManager.genKeyPair();
		
		RSA rsa=new RSA(keyPair.getPublic());
		byte[] mbs=rsa.encrypt(msg.getBytes());
		
		RSA rsa2=new RSA(keyPair.getPrivate());
		System.out.println(new String(rsa2.decrypt(mbs)));
	}
}
