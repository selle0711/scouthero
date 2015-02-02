package de.scouthero.handler;

import static de.scouthero.util.LogUtil.debugEnter;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;

import de.scouthero.beans.Message;
import de.scouthero.services.MessageService;
import de.scouthero.util.ScoutheroException;

@ManagedBean
@ViewScoped
public class MyMessageHandler extends ViewScopedHandler {

	private static final long serialVersionUID = -3887394606946476260L;
	private static final Logger logger = Logger.getLogger(MyMessageHandler.class);
	
	@EJB
	private MessageService messageService;
	private List<Message> myMessages = null;
	private Message selectedMessage;
	private boolean showReply = false;
	private String newMessage;
	
	@PostConstruct
	private void onLoad() {
		final String methodName = "onLoad()";
		debugEnter(logger, methodName);
		showReply = false;
		try {
			myMessages = messageService.getMyMessages(currentUser);
		} catch (ScoutheroException e) {
			addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
		}
	}
	
	
	/**
	 * Listener für die Selektion in der Messagetabelle
	 * @param event
	 */
	public void onRowSelect(SelectEvent event) {
		final String methodName = "onRowSelect()";
		debugEnter(logger, methodName, "params: ", event);
		setSelectedMessage((Message)event.getObject());
		if (selectedMessage.getReadDate() == null) {
			try {
				messageService.readMessage(selectedMessage);
			} catch (ScoutheroException e) {
				addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
			}
		}
    }
	
	/**
	 * Listener für die Deselektion in der Messagetabelle
	 * @param event
	 */
	public void onRowUnSelect(SelectEvent event) {
		final String methodName = "onRowUnSelect()";
		debugEnter(logger, methodName, "params: ", event);
		this.selectedMessage = null;
		this.showReply = false;
    }
	
	public void showReply() {
		showReply = true;
		newMessage = "";
	}
	
	public void sendMessage() {
		try {
			messageService.replyMessage(selectedMessage, newMessage);
			addMessage(FacesMessage.SEVERITY_INFO, "Nachricht gesendet");
		} catch (ScoutheroException e) {
			addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
		}
		showReply = false;
	}
	
	public void abortMessage() {
		showReply = false;
	}

	/**
	 * @return the myMessages
	 */
	public List<Message> getMyMessages() {
		return myMessages;
	}

	/**
	 * @param myMessages the myMessages to set
	 */
	public void setMyMessages(List<Message> myMessages) {
		this.myMessages = myMessages;
	}

	/**
	 * @return the selectedMessage
	 */
	public Message getSelectedMessage() {
		return selectedMessage;
	}

	/**
	 * @param selectedMessage the selectedMessage to set
	 */
	public void setSelectedMessage(Message selectedMessage) {
		this.selectedMessage = selectedMessage;
	}
	
	public boolean isShowReply() {
		return showReply;
	}


	/**
	 * @return the newMessage
	 */
	public String getNewMessage() {
		return newMessage;
	}


	/**
	 * @param newMessage the newMessage to set
	 */
	public void setNewMessage(String newMessage) {
		this.newMessage = newMessage;
	}
}
