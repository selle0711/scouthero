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
	private Club selectedUserClub;
	private boolean showClubPanel;

	
	public MyClubHandler() {
		showClubPanel = false;
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
	
	public void createNewClub() {
		final String methodName = "createNewClub()";
		debugEnter(logger, methodName);
		selectedUserClub = new Club();
		showClubPanel = true;
	}
	public void changeClub() {
		final String methodName = "changeClub()";
		debugEnter(logger, methodName);
		showClubPanel = true;
	}
	
	public void onClubRowSelect(SelectEvent event) {
		final String methodName = "onClubRowSelect()";
		debugEnter(logger, methodName);
    }
 
    public void onClubRowUnSelect(UnselectEvent event) {
    	final String methodName = "onClubRowUnSelect()";
		debugEnter(logger, methodName);
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
	
	/** GETTER UND SETTER **/
	
	
	public Club getSelectedUserClub() {
		return selectedUserClub;
	}

	public void setSelectedUserClub(Club selectedUserClub) {
		this.selectedUserClub = selectedUserClub;
	}
	
	public boolean getShowClubPanel(){
		return showClubPanel;
	}
	public void setShowClubPanel(boolean b) {
		showClubPanel = b;
	}
	public List<Club> getUserClub() {
		return userClubs;
	}
}
