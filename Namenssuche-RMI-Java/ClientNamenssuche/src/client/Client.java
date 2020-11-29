package client;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import namen.Name;

public class Client {
//	private List<Name> repertoire= new ArrayList<Name>();

	

	

	public void start(List<Name> repertoire) {
		String txt = JOptionPane.showInputDialog("Wieviel Namen?");
		int anzahl = Integer.parseInt(txt);
		for(int i=0; i<anzahl;i++) {
	     	txt = JOptionPane.showInputDialog("VorNamen?");
	     	repertoire.add(new Name(txt));
         }
	}
	
//	public List<Name> getRepertoire() {
//		return this.repertoire;
//	}
//
//	public void setRepertoire(List<Name> repertoire) {
//		this.repertoire = repertoire;
//	}

	public void zeigeRepo(Set<Name> repo) {
		System.out.println(repo.toString());
	}
	
	


	
	
}
