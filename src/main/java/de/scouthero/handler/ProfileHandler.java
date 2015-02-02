package de.scouthero.handler;

import static de.scouthero.util.LogUtil.debugEnter;

import java.io.ByteArrayInputStream;
import java.security.NoSuchAlgorithmException;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import de.scouthero.beans.User;
import de.scouthero.services.UserServiceSB;
import de.scouthero.util.ScoutheroException;
import de.scouthero.util.StringWorks;

@ViewScoped
@ManagedBean
public class ProfileHandler extends ViewScopedHandler {

	private static final long serialVersionUID = -2954554206416261097L;
	private static final Logger logger = Logger.getLogger(ProfileHandler.class);
	
	@EJB
	private UserServiceSB userService;
	
	private User newUser = new User();
	private StreamedContent userImage;
	private String repeatedPWD;
	
	public ProfileHandler() {
		final String methodName="ProfileHandler()";
		debugEnter(logger, methodName);
		newUser.setType(1);
	}
	
	private StreamedContent buildUserImage(User currentUser) {
		return new DefaultStreamedContent(new ByteArrayInputStream(currentUser.getImage()), currentUser.getImageType(), currentUser.getImageName());
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
	
	public void handleFileUpload(FileUploadEvent event) {
		
		byte[] bFile = new byte[(int) event.getFile().getSize()];
		try {
			event.getFile().getInputstream().read(bFile);
			event.getFile().getInputstream().close();
			currentUser.setImage(bFile);
			currentUser.setImageName(event.getFile().getFileName());
			currentUser.setImageType(event.getFile().getContentType());
			userService.updateUser(currentUser);
			userImage = buildUserImage(currentUser);
		} catch (Exception e) {
			addMessage(FacesMessage.SEVERITY_FATAL, e.getMessage());
		}
		
	}
	
	/**
	 * 
	 * @return
	 */
	public void register() {
		if (newUser == null)
			addMessage(FacesMessage.SEVERITY_FATAL, "Kein User-Objekt vorhanden");
		// 1. check if name or mail already exist
		if (userService.isUserOrMailAlreadyExist(newUser.getLoginName(), newUser.getEmail()) == false) {
			//2. password check
			if (newUser.getPassword().equals(repeatedPWD)) {
				// md5 hashing
				try {
					String md5Hash = StringWorks.md5(newUser.getPassword());
					newUser.setPassword(md5Hash);
					userService.updateUser(newUser);
					// leere Userdaten
					newUser = new User();
					addMessage(FacesMessage.SEVERITY_INFO, "Account wurde erfolgreich angelegt. Sie können Sich nun anmelden.");
				} catch (NoSuchAlgorithmException e) {
					addMessage(FacesMessage.SEVERITY_FATAL, e.getMessage());
				} catch (ScoutheroException e) {
					addMessage(FacesMessage.SEVERITY_FATAL, e.getMessage());
				}
			} else {
				addMessage(FacesMessage.SEVERITY_ERROR, "Die Passwörter stimmen nicht überein.");
			}
		} else {
			addMessage(FacesMessage.SEVERITY_ERROR, "User oder E-Mail Adresse bereits in unserer Datenbank vorhanden.");
		}
	}
	
	/** Getter und Setter **/
	
	public User getCurrentUser() {
		return this.currentUser;
	}
	
	public User getNewUser() {
		return this.newUser;
	}
	
	public void setNewUser(User user) {
		this.newUser = user;
	}

	/**
	 * @return the userImage
	 */
	public StreamedContent getUserImage() {
		if (currentUser != null && currentUser.getImage() != null && currentUser.getImageName() != null) {
			userImage = buildUserImage(currentUser);
		}
		return userImage;
	}

	/**
	 * @param repeatedPWD the repeatedPWD to set
	 */
	public void setRepeatedPWD(String repeatedPWD) {
		this.repeatedPWD = repeatedPWD;
	}

	/**
	 * @return the repeatedPWD
	 */
	public String getRepeatedPWD() {
		return repeatedPWD;
	}
}
