package de.scouthero.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
	@NamedQuery(name="Message.findAllByUser", query="select m from Message m where m.reciever = :user"),
	@NamedQuery(name="Message.findNewByUser", query="select m from Message m where m.reciever = :user and m.readDate is null")
})
public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5800374342523647586L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne
	private User reciever;
	private String subject;
	private String message;
	private Date sendDate;
	private Date readDate;
	
	/**
	 * @return the reciever
	 */
	public User getReciever() {
		return reciever;
	}
	/**
	 * @param reciever the reciever to set
	 */
	public void setReciever(User reciever) {
		this.reciever = reciever;
	}
	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the sendDate
	 */
	public Date getSendDate() {
		return sendDate;
	}
	/**
	 * @param sendDate the sendDate to set
	 */
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	/**
	 * @return the readDate
	 */
	public Date getReadDate() {
		return readDate;
	}
	/**
	 * @param readDate the readDate to set
	 */
	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
}
