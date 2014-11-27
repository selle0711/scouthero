package de.scouthero.managedBeans;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public abstract class AbstractHandler {

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
