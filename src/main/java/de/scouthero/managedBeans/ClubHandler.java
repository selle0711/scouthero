package 	de.scouthero.managedBeans;
import static de.scouthero.util.LogUtil.debugEnter;
import static de.scouthero.util.LogUtil.debugExit;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.picketbox.util.StringUtil;

import de.scouthero.beans.Club;
import de.scouthero.beans.Team;
import de.scouthero.services.ClubService;

@ManagedBean
@SessionScoped
public class ClubHandler {
	private final static Logger logger = Logger.getLogger(ClubHandler.class);
			
	private List<Club> clubs;
	private List<Team> teams;
	private String searchString;
	
	private Club userClub ;
	
	@EJB
	private ClubService clubService;
	
	public ClubHandler() {
		logger.info("--> Club()");
		
	}
	
	@PostConstruct
	public void init() {
		final String methodName = "init()";
		debugEnter(logger, methodName);
		try {
			clubs = clubService.getAllClubs();
		} catch (Exception e) {
			logger.error("",e);
			addMessage(FacesMessage.SEVERITY_FATAL, e.getMessage());
		}
	}
	
	private void addMessage(Severity severity, String summary) {  
        FacesMessage message = new FacesMessage(severity, summary,  null);  
        FacesContext.getCurrentInstance().addMessage("messages", message);  
    }  
	
	/* AB HIER NUR GETTER UND SETTER */
	
	public List<Team> getUserTeams() { 
		return this.teams;
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
	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
}
