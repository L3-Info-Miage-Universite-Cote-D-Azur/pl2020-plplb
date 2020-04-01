package metiermanager;

/**
 * Class qui permet de parser
 * des String
 */
public class 
Parser 
{	/** L'expression reguliere pour le parsing de fichier */
	public static String regParse = "\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
	
	/** Constructeur de Parser */
	public
	Parser () {}
	
	/**
	 * Permet de remplacer les tabulation (\t),
	 * les sauts de lignes (\n) et les espaces (" ")
	 * qui ne sont pas entre double quotes (")
	 * @param raw La donnee brut
	 * @return La donnee affine par parsage
	 */
	public String
	parse (String raw)
	{
		// Replace chaque \t, \n et espaces qui ne sont pas entre quotes
		return raw.replaceAll(Parser.regParse, "");
	}
}
