package de.scouthero.handler;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.apache.log4j.Logger;

import de.scouthero.beans.Club;
import de.scouthero.beans.Inserat;
import de.scouthero.beans.Message;
import de.scouthero.beans.User;
import de.scouthero.services.ClubService;
import de.scouthero.services.InseratService;
import de.scouthero.services.MessageService;
import de.scouthero.services.UserServiceSB;
import de.scouthero.util.Defs;
import de.scouthero.util.ScoutheroException;

@RequestScoped
@ManagedBean
public class GlobalHandler extends AbstractHandler{

	private static final long serialVersionUID = -9211901863760968180L;
	private static final Logger logger = Logger.getLogger(GlobalHandler.class);
	
	@EJB
	private MessageService messageService;
	@EJB
	private UserServiceSB userService;
	@EJB 
	private InseratService inseratService;
	@EJB 
	private ClubService clubService;
	
	private List<Message> myMessages = null;
	
	public int countMyMessages(User currentUser) {
		try {
			myMessages = messageService.getMyNewMessages(currentUser);
			if (myMessages != null)
				return myMessages.size();
		} catch (ScoutheroException e) {
			addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
		}
		return 0;
	}
	
	public List<User> getNewestUserList() {
		List<User> userList = userService.getUserList();
		return limit(userList);
	}
	
	public List<Inserat> getNewestPlayerInseratList() {
		try {
			List<Inserat> inseratList = inseratService.loadInserate(Defs.AccountTyp.SPIELER);
			return limit(inseratList);
		} catch (ScoutheroException e) {
			return null;
		}
	}
	
	public List<Inserat> getNewestTeamInseratList() {
		try {
			List<Inserat> inseratList = inseratService.loadInserate(Defs.AccountTyp.VEREIN);
			return limit(inseratList);
		} catch (ScoutheroException e) {
			logger.error("Exception wird ignoriert...",e);
			return null;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public String getStaticMapForClub() {
		final String mapLink = "http://maps.googleapis.com/maps/api/staticmap?center=Germany&size=450x550&zoom=6&maptype=roadmap&"+getClubMarkersForGermanyMap()+"sensor=false";
		logger.info(mapLink);
		return mapLink;
	}
	
	/**
	 * @return
	 */
	private String getClubMarkersForGermanyMap() {
		try {
			List<Club> clubList = clubService.getAllClubs();
			if (clubList != null && clubList.size() > 0) {
				StringBuilder markers = new StringBuilder();
				for (Club club : clubList) {
					markers.append(generateMarker(club.getPlz(), club.getName()));
				}
				return markers.toString();
			}
		} catch (Exception e) {
			logger.error("Exception wird ignoriert...",e);
		}
		return "";
	}
	
	private Object generateMarker(String plz, String name) {
		return "markers=color:red%7Clabel:"+name+"%7C"+plz+",Germany&";
	}

	/**
	 * 
	 * @return current Date
	 */
	public Date getNow() {
		return new Date();
	}
	
	private  <T> List<T> limit (List<T> list) {
		if (list != null && list.size() != 0) {
			if (list.size() > 5)
				return list.subList(0, 5);
			return list;
		}
		return null;
	}
}
