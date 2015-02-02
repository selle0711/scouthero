package de.scouthero.services;

import java.util.List;

import javax.ejb.Local;

import de.scouthero.beans.Message;
import de.scouthero.beans.User;
import de.scouthero.util.ScoutheroException;
import de.scouthero.util.Defs.MessageTyp;

@Local
public interface MessageService {
	public List<Message> getMyNewMessages(User currentUser) throws ScoutheroException;
	public List<Message> getMyMessages(User currentUser) throws ScoutheroException;
	public void readMessage(Message selectedMessage) throws ScoutheroException;
	public void sendMessage(User sender, User reciever, String subject, StringBuilder message, MessageTyp messageType) throws ScoutheroException;
	public void replyMessage(Message selectedMessage, String newMessage) throws ScoutheroException;
}
