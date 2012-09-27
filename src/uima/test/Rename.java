package uima.test;

import java.io.File;

public class Rename {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String s1 = "C:/task/task16-pdfpage/pdfmanager/testfiles/tf_100M.pdf";
		String s2 = "C:/task/task16-pdfpage/pdfmanager/testfiles/file1.txt";
		File f1 = new File(s1);
		File f2 = new File(s2);
		if(f2.renameTo(f1)){
			System.out.println(f2.exists());
			System.out.println(f1.exists());

		}
		
		System.out.println(s1.substring(0, s1.indexOf("tf_100M.pdf")) );
	}

}
