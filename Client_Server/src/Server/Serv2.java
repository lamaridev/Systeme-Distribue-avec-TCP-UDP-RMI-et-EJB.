package Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;

import javax.naming.InitialContext;

import EJB_center.IGestion;
import EJB_center.Serveurs;

public class Serv2 {

    public static void main(String[] args) {
        try {
            Properties props = new Properties();

            props.put("java.naming.factory.url.pkgs", "org.jboss.ejb.client.naming");
            props.put("jboss.naming.client.ejb.context", true);
            InitialContext context = new InitialContext(props);

            IGestion in = (IGestion) context.lookup("ejb:/EJB_center/Gestion!EJB_center.IGestion");

            // Create server socket outside the loop
            ServerSocket serverSocket = new ServerSocket(2002);
            
            System.out.println("Serveur 2 est en cours d'exécution.....");

            // Infinite loop to keep the server active
            while (true) {
                // Accept a new client socket inside the loop
                Socket clientSocket = serverSocket.accept();
                ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
                String receivedService = (String) inputStream.readObject();
                String receivedServer = (String) inputStream.readObject();
                
                System.out.println("Réception de la demande de service "+receivedService);

                
                Serveurs serv = in.rechercherServeurs(receivedServer);
                if (serv == null) {
                    in.ajouter(new Serveurs("Serv2", "127.0.0.1", 2002));
                }

                String service = null;

                switch (receivedService) {
                    case "S4":
                        service = in.rechercherServices(4);
                        break;
                    case "S5":
                        service = in.rechercherServices(5);
                        break;
                    case "S6":
                        service = in.rechercherServices(6);
                        break;

                }

                Socket s2 = new Socket("localhost", 2006);
                ObjectOutputStream out = new ObjectOutputStream(s2.getOutputStream());
                out.writeObject(service);

                System.out.println("Le service "+receivedService+" est envoyer au client");

                
                // Close resources in reverse order of creation
                out.close();
                s2.close();
                inputStream.close();
                clientSocket.close();
            }
        } catch (Exception e) {
            // Handle exceptions specific to the outer loop
            e.printStackTrace();
        }
    }
}

