package semester_manager;

public final class 
SemestreConsts 
{
	public static String dir = "semestres/";
	public static String regParse = "\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
	public static String[] filenames = new String[] {"s1.txt", "s2.txt"};
	public static long[] lastUpdate = new long[filenames.length];
}
