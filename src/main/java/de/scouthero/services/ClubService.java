package de.scouthero.services;

import java.util.List;

import javax.ejb.Local;

import de.scouthero.beans.Club;

@Local
public interface ClubService {
	public List<Club> getAllClubs() throws Exception ;
	public List<Club> getClubsBySearchoption(String searchString) throws Exception ;
}
