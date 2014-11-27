package de.scouthero.services;

import javax.ejb.Local;

import de.scouthero.beans.User;
import de.scouthero.util.ScoutheroException;

@Local
public interface UserServiceSB {
	public User getUserByNameAndPassword(final String login, final String password)  throws ScoutheroException;
	public void updateUser(User user);
	public void deleteUser(Long id);
}
