package de.retterdesapok.jettydooropener;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import junit.framework.TestCase;

public class UtilitiesTest extends TestCase {
	 public void testApp() throws NoSuchAlgorithmException, UnsupportedEncodingException
	    {
		 	String md5OfTest = Utilities.getMD5("test");
		 	System.out.println("Input: test");
		 	System.out.println("Output: " + md5OfTest);
	        assertTrue( md5OfTest.equals("098f6bcd4621d373cade4e832627b4f6") );
	    }
}
