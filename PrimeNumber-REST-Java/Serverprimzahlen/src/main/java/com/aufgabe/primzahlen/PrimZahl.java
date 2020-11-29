package com.aufgabe.primzahlen;

public class PrimZahl {
	/**
	 * Standardkonstruktor
	 * */
	public PrimZahl() {
		
	}
	/**
	 * Test Funktion ob der zahl Prim ist oder nicht 
	 * @param value
	 * boolean Rückgabe,true falls der Zahl prim ist, sonst false
		 * */
	public boolean isPrim(int value) {
		if(value <= 2) {
			return (value == 2);
		}
		
		for (int i = 2; i * i <= value; i++) {
    		if ((value % i) == 0) {
				return false;
			}
    	}
		return true;
	}
}
