package pap.ass4;

public interface TextLib {

	/**
	 * Clear the console screen
	 */
	void cls();
	
	/**
	 * Write a text message on the console screen at the specified position
	 */
	void writeAt(int x, int y, String msg);
	
	/**
	 * Write a text message on the console screen at the specified position and color.
	 * 
	 * Code colors are specified here: http://en.wikipedia.org/wiki/ANSI_escape_code#Colors
	 */
	void writeAt(int x, int y, String st, int color);

}
