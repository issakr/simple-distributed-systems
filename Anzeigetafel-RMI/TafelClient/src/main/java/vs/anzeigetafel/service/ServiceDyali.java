package vs.anzeigetafel.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import org.springframework.stereotype.Service;

import vs.anzeigetafel.model.*;
@Service
public interface ServiceDyali extends Remote{
	Boolean	checkObLoginExist(String login) throws RemoteException;
	Boolean checkPasswort(String login, String pass)throws RemoteException;
	boolean DBistLeer()throws RemoteException;
	
	void addTafel(String name) throws RemoteException;
	void addUser(int tafelID, String name, String login, String passwort, boolean istKoordi) throws RemoteException;
	void addNachricht(int userID, String inhalt, String titel,boolean aufeingenerTafel) throws RemoteException;

	User findeUserByLogin(String login)throws RemoteException;
	
	List<Nachricht> getNachrichtenfuerVerwaltung(int userId) throws RemoteException;
	List<Nachricht> getNachrichtfuerTafel(int tafelId) throws RemoteException;
	
	void updateNachricht(int msgID, String titel, String inhalt, boolean aufeingenerTafel, boolean aufallenTafel)throws RemoteException;
	void loescheNachricht(Nachricht selectedItem)throws RemoteException;
	
}