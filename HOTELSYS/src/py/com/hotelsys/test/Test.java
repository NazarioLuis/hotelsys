package py.com.hotelsys.test;

import java.io.IOException;

import py.com.hotelsys.util.HardDiskSN;


public class Test {

	private static String str="";

	public static void main(String[] args) throws IOException {
		
		
			char[] cs = HardDiskSN.getHardDisk("C").toCharArray();
			System.out.println( HardDiskSN.getHardDisk("C").toCharArray());
			for (int i = 0; i < cs.length; i++) {
				str=str+ Character.toChars((cs[i]+3))[0];
			}
			System.out.println(str);
	}

}
