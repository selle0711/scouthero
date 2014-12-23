package de.scouthero.services;

import java.util.List;

import javax.ejb.Local;

import de.scouthero.beans.Club;
import de.scouthero.beans.SportType;
import de.scouthero.beans.Team;
import de.scouthero.beans.User;
import de.scouthero.util.ScoutheroException;

@Local
public interface ClubService {
	public List<Club> getAllClubs() throws Exception ;
	public List<Club> getClubsBySearchoption(String searchString) throws Exception ;
	public List<Club> getUserClubs(User user) throws ScoutheroException;
	public void saveClub(Club selectedUserClub, User user) throws ScoutheroException;
	public List<Team> getClubTeams(Club selectedUserClub) throws ScoutheroException;
	public List<SportType> getAllKindOfSport() throws ScoutheroException;
	public void saveTeam(final Team selectedUserTeam, final Club userClub, Long selectedSportTypId) throws ScoutheroException;
	public void deleteTeam(Team team) throws ScoutheroException;
	public void deleteClub(Club club) throws ScoutheroException;
}
