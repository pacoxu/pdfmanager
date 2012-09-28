package uima.test;

public class Father {

	String a= "0";
	protected void setUp(){
		System.out.println("father  set up");
	}
	
	protected void tearDown(){
		System.out.println("father  tear down");
	}
}
