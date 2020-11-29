package vs.anzeigetafel.model;

import java.io.Serializable;


public class Nachricht implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id;
	private String titel;
	private String inhalt;
	private boolean aufeingenerTafel=false;
	private boolean aufAlleTafen=false;

	private User user;

	public Nachricht() {
		super();
		this.id=0;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitel() {
		return titel;
	}
	public void setTitel(String titel) {
		this.titel = titel;
	}
	public String getInhalt() {
		return inhalt;
	}
	public void setInhalt(String inhalt) {
		this.inhalt = inhalt;
	}
	public void setFrei() {
		this.setInhalt(new String(""));
		this.setTitel(new String(""));
		this.setAufeingenerTafel(false);
		this.setAufAlleTafen(false);
		this.setUser(this.getUser());
	}


	public boolean isAufeingenerTafel() {
		return aufeingenerTafel;
	}
	public void setAufeingenerTafel(boolean aufeingenerTafel) {
		this.aufeingenerTafel = aufeingenerTafel;
	}
	public boolean isAufAlleTafen() {
		return aufAlleTafen;
	}
	public void setAufAlleTafen(boolean aufAlleTafen) {
		this.aufAlleTafen = aufAlleTafen;
	}
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "\nNachricht [id=" + id + ", titel=" + titel + ", inhalt=" + inhalt + ", aufeingenerTafel="
				+ isAufeingenerTafel() + ", aufAlleTafen=" + isAufAlleTafen() + ", user=" + user + "]";
	}



	

	
	
}
