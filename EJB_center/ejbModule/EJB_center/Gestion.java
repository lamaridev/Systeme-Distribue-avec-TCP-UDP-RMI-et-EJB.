package EJB_center;

import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Stateless
public class Gestion implements IGestion {

	@PersistenceContext
	EntityManager em;
	
	@Override
	public void ajouter(Serveurs serveur) {
		em.persist(serveur);
		
	}

	@Override
	public String rechercherServices(int numero) {
	    TypedQuery<Object[]> query = em.createQuery(
	        "SELECT s.discription, s.numero, s.nom FROM Services s WHERE s.numero = :numero", Object[].class);
	    query.setParameter("numero", numero);

	    List<Object[]> results = query.getResultList();

	    StringBuilder resultString = new StringBuilder();

	    for (Object[] service : results) {
	        String description = (String) service[0];
	        int serviceNumber = (int) service[1];
	        String serviceName = (String) service[2];

	        resultString.append("Description: ").append(description)
	                     .append(", Numero: ").append(serviceNumber)
	                     .append(", Nom: ").append(serviceName)
	                     .append("\n");
	    }

	    return resultString.toString();
	}




	@Override
	public Serveurs rechercherServeurs(String nom) {
		return em.find(Serveurs.class, nom);
	}

	




}
