package de.scouthero.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("emailvalidierer")
public class EmailValidator implements Validator{
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException{
		if (!((String)value).matches(".+@.+\\..+")) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Fehlerhafte E-Mail-Syntax",
					"Keine gültige E-Mail Adresse angegeben"));
		}
	}
}
