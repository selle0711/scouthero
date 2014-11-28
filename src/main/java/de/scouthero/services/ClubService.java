package de.scouthero.services;

import java.util.List;

import javax.ejb.Local;

import de.scouthero.beans.Club;
import de.scouthero.beans.User;
import de.scouthero.util.ScoutheroException;

@Local
public interface ClubService {
	public List<Club> getAllClubs() throws Exception ;
	public List<Club> getClubsBySearchoption(String searchString) throws Exception ;
	public List<Club> getUserClubs(User user) throws ScoutheroException;
	public void saveClub(Club selectedUserClub, User user) throws ScoutheroException;
}
