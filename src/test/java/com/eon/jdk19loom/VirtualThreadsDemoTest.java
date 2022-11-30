package com.eon.jdk19loom;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VirtualThreadsDemoTest {

	@Test
	void runDemo() {
		VirtualThreadsDemo example = new VirtualThreadsDemo();
		example.collectAndJoinVirtualThreads();
		example.createFinalReport();
	}

}