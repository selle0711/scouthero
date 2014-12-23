package de.scouthero.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Transfer implements Serializable {
	
	private static final long serialVersionUID = 7753113453510772450L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/**
	 * Transferbestätigung -> NULL ist möglich (TriState)
	 */
	private Boolean approved;
	@ManyToOne
	private User spieler;
	@ManyToOne
	private Team verein;
	private Date dateOfInterest;
	private Date dateOfApprovment;
	
	/**
	 * @return the approved
	 */
	public Boolean getApproved() {
		return approved;
	}
	/**
	 * @param approved the approved to set
	 */
	public void setApproved(Boolean approved) {
		this.approved = approved;
	}
	/**
	 * @return the spieler
	 */
	public User getSpieler() {
		return spieler;
	}
	/**
	 * @param spieler the spieler to set
	 */
	public void setSpieler(User spieler) {
		this.spieler = spieler;
	}
	/**
	 * @return the verein
	 */
	public Team getVerein() {
		return verein;
	}
	/**
	 * @param verein the verein to set
	 */
	public void setVerein(Team verein) {
		this.verein = verein;
	}
	/**
	 * @return the dateOfInterest
	 */
	public Date getDateOfInterest() {
		return dateOfInterest;
	}
	/**
	 * @param dateOfInterest the dateOfInterest to set
	 */
	public void setDateOfInterest(Date dateOfInterest) {
		this.dateOfInterest = dateOfInterest;
	}
	/**
	 * @return the dateOfApprovment
	 */
	public Date getDateOfApprovment() {
		return dateOfApprovment;
	}
	/**
	 * @param dateOfApprovment the dateOfApprovment to set
	 */
	public void setDateOfApprovment(Date dateOfApprovment) {
		this.dateOfApprovment = dateOfApprovment;
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Transfer [id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transfer other = (Transfer) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
