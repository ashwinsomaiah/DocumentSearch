package com.target.demo;

import java.util.concurrent.TimeUnit;

public class DocUtils {
	protected static final String SIMPLE_SEARCH = "Simple";
	protected static final String REGEXP_SEARCH = "RegularExpression";
	protected static final String IDX_SEARCH = "Indexed";
	protected static final String DOC_SEARCH = "DocSearch";
	protected static final String RANDOM_SEARCH = "RandomSearch";
	
	protected static int getUserInputInt() {
		int input_option = -1;
		String input = System.console().readLine();
		try {
			if (input != null)
				input_option = Integer.parseInt(input);
			else
				input_option = 0;
		} catch (NumberFormatException nfe) {
			return 0;
		}
		return input_option;
	}

	protected static String getUserInputString() {
		String input = System.console().readLine();
		if (input != null)
			return input;
		else
			return "";
	}
	
	protected static void printMenuOption(String searchOption) {
		// Display menu options
		System.out.println("	===============================");
		if (searchOption.equals(DOC_SEARCH))
			System.out.println("	MENU SELECTION: DOCUMENT SEARCH");
		else if(searchOption.equals(RANDOM_SEARCH))
			System.out.println("	MENU SELECTION: RANDOM SEARCH");
		System.out.println("	===============================");
		System.out.println("	Options:                       ");
		System.out.println("	1. Simple Match				   ");
		System.out.println("	2. Regular Expression Match	   ");
		System.out.println("	3. Index Match 				   ");
		System.out.println("	4. Quit 				       ");
		System.out.println("");		
	}
	
	protected static String formattedElapsedTime(final long elapsedtime) {
		return String.format("%02d:%02d:%02d:%02d", 
    			TimeUnit.MILLISECONDS.toHours(elapsedtime),
    			TimeUnit.MILLISECONDS.toMinutes(elapsedtime) -  
    			TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(elapsedtime)), // The change is in this line
    			TimeUnit.MILLISECONDS.toSeconds(elapsedtime) - 
    			TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(elapsedtime)),
    			TimeUnit.MILLISECONDS.toMillis(elapsedtime) - 
    			TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(elapsedtime)));
	}	
}
