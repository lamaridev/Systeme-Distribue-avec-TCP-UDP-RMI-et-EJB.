package Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

import javax.naming.InitialContext;

import EJB_center.IGestion;
import EJB_center.Serveurs;

public class Serv5 {

    public static void main(String[] args) {
        try {
            Properties props = new Properties();

            props.put("java.naming.factory.url.pkgs", "org.jboss.ejb.client.naming");
            props.put("jboss.naming.client.ejb.context", true);
            InitialContext context = new InitialContext(props);

            IGestion in = (IGestion) context.lookup("ejb:/EJB_center/Gestion!EJB_center.IGestion");

            // Create server socket outside the loop
            ServerSocket serverSocket = new ServerSocket(2005);
            
            System.out.println("Serveur 5 est en cours d'exécution.....");

            // Infinite loop to keep the server active
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
                String receivedService = (String) inputStream.readObject();
                String receivedServer = (String) inputStream.readObject();

                System.out.println("Réception de la demande de service "+receivedService);

                Serveurs serv = in.rechercherServeurs(receivedServer);
                if (serv == null) {
                    in.ajouter(new Serveurs("Serv5", "127.0.0.1", 2005));
                }

                String service = null;

                switch (receivedService) {
                    case "S13":
                        service = in.rechercherServices(13);
                        break;
                    case "S14":
                        service = in.rechercherServices(14);
                        break;
                    case "S15":
                        service = in.rechercherServices(15);
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
            System.out.println(e.toString());
        }
    }
}
