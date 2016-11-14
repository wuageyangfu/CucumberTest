package util;

public class LogUtil {
	
	public void testInfo(String test_info){
		System.out.println("---------"+test_info+"---------");
	}
	
	public void info(String info){
		System.out.println("[INFO]"+info);
	}
	
	public void step(String step){
		System.out.println("[STEP]"+step);
	}
	
	public void error(String error){
		System.out.println("[ERROR]"+error);
	}
	

}
