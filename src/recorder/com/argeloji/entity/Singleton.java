package com.argeloji.entity;

public class Singleton {
	private static Singleton theInstance = new Singleton();
	// Sýnýf yüklendiðinde yaratýlan tekil nesne

	public static Singleton getInstance() {
		// Dýþarýdan nesne almak için bu metodu kullanacaðýz.
		return theInstance;
	}

	protected Singleton() {
		// Constructor private tanýmlansýn ki dýþarýdan eriþilemesin.
		System.out.println("constructor");

		//Constructor 100 ms. bekleme gibi bir "iþ" yapsýn.
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

