package de.scouthero.handler;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

/**
 * Abstrakte BasisKlasse für alle ManagedBeans
 * @author rgesell
 *
 */
public abstract class AbstractHandler implements Serializable{

	private static final long serialVersionUID = -5023165064656606164L;
	protected static final String REDIRECT_INDEX = "index.xhtml?faces-redirect=true";
	protected static final String REDIRECT_MYCLUB = "userClub.xhtml?faces-redirect=true";
	
	/**
	 * Fügt einer p:messages mit der id="messages" eine Message hinzu. Die p:message muss innerhalb der h:form sein
	 * 
	 * @param severity
	 * 			FacesMessage.Severity
	 * @param summary die Meldung
	 */
	protected final void addMessage(Severity severity, String summary) {  
        FacesMessage message = new FacesMessage(severity, summary,  null);  
        FacesContext.getCurrentInstance().addMessage("messages", message);  
    }  
	
}
