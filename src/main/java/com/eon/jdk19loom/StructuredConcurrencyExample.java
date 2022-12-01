package com.eon.jdk19loom;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import jdk.incubator.concurrent.StructuredTaskScope;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutionException;

public final class StructuredConcurrencyExample extends AbstractLoomExample {

	public static final String PAGE_MARKER = "$p";
	int synchronizedCounter =0;

	private synchronized void incrementCounter(){
		synchronizedCounter++;
	}

	public class CustomScope extends StructuredTaskScope<String>{
		Map<String,Integer> wordScores= new HashMap<>();
		public String getBestString() {
			return wordScores.entrySet().stream().max(Comparator.comparing(Map.Entry::getKey)).get().getKey();
		}
		protected void recordScore(String word, int score){
			this.wordScores.put(word,score);

		}
	}
	/** The original PDF that will be parsed. */
	public static final String EBOOK = "ebook.pdf";


	protected String findPageContainingWordLike(String word) throws IOException {
			StringBuilder bookContent = parsePdf(EBOOK);
			String[] words = bookContent.toString().split(" ");
			String homonym = Arrays.stream(words).filter(w->soundSame(w,word)).findFirst().get();
			int startingIndex = bookContent.toString().indexOf(homonym);
			String subBook = bookContent.toString().substring(0,startingIndex);
			int indexOfPageNumber = subBook.lastIndexOf(PAGE_MARKER);
				System.out.println("here: "+homonym+". on page: "+subBook.substring(indexOfPageNumber+2,indexOfPageNumber+5));

				return "here: "+homonym+". on page: "+subBook.substring(indexOfPageNumber+2,indexOfPageNumber+5);
	}

	protected List<TextExtractionStrategy> parsePdfConcurrent(String pdf) throws IOException {
		StringBuilder builder=new StringBuilder();
		List<TextExtractionStrategy> pages;
		PdfReader reader = new PdfReader(pdf);
		PdfReaderContentParser parser = new PdfReaderContentParser(reader);
		TextExtractionStrategy strategy;
		pages = new ArrayList<>();
		for (int i = 1; i <= reader.getNumberOfPages(); i++)
			pages.add(parser.processContent(i, new SimpleTextExtractionStrategy()));


		reader.close();
		return pages;
	}
	protected StringBuilder parsePdf(String pdf) throws IOException {
		StringBuilder builder=new StringBuilder();
		PdfReader reader = new PdfReader(pdf);
		PdfReaderContentParser parser = new PdfReaderContentParser(reader);
		TextExtractionStrategy strategy;
		for (int i = 1; i <= reader.getNumberOfPages(); i++) {
			strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
			builder.append(strategy.getResultantText());
			builder.append(PAGE_MARKER +i);
		}
		reader.close();
		return builder;
	}

	protected boolean soundSame(String wordA, String wordB){
		String scorew1 =Soundex.getGode(wordA);
		String scorew2 =Soundex.getGode(wordB);

		return scorew1.equals(scorew2);
	}
	protected boolean soundSimilar(String wordA, String wordB){
		String scorew1 =Soundex.getGode(wordA);
		String scorew2 =Soundex.getGode(wordB);
		int w1=Integer.valueOf(scorew1.substring(1,scorew1.length()));
		int w2=Integer.valueOf(scorew2.substring(1,scorew2.length()));
		return w1==w2;
	}

	protected boolean soundSimilarWithTolerance(String wordA, String wordB, int tolerance) throws IOException {

		String scorew1 =Soundex.getGode(wordA);
		String scorew2 =Soundex.getGode(wordB);
		int w1=Integer.valueOf(scorew1.substring(1,scorew1.length()));
		int w2=Integer.valueOf(scorew2.substring(1,scorew2.length()));
		return !(Math.abs( w1-w2) > tolerance);
	}


	protected String getStringInAConvolutedWay() throws InterruptedException, ExecutionException {
		try (var scope = new StructuredTaskScope.ShutdownOnSuccess<String>()) {
			for(int i=0; i<100; i++) {
				 scope.fork(() -> getHello());
			}

		    scope.join();

		    return scope.result();
		}
		
		
	}

	private String getHello() {

		for(int i = new Random().nextInt(100);i>0;i--) {
			incrementCounter();
			System.out.println("random number: "+i+" after "+synchronizedCounter+" goes. Thread ID: "+Thread.currentThread().threadId());
			
		}
		return "hello";
			
	}

}
