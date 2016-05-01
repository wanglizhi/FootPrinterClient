package tools;

public class Debugger {

	static StackTraceElement stack[];

	public static void main(String[] args) {
		log("hello");
	}

	public static void log(String s) {
		if (Config.DEBUG == 1) {
			stack = (new Throwable()).getStackTrace();
			System.out.println("LOG : " + stack[1].getClassName() + " -> "
					+ stack[1].getMethodName() + " MSG : " + s);
		}
	}
}
