package de.scouthero.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.Transient;

/**
 * @author rgesell
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Inserat.findByUser", query="select i from Inserat i where i.creator = :user"),
	@NamedQuery(name="Inserat.findByOption", query="select i from Inserat i where i.type = :type")
})
public class Inserat implements Serializable {

	private static final long serialVersionUID = 4947999407413215862L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date creationTime;
	
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date validUntil;
	
	private String description;
	private int viewed;
	
	@ManyToOne
	@JoinColumn(name="creator")
	private User creator;
	
	/**
	 * Type des Inserats: Spieler/Verein
	 */
	private int type;
	
	@ManyToOne
	@JoinColumn(name="team_id")
	private Team team;
	
	@ManyToMany(targetEntity=User.class, fetch=FetchType.EAGER)
	@JoinTable(name="inseratInterests", joinColumns={@JoinColumn(name="inserat_id")},inverseJoinColumns={@JoinColumn(name="user_id")})
	private List<User> interestingPeople;
	
	@OneToMany(mappedBy="inserat", fetch=FetchType.EAGER)
	private List<Transfer> transfers;
	
	public Inserat() {
		this.creationTime = new Date(System.currentTimeMillis());
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the creationTime
	 */
	public Date getCreationTime() {
		return creationTime;
	}

	/**
	 * @param creationTime the creationTime to set
	 */
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return creator;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.creator = user;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the team
	 */
	public Team getTeam() {
		return team;
	}

	/**
	 * @param team the team to set
	 */
	public void setTeam(Team team) {
		this.team = team;
	}

	/**
	 * @return the viewed
	 */
	public int getViewed() {
		return viewed;
	}

	/**
	 * @param viewed the viewed to set
	 */
	public void setViewed(int viewed) {
		this.viewed = viewed;
	}

	/**
	 * @return the creator
	 */
	public User getCreator() {
		return creator;
	}

	/**
	 * @param creator the creator to set
	 */
	public void setCreator(User creator) {
		this.creator = creator;
	}

	/**
	 * @return the interestingPeople
	 */
	public List<User> getInterestingPeople() {
		return interestingPeople;
	}

	/**
	 * @param interestingPeople the interestingPeople to set
	 */
	public void setInterestingPeople(List<User> interestingPeople) {
		this.interestingPeople = interestingPeople;
	}

	@Transient
	public int getCountInterestPeople() {
		return interestingPeople != null ? interestingPeople.size() : 0;
	}

	/**
	 * @return the validUntil
	 */
	public Date getValidUntil() {
		return validUntil;
	}

	/**
	 * @param validUntil the validUntil to set
	 */
	public void setValidUntil(Date validUntil) {
		this.validUntil = validUntil;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Inserat other = (Inserat) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/**
	 * @return the transfers
	 */
	public List<Transfer> getTransfers() {
		return transfers;
	}

	/**
	 * @param transfers the transfers to set
	 */
	public void setTransfers(List<Transfer> transfers) {
		this.transfers = transfers;
	}
}
