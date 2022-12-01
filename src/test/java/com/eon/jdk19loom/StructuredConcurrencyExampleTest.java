package com.eon.jdk19loom;

import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class StructuredConcurrencyExampleTest {

	public static final String DECLAN = "declan";

	@Test
	void getStringInAConvolutedWay() throws ExecutionException, InterruptedException {
		String hello = new StructuredConcurrencyExample().getStringInAConvolutedWay();
		System.out.println(hello+", world");
	}
//	@Test
//	void testSoundexDemo(){
//		assertTrue(new StructuredConcurrencyExample().soundSame("byte","bite"));
//		assertTrue(new StructuredConcurrencyExample().soundSame("reid","reed"));
//		assertTrue(new StructuredConcurrencyExample().soundSame("jik","jick"));
//	}
//
//	@Test
//	void testSoundexDemo2(){
//		assertTrue(new StructuredConcurrencyExample().soundSimilar("byte","bite"));
//		assertTrue(new StructuredConcurrencyExample().soundSimilar("reid","reed"));
//		assertTrue(new StructuredConcurrencyExample().soundSimilar("jik","jick"));
//		assertTrue(new StructuredConcurrencyExample().soundSimilar("home","ome"));
//	}
//
//	@Test
//	void testSoundexDemo4() throws IOException {
//		assertTrue(new StructuredConcurrencyExample().soundSimilarWithTolerance("byte","bite",0));
//		assertTrue(new StructuredConcurrencyExample().soundSimilarWithTolerance("reid","reed",0));
//		assertTrue(new StructuredConcurrencyExample().soundSimilarWithTolerance("jik","jick",0));
//		assertFalse(new StructuredConcurrencyExample().soundSimilarWithTolerance("home","ute",10));
//	}
//
//	@Test
//	void findWordLike() throws IOException {
//		StopWatch stopWatch=new StopWatch();
//		stopWatch.start();
//		String homonym = new StructuredConcurrencyExample().findPageContainingWordLike(DECLAN);
//		stopWatch.stop();
//		System.out.println(homonym.substring(homonym.indexOf("here:")+6,homonym.indexOf(". on "))+" is not really like "+DECLAN+", in "+stopWatch.prettyPrint());
//
//	}
}