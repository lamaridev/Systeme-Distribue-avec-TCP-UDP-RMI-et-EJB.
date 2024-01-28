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

public class Serv1 {

    public static void main(String[] args) {
        try {
            Properties props = new Properties();

            props.put("java.naming.factory.url.pkgs", "org.jboss.ejb.client.naming");
            props.put("jboss.naming.client.ejb.context", true);
            InitialContext context = new InitialContext(props);

            IGestion in = (IGestion) context.lookup("ejb:/EJB_center/Gestion!EJB_center.IGestion");

            // Create server socket outside the loop
            ServerSocket serverSocket = new ServerSocket(2001);
            
            System.out.println("Serveur 1 est en cours d'exécution.....");

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
                    in.ajouter(new Serveurs("Serv1", "127.0.0.1", 2001));
                }

                String service = null;

                switch (receivedService) {
                    case "S0":
                        service = in.rechercherServices(16);
                        break;
                    case "S1":
                        service = in.rechercherServices(1);
                        break;
                    case "S2":
                        service = in.rechercherServices(2);
                        break;
                    case "S3":
                        service = in.rechercherServices(3);
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

