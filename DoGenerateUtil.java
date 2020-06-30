package com.rory;

/**  
 * @author ruirli
 */
public class DoGenerateUtil {
	
	/**
	 * 将下划线后面的一个字母转换为大写, 并删除下划线
	 * 
	 * trans_totel_num  change to transTotelNum
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		String changeToDoFileds = changeToDoFileds("trans_totel_num");
		System.out.println(changeToDoFileds);
		
	}

	
	private static String changeToDoFileds(String value){
		String name = value.toLowerCase();
		char[] charArray = name.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			if ("_".equals(String.valueOf(charArray[i]))) {
				if (name.charAt(i + 1) >='a' && name.charAt(i + 1) <='z') {
					charArray[i+1] = (char) (charArray[i+1] -32);
				}
			}
		}
		String valueOf = String.valueOf(charArray);
		valueOf = valueOf.replaceAll("_", "");
		return valueOf;
	}
	
}
