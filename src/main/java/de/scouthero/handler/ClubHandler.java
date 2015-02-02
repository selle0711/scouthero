package de.scouthero.handler;

import static de.scouthero.util.LogUtil.debugEnter;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import org.apache.log4j.Logger;

import de.scouthero.beans.Club;
import de.scouthero.services.ClubService;

@ViewScoped
@ManagedBean
public class ClubHandler extends AbstractHandler{

	private final static Logger logger = Logger.getLogger(ClubHandler.class);
	private static final long serialVersionUID = 6945876997863030103L;
	
	@EJB
	private ClubService clubService;
	
	private List<Club> clubs;
	
	public ClubHandler() {
		logger.info("--> Club()");
		
	}
	
	@PostConstruct
	public void init() {
		final String methodName = "init()";
		debugEnter(logger, methodName);
		try {
			clubs = clubService.getAllClubs();
		} catch (Exception e) {
			logger.error("",e);
			addMessage(FacesMessage.SEVERITY_FATAL, e.getMessage());
		}
	}
	
	public List<Club> getClubs() {
		return this.clubs;
	}
}
