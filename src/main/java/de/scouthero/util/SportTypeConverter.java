package de.scouthero.util;

import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.scouthero.beans.SportType;

@FacesConverter(forClass = SportType.class, value="sportType")
@ManagedBean(name="sportTypeConverter")
public class SportTypeConverter implements Converter {

	@PersistenceContext
    private EntityManager entityManager;
	
	public SportTypeConverter() {

	}
	
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
    	 return (submittedValue == null) ? null : entityManager.find(SportType.class, Long.parseLong(submittedValue));
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
    	return (String) ((value == null) ? null : value);
    }
}
