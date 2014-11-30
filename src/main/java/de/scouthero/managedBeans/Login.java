package de.scouthero.managedBeans;

import static de.scouthero.util.LogUtil.debugEnter;
import static de.scouthero.util.LogUtil.debugExit;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

import de.scouthero.beans.User;
import de.scouthero.handler.AbstractHandler;
import de.scouthero.services.UserServiceSB;
import de.scouthero.util.ScoutheroException;
import de.scouthero.util.ScoutheroException.ERROR;
import de.scouthero.util.StringWorks;

@SessionScoped
@ManagedBean
public class Login extends AbstractHandler {

	private static final long serialVersionUID = 3344803306950684531L;

	private Logger logger = Logger.getLogger(Login.class);
	
	@EJB
	private UserServiceSB userService;
	
	private String username;
	private String password;
	
	private User user;

	public String login() {
		final String methodName = "login()";
		debugEnter(logger, methodName);
		try {
			user = userService.getUserByNameAndPassword(username, StringWorks.md5(password));
			return "index.xhtml?faces-redirect=true";
			
		} catch (ScoutheroException e) {
			if (e.getErrorCode() == ERROR.ENTITY_NOT_FOUND) {
				addMessage(FacesMessage.SEVERITY_ERROR, "Keine Übereinstimmung gefunden. Entweder Sie versuchen die Anmeldung erneut, oder Sie nutzen die Passwort vergessen-Funktion.");
			}
		} catch (Exception e) {
			addMessage(FacesMessage.SEVERITY_FATAL, e.getMessage()); 
		}
		debugExit(logger, methodName, "user=", user);
		return null;
	}

	public String logout() {
		user = null;
		return "index.xhtml?faces-redirect=true";
	}

	public boolean isLoggedIn() {
		return user != null;
	}

	public User getUser() {
		return user;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public String getType() {
		return user!=null && user.getType() == 1? "Spieleraccount":"Vereinsaccount";
	}
}
