package semester_manager;

public class 
Parser 
{	
	public
	Parser () {}
	
	public String
	parse (String raw)
	{
		// Replace chaque \t, \n et espaces qui ne sont pas entre quotes
		return raw.replaceAll(SemestreConsts.regParse, "");
	}
}
