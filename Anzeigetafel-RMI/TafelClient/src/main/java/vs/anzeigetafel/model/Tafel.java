package vs.anzeigetafel.model;

import java.io.Serializable;
import java.util.List;

public class Tafel implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	
	private String name;

	private List<User> userlist;
	
	
	public List<User> getUserlist() {
		return userlist;
	}
	public void setUserlist(List<User> userlist) {
		this.userlist = userlist;
	}
	
	public Tafel() {
		super();
		this.id=0;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "\nTafel= "  + "Name" + getName() + ", ID=" + getId() + "]";
	}


	
	
}
