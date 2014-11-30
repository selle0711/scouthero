package de.scouthero.managedBeans;

import static de.scouthero.util.LogUtil.debugEnter;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;
import org.primefaces.event.FlowEvent;

import de.scouthero.beans.Club;
import de.scouthero.beans.User;
import de.scouthero.handler.ViewScopedHandler;
import de.scouthero.services.ClubService;
import de.scouthero.services.UserServiceSB;
import de.scouthero.util.ScoutheroException;
import de.scouthero.util.StringWorks;

/**
 * 
 * @author Selle
 *
 */
@SessionScoped
@ManagedBean
public class UserHandler extends ViewScopedHandler {
	private static final long serialVersionUID = -8432316080384576474L;

	private static final Logger logger = Logger.getLogger(UserHandler.class);
	
	@EJB
	private UserServiceSB userService; 
	@EJB
	private ClubService clubService;
	
	private User user;
	private String login;
	private String password;
	private String repeatedPWD;
	private boolean skip;  
	private boolean loginFailed = false;
	private boolean loggedIn = false;
	
	private Club selectedUserClub;
	private boolean showClubPanel = false;

	private List<Club> userClubs;
	
	public UserHandler() {
		loadUserClubs();
	}
	
	private void loadUserClubs() {
		final String methodName = "getUserClubs()";
		debugEnter(logger, methodName);
		if (user != null) {
			try {
				userClubs = clubService.getUserClubs(user);
			} catch (ScoutheroException e) {
				logger.error("", e);
			}
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

	public Club getSelectedUserClub() {
		return selectedUserClub;
	}

	public void setSelectedUserClub(Club selectedUserClub) {
		this.selectedUserClub = selectedUserClub;
	}
	
	public boolean getShowClubPanel(){
		return showClubPanel;
	}
	public void setShowClubPanel(boolean b) {
		showClubPanel = b;
	}
	public List<Club> getUserClub() {
		return userClubs;
	}
}
