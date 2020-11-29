package vs.anzeigetafel.service;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import vs.anzeigetafel.model.*;

/**
 * Die Implementierungen der Funktionen, die ein Client durch RMI aufrufen darf.
 */
public class ServiceDyaliImpli implements ServiceDyali{
	@Autowired 
	private TafelRepo tafelRepo;
	@Autowired 
	private UserRepo userRepo;
	@Autowired 
	private NachrichtRepo nachrichtRepo;

	
	public ServiceDyaliImpli() {
	}
	
	/**
	 * Gibt an, ob wenigstens eine Tafel in der Datenbank vorhanden ist,
	 * sodass der Client entscheiden kann, ob er Beispieldaten hochladen kann oder nicht.
	 */
	@Override
	public boolean DBistLeer() throws RemoteException {
		return tafelRepo.findAll().isEmpty();
	}
	
	/**
	 * Gibt an, ob der Login existiert
	 * @return exist	true, wenn TafelRepo den eingegebenen Login findet
	 */
	@Override
	public Boolean checkObLoginExist(String login) throws RemoteException{
		Boolean exist= false;
		for(User u:userRepo.findAll()) {
			if(u.getLogin().equals(login))
				exist = true;
		}
		return exist;
	}
	
	/**
	 *	gibt einen User zurück, wenn er gefunden ist, der den "login" hat.
	 *	@param	login	der login des zu suchenden Users
	 *	@return	der zu suchende User
	 */
	@Override public User findeUserByLogin(String login) {
		return userRepo.findByLogin(login);
	}

	/**
	 * 	@return true, wenn der User mit dem "Login" das Passwort "pass" hat.
	 */
	@Override
	public Boolean checkPasswort(String login, String pass) throws RemoteException{
		Boolean a=false;
		if(findeUserByLogin(login).getPasswort().equals(pass)) {
			a=true;
			System.out.println(a);
		}
		return a;
	}
	
	/**
	 * 	ändert einen User mit den neuen eigegebenen Daten.
	 * 	@param	msgID	damit es keine neue Nachricht erzeugt wird.
	 */
	@Override
	public void updateNachricht(int msgID,String titel, String inhalt,boolean eufeingenerTafel, boolean aufallenTafel) {
		Nachricht n = new Nachricht();
		n.setTitel(titel);
		n.setInhalt(inhalt);
		n.setAufeingenerTafel(eufeingenerTafel);
		n.setAufAlleTafen(aufallenTafel);
		n.setId(msgID);
		n.setUser(nachrichtRepo.findByid(msgID).getUser());
		nachrichtRepo.save(n);
	}
	
	/**
	 * löscht einen User aus der Datenbank, wenn er erxistiert.
	 */
	@Override
	public void loescheNachricht(Nachricht selectedItem) {
		if(nachrichtRepo.existsById(selectedItem.getId())) {
			nachrichtRepo.delete(selectedItem);
		}else {
			System.out.println("Nachricht existiert nicht");
		}	
	}

	/**
	 * 	fügt eine neue Tafel mit dem Namen "tafelname" in der Datenbank hinzu.
	 *  Für diese Tafel eine ganze neue UserList erstellt, 
	 */
	@Override
	public void addTafel(String tafelname) {
		Tafel t= new Tafel();
		t.setId(0);
		t.setName(tafelname);
		t.setUserlist(new ArrayList<User>());
		tafelRepo.save(t);
		System.out.println(t.toString()+" wurde gespeichert");
	}

	/**
	 * 	fügt einen neuen User mit den eingegebenen Eigenschaften in der Datenbank hinzu,
	 * 	wenn eine Tafel mit der ID="tafelID" existiert.
	 * 	Für diesen Uswer wird eine neue NachrichtList erzeugt.
	 */
	@Override
	public void addUser(int tafelID, String name, String login, String passwort, boolean istKoordi) {
		if(tafelRepo.existsById(tafelID)) {
			User u=new User();
//			u.setId(0);
			u.setIstKoordi(istKoordi);
			u.setName(name);
			u.setLogin(login);
			u.setPasswort(passwort);
			u.setNachrichtlist(new ArrayList<Nachricht>());
			u.setTafel(tafelRepo.findByid(tafelID));
			userRepo.save(u);
			System.out.println(u.toString()+" wurde gespeichert");
		}else {
			System.out.println("AddUser(): tafel nicht gefunden");
		}
		
	}

	/**
	 * 	fügt eine neue Nachricht mit den eingegebenen Eigenschaften in der Datenbank hinzu,
	 * 	wenn ein User mit der ID="userID" existiert.
	 * 	diese Nachricht ist automatisch nicht auf allen anderen Tafeln veröffentlicht 
	 */
	@Override
	public void addNachricht(int userID, String inhalt, String titel, boolean aufeingenerTafel) {
		if(userRepo.existsById(userID)) {
			Nachricht n = new Nachricht();
			n.setInhalt(inhalt);
			n.setAufeingenerTafel(aufeingenerTafel);
			n.setTitel(titel);
			n.setUser(userRepo.findByid(userID));
			nachrichtRepo.save(n);
			System.out.println(n.toString()+" wurde gespeichert");
		}else {
			System.out.println("addNachricht(): User nicht gefunden");
		}
		
	}

	/**
	 * 	Auf einer Tafel mit der ID="tafelId", wenn sie existiert, eine NachrichtList hinzugefügt.
	 * 	Diese NachrichtList besteht aus:
	 * 		- Nachrichten von jedem Benutzer dieser Tafel, die auf eigener Tafel publiziert werden sollen.
	 * 		- Alle Nachrichten von allen Tafeln, die auf allen anderen Tafeln veröffentlicht werden sollen,
	 * 				und nicht schon auf der Liste vorhanden sind.
	 */
	@Override
	public List<Nachricht> getNachrichtfuerTafel(int tafelId) throws RemoteException{
		List<Nachricht> nachrichtList = new ArrayList<Nachricht>();
		Tafel tafel = tafelRepo.findByid(tafelId);
		//alle publiziert
		for(User u:getUserListFromTafel(tafel)) {
			nachrichtList.addAll(getAllPublishedNachrichtenVonUser(u));
		}
		//alle öffentliche und nicht schon vorhandene Nachrichten
		nachrichtRepo.findAll().forEach(n->{
			if(n.istAufAlleTafen() && !msgExist(nachrichtList,n)) 
				nachrichtList.add(n);
		});
		return nachrichtList;
	}
	private boolean msgExist(List<Nachricht> list, Nachricht msg) {
		for(Nachricht n:list) {
			if(n.getId() == msg.getId()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 	Die Methode wird intern benutzt, um eine NachrichtList zu erzeugen,
	 * 	die aus Nachrichten besteht, die nur auf eigener Tafel zu publizieren sind.
	 * 	@param user	Der Verfasser der Nachrichten.
	 * 	@return ausgefilterte NachrichtList
	 */
	private List<Nachricht> getAllPublishedNachrichtenVonUser(User user) {
		List<Nachricht> published_nachrichtList = new ArrayList<Nachricht>();
		for(Nachricht n: user.getNachrichtlist()) {
			if(n.istAufeingenerTafel()) {
				published_nachrichtList.add(n);
			}
		}
		return published_nachrichtList;
	}
	
	/**
	 * 	Methode wird intern benutzt, um aus einer Tafel die UserList zu nehmen.
	 * @param tafel		Die Gruppe der Usern
	 */
	private List<User> getUserListFromTafel(Tafel tafel) {
		List<User >userList= new ArrayList<User>();
		if(tafelRepo.existsById(tafel.getId())) {
			tafel.getUserlist().forEach(u->userList.add(u));
//			userList.addAll(tafel.getUserlist());
		}else {
			System.out.println("getUserListFromTafel(): Tafel existiert nicht");
		}
		return userList;
	}
	
	/**
	 *  Wenn der User Koordinator ist, werden alle Nachrichten hinzugefügt, die aufeingnerTafel publiziert werden sollen.
	 *  Danach alle restlichen eigenen Nachrichten.
	 *  Wenn der User ein normaler Use ist, werden alle eigenen Nachrichten hinzugefügt.
	 */
	@Override
	public List<Nachricht> getNachrichtenfuerVerwaltung(int userId) throws RemoteException{
		List<Nachricht> nachrichtList = new ArrayList<Nachricht>();
		Tafel tafel = tafelRepo.findByid(userRepo.findByid(userId).getId());
		
		//alle publiziert Nachrichten
		if(userRepo.findByid(userId).getIstKoordi() == true) {
			
			for(User u:getUserListFromTafel(tafel)) {
				nachrichtList.addAll(getAllPublishedNachrichtenVonUser(u));
			}
//			nachrichtList.addAll(getNachrichtfuerTafel(userRepo.findByid(userId).getTafel().getId()));
			//alle eingene nicht publiziert Nachrichten
			userRepo.findByid(userId).getNachrichtlist().forEach(n-> {
				if(!n.istAufeingenerTafel())
					nachrichtList.add(n);
			});
		}else {
			//alle eingene Nachrichten
			nachrichtList.addAll(userRepo.findByid(userId).getNachrichtlist());
		}
		return nachrichtList;
	}
	
	
}
