package fr.marcdev.urlsbatch.config;

public final class ConsoleConstants {

	private ConsoleConstants() {
		throw new IllegalStateException("Utility class");
	}
	
	public static final String NEW_LINE = System.getProperty("line.separator");
	
	public static String DEFAULT_START_MESSAGE = 
			new StringBuilder(NEW_LINE)
			.append(NEW_LINE)
			.append("Ce programme permet de déterminer quels sont les 5 ")
			.append("\"ids\" qui ont le plus d'occurences\ndans des fichiers logs d'Urls ")
			.append("contenant des urls au format\n")
			.append("http(s)://(www.)test.com?(data=zzz&)id=36")
			.toString();
	
	public static String INPUT_FOLDER_PATH_INSTRUCTIONS =
			new StringBuilder("Veuillez entrer le Path (absolu ou relatif) du répertoire de fichiers de logs à analyser")
			.append(NEW_LINE)
			.append("> ")
			.toString();
	
	public static String RESULT_TABLE_ALIGN_FORMAT = "| %-15s | %-3s | %-10s | %-5s |%n";
	public static String RESULT_TABLE_HEADER =
			new StringBuilder(NEW_LINE)
			.append("Ids qui ont le plus grand nombre d'occurences")
			.append(NEW_LINE)
			.append(NEW_LINE)
			.append("+-----------------+-----+------------+-------+")
			.append(NEW_LINE)
			.append("| Domaine         | Clé | Val clé    | Nb occ.")
			.append(NEW_LINE)
			.append("+-----------------+-----+------------+-------+")
			.toString();
	
	public static String RESULT_TABLE_FOOTER = 
			new StringBuilder(NEW_LINE)
			.append("+-----------------+-----+------------+-------+")
			.append(NEW_LINE)
			.toString();
	
	public static String ERR_DIR_INVALID = "Répertoire saisi invalide";
}
