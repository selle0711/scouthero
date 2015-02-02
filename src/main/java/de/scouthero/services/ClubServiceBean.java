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

import de.scouthero.beans.Club;
import de.scouthero.beans.SportType;
import de.scouthero.beans.Team;
import de.scouthero.beans.User;
import de.scouthero.util.ScoutheroException;

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

	/**
	 * @param user
	 * @return Vereine des angemeldeten Benutzers
	 */
	public List<Club> getUserClubs(User user) throws ScoutheroException {
		final String methodName = "getUserClubs()";
		debugEnter(logger, methodName, "params:", user);
		TypedQuery<Club> query = em.createNamedQuery("Club.findByUser", Club.class);
		query.setParameter("user", user);
		try {
			return query.getResultList();
		} catch (NoResultException e) {
			throw new ScoutheroException(e);
		}
	}

	/**
	 * Legt den Club an (falls ID noch nich gefüllt) und speichert die Änderungen
	 * 
	 * @param selectedUserClub
	 * @param user
	 * @param selectedSportTypId
	 * @throws ScoutheroException
	 */
	public void saveClub(final Club selectedUserClub, final User user) throws ScoutheroException {
		final String methodName = "saveClub()";
		debugEnter(logger, methodName, "params: ", selectedUserClub, user);
		try {
			if (selectedUserClub.getId() == null) {
				selectedUserClub.setUser(user);
				em.persist(selectedUserClub);
			} else {
				em.merge(selectedUserClub);
			}
		} catch (Exception e) {
			logger.error("", e);
			throw new ScoutheroException(e);
		}
	}

	/**
	 * Liefert alle Mannschaften eines Vereins
	 * @param selectedUserClub
	 * @return List
	 * @throws ScoutHeroException
	 */
	public List<Team> getClubTeams(final Club selectedUserClub) throws ScoutheroException {
		final String methodName = "getClubTeams()";
		debugEnter(logger, methodName, "params: ", selectedUserClub);
		List<Team> teams = null;
		try {
			TypedQuery<Team> query = em.createNamedQuery("Team.findByClub", Team.class);
			query.setParameter("club", selectedUserClub);
			teams = query.getResultList();
		} catch (Exception e) {
			throw new ScoutheroException(e);
		}
		debugExit(logger, methodName, "teams=", teams);
		return teams;
	} 
	
	/**
	 * Legt das Teaman (falls ID noch nich gefüllt) und speichert die Änderungen
	 * 
	 * @param selectedUserTeam
	 * @param userClub
	 * @param selectedSportTypId
	 * @throws ScoutheroException
	 */
	public void saveTeam(final Team selectedUserTeam, final Club userClub, final Long selectedSportTypId) throws ScoutheroException {
		final String methodName = "saveTeam()";
		debugEnter(logger, methodName, "params: ", selectedUserTeam, userClub, selectedSportTypId);
		try {
			// find sportType
			final SportType sportType = em.find(SportType.class, selectedSportTypId);
			selectedUserTeam.setSportType(sportType);
			logger.info(sportType.getName() + " gesetzt");
			if (selectedUserTeam.getId() == null) {
				selectedUserTeam.setClub(userClub);
				em.persist(selectedUserTeam);
			} else {
				em.merge(selectedUserTeam);
			}
			
		} catch (Exception e) {
			logger.error("", e);
			throw new ScoutheroException(e);
		}
	}
	
	/**
	 * Löscht den übergebenen Club
	 * @param team
	 * @throws ScoutheroException
	 */
	public void deleteClub(final Club club) throws ScoutheroException {
		if (club != null && club.getId() != null) {
			final Club club2Delete = em.find(Club.class, club.getId());
			em.remove(club2Delete);
		} else {
			throw new ScoutheroException("Kein Club übergeben worden");
		}
	}
	
	/**
	 * Löscht das übergebene Team
	 * @param team
	 * @throws ScoutheroException
	 */
	public void deleteTeam(final Team team) throws ScoutheroException {
		if (team != null && team.getId() != null) {
			final Team team2Delete = em.find(Team.class, team.getId());
			em.remove(team2Delete);
		} else {
			throw new ScoutheroException("Kein Team übergeben worden");
		}
	}
	
	/**
	 * Liefert alle Sportarten aus der DB
	 * @return List
	 * @throws ScoutheroException
	 */
	public List<SportType> getAllKindOfSport() throws ScoutheroException {
		final String methodName = "getAllKindOfSport()";
		debugEnter(logger, methodName);
		List<SportType> sports = null;
		try {
			TypedQuery<SportType> query = em.createNamedQuery("Sport.findAll", SportType.class);
			sports = query.getResultList();
		} catch (Exception e) {
			throw new ScoutheroException(e);
		}
		debugExit(logger, methodName, "sports=", sports);
		return sports;
	}
}
