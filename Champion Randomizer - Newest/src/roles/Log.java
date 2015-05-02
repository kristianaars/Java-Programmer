package roles;

public class Log {
	
	private String name;
	
	public final int ERROR_MESSAGE = 0;
	public final int INFORMATION_MESSAGE = 1;
	public final int DEBUG_MESSAGE = 2;
	
	public Log(String name) {
		this.name = name;
	}
	
	public void printMsg(String s, int type) {
		s = String.format("Message from %s: %s", name, s);
		switch(type){
		case(ERROR_MESSAGE):
			s = String.format("ERROR-%s", s);
			System.err.println(s);
			return;
		case(INFORMATION_MESSAGE):
			s = String.format("INFO-%s", s);
			break;
		case(DEBUG_MESSAGE):
			s = String.format("DEBUG-%s", s);
			break;
		}
		
		System.out.println(s);
	}
	
}
