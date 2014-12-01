package de.scouthero.handler;

import static de.scouthero.util.LogUtil.debugEnter;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import de.scouthero.beans.Club;
import de.scouthero.beans.Team;
import de.scouthero.services.ClubService;
import de.scouthero.util.ScoutheroException;

@ViewScoped
@ManagedBean
public class MyClubHandler extends ViewScopedHandler {

	private static final long serialVersionUID = -9216235001938303075L;

	private static final Logger logger = Logger.getLogger(MyClubHandler.class);
	
	@EJB
	private ClubService clubService;
	
	private List<Club> userClubs;
	private List<Team> userTeams;
	private Club selectedUserClub;
	private Team selectedUserTeam;
	private boolean showClubPanel;
	private boolean showTeamPanel;
	private boolean showTeamEditPanel;

	
	public MyClubHandler() {
		showClubPanel = false;
		showTeamPanel = false;
	}
	
	@PostConstruct
	public void loadUserClubs() {
		final String methodName = "loadUserClubs()";
		debugEnter(logger, methodName);
		if (currentUser != null) {
			try {
				userClubs = clubService.getUserClubs(currentUser);
			} catch (ScoutheroException e) {
				logger.error("", e);
			}
		}
	}
	
	/**
	 * 
	 */
	private void loadClubTeams() {
		final String methodName = "loadUserTeams()";
		debugEnter(logger, methodName);
		if (selectedUserClub != null) {
			try {
				userTeams = clubService.getClubTeams(this.selectedUserClub);
			} catch (ScoutheroException e) {
				addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
			}
		}
	}
	
	public void createNewClub() {
		final String methodName = "createNewClub()";
		debugEnter(logger, methodName);
		selectedUserClub = new Club();
		showClubPanel = true;
	}
	
	public void createNewTeam() {
		final String methodName = "createNewTeam()";
		debugEnter(logger, methodName);
		selectedUserTeam = new Team();
		showTeamEditPanel = true;
	}
	
	public void onClubRowSelect(SelectEvent event) {
		final String methodName = "onClubRowSelect()";
		debugEnter(logger, methodName, "params: ", event);
		showTeamPanel = true;
    }
	
	public void onTeamRowSelect(SelectEvent event) {
		final String methodName = "onTeamRowSelect()";
		debugEnter(logger, methodName, "params: ", event);
		onTeamEdit((Team)event.getObject());
    }
	
	public void onClubEdit(Club selClub) {
		final String methodName = "onClubEdit()";
		debugEnter(logger, methodName, "params: ", selClub);
		selectedUserClub = selClub;
		showClubPanel = true;
	}
	
	public void onTeamEdit(Team t) {
		final String methodName = "onTeamEdit()";
		debugEnter(logger, methodName, "params: ", t);
		selectedUserTeam = t;
		showTeamEditPanel = true;
	}
 
    public void onClubRowUnSelect(UnselectEvent event) {
    	final String methodName = "onClubRowUnSelect()";
    	debugEnter(logger, methodName, "params: ", event);
    	selectedUserClub = null;
    	showTeamPanel = false;
    }
    
    public void onTeamRowUnSelect(UnselectEvent event) {
    	final String methodName = "onTeamRowUnSelect()";
    	debugEnter(logger, methodName, "params: ", event);
    	selectedUserTeam = null;
    	showTeamEditPanel = false;
    }
	
	public void saveClub() {
		final String methodName = "saveClub()";
		debugEnter(logger, methodName);
		if (selectedUserClub != null && currentUser != null) {
			try {
				clubService.saveClub(selectedUserClub, currentUser);
				showClubPanel = false;
//				return "userClub.xhtml?faces-redirect=true";
			} catch (ScoutheroException e) {
				addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
			}
		}
//		return null;
	}
	
	public void saveClub2() {
		final String methodName = "saveClub2()";
		debugEnter(logger, methodName);
	}
	
	public void saveTeam() {
		final String methodName = "saveTeam()";
		debugEnter(logger, methodName);
	}
	
	public void showTeamPanel(Club selClub) {
		final String methodName = "showTeamPanel()";
		debugEnter(logger, methodName, "params: ", selClub);
		showTeamPanel = true;
		this.setSelectedUserClub(selClub);
		// lade Mannschaften
		this.loadClubTeams();
	}
	
	/** GETTER UND SETTER **/
	public Club getSelectedUserClub() {
		return selectedUserClub;
	}

	public void setSelectedUserClub(Club selectedUserClub) {
		this.selectedUserClub = selectedUserClub;
	}
	
	public Team getSelectedUserTeam() {
		return selectedUserTeam;
	}

	public void setSelectedUserTeam(Team selectedUserTeam) {
		this.selectedUserTeam = selectedUserTeam;
	}

	public boolean getShowClubPanel(){
		return showClubPanel;
	}
	public void setShowClubPanel(boolean b) {
		showClubPanel = b;
	}
	public boolean getShowTeamPanel() {
		return showTeamPanel;
	}
	public boolean getShowTeamEditPanel() {
		return showTeamEditPanel;
	}
	public List<Club> getUserClub() {
		return userClubs;
	}

	public List<Team> getUserTeams() {
		return userTeams;
	}

	public void setUserTeams(List<Team> userTeams) {
		this.userTeams = userTeams;
	}
}
