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
}