package com.eon.jdk19loom;

import jdk.incubator.concurrent.StructuredTaskScope;

import java.util.Random;
import java.util.concurrent.ExecutionException;

public final class StructuredConcurrencyExample extends AbstractLoomExample {
	int synchronizedCounter =0;

	private synchronized void incrementCounter(){
		synchronizedCounter++;
	}
	public class Response {

	}

	public class UserInterests {

	}

	public class SubscriptionTier {

	}

	public class User {

	}
	
	public class CustomScope extends StructuredTaskScope<String>{
		public String getBestString() {
			return "";
		}
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
