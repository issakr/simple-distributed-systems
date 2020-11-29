package vs.anzeigetafel.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name = "user")
public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	
	@Column(name="name",unique = true)
	private String name;
	
	@Column(name="login",unique = true)
	private String login;
	
	
	private String passwort;
	private boolean istKoordi;
	
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private List<Nachricht> nachrichtlist;// = new ArrayList<Nachricht>();
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Tafel tafel;

	public User() {
		super();
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Tafel getTafel() {
		return tafel;
	}

	public void setTafel(Tafel tafel) {
		this.tafel = tafel;
	}

	public List<Nachricht> getNachrichtlist() {
		return nachrichtlist;
	}
	public void setFrei() {
		this.setName(new String(""));
		this.setLogin(new String(""));
		this.setPasswort(new String(""));
		this.setNachrichtlist(new ArrayList<Nachricht>());
		this.setIstKoordi(false);
		this.setTafel(this.getTafel());
	}
	public void setNachrichtlist(List<Nachricht> nachrichtlist) {
		this.nachrichtlist = nachrichtlist;
	}

	@Override
	public String toString() {
		return "\nUser [id=" + id + ", name=" + name + ", tafel=" + tafel.getId() + "]";
	}
	
	
}
