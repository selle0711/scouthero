package de.scouthero.managedBeans;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.primefaces.event.FlowEvent;

import de.scouthero.beans.User;
import de.scouthero.util.EMF;
import de.scouthero.util.StringWorks;

@ManagedBean
@SessionScoped
public class UserHandler {
	private static final Logger logger = Logger.getLogger(UserHandler.class);
	
	private EntityManager em;
	
	private User user;
	private String login;
	private String password;
	private String repeatedPWD;
	private boolean skip;  
	private boolean loginFailed = false;
	private boolean loggedIn = false;
	
	public UserHandler() {
		if (user == null)
			user = new User();
		em = EMF.createEntityManager();
	}
	
	public String login() {
		logger.info("--> login()");
		if (user != null && user.getLoginName() != null) {
			setLogin(user.getLoginName());
			setPassword(user.getPassword());
		}
			
		try {
			Query query = em.createNamedQuery("User.findByLoginAndPass");
			query.setParameter("loginName", login);
			if (user == null || user.getPassword() == null) { // in user.password is schon md5 drin 
				logger.info(login+": hashing password: "+password);
				query.setParameter("password", StringWorks.md5(password));
				logger.info(login+": hashing password: "+password);
			}
			else
				query.setParameter("password", password);
			logger.info(StringWorks.md5(password));
			
			@SuppressWarnings("unchecked")
			List<User> res = query.getResultList();
			logger.info("Gefundene User: "+res.size());
			if (res != null && res.size() == 1) {
				user = res.get(0);
				loginFailed = false;
				loggedIn = true;
				logger.info("login=true -> return: /index.xhtml?faces-redirect=true");
				addMessage(FacesMessage.SEVERITY_INFO, "Benutzer erfolgreich angemeldet");
				return "/index.xhtml?faces-redirect=true";
			}
			addMessage(FacesMessage.SEVERITY_ERROR, "Benutzer und Passwort stimmen nicht überein.");
			loginFailed = true;
			logger.info("login=false");
		} catch (Exception e) {
//			ApplicationUtils.showErrorMessageInClient(e.getMessage(), registerButton,FacesContext.getCurrentInstance());
			logger.error("",e);
			addMessage(FacesMessage.SEVERITY_FATAL, e.getMessage());
		}
		return null;
	}
	
	public String logout() {
		addMessage( FacesMessage.SEVERITY_INFO, "Benutzer wurde erfolgreich abgemeldet");
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/index.xhtml?faces-redirect=true";
	}
	
	public void checkLoggedIn(ComponentSystemEvent cse) {
		FacesContext context = FacesContext.getCurrentInstance();
		if (user == null || user.getLoginName() == null) {
			context.getApplication().getNavigationHandler().handleNavigation(context, null, "/login.xhtml?faces-redirect=true");
		}
	}

	public String register() {
		// 1. schon daten in der DB?
		Query query = em.createNamedQuery("User.findByEmailOrLogin");
		query.setParameter("email", user.getEmail());
		query.setParameter("loginName", user.getLoginName());
		@SuppressWarnings("unchecked")
		List<User> ll = query.getResultList();
		if (ll == null || ll.isEmpty()) {
			try {
				logger.info("register new User: "+user.getLoginName()+" -> "+ user.getPassword());
				// 2. ab in die DB
				em.getTransaction().begin();
				// md5 hashing
				String md5Hash = StringWorks.md5(user.getPassword());
				user.setPassword(md5Hash);
				em.persist(user);
				em.getTransaction().commit();
				addMessage(FacesMessage.SEVERITY_INFO, "Registrierung war erfolgreich.");
				return login();
			} catch (Exception e) {
				logger.error("",e);
				em.getTransaction().setRollbackOnly();
				addMessage(FacesMessage.SEVERITY_FATAL, e.getMessage());
			}
		} else {
			logger.error("user or mail already exists: "+user.getLoginName()+" / "+user.getEmail());
			addMessage( FacesMessage.SEVERITY_ERROR, "Angegebene Benutzer oder Email-Adresse sind bereits vergeben.");

		}
		return null;		
	}
	
	public void updateUser() throws Exception {
		if (user == null)
			throw new Exception("Kein User angemeldet");
		em.getTransaction().begin();
        em.merge(user);
        em.getTransaction().commit();
        addMessage(FacesMessage.SEVERITY_INFO, "Benutzer erfolgreich geändert");
	}
	
	public String deleteUser() throws Exception {
		if (user == null)
			throw new Exception("Kein User angemeldet");
		em.getTransaction().begin();
        em.remove(user);
        em.getTransaction().commit();
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
	private void addMessage(Severity severity, String summary) {  
        FacesMessage message = new FacesMessage(severity, summary,  null);  
        FacesContext.getCurrentInstance().addMessage(null, message);  
    }  
}
