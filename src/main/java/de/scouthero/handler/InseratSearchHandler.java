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
import de.scouthero.services.InseratService;
import de.scouthero.util.Defs.AccountTyp;
import de.scouthero.util.ScoutheroException;

@ViewScoped
@ManagedBean
public class InseratSearchHandler extends ViewScopedHandler {

	private static final long serialVersionUID = 6441340349371731128L;
	private static Logger logger = Logger.getLogger(InseratHandler.class);
	private List<Inserat> inserate;
	private AccountTyp selectedSearchOption;
	private Inserat selectedInserat;
	
	@EJB
	private InseratService inseratService;
	
	public InseratSearchHandler() {
		selectedSearchOption = AccountTyp.VEREIN; 
	}
	
	@PostConstruct
	private void loadInserate() {
		final String methodName = "loadInserate()";
		debugEnter(logger, methodName);
		try {
			inserate = inseratService.loadInserate(selectedSearchOption);
		} catch (ScoutheroException e) {
			addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
		}
	}
	
	public void approve() {
		if (selectedInserat != null && currentUser != null) {
			try {
				inseratService.contactUserInserat(selectedInserat, currentUser);
			} catch (ScoutheroException e) {
				addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
			}
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
		if (currentUser != null) {
			try {
				inseratService.viewDetails(selectedInserat);
			} catch (ScoutheroException e) {
				addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
			}
		}
    }
	
	public void linkToInteresting() {
		final String methodName = "linkToInteresting()";
		debugEnter(logger, methodName);
		if (selectedInserat != null && currentUser != null) {
			try {
				inseratService.linkToInterestingPeople(selectedInserat, currentUser);
			} catch (ScoutheroException e) {
				addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
			}
		}
	}
	
	public void unLinkToInteresting() {
		final String methodName = "unLinkToInteresting()";
		debugEnter(logger, methodName);
		if (selectedInserat != null && currentUser != null) {
			try {
				inseratService.unLinkToInterestingPeople(selectedInserat, currentUser);
			} catch (ScoutheroException e) {
				addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
			}
		}
	}
	
	public boolean isAlreadyLinkedToInserat(final Inserat inserat) {
		if (currentUser != null) {
			try {
				return inseratService.isAlreadyLinkedToInterestingPeople(inserat, currentUser);
			} catch (ScoutheroException e) {
				addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
			}
		}
		return false;
	}
	
	public boolean playerHasTransferContact(Inserat inserat) {
		try {
			return inseratService.playerHasTransferContact(inserat, currentUser);
		} catch (ScoutheroException e) {
			addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
		}
		return false;
	}
	
	public boolean playerHasTransferContact() {
		return playerHasTransferContact(selectedInserat);
	}
	
	/**
	 * Listener für die Deselektion in der Inserattabelle
	 * @param event
	 */
	public void onRowUnSelect(SelectEvent event) {
		final String methodName = "onRowUnSelect()";
		debugEnter(logger, methodName, "params: ", event);
		this.selectedInserat = null;
    }

	public int getSelectedSearchOption () {
		if (selectedSearchOption != null) {
			return selectedSearchOption.value();
		}
		return AccountTyp.VEREIN.value();
	}
	
	public void setSelectedSearchOption(int value) {
		switch (value) {
		case 2:
			selectedSearchOption = AccountTyp.SPIELER;
			break;
			
		default:
			selectedSearchOption = AccountTyp.VEREIN;
			break;
		}
	}
	
	/**
	 * @return the inserate
	 */
	public List<Inserat> getInserate() {
		return inserate;
	}

	/**
	 * @param inserate the inserate to set
	 */
	public void setInserate(List<Inserat> inserate) {
		this.inserate = inserate;
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
		this.selectedInserat = selectedInserat;
	}
}
