package Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class interServer extends java.rmi.server.UnicastRemoteObject implements Iinter {

    protected interServer() throws RemoteException {
        super();
    }


    @Override
    public String RecevoirDemandeClient(String Serveur, String Service) {
    	
    try {
    	
    	int port = 0 ;
    	
    	if(Serveur.equals("Serv1"))
    	{
    	   port=2001;
    	}
    	if(Serveur.equals("Serv2"))
    	{
    		port= 2002;
    	}
    	if(Serveur.equals("Serv3"))
    	{
    		port= 2003;
    	}
    	if(Serveur.equals("Serv4"))
    	{
    		port= 2004;
    	}
    	if(Serveur.equals("Serv5"))
    	{
    		port= 2005;
    	}

//    	System.out.println(port);
    	
    	
    	Socket s1 = new Socket("localhost", port);
        ObjectOutputStream out1 = new ObjectOutputStream(s1.getOutputStream());

        
        switch (Service) {
        case "S0":
            out1.writeObject("S0");
            out1.writeObject(Serveur);
            break;
        case "S1":
            out1.writeObject("S1");
            out1.writeObject(Serveur);
            break;
        case "S2":
            out1.writeObject("S2");
            out1.writeObject(Serveur);
        break;
        case "S3":
            out1.writeObject("S3");
            out1.writeObject(Serveur);
            break;
        case "S4":
            out1.writeObject("S4");
            out1.writeObject(Serveur);
            break;   	
        case "S5":
            out1.writeObject("S5");
            out1.writeObject(Serveur);
            break;
        case "S6":
            out1.writeObject("S6");
            out1.writeObject(Serveur);
            break;
        case "S7":
            out1.writeObject("S7");
            out1.writeObject(Serveur);
            break;
        case "S8":
            out1.writeObject("S8");
            out1.writeObject(Serveur);
            break;
        case "S9":
            out1.writeObject("S9");
            out1.writeObject(Serveur);
            break;
        case "S10":
            out1.writeObject("S10");
            out1.writeObject(Serveur);
            break;
        case "S11":
            out1.writeObject("S11");
            out1.writeObject(Serveur);
            break;
        case "S12":
            out1.writeObject("S12");
            out1.writeObject(Serveur);
            break;
        case "S13":
            out1.writeObject("S13");
            out1.writeObject(Serveur);
            break;
        case "S14":
            out1.writeObject("S14");
            out1.writeObject(Serveur);
            break;
        case "S15":
            out1.writeObject("S15");
            out1.writeObject(Serveur);
            break;
            
    	}
      
        out1.close();
        s1.close();       

    		ServerSocket se3 = new ServerSocket(2006);
    		Socket con3 = se3.accept();
    		ObjectInputStream inp3 = new ObjectInputStream(con3.getInputStream());
    		
    		String receivedService = (String) inp3.readObject();
    		
    
            inp3.close();
            con3.close();
            se3.close();
            
            return receivedService ;
            	
           
        	
       } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
		
    }

    public static void main(String[] args) {
        try {
            interServer IS = new interServer();
            System.out.println("serveur intermidaire execution ...");
            Registry reg = LocateRegistry.createRegistry(1099);
            reg.rebind("refSrvI", IS);

    		
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }


}


