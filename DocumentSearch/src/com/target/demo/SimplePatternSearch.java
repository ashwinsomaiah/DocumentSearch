package com.target.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SimplePatternSearch implements PatternSearch {

	@Override
	public Map<String, Long> search(String pattern, String folderLocation) throws FileNotFoundException, IOException {
		HashMap<String, Long> result = new HashMap<String, Long>();
    	File[] listOfFiles =  new File(folderLocation).listFiles(new FilenameFilter() {
    		@Override
	         public boolean accept(File dir, String filename)
             { return filename.endsWith(".txt"); }
    	} );
    	if(listOfFiles != null && listOfFiles.length > 0){
    		for (File file : listOfFiles) {
    			long count = 0;
        		try (BufferedReader br = new BufferedReader(new FileReader(file));){
        		    while (br.ready()) {
        		        String line = br.readLine();
        		        int startIndex = line.indexOf(pattern);
        		        while (startIndex != -1) {
        		        	count++;
        		        	startIndex = line.indexOf(pattern, startIndex + pattern.length());
        		        }
        		    }
        		    
        		    result.put(file.getName(), count);
        		}
    		}
    	}

		return result;
	}	
}