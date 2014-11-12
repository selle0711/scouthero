package de.scouthero.managedBeans;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import de.scouthero.util.EMF;


@ManagedBean
@SessionScoped
public class DateHandler {
	
	private final static Logger logger = Logger.getLogger(DateHandler.class);
	private EntityManager em;
	
	public DateHandler() {
		em = EMF.createEntityManager();
	}
	public Date getNow() {
		
		Query query = em.createNativeQuery("SELECT NOW() from dual");
		Date d = (Date) query.getSingleResult();
		logger.info("Date: "+d);
//		session.close();
		return d;
	}
}
