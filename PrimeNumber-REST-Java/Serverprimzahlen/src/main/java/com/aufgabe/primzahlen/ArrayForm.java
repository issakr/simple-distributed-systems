package com.aufgabe.primzahlen;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/ArrayForm")
public class ArrayForm extends PrimZahl{
	/**
	 *  Array anlegen
	 */
	private int[] primarray = null;
	
	public ArrayForm() {
		super();
	}
	
	/**
	 * Funktion um die Array zu erhalten
	 * */
	public int[] getPrim_Array(){
		return this.primarray;
	}
	/**
	 * Funktiom um die Array zu überschreiben
	 * */
	public void setPrim_Array(int[] primarray){
		this.primarray = primarray;
	}
	/**
	 * kommunikation über Rest
		 * */
	/**
	 * funktion zum einfügen und zurückgeben
	 * @param zahl
	 * */
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	 public String primArray(@FormParam("anzahl") int zahl) {
		
			int iterator = 0;
			int counter=0;
			int pos=0;
			this.primarray = new int[zahl];

			while(counter != zahl) {		    
				iterator++;
		    	if(isPrim(iterator)) {
	    			this.primarray[pos] =iterator;
	    			pos ++;
	    			counter++;
	    		}
		    	
		    }
		    return this.toString();
		
		}
	
	public String toString() {
		int i = 1;
		String word = new String("Array: \n");
		for(int p : this.primarray) {
			word += i + ": " + p +"\n";
			i++;
		}
		
		return word;
	}	
		 
}
