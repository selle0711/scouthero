package de.scouthero.managedBeans;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


@ManagedBean
@SessionScoped
public class DateHandler {
	
	
	public Date getNow() {
		
		return new Date();
	}
}
