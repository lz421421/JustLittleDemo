package com.lizhi.demo.utils;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author linan 
 * @version 创建时间：2012-8-20 下午5:07:27
 * 类说明
 */
public class CrcUtil {
	
	public static String getCrc(String timeStamp,String IMEI,String UID,String passwordWithMd5,String infoString) throws Exception {
		
		String crc = MD5(timeStamp+IMEI+UID+passwordWithMd5+infoString);
		
		return crc;
		
	}
	
	public static String MD5(String source) throws Exception {
		String resultHash = null;
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		byte[] result = new byte[md5.getDigestLength()];
		md5.reset();
		md5.update(source.getBytes("UTF-8"));
		result = md5.digest();

		StringBuffer buf = new StringBuffer(result.length * 2);

		for (int i = 0; i < result.length; i++) {
			int intVal = result[i] & 0xff;
			if (intVal < 0x10) {
				buf.append("0");
			}
			buf.append(Integer.toHexString(intVal));
		}

		resultHash = buf.toString();

		return resultHash.toString();
	
		
	}


	/**
	 * sha256 加密
	 *
	 * @param val
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String getSHA256(String val)  {
		//        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
		//        sha256.update(val.getBytes());
		//        byte[] m = sha256.digest();
		////        return getString(m);
		//        String psw = new String(Hex.encodeHex(DigestUtils.sha256(val)));
		//        return psw;
		if (StringUtil.isNullOrEmpty(val)){
			return  "";
		}

		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		Base64Encoder base64en = new Base64Encoder();
		//加密后的字符串
		String newstr = "";
		try {
			newstr = Base64Encoder.encode(md5.digest(val.getBytes("utf-8")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return newstr;
	}

	/**
	 * md5 加密
	 *
	 * @param string
	 * @return
	 */
	public static String getMD5(String string) {
		byte[] hash;
		try {
			hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Huh, MD5 should be supported?", e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Huh, UTF-8 should be supported?", e);
		}

		StringBuilder hex = new StringBuilder(hash.length * 2);
		for (byte b : hash) {
			if ((b & 0xFF) < 0x10)
				hex.append("0");
			hex.append(Integer.toHexString(b & 0xFF));
		}
		return hex.toString();
	}

	/**
	 * rc4 加密
	 */
	public static String getRC4(String param) {
		return Base64.encodeToString(RC4.encry_RC4_byte(param, "zhtx"), Base64.DEFAULT);
	}

}
