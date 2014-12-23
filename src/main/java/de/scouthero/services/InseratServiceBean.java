package de.scouthero.services;

import static de.scouthero.util.LogUtil.debugEnter;
import static de.scouthero.util.LogUtil.debugExit;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import de.scouthero.beans.Inserat;
import de.scouthero.beans.Team;
import de.scouthero.beans.User;
import de.scouthero.util.ScoutheroException;
import de.scouthero.util.Defs.AccountTyp;

@Stateless
@Local(InseratService.class)
public class InseratServiceBean implements InseratService {
	private static Logger logger = Logger.getLogger(InseratServiceBean.class);
	
	@PersistenceContext(name="scoutheroDS")
	private EntityManager em;
	
	/**
	 * 
	 * @param currentUser
	 * @return
	 * @throws ScoutheroException
	 */
	public List<Team> getUserTeamList(final User currentUser) throws ScoutheroException {
		final String methodName = "getUserTeamList()";
		debugEnter(logger, methodName, "params: ", currentUser);
		List<Team> teams = null;
		try {
			TypedQuery<Team> query = em.createNamedQuery("Team.findByUser", Team.class);
			query.setParameter("user", currentUser);
			teams = query.getResultList();
		} catch (Exception e) {
			throw new ScoutheroException(e);
		}
		debugExit(logger, methodName, "teams=", teams);
		return teams;
	}

	/**
	 * Speichert ein Inserat in der DB
	 * @param selectedInserat
	 * @param currentUser
	 * @param selTeamId
	 * @throws ScoutheroException
	 */
	public void saveInserat(Inserat selectedInserat, User currentUser, final Long selTeamId) throws ScoutheroException {
		final String methodName = "saveInserat()";
		debugEnter(logger, methodName, "params: ", selectedInserat, currentUser);
		try {
			// find user team
			if (selTeamId == null)
				throw new ScoutheroException("Kein Team ausgewählt");
			final Team team = em.find(Team.class, selTeamId);
			selectedInserat.setTeam(team);
			if (selectedInserat.getId() == null) {
				selectedInserat.setCreator(currentUser);
				em.persist(selectedInserat);
			} else {
				em.merge(selectedInserat);
				if (selectedInserat.getCreator().equals(currentUser) == false) {
					throw new ScoutheroException("Falscher User versucht ein Inserat zu bearbeiten");
				}
			}
			// setze den typo den inserats - anhand des Users
			selectedInserat.setType(currentUser.getType());
		} catch (Exception e) {
			logger.error("", e);
			throw new ScoutheroException(e);
		}
	}

	/**
	 * @param currentUser
	 */
	public List<Inserat> loadUserInserate(User currentUser) throws ScoutheroException {
		final String methodName = "getUserTeamList()";
		debugEnter(logger, methodName, "params: ", currentUser);
		List<Inserat> inserate = null;
		try {
			TypedQuery<Inserat> query = em.createNamedQuery("Inserat.findByUser", Inserat.class);
			query.setParameter("user", currentUser);
			inserate = query.getResultList();
		} catch (Exception e) {
			throw new ScoutheroException(e);
		}
		debugExit(logger, methodName, "inserate=", inserate);
		return inserate;
	}
	
	/**
	 * 
	 * @param inserat
	 * @throws ScoutheroException
	 */
	public void deleteInserat(final Inserat inserat) throws ScoutheroException {
		final String methodName = "deleteInserat()";
		debugEnter(logger, methodName, "params: ", inserat);
		if (inserat == null || inserat.getId() == null)
			throw new ScoutheroException("Kein Inserat übergeben worden.");
		if (inserat.getCountInterestPeople() != 0) {
			throw new ScoutheroException("Inserat kann nicht gelöscht werden. Es haben bereits einige User Interesse bekundet.");
		}
		final Inserat inserat2Delete = em.find(Inserat.class, inserat.getId());
		em.remove(inserat2Delete);
	}
	
	/**
	 * @param searchOption
	 * @throws ScoutheroException
	 */
	public List<Inserat> loadInserate(final AccountTyp searchOption) throws ScoutheroException {
		final String methodName = "loadInserate()";
		debugEnter(logger, methodName, "params: ", searchOption);
		List<Inserat> inserate = null;
		try {
			TypedQuery<Inserat> query = em.createNamedQuery("Inserat.findByOption", Inserat.class);
			query.setParameter("type", searchOption.value());
			inserate = query.getResultList();
		} catch (Exception e) {
			throw new ScoutheroException(e);
		}
		debugExit(logger, methodName, "inserate=", inserate);
		return inserate;
	}
	
	/**
	 * 
	 * @param inserat
	 * @param user
	 * @throws ScoutheroException
	 */
	public void viewDetails(Inserat inserat) throws ScoutheroException {
		final String methodName = "viewDetails()";
		debugEnter(logger, methodName, "params: ", inserat);
		if (inserat != null) {
			//attach POJOs
			inserat = em.find(Inserat.class, inserat.getId());
			inserat.setViewed(inserat.getViewed()+1);
		}
	}
	
	
	/**
	 * 
	 * @param inserat
	 * @param user
	 * @throws ScoutheroException
	 */
	public void unLinkToInterestingPeople(Inserat inserat, User user) throws ScoutheroException {
		final String methodName = "unLinkToInterestingPeople()";
		debugEnter(logger, methodName, "params: ", inserat, user);
		if (inserat != null && user != null) {
			//attach POJOs
			inserat = em.find(Inserat.class, inserat.getId());
			user    = em.find(User.class, user.getId());
			// link user to inserat
			if (inserat.getInterestingPeople() != null && inserat.getInterestingPeople().contains(user)) {
				inserat.getInterestingPeople().remove(user);
			}
		}
	}
	
	/**
	 * 
	 * @param inserat
	 * @param user
	 * @throws ScoutheroException
	 */
	public void linkToInterestingPeople(Inserat inserat, User user) throws ScoutheroException {
		final String methodName = "linkToInterestingPeople()";
		debugEnter(logger, methodName, "params: ", inserat, user);
		if (inserat != null && user != null) {
			//attach POJOs
			inserat = em.find(Inserat.class, inserat.getId());
			user    = em.find(User.class, user.getId());
			// link user to inserat
			if (inserat.getInterestingPeople() == null) {
				inserat.setInterestingPeople(new ArrayList<User>());
			}
			inserat.getInterestingPeople().add(user);
		}
	}
	
	/**
	 * 
	 * @param inserat
	 * @param user
	 * @return
	 */
	public boolean isAlreadyLinkedToInterestingPeople(Inserat inserat, User user) throws ScoutheroException{
		final String methodName = "isAlreadyLinkedToInterestingPeople()";
		debugEnter(logger, methodName, "params: ", inserat, user);
		if (inserat != null && user != null) {
			//attach POJOs
			inserat = em.find(Inserat.class, inserat.getId());
			user    = em.find(User.class, user.getId());
			if (inserat.getInterestingPeople() != null && inserat.getInterestingPeople().contains(user)) {
				return true;
			}
		}
		return false;
	}
}
