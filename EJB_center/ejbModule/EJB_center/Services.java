package EJB_center;



import java.io.Serializable;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class Services implements Serializable {
	   @Id
	   private int numero;
	   private String nom;
	   private String discription;
   
	   public Services() {
	      super();
	   }

	public Services(int numero, String nom, String discription) {

		this.numero = numero;
		this.nom = nom;
		this.discription = discription;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}


	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}
	
	
	   public String toString() {
		return " le Service: "+numero+" "+nom+" "+discription ;
		   }
  
}
