package Server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Iinter extends Remote {

	public String RecevoirDemandeClient(String S , String y) throws RemoteException;
	
}
