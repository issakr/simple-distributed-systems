package com.aufgabe.primzahlen;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/StruktForm")
public class StruktForm extends PrimZahl {
	
	ArrayForm af = null;
	StringForm sf = null;
	
	public StruktForm() {
		super();
	}
	/**
	 * kommunikation über Rest
		 * */
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	/**
	 * Funktion, die ein Object PrimZahlen erzeugt. Fügt die Primzahlen in einem String und einer Array 
	 * @param zahl
	 * */
	public String primStruct(@FormParam("anzahl") int zahl){
		
		af = new ArrayForm();
		sf = new StringForm();
		af.primArray(zahl);
		sf.primString(zahl);
		
		return this.toString();
		
	}
	
	public String toString() {
		return af.toString() + "\n" + sf.toString();
	}
}

