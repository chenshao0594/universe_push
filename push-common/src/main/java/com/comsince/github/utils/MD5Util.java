package com.comsince.github.utils;

import java.security.MessageDigest;

/**
 * @class       MD5Util
 * @author      xiawen <xiawen@meizu.com>
 * @date        2017/10/16 19:44
 * @version     1.0
 */
public class MD5Util {
	private final static String[] hexDigits = {
	        "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "a", "b", "c", "d", "e", "f" };

	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i], true));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b, boolean bigEnding) {
		int n = b >= 0 ? b : 256 + b;
		int d1 = n / 16;
		int d2 = n % 16;
		return (bigEnding) ? (hexDigits[d1] + hexDigits[d2]) : (hexDigits[d2] + hexDigits[d1]);
	}

	public static String md5Encode(String origin) {
		return md5Encode(origin, "UTF-8");
	}


	public static String md5Encode(String origin, String encoding) {
		String resultString = null;

		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (encoding == null) {
				resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
			} else {
				resultString = byteArrayToHexString(md.digest(resultString.getBytes(encoding)));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return resultString;
	}
}
