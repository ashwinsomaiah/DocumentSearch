package com.target.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class RandomTermSearch {
	private static int RDM_COUNT = 2000000;
	private static ArrayList<String> randomwords = new ArrayList<String>();	
	public static void main(String[] args) throws Exception {
		System.out.print("Enter folder location: ");
		String folderLoc = DocUtils.getUserInputString();
		while(!new File(folderLoc).exists()){			
			System.out.println("There is no directory associated to the path: " + folderLoc + ". Please enter a valid folder location.");
			folderLoc = DocUtils.getUserInputString();
		}
		
		System.out.println("Reading files...");
    	File[] listOfFiles =  new File(folderLoc).listFiles();
    	if(listOfFiles != null && listOfFiles.length > 0){
    		for (File file : listOfFiles) {
    			try (BufferedReader br = new BufferedReader(new FileReader(file));){		    
    			    while (br.ready()) {
    			        String line = br.readLine();
    			        String[] words = line.trim().split(" ");    			        
    			        randomwords.addAll((Arrays.asList(words)));
    			    }    			    
    			}	    			
    		}
    	}

    	System.out.println("Finished Reading files...");   	
		System.out.print("Enter the number of random term searches (default is 2M): ");
		int termcnt = DocUtils.getUserInputInt();
		if (termcnt != 0){
			RDM_COUNT = termcnt;
		}
		
		PatternSearch patternSearch =null;
		while(true){
			DocUtils.printMenuOption(DocUtils.RANDOM_SEARCH);
			System.out.print("Enter Choice: ");
			int option = DocUtils.getUserInputInt();			
			switch (option) {
			case 1:
				patternSearch = (SimplePatternSearch)SearchFactory.get(PatternSearch.class, DocUtils.SIMPLE_SEARCH);
				searchPattern(patternSearch, folderLoc);				
		    	break;
			case 2:
				patternSearch = (RegularExpressionPatternSearch)SearchFactory.get(PatternSearch.class, DocUtils.REGEXP_SEARCH);
				searchPattern(patternSearch, folderLoc);
		    	break;
			case 3:
				patternSearch = (IndexedPatternSearch)SearchFactory.get(PatternSearch.class, DocUtils.IDX_SEARCH);
				searchPattern(patternSearch, folderLoc);
		    	break;
			case 4:				
				System.out.println("Good Bye!");
				System.exit(0);	    	
			default :
				System.out.println("Invalid selection... Please choose the appropriate option from the menu.");
			}
		}
	}

	private static void searchPattern(PatternSearch patternSearch, String folderLoc) throws Exception {
    	int cnt = 0;
    	System.out.println("Searching " + RDM_COUNT + " random terms...");
		long starttime = System.currentTimeMillis();    	
    	while(cnt < RDM_COUNT){    		
    		boolean cntReached = false;
    		for(String term : randomwords){
        		if(!term.equals("")){
            		patternSearch.search(term, folderLoc);
            		if(++cnt == RDM_COUNT){        			
            			cntReached = true;
            			break;
            		}
        		}
    		}

        	if(cntReached)
        		break;
    	}

    	System.out.println("Search Completed");
    	long endtime = System.currentTimeMillis();
        System.out.println("Elapsed Time: " + DocUtils.formattedElapsedTime((endtime - starttime)));
	}
}