package de.retterdesapok.jettydooropener;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utilities {

	public static int MAX_LOGIN_ATTEMPTS = 3;

	// TODO salt passwords, not the most important thing for this use case
	// though
	// Based on http://viralpatel.net/blogs/java-md5-hashing-salting-password/
	public static String getMD5(String input) throws NoSuchAlgorithmException {

		// Create MessageDigest object for MD5
		MessageDigest digest = MessageDigest.getInstance("MD5");

		// Update input string in message digest
		digest.update(input.getBytes(), 0, input.length());

		// Converts message digest value in base 16 (hex)
		String md5 = new BigInteger(1, digest.digest()).toString(16);

		while (md5.length() < 32) {
			md5 = "0" + md5;
		}

		return md5;
	}

}
