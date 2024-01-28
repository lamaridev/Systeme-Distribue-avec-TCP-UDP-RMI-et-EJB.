package EJB_center;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class Serveurs implements Serializable {
	   @Id
	   private String nom;
	   private String adressIP;
	   private int port;
	   
	   
	   public Serveurs() {
	      super();
	   }


	public Serveurs(String nom, String adressIP, int port) {
		this.nom = nom;
		this.adressIP = adressIP;
		this.port = port;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getAdressIP() {
		return adressIP;
	}


	public void setAdressIP(String adressIP) {
		this.adressIP = adressIP;
	}


	public int getPort() {
		return port;
	}


	public void setPort(int port) {
		this.port = port;
	}
	
	   public String toString() {
		return " le Serveur: "+nom+" "+adressIP+" "+port ;
		   }



	   
}
