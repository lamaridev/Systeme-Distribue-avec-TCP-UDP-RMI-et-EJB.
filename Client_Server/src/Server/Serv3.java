package Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

import javax.naming.InitialContext;

import EJB_center.IGestion;
import EJB_center.Serveurs;

public class Serv3 {

    public static void main(String[] args) {
        try {
            Properties props = new Properties();

            props.put("java.naming.factory.url.pkgs", "org.jboss.ejb.client.naming");
            props.put("jboss.naming.client.ejb.context", true);
            InitialContext context = new InitialContext(props);

            IGestion in = (IGestion) context.lookup("ejb:/EJB_center/Gestion!EJB_center.IGestion");

            // Create server socket outside the loop
            ServerSocket serverSocket = new ServerSocket(2003);
            
            System.out.println("Serveur 3 est en cours d'exécution.....");

            // Infinite loop to keep the server active
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
                String receivedService = (String) inputStream.readObject();
                String receivedServer = (String) inputStream.readObject();

                System.out.println("Réception de la demande de service "+receivedService);

                Serveurs serv = in.rechercherServeurs(receivedServer);
                if (serv == null) {
                    in.ajouter(new Serveurs("Serv3", "127.0.0.1", 2003));
                }

                String service = null;

                switch (receivedService) {
                    case "S7":
                        service = in.rechercherServices(7);
                        break;
                    case "S8":
                        service = in.rechercherServices(8);
                        break;
                    case "S9":
                        service = in.rechercherServices(9);
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
