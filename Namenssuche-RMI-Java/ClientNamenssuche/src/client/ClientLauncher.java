package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import namen.Name;
import rmiServices.Service;


public class ClientLauncher {
	
	public static void fillTHElist(List<Name> repertoire) {
		int anzahl=0;
		do {
			String txt = JOptionPane.showInputDialog("Wieviel Namen?");
			anzahl = Integer.parseInt(txt);
		}while(anzahl>10 || anzahl<0);
		for(int i=0; i<anzahl;i++) {
	     	String txt = JOptionPane.showInputDialog("Geben Sie einen Nachname?");
	     	repertoire.add(new Name(txt));
         }
	}
	
	public static void main(String[] args) {

		List<Name> repertoire= new ArrayList<Name>();
		
		String host = (args.length < 1) ? null : args[0];
	    System.out.println("host: "+host);
	    try {
            Registry registry = LocateRegistry.getRegistry(host, 1099);
            Service stub =  (Service) registry.lookup("rmi://localhost:1099/Namenssuche");

            //hier wird die Liste mit Nachnamen gefuellt
            fillTHElist(repertoire);
            
            /*
             * Wir geben eine Liste mit nur Nachnamen weiter 
             * Und bekommen dieselbe Liste mit noch den Vornamen eingetragen zurueck
             */
            repertoire=stub.namenSuche(repertoire);
            
            System.out.println(repertoire.toString());
            
	    } catch (Exception e) {
	    	System.err.println("Client exception: " + e.toString());
	        e.printStackTrace();
	    }
	}
}
