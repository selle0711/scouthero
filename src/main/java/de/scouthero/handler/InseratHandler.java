package de.scouthero.handler;

import static de.scouthero.util.LogUtil.debugEnter;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;

import de.scouthero.beans.Inserat;
import de.scouthero.beans.SportType;
import de.scouthero.beans.Team;
import de.scouthero.beans.Transfer;
import de.scouthero.services.ClubService;
import de.scouthero.services.InseratService;
import de.scouthero.util.ScoutheroException;

/**
 * Handler für den View inserat.xhtml
 * @author rgesell
 *
 */
@ViewScoped
@ManagedBean
public class InseratHandler extends ViewScopedHandler{

	/**
	 * Serial-ID
	 */
	private static final long serialVersionUID = 5821419496144048728L;
	
	private static final Logger logger = Logger.getLogger(InseratHandler.class);
	
	@EJB
	private InseratService inseratService;
	@EJB
	private ClubService clubService;
	
	private List<Inserat> inserate;
	private List<Team> teams;
	private List<SportType> sportTypes;
	private Inserat selectedInserat;
	private boolean showAdEditPanel = false;
	private Long selectedUserTeam;
	private Long selectedSportType;
	
	public InseratHandler() {
	}
	
	/**
	 * Lädt alle Teams des Benutzers
	 */
	@PostConstruct
	private void init() {
		final String methodName = "init()";
		debugEnter(logger, methodName);
		try {
			teams = inseratService.getUserTeamList(currentUser);
			inserate = inseratService.loadUserInserate(currentUser);
			sportTypes = clubService.getAllKindOfSport();
		} catch (ScoutheroException e) {
			addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
		}
	}
	
	/**
	 * Listener für die Selektion in der Inserattabelle
	 * @param event
	 */
	public void onRowSelect(SelectEvent event) {
		final String methodName = "onRowSelect()";
		debugEnter(logger, methodName, "params: ", event);
		setSelectedInserat((Inserat)event.getObject());
		showAdEditPanel = true;
    }
	
	/**
	 * Listener für die Deselektion in der Inserattabelle
	 * @param event
	 */
	public void onRowUnSelect(SelectEvent event) {
		final String methodName = "onRowUnSelect()";
		debugEnter(logger, methodName, "params: ", event);
		this.selectedInserat = null;
		this.selectedUserTeam = null;
		showAdEditPanel = false;
    }
	
	public void createNewInserat() {
		final String methodName = "createNewInserat()";
		debugEnter(logger, methodName);
		this.showAdEditPanel=true;
		this.selectedInserat = new Inserat();
		setSelectedInserat(selectedInserat);
	}
	
	public void saveInserat() {
		final String methodName = "saveInserat()";
		debugEnter(logger, methodName);
		if (selectedInserat != null && currentUser != null) {
			try {
				inseratService.saveInserat(selectedInserat, currentUser, selectedUserTeam);
				addMessage(FacesMessage.SEVERITY_INFO, "Änderung erfolgreich gespeichert");
				showAdEditPanel = false;
			} catch (ScoutheroException e) {
				addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
			}
		}
	}
	
	public void deleteInserat(Inserat inserat) {
		final String methodName = "deleteInserat()";
		debugEnter(logger, methodName, "params: ", inserat);
		setSelectedInserat(inserat);
		try {
			inseratService.deleteInserat(inserat);
			addMessage(FacesMessage.SEVERITY_INFO, "Inserat erfolgreich gelöscht.");
			showAdEditPanel = false;
			selectedInserat = null;
			selectedUserTeam = null;
		} catch (ScoutheroException e) {
			addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
		}
	}
	
	public void disApprove(Transfer transfer) {
		final String methodName="disApprove()";
		debugEnter(logger, methodName, "params: ", transfer);
		if (transfer == null) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Transfer is null");
		}
		try {
			inseratService.disApprove(transfer);
		} catch (ScoutheroException e) {
			addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
		}
	}
	
	public void approve(Transfer transfer) {
		final String methodName="approve()";
		debugEnter(logger, methodName, "params: ", transfer);
		if (transfer == null) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Transfer is null");
		}
		try {
			inseratService.approve(transfer);
		} catch (ScoutheroException e) {
			addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
		}
	}
	
	/** GETTER & SETTER */
	
	/**
	 * @return alle Teams des Benutzers 
	 */
	public List<Team> getTeams() {
		return this.teams;
	}

	/**
	 * @return Alle Inserate des Benutzers
	 */
	public List<Inserat> getInserate() {
		return this.inserate;
	}

	/**
	 * @return the selectedInserat
	 */
	public Inserat getSelectedInserat() {
		return selectedInserat;
	}

	/**
	 * @param selectedInserat the selectedInserat to set
	 */
	public void setSelectedInserat(Inserat selectedInserat) {
		if (selectedInserat != null) {
			this.selectedInserat = selectedInserat;
			if (this.selectedInserat.getTeam() != null)
				this.selectedUserTeam = this.selectedInserat.getTeam().getId();
		}
	}

	/**
	 * @return the showAdEditPanel
	 */
	public boolean isShowAdEditPanel() {
		return showAdEditPanel;
	}

	/**
	 * @return the selectedUserTeam
	 */
	public Long getSelectedUserTeam() {
		return selectedUserTeam;
	}

	/**
	 * @param selectedUserTeam the selectedUserTeam to set
	 */
	public void setSelectedUserTeam(Long selectedUserTeam) {
		this.selectedUserTeam = selectedUserTeam;
	}

	/**
	 * @return the selectedSportType
	 */
	public Long getSelectedSportType() {
		return selectedSportType;
	}

	/**
	 * @param selectedSportType the selectedSportType to set
	 */
	public void setSelectedSportType(Long selectedSportType) {
		this.selectedSportType = selectedSportType;
	}

	/**
	 * @return the sportTypes
	 */
	public List<SportType> getSportTypes() {
		return sportTypes;
	}
 }
