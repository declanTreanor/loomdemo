package com.eon.jdk19loom;

import com.google.gson.Gson;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

public final class VirtualThreadsDemo extends AbstractLoomExample {
	private static final int MAX_JOKES = 100;
	private static final double LOOP_AMOUNT = MAX_JOKES + (MAX_JOKES * 0.5);
	private static Set<String> jokes = new ConcurrentSkipListSet<>();
	private static int attemptsCounter = 0;
	private static final String HTTPS_API_CHUCKNORRIS_IO_JOKES_RANDOM = "https://api.chucknorris.io/jokes/random";

	private class Chuck {
		private String value;
		private String[] categories;

		public String getValue() {
			return this.value;
		}

		public String[] getCategories() {
			return this.categories;
		}

	}

	private record Person(String name, int age) {
	}

	private record Animal(String name) {
	}

	public static void printName(Object o) {
		if (o instanceof Person p)
			System.out.println(p.name);

		if (o instanceof Person(String name, int age))
			System.out.println(name+" is "+age+" years old");
		else
			System.out.println("not a person: " + o.toString());

	}

	synchronized void addJoke(String joke) {

		jokes.add(joke);

	}

	public static void main(String[] args) throws InterruptedException, IOException {


		printName(new Person("Declan", 40));
		printName(new Animal("Chuck Norris"));

	}

	protected void createFinalReport() {
		jokes.forEach(System.out::println);
		System.out.println("source:\t" + HTTPS_API_CHUCKNORRIS_IO_JOKES_RANDOM);
		System.out.println(jokes.size() + " unique chuck jokes, out of " + attemptsCounter + " attempts");
	}

	protected void collectAndJoinVirtualThreads() {
		List<Thread> virtualThreads = new ArrayList<>();

		for (int i = 0; i < LOOP_AMOUNT; i++) {
			Thread virtual = Thread.ofVirtual().start(fibreRunnable());
			virtual.setUncaughtExceptionHandler(getUncaughtExceptionBehaviour());
			virtualThreads.add(virtual);
		}
		virtualThreads.forEach(v -> {
			try {
				v.join();
			} catch (InterruptedException e) {
				reportError(e);
			}

		});
	}

	private Thread.UncaughtExceptionHandler getUncaughtExceptionBehaviour() {
		return (t,
				e) -> System.out.println("ouch! " + e.getMessage() + " #jokes/attempts at this time: " + jokes.size()
				+ "/" + attemptsCounter + ", this will not impact the main thread, but it will "
				+ "affect the final joke count. thread name: "+t.threadId());
	}

	private static Runnable fibreRunnable() {
		return () -> {
			new VirtualThreadsDemo().compileChuckJokes();
		};
	}

	private static void reportError(Exception e) {
		System.out.println("ooooooooooops: " + e.getMessage());
		e.printStackTrace();
	}

	private void compileChuckJokes() {

		try (InputStream input = new URL(HTTPS_API_CHUCKNORRIS_IO_JOKES_RANDOM).openStream()) {
			if (jokes.size() < MAX_JOKES) {
				addJokeToCommonSet(input);
			}
			attemptsCounter++;

		} catch (MalformedURLException e) {
			reportError(e);
		} catch (IOException e) {
			reportError(e);
		}

	}

	private void addJokeToCommonSet(InputStream input) throws UnsupportedEncodingException {
		Reader reader = new InputStreamReader(input, "UTF-8");
		if (jokes.size() >= 200)
			throw new IllegalStateException("Oi! this is runtime!");
		Chuck result = new Gson().fromJson(reader, Chuck.class);
		if (notARudeJoke(result))
			addJoke(result.getValue());
	}

	private boolean notARudeJoke(Chuck result) {
		return !Arrays.asList(result.getCategories()).contains("explicit");
	}


}
