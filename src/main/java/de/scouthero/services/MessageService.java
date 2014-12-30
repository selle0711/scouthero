package de.scouthero.services;

import java.util.List;

import javax.ejb.Local;

import de.scouthero.beans.Message;
import de.scouthero.beans.User;
import de.scouthero.util.ScoutheroException;

@Local
public interface MessageService {
	public List<Message> getMyNewMessages(User currentUser) throws ScoutheroException;
	public List<Message> getMyMessages(User currentUser) throws ScoutheroException;
}
