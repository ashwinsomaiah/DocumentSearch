package com.target.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.shingle.ShingleAnalyzerWrapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.Version;

public class IndexedPatternSearch implements PatternSearch {
	private static final Analyzer standardanalyzer = new StandardAnalyzer(Version.LUCENE_40,CharArraySet.EMPTY_SET);	
	private static final Analyzer analyzer = new ShingleAnalyzerWrapper(standardanalyzer,2, 10);	
	private static final Directory ramDirectory = new RAMDirectory();
	private static final IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_40, analyzer);
	private static int cnt = 0;
	private static ArrayList<String> docNames = new ArrayList<String>();
	private static boolean indexCreated = false;

	@SuppressWarnings("deprecation")
	@Override
	public Map<String, Long> search(String term, String folderLocation) throws Exception {
		// if you want the indexes files to reside on the file system choose this option.
//		directory = FSDirectory.open(new File(new File(folderLocation).getParent(), "indexfiles"));		
		HashMap<String, Long> result = new HashMap<String, Long>();
		if(!indexCreated){
			System.out.println("Creating Index...");
			createIndex(folderLocation, term);
			System.out.println("Index creatiion complete. Searching...");			
		}

		IndexReader indexReader = IndexReader.open(ramDirectory);
		IndexSearcher searcher = new IndexSearcher(indexReader);
		for (int i = 0; i < cnt; i++) {
			result.put(docNames.get(i), new Long(0));
			Terms terms = indexReader.getTermVector(i, "content");
			TermsEnum termsEnum = terms.iterator(null);
			BytesRef bytesRef = termsEnum.next();
			while (bytesRef != null) {
				if (bytesRef.utf8ToString().equals(term)) {
					result.put(docNames.get(i), termsEnum.totalTermFreq());
				}

				bytesRef = termsEnum.next();
			}
		}

		return result;
	}

	@SuppressWarnings("deprecation")
	private void createIndex(String folderLocation, String term)
			throws CorruptIndexException, LockObtainFailedException, IOException, FileNotFoundException {
		IndexWriter writer = new IndexWriter(ramDirectory, config);		
		File[] listOfFiles = new File(folderLocation).listFiles(new FilenameFilter() {
    		@Override
	         public boolean accept(File dir, String filename)
           { return filename.endsWith(".txt"); }
		} );
		for (File file : listOfFiles) {
			Document document = new Document();
			final String title = file.getName();
			document.add(new Field("title", title, Field.Store.YES, Field.Index.ANALYZED));
			StringBuffer contents = new StringBuffer();
			try (BufferedReader br = new BufferedReader(new FileReader(file));) {
				while (br.ready()) {
					String line = br.readLine();
					contents.append(line);
				}

				document.add(new Field("content", contents.toString(), Field.Store.YES, Field.Index.ANALYZED,
						Field.TermVector.YES));
			}

			writer.addDocument(document);
			cnt++;
			docNames.add(title);
		}

		writer.commit();
		writer.close();
		indexCreated = true;
	}
}