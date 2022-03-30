package edu.eci.arep.roundRobin.model;

public class RoundRobin {
	
	private static volatile RoundRobin instance;
	private static final Object mutex = new Object();
	
	private int turns;
	private int currentTurn;
	private String[] urls;
	
	private RoundRobin() {
		turns  = 2;
		currentTurn = 0;
		String firstUrl = System.getenv("AWS1");
		String secondUrl = System.getenv("AWS2");
		if (firstUrl != null & secondUrl != null) {
			urls = new String[]{firstUrl, secondUrl};
		}
		else {
			urls = new String[]{"http://localhost:4568", "http://localhost:4569"};
		}
	}
	
	public static RoundRobin getInstance() {
		RoundRobin result = instance;
		if (result == null) {
			synchronized(mutex) {
				result = instance;
				if (result == null) {
					instance = result = new RoundRobin();
				}
			}
		}
		return instance;
	}
	
	public String[] getNext() {
		synchronized(mutex) {
			if (currentTurn == turns) {
				currentTurn = 1;
			}
			else {
				currentTurn += 1;
			}
		}
		return new String[]{currentTurn+"", urls[currentTurn-1]};
	}
}