package de.scouthero.services;
import static de.scouthero.util.LogUtil.debugEnter;
import static de.scouthero.util.LogUtil.debugExit;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import de.scouthero.beans.Club;

@Stateless
@Local(ClubService.class)
public class ClubServiceBean implements ClubService {
	private static Logger logger = Logger.getLogger(ClubServiceBean.class);
	
	@PersistenceContext(name="scoutheroDS")
	EntityManager em;
	
	/**
	 * 
	 * @param myClub
	 * @return
	 */
	public Club createClub (Club myClub) {
		final String methodName = "createClub()";
		debugEnter(logger, methodName, "params: %s", myClub);
		
		return null;
	}
	
	/**
	 * sucht alle Clubs
	 * @return alle Clubs
	 */
	public List<Club> getAllClubs() throws Exception {
		final String methodName = "getAllClubs()";
		debugEnter(logger, methodName);
		TypedQuery<Club> query = em.createNamedQuery("Club.findAll", Club.class);
		List<Club> clubs = query.getResultList();
		debugExit(logger, methodName, "clubs=", clubs);
		return clubs;
	}
	
	/**
	 * Sucht die Vereine anhand des übergebenen Strings. 
	 * Sucht in Stadt, Vereinsname, Sparte
	 * @param searchString
	 * @return gefundene Vereine
	 */
	public List<Club> getClubsBySearchoption(String searchString) throws Exception {
		final String methodName = "getClubsBySearchoption()";
		debugEnter(logger, methodName, "params:", searchString);
		TypedQuery<Club> query = em.createNamedQuery("Club.findBySearchString", Club.class);
		query.setParameter("searchString", "%"+searchString+"%");
		List<Club> clubs = query.getResultList();
		debugExit(logger, methodName, "clubs=", clubs);
		return null;
	}
}
