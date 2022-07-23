/**
 * 
 */
package com.insigniait.accessControl.util;

/**
 * @author Doni, 19/07/2022 20:16:34
 *
 */
public class StringUtils extends org.springframework.util.StringUtils {
	
	public static String doubleQuote(String str) {
		return str == null ? null : "\"" + str + "\"";
	}
}
