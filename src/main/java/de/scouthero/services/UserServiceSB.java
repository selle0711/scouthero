package de.scouthero.services;

import java.util.List;

import javax.ejb.Local;

import de.scouthero.beans.User;
import de.scouthero.util.ScoutheroException;

@Local
public interface UserServiceSB {
	public User getUserByNameAndPassword(final String login, final String password) throws ScoutheroException;
	public void updateUser(User user) throws ScoutheroException;
	public void deleteUser(User user) throws ScoutheroException;
	public List<User> getUserList();
	public boolean isUserOrMailAlreadyExist(final String login, final String mail);
}
