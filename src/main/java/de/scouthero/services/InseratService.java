package de.scouthero.services;

import java.util.List;

import javax.ejb.Local;

import de.scouthero.beans.Inserat;
import de.scouthero.beans.Team;
import de.scouthero.beans.User;
import de.scouthero.util.Defs.AccountTyp;
import de.scouthero.util.ScoutheroException;

@Local
public interface InseratService {
	public List<Team> getUserTeamList(final User currentUser) throws ScoutheroException;
	public void saveInserat(Inserat selectedInserat, User currentUser, Long selectedUserTeam) throws ScoutheroException;
	public List<Inserat> loadUserInserate(User currentUser) throws ScoutheroException;
	public void deleteInserat(Inserat inserat) throws ScoutheroException;
	public List<Inserat> loadInserate(AccountTyp selectedSearchOption) throws ScoutheroException;
	public void viewDetails(Inserat inserat) throws ScoutheroException;
	public void linkToInterestingPeople(Inserat inserat, User user) throws ScoutheroException;
	public void unLinkToInterestingPeople(Inserat inserat, User user) throws ScoutheroException;
	public boolean isAlreadyLinkedToInterestingPeople(Inserat inserat, User user) throws ScoutheroException;
	public void contactUserInserat(Inserat selectedInserat, User currentUser)throws ScoutheroException;
	public boolean playerHasTransferContact(Inserat inserat, User player) throws ScoutheroException;
}
