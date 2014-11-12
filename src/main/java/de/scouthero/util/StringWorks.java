package de.scouthero.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringWorks {
	
	/**
	 * Return a MD5-Hash from a String 
	 * @param s
	 * @return md5 Hash
	 */
	public static String md5(String s) throws NoSuchAlgorithmException, NullPointerException {
		if (s == null)
			throw new NullPointerException("Kein String Übergeben worden");
		StringBuffer sbMD5SUM = new StringBuffer();
		MessageDigest md = java.security.MessageDigest.getInstance("MD5");
		md.update(s.getBytes());
	    byte[] digest = md.digest();
	    /* MD5-Hash-Bytearray durchlaufen und Bytes konvertieren */
	    for (byte d : digest) {
	    	/* Sicherstellen, das keine negativen Vorzeichen vorhanden sind */
	    	/* Sicherstellen, das der Hexwert des Bytes 2-stellig ausgegeben wird (Vornullen) */
	    	sbMD5SUM.append(Integer.toHexString((d & 0xFF) | 0x100).toLowerCase().substring(1, 3));
	    }
	    return sbMD5SUM.toString();
	}
	
	public static String formatDate(Date d) {
		Format formatter =  new SimpleDateFormat("dd.MM.yyyy");
		return formatter.format(d); 
	}
}
