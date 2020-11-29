package rmiServices;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import namen.Name;

public class ServiceImpl extends UnicastRemoteObject implements Service{

	private static final long serialVersionUID = 1L;
	private List<Name> nameBuch;

    public ServiceImpl() throws RemoteException{
    	nameBuch = new ArrayList<Name>();

    	/*
    	 * Liste bedeutender Personen für die Informatik – Wikipedia
    	 */
    	nameBuch.add(new Name());
    	nameBuch.add((new Name("Marc","Andreessen")));
    	nameBuch.add((new Name("Rudolf","Bayer")));
    	nameBuch.add((new Name("Karlheinz","Brandenburg")));
    	nameBuch.add((new Name("Noam","Chomsky")));
    	nameBuch.add((new Name("James","Gosling")));
    	nameBuch.add((new Name("Alan","Kay")));
    	nameBuch.add((new Name("Dennis","Richie")));
    	nameBuch.add((new Name("Richard","Stallman")));
    	nameBuch.add((new Name("Ken","Thompson")));
    	nameBuch.add((new Name("Ray","TomLinson")));
    	nameBuch.add((new Name("Konrad","Zuse")));
    }


    @Override
	public List<Name> namenSuche(List<Name> repertoire) throws RemoteException {
		for(Iterator<Name> iterator = repertoire.iterator(); iterator.hasNext(); ) {
			
			/*
			 * clientname ist der Name mit gesuchtem Vorname
			 */
			Name clientname = iterator.next();
			
			/*
			 * servername ist der gefundene Name mit dem gegebenen clientNachname
			 */
			Name servername = findeNameAusNachname(clientname.getNachname());
			
			if(servername != null) {
				System.out.println(servername.getVorname()+": "+clientname.getNachname());
				clientname.setVorname(servername.getVorname());
			}else {
				System.out.println("Vorname fuer <"+clientname.getNachname()+"> koennte nicht gefunden werden");
				clientname.setVorname("Vorname existiert nicht");
				
			}
		}
		return repertoire;
	}

    /*
     * Wir suchen die Liste durch,
     * ob ein Name mit dem Nachname vorhaben ist
     */
    private Name findeNameAusNachname(String nachname) {
    	for(Iterator<Name> iterator = this.nameBuch.iterator(); iterator.hasNext(); ) {
    		Name name = iterator.next();
    		if(name.getNachname().equals(nachname)) {
    			return name;
    		}
    	}
        return null;
    }



}
