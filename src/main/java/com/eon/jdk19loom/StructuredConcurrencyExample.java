package com.eon.jdk19loom;

import jdk.incubator.concurrent.StructuredTaskScope;

import java.util.Random;
import java.util.concurrent.ExecutionException;

public final class StructuredConcurrencyExample extends AbstractLoomExample {
	int synchronizedCounter =0;

	private synchronized void incrementCounter(){
		synchronizedCounter++;
	}

	public class CustomScope extends StructuredTaskScope<String>{
		public String getBestString() {
			return "";
		}
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

	protected boolean soundSimilarWithTolerance(String wordA, String wordB, int tolerance){
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
		   // scope.throwIfFailed(IllegalArgumentException::new);

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
