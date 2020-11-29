package server;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import rmiServices.Service;
import rmiServices.ServiceImpl;

public class ServiceLauncher {
public static void main(String args[]) {
        
        try {
//        	ServiceImpl obj = new ServiceImpl();
//        	Service stub = (Service) UnicastRemoteObject.exportObject( obj, 0);
        	
        	/*
        	 * Wir sprechen die Interface an und nicht die Impli
        	 */
        	Service stub = new ServiceImpl();
            System.out.println(stub.toString());
            // Bind the remote object's stub in the registry
            LocateRegistry.createRegistry(1099);
            Registry registry = LocateRegistry.getRegistry(1099);
            registry.rebind("rmi://localhost:1099/Namenssuche", stub);

            System.err.println("Server ready");
        } catch (RemoteException e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
