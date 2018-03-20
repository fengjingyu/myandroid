package com.jingyu.java.mytool.encryption.des;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.util.Base64;


/**
 * DES加密解密处理类
 * @author 徐金山
 * @version V1.0
 */
class DesCoder {
	/** 类实例对象 */
	private static DesCoder inst = null;
	/** DES KEY */
	private static final String DES_KEY = "12345678;";
	/** DES IV 也可以自定义一个字符串获取数组*/
	private static final byte[] DES_IV = { (byte) 0x12, (byte) 0x34, (byte) 0x56, (byte) 0x78, (byte) 0x90, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF };

	/** 加密算法的参数接口，IvParameterSpec是它的一个实现 */
	private AlgorithmParameterSpec iv = null;
	/** 密钥对象 */
	private Key key = null;
	/** 加密对象Cipher */
	private Cipher cipher = null;

	/**
	 * 获取实例对象
	 * @return DesCoder 本类的单例实例对象
	 * @throws Exception  异常对象
	 */
	public static DesCoder getInstance() throws Exception {
		if (inst == null) {
			inst = new DesCoder();
		}
		return inst;
	}

	/**
	 * 构造方法
	 * @throws Exception  异常对象
	 */
	private DesCoder() throws Exception {
		DESKeySpec keySpec = new DESKeySpec(DES_KEY.getBytes("UTF-8"));// 设置密钥参数
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");// 获得密钥工厂
		key = keyFactory.generateSecret(keySpec); // 得到密钥对象
		cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");// 得到加密对象Cipher
		iv = new IvParameterSpec(DES_IV);// 设置向量
	}

	/**
	 * 加密
	 * @param data  请求报文对应的信息内容
	 * @return String 加密后的信息内容
	 * @throws Exception  异常对象
	 */
	public synchronized String encode(String data) throws Exception {
		cipher.init(Cipher.ENCRYPT_MODE, key, iv); // 设置工作模式为加密模式，给出密钥和向量
		byte[] pasByte = cipher.doFinal(data.getBytes());
		String tempStr = new String(Base64.getEncoder().encode(pasByte));

		return tempStr;
	}

	/**
	 * 解密
	 * @param data  应答报文对应的信息内容
	 * @return String 解密后的信息内容
	 * @throws Exception  异常对象
	 */
	public synchronized String decode(String data) throws Exception {
		cipher.init(Cipher.DECRYPT_MODE, key, iv);// 设置工作模式为解密模式，给出密钥和向量
		byte[] pasByte = cipher.doFinal(Base64.getDecoder().decode(data.getBytes()));
		String tempStr = new String(pasByte);

		return tempStr;
	}
}
