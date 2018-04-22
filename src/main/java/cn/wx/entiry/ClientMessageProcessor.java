package cn.wx.entiry;

import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;

import com.alibaba.fastjson.JSON;

import cn.wx.security.AES;
import cn.wx.security.ClientKeyManager;
import cn.wx.security.RSA;
import cn.wx.util.Serializor;

public class ClientMessageProcessor {
	private User user;
	private ArrayList<Message> messageCollected;
	private ArrayList<Message> messagesAfterParse; 
	private ClientKeyManager keyManager;
	private boolean isParsed=false;
	
	//构造器
	public ClientMessageProcessor(User user,ArrayList<Message> messageCollected, ClientKeyManager keyManager) {
		super();
		this.user=user;
		this.messageCollected=messageCollected;
		this.keyManager=keyManager;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ArrayList<Message> getMessageCollected() {
		return messageCollected;
	}

	public void setMessageCollected(ArrayList<Message> messageCollected) {
		this.messageCollected = messageCollected;
	}

	public ClientKeyManager getKeyManager() {
		return keyManager;
	}

	public void setKeyManager(ClientKeyManager keyManager) {
		this.keyManager = keyManager;
	}

	/**
	 * 对messageCollect中的ArrayList<Message> messages进行处理，并放入afterParse
	 */
	public void parseMessage() {
		messagesAfterParse=messageCollected;
		isParsed=true;
	}
	/**
	 * 
	 * @return 加密后的直接可以提交给服务器的数据
	 * @throws Exception 加密异常,或摘要
	 */
	public MessageTransfered doFinal() throws Exception {
		if(!isParsed)
			parseMessage();
		//1加密用户信息
		byte[] userbytes=Serializor.serialzeTobytes(user);
		AES aes=new AES(keyManager.getAESSecretKey());
		byte[] privateMessage=aes.encrypt(userbytes);
		 

		//2加密AES密钥
		RSA rsa=new RSA(keyManager.getServerPublicKey());
		byte [] secretKey=rsa.encrypt(Serializor.serialzeTobytes(keyManager.getAESSecretKey()));
		
		//3公共信息序列化
		String publicMessage= JSON.toJSONString(messagesAfterParse);
		
		//4签名信息
		String sign=user.getUsername()+":"+MessageDigest.getInstance("SHA-256").digest(publicMessage.getBytes());
		RSA rsa2=new RSA(keyManager.getSignatureKeyPair().getPublic());
		byte[]signature=rsa2.encrypt(sign.getBytes());
		
		//5客户端私钥
		PrivateKey clientPrivateKey=keyManager.getSignatureKeyPair().getPrivate();
		byte[] publicKey=Serializor.serialzeTobytes(clientPrivateKey);
		
		//返回MessageTransFered
		MessageTransfered transfer=new MessageTransfered();
		transfer.setPrivateMessage(privateMessage);
		transfer.setPublicMessage(publicMessage);
		transfer.setPrivateKey(publicKey);
		transfer.setSecretKey(secretKey);
		transfer.setSignature(signature);
		return transfer;
	}
}
