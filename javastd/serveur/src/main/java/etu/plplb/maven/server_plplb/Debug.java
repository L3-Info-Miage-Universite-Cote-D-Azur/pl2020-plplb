package etu.plplb.maven.server_plplb;

public class 
Debug 
{
	/* Set this value to true if you want to get verbose mode */
	public static boolean verbose = false;
	private Debug () {}
	
	public static void
	debug (String msg)
	{
		if (Debug.verbose)
			System.out.println(msg);
	}
	
	public static void
	log (String msg)
	{
		Debug.debug("[*] " + msg);
	}
	
	public static void
	error (String msg)
	{
		Debug.debug("[E] " + msg);
	}
}
