package com.aufgabe.primzahlen;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/StringForm")
public class StringForm extends PrimZahl {
	private String primstring = null;
	
	public StringForm(){
		super();
	}

/**
 * kommunikation über Rest
	 * */
	/**
	 * funktion für Rückgabe in String form
	 * @param zahl
	**/
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String primString(@FormParam("anzahl") int zahl)  {
		
			int iterator = 0;
			int counter=0;
			primstring = new String("");
			while(counter != zahl) {
				iterator++;
		    	if(isPrim(iterator)) {
		    		primstring += iterator + ", ";
		    		counter++;
	    		}
		    	
		    }
			
			primstring = primstring.substring(0, primstring.length()-2) + ".";
					return this.toString();
		
	}
	public String toString() {
		return "String: " + this.primstring;
	}

}
