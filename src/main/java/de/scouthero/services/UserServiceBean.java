package de.scouthero.services;

import static de.scouthero.util.LogUtil.debugEnter;
import static de.scouthero.util.LogUtil.debugExit;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import de.scouthero.beans.User;
import de.scouthero.util.ScoutheroException;

@Stateless
@Local(UserServiceSB.class)
public class UserServiceBean implements UserServiceSB {
	
	private final static Logger logger = Logger.getLogger(UserServiceBean.class);
	
	@PersistenceContext(name="scoutheroDS")
	private EntityManager em;
	
	
	public List<User> getUserList() {
		final String methodName = "getUserByNameAndPassword()";
		debugEnter(logger, methodName);
		TypedQuery<User> query = em.createNamedQuery("User.findAll", User.class);
		return query.getResultList();
	}
	
	/**
	 * 
	 * @param login
	 * @param mail
	 * @return
	 */
	public boolean isUserOrMailAlreadyExist(final String login, final String mail) {
		final String methodName = "isUserOrMailAlreadyExist()";
		debugEnter(logger, methodName, "params: ", login, mail);
		
		TypedQuery<User> query = em.createNamedQuery("User.findByEmailOrLogin", User.class);
		query.setParameter("loginName", login);
		query.setParameter("email", mail);
		try {
			List<User> ll = query.getResultList();
			if (ll == null || ll.isEmpty()) {
				throw new Exception();
			}
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * @param login
	 * @param password
	 * @return User
	 * @throws ScoutheroException
	 */
	public User getUserByNameAndPassword(final String login, final String password) throws ScoutheroException {
		final String methodName = "getUserByNameAndPassword()";
		debugEnter(logger, methodName, "params: ", login, password);
		
		TypedQuery<User> query = em.createNamedQuery("User.findByLoginAndPass", User.class);
		query.setParameter("loginName", login);
		query.setParameter("password", password);
		
		try {
			final User user =  query.getSingleResult();
			
			if (user == null) {
				throw new NoResultException("fail");
			}
			return user;
		} catch ( NoResultException e) {
			throw new ScoutheroException(e);
		} finally {
			debugExit(logger, methodName);
		}
	}
	
	/**
	 * 
	 * @param user
	 */
	public void updateUser(User user) throws ScoutheroException{
		if (user != null) {
			if (user.getId() != null) {
//				 update
				em.merge(user);
			} else {
//				insert
				em.persist(user);
			}
		} else {
			throw new ScoutheroException("Kein User vorhanden");
		}
	}
	
	/**
	 * 
	 * @param user
	 */
	public void deleteUser(User user) {
		
	}
}
