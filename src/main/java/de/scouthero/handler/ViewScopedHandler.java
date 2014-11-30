package de.scouthero.handler;

import javax.faces.bean.ManagedProperty;

import de.scouthero.beans.User;

public class ViewScopedHandler extends AbstractHandler {
	private static final long serialVersionUID = 5970529380062953591L;
	
	@ManagedProperty(value="#{login.user}")  
	protected User currentUser; 
	
	public void setCurrentUser(User login) {
		this.currentUser = login;
	}
}
