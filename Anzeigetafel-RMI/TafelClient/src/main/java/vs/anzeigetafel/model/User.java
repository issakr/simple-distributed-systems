package vs.anzeigetafel.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	
	private String name;
	

	private String login;
	private boolean istKoordi;
	private String passwort;
	

	private Tafel tafel;
	
	private List<Nachricht> nachrichtlist= new ArrayList<Nachricht>(); // new 
	
	public List<Nachricht> getNachrichtlist() {
		return nachrichtlist;
	}
	public void setNachrichtlist(List<Nachricht> nachrichtlist) {
		this.nachrichtlist = nachrichtlist;
	}
	public User() {
		super();
		setTafel(new Tafel());
	}
	public User(Tafel tafel) {
		super();
		this.id=0;
		this.istKoordi=false;
		this.setNachrichtlist(new ArrayList<Nachricht>());
		this.setLogin(" ");
		this.setPasswort(" ");
		this.setTafel(tafel);
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public boolean getIstKoordi() {
		return istKoordi;
	}
	public void setIstKoordi(boolean istKoordi) {
		this.istKoordi = istKoordi;
	}
	public String getPasswort() {
		return passwort;
	}
	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setFrei() {
		this.setName(new String(""));
		this.setLogin(new String(""));
		this.setPasswort(new String(""));
		this.setNachrichtlist(new ArrayList<Nachricht>());
		this.setIstKoordi(false);
		this.setTafel(this.getTafel());
	}
	public Tafel getTafel() {
		return tafel;
	}
	public void setTafel(Tafel tafel) {
		this.tafel = tafel;
	}
	@Override
	public String toString() {
		return "\nUser [ID" + getId() + ", Login=" + getLogin() + ", Koordi=" + getIstKoordi()
				+ ", Pass=" + getPasswort() + ", Name=" + getName() + ", TafelID=" + getTafel().getId() + "]";
	}

}
