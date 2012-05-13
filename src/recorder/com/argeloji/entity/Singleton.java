package com.argeloji.entity;

public class Singleton {
	private static Singleton theInstance = new Singleton();
	// S�n�f y�klendi�inde yarat�lan tekil nesne

	public static Singleton getInstance() {
		// D��ar�dan nesne almak i�in bu metodu kullanaca��z.
		return theInstance;
	}

	protected Singleton() {
		// Constructor private tan�mlans�n ki d��ar�dan eri�ilemesin.
		System.out.println("constructor");

		//Constructor 100 ms. bekleme gibi bir "i�" yaps�n.
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

