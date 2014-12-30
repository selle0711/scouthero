package de.scouthero.services;

import static de.scouthero.util.LogUtil.debugEnter;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import de.scouthero.beans.Message;
import de.scouthero.beans.User;
import de.scouthero.util.ScoutheroException;

@Stateless
@Local(MessageService.class)
public class MessageServiceBean {

	private static Logger logger = Logger.getLogger(MessageServiceBean.class);
	
	@PersistenceContext(name="scoutheroDS")
	private EntityManager em;
	
	public List<Message> getMyNewMessages(User currentUser) throws ScoutheroException {
		final String methodName = "getMyNewMessages()";
		debugEnter(logger, methodName, "params: ", currentUser);
		List<Message> newMessages = null;
		currentUser = em.find(User.class, currentUser.getId());
		
		try {
			TypedQuery<Message> query = em.createNamedQuery("Message.findNewByUser", Message.class);
			query.setParameter("user", currentUser);
			newMessages = query.getResultList();
		} catch (Exception e) {
			throw new ScoutheroException(e);
		}
		return newMessages;
	}
	
	public List<Message> getMyMessages(User currentUser) throws ScoutheroException {
		final String methodName = "getMyMessages()";
		debugEnter(logger, methodName, "params: ", currentUser);
		List<Message> newMessages = null;
		currentUser = em.find(User.class, currentUser.getId());
		
		try {
			TypedQuery<Message> query = em.createNamedQuery("Message.findAllByUser", Message.class);
			query.setParameter("user", currentUser);
			newMessages = query.getResultList();
		} catch (Exception e) {
			throw new ScoutheroException(e);
		}
		return newMessages;
	}
}
