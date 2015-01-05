package de.scouthero.validator;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("birthdateValidator")
public class BirthdateValidator implements Validator {
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		
		final Date now = new Date();
		
			Date input = (Date)value;
			
			final long diff = now.getTime() - input.getTime();
			final long days = TimeUnit.MILLISECONDS.toDays(diff);
			
			if (days < (12 * 365)) {
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						"User muss mindestens 12 Jahre alt sein",
						"User muss mindestens 12 Jahre alt sein"));
			}
		
	}
}
