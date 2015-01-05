package de.scouthero.handler;

import static de.scouthero.util.LogUtil.debugEnter;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import de.scouthero.beans.User;
import de.scouthero.services.UserServiceSB;
import de.scouthero.util.ScoutheroException;

@ViewScoped
@ManagedBean
public class ProfileHandler extends ViewScopedHandler {

	private static final long serialVersionUID = -2954554206416261097L;
	private static final Logger logger = Logger.getLogger(ProfileHandler.class);
	
	@EJB
	private UserServiceSB userService;
	
	public ProfileHandler() {
		final String methodName="ProfileHandler()";
		debugEnter(logger, methodName);
	}
	
	public void save() {
		try {
			userService.updateUser(currentUser);
		} catch (ScoutheroException e) {
			addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
		}
		addMessage(FacesMessage.SEVERITY_INFO, "Benutzer erfolgreich geändert");
	}
	
	public void deleteUser() {
		if (currentUser != null) {
			try {
				userService.deleteUser(currentUser);
			} catch (ScoutheroException e) {
				addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
			}
		}
	}
	
	/** Getter und Setter **/
	
	public User getCurrentUser() {
		return this.currentUser;
	}
}
