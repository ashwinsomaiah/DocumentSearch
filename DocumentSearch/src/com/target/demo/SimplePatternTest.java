package com.target.demo;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SimplePatternTest {
	private static final String CURRENT_DIRECTORY = System.getProperty("user.dir");
	private static final String FILE1= "Warp drive is a hypothetical faster-than-light (FTL) propulsion system in many works, most notably Star Trek. "
			+ "A spacecraft equipped with a warp drive may travel at apparent speeds greater than that of light by many orders of magnitude, "
			+ "while circumventing the relativistic problem of time dilation. In contrast to many other FTL technologies, such as a jump drive"
			+ " or the Infinite Improbability Drive, the warp drive does not permit instantaneous travel between two points; instead, warp drive "
			+ "technology creates an artificial \"bubble\" of normal space-time which surrounds the spacecraft (as opposed to entering a separate realm"
			+ " or dimension like hyperspace, as is used in the Star Wars, Stargate, Warhammer 40,000, Babylon 5, Cowboy Bebop, and Andromeda franchises).";
	
	private static final String FILE2 = "The Hitchhiker's Guide to the Galaxy[3] is often abbreviated \"HG2G\" [4] and \"HHGTTG\" [5] (as used on fan websites) "
			+ "also \"H2G2\" (first used by Neil Gaiman as a chapter title in Don't Panic and later by the online guide). The series is also often referred to as "
					+ "\"The Hitchhiker's Guide\", \"Hitchhiker's\", or simply \"[The] Guide\". This title can refer to any of the various incarnations of the story"
					+ " of which the books are the most widely distributed, having been translated into more than 30 languages by 2005.";
	private static final String FILE3 = "The military history of France encompasses an immense panorama of conflicts and struggles extending for more than 2,000 years "
			+ "across areas including modern France, greater Europe, and French territorial possessions overseas. According to the British historian Niall Ferguson, France "
			+ "has participated in 168 major European wars since 387 BC, out of which they have won 109, drawn 10 and lost 49: this makes France the most successful military"
			+ " power in European history - in terms of number of fought and won.[4]";
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	    	try(BufferedWriter br = new BufferedWriter(new FileWriter("file1.txt"));){
	    			br.write(FILE1 + "\n");
	    	}	    	
	    	try(BufferedWriter br = new BufferedWriter(new FileWriter("file2.txt"));){
    			br.write(FILE2 + "\n");
	    	}
	    	try(BufferedWriter br = new BufferedWriter(new FileWriter("file3.txt"));){
    			br.write(FILE3 + "\n");
	    	}	    		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		File file1 = new File("file1.txt");
		if(file1.exists())
			file1.delete();
		File file2 = new File("file2.txt");
		if(file2.exists())
			file2.delete();
		File file3 = new File("file3.txt");
		if(file3.exists())
			file3.delete();
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {		
	}

	@Test
	public final void testSearchSingleTerm() throws Exception {
		System.out.println(System.getProperty("user.dir"));
		PatternSearch patternSearch = (SimplePatternSearch)SearchFactory.get(PatternSearch.class, DocUtils.SIMPLE_SEARCH);
		Map<String, Long> result = patternSearch.search("France", CURRENT_DIRECTORY);
		assertEquals(3, result.size());
		assertEquals(4, result.get("file3.txt").longValue());
	}
	
	@Test
	public final void testSearchMultipleTerms() throws Exception {
		System.out.println(System.getProperty("user.dir"));
		PatternSearch patternSearch = (SimplePatternSearch)SearchFactory.get(PatternSearch.class, DocUtils.SIMPLE_SEARCH);
		Map<String, Long> result = patternSearch.search("Niall Ferguson", CURRENT_DIRECTORY);
		assertEquals(3, result.size());
		assertEquals(1, result.get("file3.txt").longValue());
	}
	
	@Test
	public final void testSearchSingleTermInMultipleFiles() throws Exception {
		System.out.println(System.getProperty("user.dir"));
		PatternSearch patternSearch = (SimplePatternSearch)SearchFactory.get(PatternSearch.class, DocUtils.SIMPLE_SEARCH);
		Map<String, Long> result = patternSearch.search("most", CURRENT_DIRECTORY);
		assertEquals(3, result.size());
		assertEquals(1, result.get("file1.txt").longValue());
		assertEquals(1, result.get("file2.txt").longValue());
		assertEquals(1, result.get("file3.txt").longValue());
	}
	
	@Test
	public final void testMultipleTermsMultipleFiles() throws Exception {
		System.out.println(System.getProperty("user.dir"));
		PatternSearch patternSearch = (SimplePatternSearch)SearchFactory.get(PatternSearch.class, DocUtils.SIMPLE_SEARCH);
		Map<String, Long> result = patternSearch.search("to the", CURRENT_DIRECTORY);
		assertEquals(3, result.size());
		assertEquals(1, result.get("file2.txt").longValue());
		assertEquals(1, result.get("file3.txt").longValue());
	}	
}