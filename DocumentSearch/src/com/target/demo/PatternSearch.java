package com.target.demo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public interface PatternSearch {
	public Map<String, Long> search(String pattern, String folderLocation)throws Exception;	
}