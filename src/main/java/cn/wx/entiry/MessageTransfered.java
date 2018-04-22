package cn.wx.entiry;

public class MessageTransfered {
	private byte[] privateMessage;
	private byte[] secretKey;
	private String publicMessage;
	private byte[] signature;
	private byte[] privateKey;
	public byte[] getPrivateMessage() {
		return privateMessage;
	}
	public void setPrivateMessage(byte[] privateMessage) {
		this.privateMessage = privateMessage;
	}
	public byte[] getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(byte[] secretKey) {
		this.secretKey = secretKey;
	}
	public String getPublicMessage() {
		return publicMessage;
	}
	public void setPublicMessage(String publicMessage) {
		this.publicMessage = publicMessage;
	}
	public byte[] getSignature() {
		return signature;
	}
	public void setSignature(byte[] signature) {
		this.signature = signature;
	}
	public byte[] getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(byte[] privateKey) {
		this.privateKey = privateKey;
	}
	
}
