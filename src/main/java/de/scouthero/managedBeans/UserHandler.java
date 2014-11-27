package de.scouthero.managedBeans;

import static de.scouthero.util.LogUtil.debugEnter;
import static de.scouthero.util.LogUtil.debugExit;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.apache.log4j.Logger;
import org.primefaces.event.FlowEvent;

import de.scouthero.beans.User;
import de.scouthero.services.UserServiceSB;
import de.scouthero.util.ScoutheroException;
import de.scouthero.util.StringWorks;
import de.scouthero.util.ScoutheroException.ERROR;

/**
 * 
 * @author Selle
 *
 */
@SessionScoped
@ManagedBean
public class UserHandler extends AbstractHandler {
	private static final Logger logger = Logger.getLogger(UserHandler.class);
	
	@EJB
	private UserServiceSB userService; 
	
	private User user;
	private String login;
	private String password;
	private String repeatedPWD;
	private boolean skip;  
	private boolean loginFailed = false;
	private boolean loggedIn = false;

	public UserHandler() {
		
	}
	
	/**
	 * 
	 * @return
	 */
	public String login() {
		final String methodName="login()";
		debugEnter(logger, methodName);
		
		try {
			user = userService.getUserByNameAndPassword(login, StringWorks.md5(password));
			loginFailed = false;
			loggedIn = true;
			addMessage(FacesMessage.SEVERITY_INFO, "Benutzer erfolgreich angemeldet");
			debugExit(logger, methodName);
			return "index.xhtml?faces-redirect=true";
		} catch (ScoutheroException e) {
			if (e.getErrorCode() == ERROR.ENTITY_NOT_FOUND) {
				addMessage(FacesMessage.SEVERITY_ERROR, "Keine Übereinstimmung gefunden. Entweder Sie versuchen die Anmeldung erneut, oder Sie nutzen die Passwort vergessen-Funktion.");
			}
		} catch (Exception e) {
			addMessage(FacesMessage.SEVERITY_FATAL, e.getMessage()); 
		}
		return null;

	}
	
	/**
	 * 
	 * @return
	 */
	public String logout() {
		final String methodName = "logout()";
		debugEnter(logger, methodName);
		user = null;
		loggedIn = false;
		addMessage( FacesMessage.SEVERITY_INFO, "Benutzer wurde erfolgreich abgemeldet");
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "index.xhtml?faces-redirect=true";
	}
	
	public void checkLoggedIn(ComponentSystemEvent cse) {
		FacesContext context = FacesContext.getCurrentInstance();
		if (user == null || user.getLoginName() == null) {
			context.getApplication().getNavigationHandler().handleNavigation(context, null, "/login.xhtml?faces-redirect=true");
		}
	}

	public String register() {
		// 1. schon daten in der DB?
//		Query query = em.createNamedQuery("User.findByEmailOrLogin");
//		query.setParameter("email", user.getEmail());
//		query.setParameter("loginName", user.getLoginName());
//		@SuppressWarnings("unchecked")
//		List<User> ll = query.getResultList();
//		if (ll == null || ll.isEmpty()) {
//			try {
//				logger.info("register new User: "+user.getLoginName()+" -> "+ user.getPassword());
//				// 2. ab in die DB
//				em.getTransaction().begin();
//				// md5 hashing
//				String md5Hash = StringWorks.md5(user.getPassword());
//				user.setPassword(md5Hash);
//				em.persist(user);
//				em.getTransaction().commit();
//				addMessage(FacesMessage.SEVERITY_INFO, "Registrierung war erfolgreich.");
//				return login();
//			} catch (Exception e) {
//				logger.error("",e);
//				em.getTransaction().setRollbackOnly();
//				addMessage(FacesMessage.SEVERITY_FATAL, e.getMessage());
//			}
//		} else {
//			logger.error("user or mail already exists: "+user.getLoginName()+" / "+user.getEmail());
//			addMessage( FacesMessage.SEVERITY_ERROR, "Angegebene Benutzer oder Email-Adresse sind bereits vergeben.");
//
//		}
		return null;		
	}
	
	public void updateUser() throws Exception {
		if (user == null)
			throw new Exception("Kein User angemeldet");
		userService.updateUser(user);
        addMessage(FacesMessage.SEVERITY_INFO, "Benutzer erfolgreich geändert");
	}
	
	public String deleteUser() throws Exception {
		if (user == null)
			throw new Exception("Kein User angemeldet");
		userService.deleteUser(user.getId());	
		return logout();
	}
	
	public String onFlowProcess(FlowEvent event) {  
        logger.info("Current wizard step:" + event.getOldStep());  
        logger.info("Next step:" + event.getNewStep());  
          
        if(skip) {  
            skip = false;   //reset in case user goes back  
            return "confirm";  
        }  
        else {  
            return event.getNewStep();  
        }  
    }  
	
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
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

	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return the loginFailed
	 */
	public boolean isLoginFailed() {
		return loginFailed;
	}

	/**
	 * @param loginFailed the loginFailed to set
	 */
	public void setLoginFailed(boolean loginFailed) {
		this.loginFailed = loginFailed;
	}

	/**
	 * @return the loggedIn
	 */
	public boolean isLoggedIn() {
		return loggedIn;
	}

	/**
	 * @param loggedIn the loggedIn to set
	 */
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	
	public boolean isSkip() {  
        return skip;  
    }  
  
    public void setSkip(boolean skip) {  
        this.skip = skip;  
    }  
	    
    /**
	 * @return the repeatedPWD
	 */
	public String getRepeatedPWD() {
		return repeatedPWD;
	}

	/**
	 * @param repeatedPWD the repeatedPWD to set
	 */
	public void setRepeatedPWD(String repeatedPWD) {
		this.repeatedPWD = repeatedPWD;
	}
	
	public String getRegisterDate() {
		if (user!=null && user.getRegisterDate()!=null) {
			return StringWorks.formatDate(user.getRegisterDate());
		}
		return null;
	}
	
	public boolean isAccountForClub() {
		return user!= null && user.getType() == 2;
	}

	public String getType() {
		return user!=null && user.getType() == 1? "Spieleraccount":"Vereinsaccount";
	}
}
