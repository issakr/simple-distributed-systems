package namen;

import java.io.Serializable;

public class Name implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String vorname;
	private String nachname;
	
	public Name() {
		super();
		vorname = "defaultServerVorname";
		nachname = "defaultServerNachname";
	}
	public Name(String vorname, String nachname) {
		this(nachname);
		this.vorname = vorname;
	}
	public Name(String nachname) {
		this.nachname = nachname;
		vorname = "defaultVorname";
	}
	public String getVorname() {
		return vorname;
	}
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	public String getNachname() {
		return nachname;
	}
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	public String toString() {
		return "\n"+getVorname()  + ": "+getNachname()+"\n";
//		+ "\n---------------------\n";		
	}	
	
}
