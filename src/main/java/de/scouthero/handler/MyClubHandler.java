package de.scouthero.handler;

import static de.scouthero.util.LogUtil.debugEnter;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import de.scouthero.beans.Club;
import de.scouthero.beans.SportType;
import de.scouthero.beans.Team;
import de.scouthero.services.ClubService;
import de.scouthero.util.Defs;
import de.scouthero.util.ScoutheroException;

/**
 * Handler für den View userClub
 * @see ViewScoped 
 * @see ViewScopedHandler
 * @author rgesell
 *
 */
@ViewScoped
@ManagedBean
public class MyClubHandler extends ViewScopedHandler {

	private static final long serialVersionUID = -9216235001938303075L;

	/**
	 * Logger
	 */
	private static final Logger logger = Logger.getLogger(MyClubHandler.class);
	
	@EJB
	private ClubService clubService;
	
	/**
	 * Liste der Vereine des angemeldeten Benutzers
	 */
	private List<Club> userClubs;
	/**
	 * Liste der Mannschaften des selektierten Vereins
	 */
	private List<Team> userTeams;
	/**
	 * selektierter Club des angemeldeten Benutzers
	 */
	private Club selectedUserClub;
	/**
	 * selektiertes Team des selektierten Clubs
	 */
	private Team selectedUserTeam;
	/**
	 * Flag für die Anzeige des Panels für die Club-Bearbeitung
	 */
	private boolean showClubPanel;
	/**
	 * Flag für die Anzeige der Mannschaftstabelle
	 */
	private boolean showTeamPanel;
	/**
	 * Flag für die Anzeige des Panels für die Bearbeitung der selektierten Mannschaft
	 */
	private boolean showTeamEditPanel;

	private Long selectedSportTypId;
	
	public MyClubHandler() {
		resetShowPanel();
	}
	
	/**
	 * lädt alle Vereine des angemeldeten Benutzers - als PostConstruct annotiert
	 */
	@PostConstruct
	public void loadUserClubs() {
		final String methodName = "loadUserClubs()";
		debugEnter(logger, methodName);
		if (currentUser != null && userClubs == null) {
			logger.info("-> lade UserClubs");
			try {
				userClubs = clubService.getUserClubs(currentUser);
			} catch (ScoutheroException e) {
				logger.error("", e);
			}
		}
	}
	
	/**
	 * Lädt alle Mannschaften des selektierten Vereins
	 */
	private List<Team> loadClubTeams() {
		final String methodName = "loadUserTeams()";
		debugEnter(logger, methodName);
		if (selectedUserClub != null) {
			try {
				return clubService.getClubTeams(this.selectedUserClub);
			} catch (ScoutheroException e) {
				addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
			}
		}
		return null;
	}
	
	/**
	 * 
	 */
	public void createNewClub() {
		final String methodName = "createNewClub()";
		debugEnter(logger, methodName);
		this.selectedUserTeam = null;
		userTeams = null;
		selectedUserClub = new Club();
		showClubEditPanel();
	}
	
	/**
	 * 
	 */
	public void createNewTeam() {
		final String methodName = "createNewTeam()";
		debugEnter(logger, methodName);
		selectedUserTeam = new Team();
		showTeamEditPanel();
	}
	
	/**
	 * Listener für die Selektion in der Vereinstabelle
	 * @param event
	 */
	public void onClubRowSelect(SelectEvent event) {
		final String methodName = "onClubRowSelect()";
		debugEnter(logger, methodName, "params: ", event);
		setSelectedUserClub((Club)event.getObject());
		showTeamPanel();
		
    }
	
	/**
	 * Listener für die Selektion in der Mannschaftstabelle
	 * @param event
	 */
	public void onTeamRowSelect(SelectEvent event) {
		final String methodName = "onTeamRowSelect()";
		debugEnter(logger, methodName, "params: ", event);
		onTeamEdit((Team)event.getObject());
		showTeamEditPanel();
    }
	
	/**
	 * 
	 * @param selClub
	 */
	public void onClubEdit(Club selClub) {
		final String methodName = "onClubEdit()";
		debugEnter(logger, methodName, "params: ", selClub);
		selectedUserClub = selClub;
		showClubEditPanel();
	}
	
	/**
	 * 
	 * @param t
	 */
	public void onTeamEdit(Team t) {
		final String methodName = "onTeamEdit()";
		debugEnter(logger, methodName, "params: ", t);
		selectedUserTeam = t;
		selectedSportTypId = selectedUserTeam.getSportType().getId();
		showTeamEditPanel();
	}
 
	/**
	 * Listener für die De-Selektion in der Vereinstabelle
	 * @param event
	 */
    public void onClubRowUnSelect(UnselectEvent event) {
    	final String methodName = "onClubRowUnSelect()";
    	debugEnter(logger, methodName, "params: ", event);
    	selectedUserClub = null;
    	resetShowPanel();
    }
    
    /**
     * Listener für die De-Selektion in der Mannschaftstabelle 
     * @param event
     */
    public void onTeamRowUnSelect(UnselectEvent event) {
    	final String methodName = "onTeamRowUnSelect()";
    	debugEnter(logger, methodName, "params: ", event);
    	selectedUserTeam = null;
    	selectedSportTypId = null;
    	resetShowPanel();
    }
	
    /**
     * Speichert den Verein
     */
	public void saveClub() {
		final String methodName = "saveClub()";
		debugEnter(logger, methodName);
		if (selectedUserClub != null && currentUser != null) {
			try {
				clubService.saveClub(selectedUserClub, currentUser);
				addMessage(FacesMessage.SEVERITY_INFO, "Änderung erfolgreich gespeichert");
				resetShowPanel();;
			} catch (ScoutheroException e) {
				addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
			}
		}
	}
	
	/**
	 * Speichert die Mannschaft
	 */
	public void saveTeam() {
		final String methodName = "saveTeam()";
		debugEnter(logger, methodName);
		if (selectedUserTeam != null && selectedUserClub != null) {
			try {
				clubService.saveTeam(selectedUserTeam, selectedUserClub, selectedSportTypId);
				addMessage(FacesMessage.SEVERITY_INFO, "Änderung erfolgreich gespeichert");
				resetShowPanel();
			} catch (ScoutheroException e) {
				addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
			}
		}
	}
	
	/**
	 * 
	 * @param selClub
	 */
	public void showTeamPanel(Club selClub) {
		final String methodName = "showTeamPanel()";
		debugEnter(logger, methodName, "params: ", selClub);
		this.setSelectedUserClub(selClub);
		showTeamPanel();
	}
	
	/**
	 * Gibt alle Sportarten aus der DB zurück
	 * @return Liste der Sportarten
	 */
	public List<SportType> getAlleSportarten() {
		try {
			return clubService.getAllKindOfSport();
		} catch (ScoutheroException e) {
			logger.error("",e);
			return null;
		}
	}
	
	/**
	 * Löscht den Club aus der DB
	 * @param team
	 */
	public void deleteClub(Club club) {
		if(club != null) {
			try {
				clubService.deleteClub(club);
				this.selectedUserTeam = null;
				this.selectedSportTypId = null;
				addMessage(FacesMessage.SEVERITY_INFO, "Verein wurde erfolgreich gelöscht.");
			} catch (ScoutheroException e) {
				addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
			}
			
		}
	}
	
	/**
	 * Löscht das Team aus der DB
	 * @param team
	 */
	public void deleteTeam(Team team) {
		if(team != null) {
			try {
				clubService.deleteTeam(team);
				this.selectedUserTeam = null;
			} catch (ScoutheroException e) {
				addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
			}
			
		}
	}
	
	private void resetShowPanel() {
		showClubPanel = false;
		showTeamPanel = false;
		showTeamEditPanel = false;
	}
	
	private void showClubEditPanel() {
		showClubPanel = true;
		showTeamPanel = false;
		showTeamEditPanel = false;
	}
	
	private void showTeamPanel() {
		showClubPanel = false;
		showTeamPanel = true;
		showTeamEditPanel = false;
	}
	
	private void showTeamEditPanel() {
		showClubPanel = false;
		showTeamPanel = true;
		showTeamEditPanel = true;
	}
	/** GETTER UND SETTER **/
	
	public List<String> getAltersKlassen() {
		return Arrays.asList(Defs.ALTERSKLASSEN);
	}
	
	public List<String> getLeistungsklassen() {
		return Arrays.asList(Defs.LEISTUNGSKLASSEN);
	}
	
	public Club getSelectedUserClub() {
		return selectedUserClub;
	}

	public void setSelectedUserClub(Club selectedUserClub) {
		this.selectedUserClub = selectedUserClub;
		if (this.selectedUserClub != null) {
			this.userTeams = loadClubTeams();
		}
	}
	
	public Team getSelectedUserTeam() {
		return selectedUserTeam;
	}

	public void setSelectedUserTeam(Team selectedUserTeam) {
		if (selectedUserTeam != null)
			this.selectedUserTeam = selectedUserTeam;
	}

	/**
	 * Soll Panel angezeigt werden?
	 * @return
	 */
	public boolean getShowClubPanel(){
		return showClubPanel;
	}
	public void setShowClubPanel(boolean b) {
		showClubPanel = b;
	}
	/**
	 * Soll Panel angezeigt werden?
	 * @return
	 */
	public boolean getShowTeamPanel() {
		return showTeamPanel;
	}
	/**
	 * Soll Panel angezeigt werden?
	 * @return
	 */
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

	/**
	 * @return the selectedSportTypId
	 */
	public Long getSelectedSportTypId() {
		return selectedSportTypId;
	}

	/**
	 * @param selectedSportTypId the selectedSportTypId to set
	 */
	public void setSelectedSportTypId(Long selectedSportTypId) {
		this.selectedSportTypId = selectedSportTypId;
	}
}
