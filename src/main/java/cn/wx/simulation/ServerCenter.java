package cn.wx.simulation;

import java.io.File;
import java.security.PrivateKey;

import java.util.List;

import javax.crypto.SecretKey;

import com.alibaba.fastjson.JSON;

import cn.wx.entiry.Message;
import cn.wx.entiry.MessageTransfered;
import cn.wx.entiry.User;
import cn.wx.security.AES;
import cn.wx.security.RSA;
import cn.wx.util.FileOperation;
import cn.wx.util.Serializor;

public class ServerCenter {
	
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		PrivateKey serverPrivateKey=Serializor.deSerialize("D:/KeyCenter/serverPrivateKey", PrivateKey.class);
		
		File []files=FileOperation.getFileList("D:\\Messsage");
		for(File file:files) {
			String msg=new String(FileOperation.readFile(file.getAbsolutePath()));
			MessageTransfered transfered=JSON.parseObject(msg, MessageTransfered.class);
			//1获取公共数据
			String string=transfered.getPublicMessage();
			List<Message> messages2=JSON.parseArray(string, Message.class);
			for(Message message:messages2)
				System.out.println(message);
			
			//2获取SecretKey
				//2.1取加密后的密钥
			byte []bs;
			bs=transfered.getSecretKey();
				//2.2创建RAS解码器
			RSA rsa=new RSA(serverPrivateKey);
				//2.3解码密钥
			byte[] skbs=rsa.decrypt(bs);
				//2.4构建密钥
			SecretKey secretKey=Serializor.deSerialize(skbs, SecretKey.class);
			//3获取隐私数据
				//3.1取得加密后的隐私数据
			bs=transfered.getPrivateMessage();
				//3.2创建AES解码器
			AES aes=new AES(secretKey);
				//3.3解码隐私数据
			byte[] pmbs=aes.decrypt(bs);
 			User user=Serializor.deSerialize(pmbs, User.class);
			System.out.println(user);
			//4获取客户端私钥进行签名认证
				//4.1获取客户端的签名
			byte[] signature=transfered.getSignature();
				//4.2获取客户端的序列化后的私钥并反序列化
			byte[] privateKeybs=transfered.getPrivateKey();
			PrivateKey clientPrivateKey=Serializor.deSerialize(privateKeybs, PrivateKey.class);
				//4.3用私钥生成节码器并解密进行认证
			rsa=new RSA(clientPrivateKey);
			bs=rsa.decrypt(signature);
			System.out.println(new String(bs));
		}
	}
}
