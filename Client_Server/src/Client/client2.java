package Client;


import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import Server.Iinter;

public class client2 {
	
	static String x="null";
	static int i;
	static int jeton=0; 
	static String information; 
	
	public static void main(String[] args) {
		
		String[] reference2 = {"S15", "S1", "S4", "S2", "S3", "S6", "S7", "S10", "S1", "S13", "S6", "S14", "S2", "S0", "S1", "S3", "S2", "S7", "S0", "S1", "FIN"};
	    i=0;
	    
		try {
			
		  Registry reg = LocateRegistry.getRegistry("localhost",1099);
		  Iinter IS = (Iinter) reg.lookup("refSrvI");

		  
	      MonTimer t1 = new MonTimer();

		  
		  while (true) {
			  
		  System.out.println("client 2 attend le jeton ");	
	      t1.startTimer(4000);
	      System.out.println("Starting Timer....");

		  DatagramSocket s1 = new DatagramSocket(1002);
		  byte[] receiveData = new byte[10];
		  DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);						
		  s1.receive(receivePacket);						
		  String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());						
		  s1.close();
		  if(receivedMessage.equals("1") && t1.isTimerExpired()==false)
		  {jeton=Integer.parseInt(receivedMessage); System.out.println("Bonne reception du jeton ..");		        LocalTime heureActuelle = LocalTime.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss:SS");
	        String formattedTime = heureActuelle.format(formatter); System.out.println("Reçu a : " + formattedTime);}
		  else if (receivedMessage.equals("1") && t1.isTimerExpired() == true) {
			    System.out.println("j'ai pas reçu le jeton du client 1 dans le délai ");
			    Scanner s = new Scanner(System.in);
			    
			    do {
			        System.out.print("Vous voulez que je crée un nouveau jeton pour continuer le processus yes or no : ");

			        String userInput = s.nextLine();

			        // Check if the user input is "yes"
			        if (userInput.equals("yes")) {
			            jeton=1;
			            MonTimer t2 = new MonTimer();
			            // Démarrer le nouveau minuteur
			            t2.startTimer(10000);
			            // Assigner t2 à t1, afin que la boucle utilise le nouveau minuteur
			            t1 = t2;
			            break;
			        }

			    } while (true); // Infinite loop until user enters "yes"
			    
			}

		  		System.out.println("Demande de service en cours...");
		              
				if (i<reference2.length && jeton==1)
				{	if(reference2[i].equals("FIN")) {
					t1.stopTimer();
				    System.out.println("your are in the end");
			        DatagramSocket dg = new DatagramSocket();
			        byte[] Mdata = new byte[10];
			        InetAddress ip = InetAddress.getByName("localhost");
					Mdata=Integer.toString(jeton).getBytes();
					DatagramPacket p = new DatagramPacket(Mdata, Mdata.length,ip,1003);
					dg.send(p);
					dg.close();
					jeton=0;

					System.exit(0);

				}else
				{
					switch(reference2[i])
					{
					        case "S0":
					            x =  IS.RecevoirDemandeClient("Serv1", "S0");
					            break;
					        case "S1":
					            x= IS.RecevoirDemandeClient( "Serv1", "S1");
					            break;
					        case "S2":
					           x= IS.RecevoirDemandeClient("Serv1", "S2");
					            break;
					        case "S3":
					           x= IS.RecevoirDemandeClient("Serv1", "S3");
					            break;
					        case "S4":
					           x= IS.RecevoirDemandeClient("Serv2", "S4");
					            break;
					        case "S5":
					           x= IS.RecevoirDemandeClient( "Serv2", "S5");
					            break;
					        case "S6":
					           x= IS.RecevoirDemandeClient( "Serv2", "S6");
					            break;
					        case "S7":
					        	x= IS.RecevoirDemandeClient("Serv3", "S7");
					            break;
					        case "S8":
					        	x=IS.RecevoirDemandeClient("Serv3", "S8");
					            break;
					        case "S9":
					        	x=IS.RecevoirDemandeClient("Serv3", "S9");
					            break;
					        case "S10":
					        	x= IS.RecevoirDemandeClient("Serv4", "S10");
					            break;
					        case "S11":
					        	x= IS.RecevoirDemandeClient("Serv4", "S11");
					            break;
					        case "S12":
					        	x= IS.RecevoirDemandeClient("Serv4", "S12");
					            break;
					        case "S13":
					        	x= IS.RecevoirDemandeClient("Serv5", "S13");
					            break;
					        case "S14":
					        	x=  IS.RecevoirDemandeClient("Serv5", "S14");
					            break;
					        case "S15":
					        	x= IS.RecevoirDemandeClient("Serv5", "S15");
					            break;
					    }
 
					if(x!="null")
					{
						System.out.println("Réception du service avec succès...");
						System.out.println(x);
				        DatagramSocket dg = new DatagramSocket();
				        byte[] Mdata = new byte[10];
				        InetAddress ip = InetAddress.getByName("localhost");
						Mdata=Integer.toString(jeton).getBytes();
						DatagramPacket p = new DatagramPacket(Mdata, Mdata.length,ip,1003);
						dg.send(p);
						dg.close();
						jeton=0;
						
					}

				}
						       
				i++;
			}
				
	     }   
		  								
		}
		catch (Exception e) {			
			System.out.println(e.toString());
        } 
    }
}