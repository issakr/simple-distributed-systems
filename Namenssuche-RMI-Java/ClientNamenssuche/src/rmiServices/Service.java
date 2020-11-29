package rmiServices;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import namen.Name;

public interface Service extends Remote{
	List<Name> namenSuche(List<Name> repertoire) throws RemoteException,Exception;
}
