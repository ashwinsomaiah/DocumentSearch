package com.target.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class RegularExpressionPatternSearch implements PatternSearch {

	@Override
	public Map<String, Long> search(String pattern, String folderLocation) throws FileNotFoundException, IOException {
		HashMap<String, Long> result = new HashMap<String, Long>();
    	File[] listOfFiles = new File(folderLocation).listFiles(new FilenameFilter() {
    		@Override
	         public boolean accept(File dir, String filename)
            { return filename.endsWith(".txt"); }
    	} );
    	Pattern patt = null;
    	try{
    	patt = Pattern.compile(pattern);
    	}catch(PatternSyntaxException pse){
    		System.err.println("Error: " + pse.getDescription() + " for: " + pse.getPattern());
    		return result;
    	}
    	
    	if(listOfFiles != null && listOfFiles.length > 0){
    		for (File file:listOfFiles) {
    			long count = 0;
        		try (BufferedReader br = new BufferedReader(new FileReader(file));){		    
        		    while (br.ready()) {
        		        String line = br.readLine();
        		        Matcher m = patt.matcher(line);
        		        while(m.find()){        		        	
        		        	count++;
        		        }
        		    }
        		    
        		    result.put(file.getName(), count);
        		}
    		} 		
    	}

		return result;
	}
}