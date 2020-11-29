package vs.anzeigetafel.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity(name = "nachricht")
public class Nachricht implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String inhalt;
	private boolean aufeingenerTafel;
	private boolean aufAlleTafen;
	
	private String titel;
	
	@ManyToOne(fetch = FetchType.EAGER)
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
	public String getInhalt() {
		return inhalt;
	}
	public void setInhalt(String inhalt) {
		this.inhalt = inhalt;
	}
	
	public String getTitel() {
		return titel;
	}
	public void setTitel(String titel) {
		this.titel = titel;
	}
	public boolean istAufeingenerTafel() {
		return aufeingenerTafel;
	}
	public void setAufeingenerTafel(boolean aufeingenerTafel) {
		this.aufeingenerTafel = aufeingenerTafel;
	}
	public boolean istAufAlleTafen() {
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
	public void setFrei() {
		this.setInhalt(new String(""));
		this.setTitel(new String(""));
		this.setAufeingenerTafel(false);
		this.setAufAlleTafen(false);
		this.setUser(this.getUser());
	}
	@Override
	public String toString() {
		return "\nNachricht [id=" + id + ", inhalt=" + inhalt + ", aufeingenerTafel=" + istAufeingenerTafel()
				+ ", aufAlleTafen=" + istAufAlleTafen() + ", titel=" + titel + ", user=" + user.getId() + "]";
	}
	

	
	
}
