package de.scouthero.services;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import de.scouthero.beans.User;

@Stateless
@Local(UserServiceSB.class)
public class UserServiceBean implements UserServiceSB {
	
	private final static Logger LOG = Logger.getLogger(UserServiceBean.class);
	
	@PersistenceContext(name="scoutheroDS")
	private EntityManager em;
	
	public User getUserByNameAndPassword(final String login, final String password) {
		final String methodName = "getUserByNameAndPassword()";
		LOG.debug(methodName+ "login="+login+" password="+password);
		
		TypedQuery<User> query = em.createNamedQuery("User.findByLoginAndPass", User.class);
		query.setParameter("loginName", login);
		query.setParameter("password", password);
		return query.getSingleResult();
	}
	
	/**
	 * 
	 * @param user
	 */
	public void updateUser(User user) {
		if (user != null) {
			if (user.getId() != null) {
//				 update
				em.merge(user);
			} else {
//				insert
				em.persist(user);
			}
		}
	}
	
	/**
	 * 
	 * @param id
	 */
	public void deleteUser(Long id) {
		User user = em.find(User.class, id);
		if (user != null)
			em.remove(user);
	}
}
