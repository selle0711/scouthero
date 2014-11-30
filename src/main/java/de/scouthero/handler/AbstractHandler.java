package de.scouthero.handler;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public abstract class AbstractHandler implements Serializable{

	private static final long serialVersionUID = -5023165064656606164L;

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
