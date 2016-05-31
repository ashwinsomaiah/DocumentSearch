package com.target.demo;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;

public final class DocumentSearch {	
	public static void main(String[] args) throws Exception {
		int option = 0;
		String term = "";
		PatternSearch patternSearch = null;
		Map<String, Long> result = null;
		System.out.print("Please enter the location of sample files on the file system: ");
		String folderLocation = DocUtils.getUserInputString();
		while(!new File(folderLocation).exists()){			
			System.out.println("THere is no directory associated to the path: " + folderLocation + ". Please enter a valid folder location.");
			folderLocation = DocUtils.getUserInputString();			
		}
		
		while (true) {
			DocUtils.printMenuOption(DocUtils.DOC_SEARCH);
			System.out.print("Enter Choice: ");			
			option = DocUtils.getUserInputInt();
			long starttime = 0;
			long endtime = 0;
			switch (option) {
			case 1:
				System.out.print("Enter Search Term: ");
				term = DocUtils.getUserInputString();
				patternSearch = (SimplePatternSearch)SearchFactory.get(PatternSearch.class, DocUtils.SIMPLE_SEARCH);				
				starttime = System.currentTimeMillis();
				result = patternSearch.search(term, folderLocation);
				endtime = System.currentTimeMillis();				
				System.out.println("Search Result: ");
				for(Entry<String, Long> aResult: result.entrySet()){
					System.out.println(aResult.getKey() + " - " + aResult.getValue() + " matches");
				}
				
				System.out.println("Elapsed Time (milliseconds): " + (endtime - starttime));
				break;
			case 2:
				System.out.print("Enter Search Term: ");
				term = DocUtils.getUserInputString();
				patternSearch = (RegularExpressionPatternSearch)SearchFactory.get(PatternSearch.class, DocUtils.REGEXP_SEARCH);
				starttime = System.currentTimeMillis();
				result = patternSearch.search(term, folderLocation);
				endtime = System.currentTimeMillis();
				System.out.println("Search Result: ");
				for(Entry<String, Long> aResult: result.entrySet()){
					System.out.println(aResult.getKey() + " - " + aResult.getValue() + " matches");
				}

				System.out.println("Elapsed Time (milliseconds): " + (endtime - starttime));
				break;
			case 3:
				System.out.print("Enter Search Term: ");
				term = DocUtils.getUserInputString();
				patternSearch = (IndexedPatternSearch)SearchFactory.get(PatternSearch.class, DocUtils.IDX_SEARCH);
				starttime = System.currentTimeMillis();				
				result = patternSearch.search(term.toLowerCase(), folderLocation);
				endtime = System.currentTimeMillis();
				System.out.println("Search Result: ");
				for(Entry<String, Long> aResult: result.entrySet()){
					System.out.println(aResult.getKey() + " - " + aResult.getValue() + " matches");
				}
				
				System.out.println("Elapsed Time: " + (endtime - starttime));				
				break;
			case 4:				
				System.out.println("Good Bye!");
				System.exit(0);
			default:
				System.out.println("Invalid selection... Please choose the appropriate option from the menu.");
			}
		}
	}
}