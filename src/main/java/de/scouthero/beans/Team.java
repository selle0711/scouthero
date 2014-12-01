/**
 * 
 */
package de.scouthero.beans;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 * @author rgesell
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "Team.findAll", query = "select t from Team t"),
	@NamedQuery(name = "Team.findByClub", query = "select t from Team t where t.club=:club") })
public class Team implements Serializable{

	private static final long serialVersionUID = 3433501125497745800L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	private String description;
	
	@OneToMany(mappedBy="team")
	private Set<Ad> ad;
	
	@ManyToOne
	@JoinColumn(name="club_id")
	private Club club;
	
	public Team() {
		
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * @return the ad
	 */
	public Set<Ad> getAd() {
		return ad;
	}

	/**
	 * @param ad the ad to set
	 */
	public void setAd(Set<Ad> ad) {
		this.ad = ad;
	}
}
