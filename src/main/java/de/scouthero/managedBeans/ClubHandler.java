package de.scouthero.managedBeans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.el.ELResolver;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;

import org.apache.log4j.Logger;

import de.scouthero.beans.Club;
import de.scouthero.beans.Team;
import de.scouthero.beans.User;
import de.scouthero.util.EMF;

@ManagedBean
@SessionScoped
public class ClubHandler {
	private final static Logger logger = Logger.getLogger(ClubHandler.class);
			
	private List<Club> clubs;
	private List<Team> teams;
	private EntityManager em;
	private Club userClub ;  
	
	@Resource
	private UserTransaction utx;
	
	public ClubHandler() {
		logger.info("--> Club()");
		em = EMF.createEntityManager();
	}
	
	@PostConstruct
	public void init() {
		try {
			TypedQuery<Team> query = em.createNamedQuery("Team.findByUser", Team.class);
			query.setParameter("user", getActualUser());
			teams = new ArrayList<Team>();
			teams.addAll(query.getResultList());
		} catch (Exception e) {
			logger.error("Fehler beim Holen der Teams",e);
		} 
//		
		try {
			TypedQuery<Club> query = em.createNamedQuery("Club.findByUser", Club.class);
			query.setParameter("user", getActualUser());
			userClub = query.getSingleResult();
			if (userClub == null) {
				userClub = new Club();
				insertNewClub();
			}
			logger.info(userClub.getUser());
		} catch (Exception e) {
			logger.error("Fehler beim Holen der Clubs",e);
		} 
		
		try {
			TypedQuery<Club> query = em.createNamedQuery("Club.findAll", Club.class);
			clubs = new ArrayList<Club>();
			clubs.addAll(query.getResultList());
		} catch (Exception e) {
			logger.error("Fehler beim Holen der Clubs",e);
		} 
	}
	
	public String insertNewClub() {
		logger.info("--> insert new club");
		em.getTransaction().begin();
		userClub.setUser(getActualUser());
		em.persist(userClub);
		em.getTransaction().commit();
		logger.info("return 2 index");
		return "index";
	}
	
	public List<Team> getUserTeams() { 
		return this.teams;
	}
	
//	public List<Club> getUserClubs() {
////		logger.info("--> getUserClubs");
//		return this.userClubs;
//	}
	
	public void createNewClub() {
//		logger.info("--> createClub()");
//		currentClub = new Club();
//		em.getTransaction().begin();
//		currentClub.setUser(getActualUser());
//		em.persist(currentClub);
//		em.getTransaction().commit();
//		
		addMessage(FacesMessage.SEVERITY_INFO, "Verein erfolgreich angelegt.");
	}
	
	public String updateClub() {
		logger.info("--> updateClub");
		em.getTransaction().begin();
		em.merge(userClub);
		em.getTransaction().commit();
		addMessage(FacesMessage.SEVERITY_INFO, "Verein erfolgreich bearbeitet");
		return null;
	}
	
	public void deleteClub() {
//		logger.info("---> deleteClub()");
//		if (currentClub == null || currentClub.getId() < 0) {
//			addMessage(FacesMessage.SEVERITY_ERROR, "Kein Verein zum Löschen ausgewählt");
//		} else if (currentClub.getTeams() != null && currentClub.getTeams().size() != 0) {
//			addMessage(FacesMessage.SEVERITY_ERROR, "Verein hat Teams zugeordnet");
//		} else {
//			em.getTransaction().begin();
//	        em.remove(currentClub);
//	        em.getTransaction().commit();
//	        addMessage(FacesMessage.SEVERITY_INFO, "Verein erfolgreich gelöscht");
//		}
	}
	
	private User getActualUser()  {
		ELResolver el = FacesContext.getCurrentInstance()
        	      .getApplication().getELResolver();
        UserHandler userHandler = (UserHandler) el.getValue(FacesContext.getCurrentInstance()
        	      .getELContext(), null, "userHandler");
        User currentUser = userHandler.getUser();
        if (currentUser.getLoginName() == null)
        	throw new RuntimeException("Kein User angemeldet");
        return currentUser;
	}
	
	public List<Club> getClubs() {
		return this.clubs;
	}

	public Club getUserClub() {
		return userClub;
	}

	public void setUserClub(Club currentClub) {
		this.userClub = currentClub;
	}
//	
//	public boolean isShowDetailPanel() {
//		return this.currentClub != null;
//	}
	
	private void addMessage(Severity severity, String summary) {  
        FacesMessage message = new FacesMessage(severity, summary,  null);  
        FacesContext.getCurrentInstance().addMessage(null, message);  
    }  

}
