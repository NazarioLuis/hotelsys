package py.com.hotelsys.test;

import java.io.IOException;

import py.com.hotelsys.util.MotherboardSN;


public class Test {

	private static String str="";

	public static void main(String[] args) throws IOException {
		
		
			char[] cs = MotherboardSN.getMotherboardWindows().toCharArray();
			System.out.println( MotherboardSN.getMotherboardWindows().toCharArray());
			for (int i = 0; i < cs.length; i++) {
				str=str+ Character.toChars((cs[i]+3))[0];
			}
			System.out.println(str);
	}

}
