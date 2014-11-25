package de.scouthero.services;

import javax.ejb.Local;

import de.scouthero.beans.User;

@Local
public interface UserServiceSB {
	public User getUserByNameAndPassword(final String login, final String password);
	public void updateUser(User user);
	public void deleteUser(Long id);
}
