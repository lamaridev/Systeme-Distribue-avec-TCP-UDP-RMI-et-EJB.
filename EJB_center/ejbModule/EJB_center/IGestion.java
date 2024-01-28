package EJB_center;

import java.util.List;

import jakarta.ejb.Remote;

@Remote
public interface IGestion {
	
	public void ajouter(Serveurs serveur);
	public String rechercherServices(int numero);
	public Serveurs rechercherServeurs(String nom);
	
}
