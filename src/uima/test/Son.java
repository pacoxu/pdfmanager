package uima.test;

public class Son extends Father {
	
	String a = "son";
	
	protected void setUp(){
		System.out.println("son  set up");
		System.out.println(a);
		
	}
	
	protected void tearDown(){
		System.out.println("son  tear down");
	}
}
