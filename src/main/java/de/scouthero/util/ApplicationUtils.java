package de.scouthero.util;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public class ApplicationUtils {
	
	public static void showErrorMessageInClient(String s, UIComponent component, FacesContext context) {
		FacesMessage message = new FacesMessage(s);
		context.addMessage(component.getClientId(context), message);
	}
	
}
