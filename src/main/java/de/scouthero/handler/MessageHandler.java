package de.scouthero.handler;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;

import de.scouthero.beans.Message;
import de.scouthero.beans.User;
import de.scouthero.services.MessageService;
import de.scouthero.util.ScoutheroException;

@ManagedBean
public class MessageHandler extends AbstractHandler{

	private static final long serialVersionUID = -9211901863760968180L;
	@EJB
	private MessageService messageService;
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
}
