package com.eon.jdk19loom;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class StructuredConcurrencyExampleTest {

	@Test
	void getStringInAConvolutedWay() throws ExecutionException, InterruptedException {
		String hello = new StructuredConcurrencyExample().getStringInAConvolutedWay();
		System.out.println(hello+", world");
	}
	@Test
	void testSoundexDemo(){
		assertTrue(new StructuredConcurrencyExample().soundSame("byte","bite"));
		assertTrue(new StructuredConcurrencyExample().soundSame("reid","reed"));
		assertTrue(new StructuredConcurrencyExample().soundSame("jik","jick"));
	}

	@Test
	void testSoundexDemo2(){
		assertTrue(new StructuredConcurrencyExample().soundSimilar("byte","bite"));
		assertTrue(new StructuredConcurrencyExample().soundSimilar("reid","reed"));
		assertTrue(new StructuredConcurrencyExample().soundSimilar("jik","jick"));
		assertTrue(new StructuredConcurrencyExample().soundSimilar("home","ome"));
	}

	@Test
	void testSoundexDemo4(){
		assertTrue(new StructuredConcurrencyExample().soundSimilarWithTolerance("byte","bite",0));
		assertTrue(new StructuredConcurrencyExample().soundSimilarWithTolerance("reid","reed",0));
		assertTrue(new StructuredConcurrencyExample().soundSimilarWithTolerance("jik","jick",0));
		assertFalse(new StructuredConcurrencyExample().soundSimilarWithTolerance("home","ute",10));
	}




}