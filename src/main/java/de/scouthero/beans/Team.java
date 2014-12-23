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
import javax.persistence.OneToOne;

/**
 * @author rgesell
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "Team.findAll", query = "select t from Team t"),
	@NamedQuery(name = "Team.findByUser", query = "select t from Team t where t.club.user=:user"),
	@NamedQuery(name = "Team.findByClub", query = "select t from Team t where t.club=:club") })
public class Team implements Serializable{

	private static final long serialVersionUID = 3433501125497745800L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	private String description;
	
	@OneToMany(mappedBy="team")
	private Set<Inserat> ad;
	
	@ManyToOne
	@JoinColumn(name="club_id")
	private Club club;
	
	@OneToOne
	@JoinColumn(name="sport_id")
	private SportType sportType;
	
	private String leistungsKlasse;
	private int minAge;
	private int maxAge;
	
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
	public Set<Inserat> getAd() {
		return ad;
	}

	/**
	 * @param ad the ad to set
	 */
	public void setAd(Set<Inserat> ad) {
		this.ad = ad;
	}

	public SportType getSportType() {
		return sportType;
	}

	public void setSportType(SportType sportType) {
		this.sportType = sportType;
	}

	/**
	 * @return the club
	 */
	public Club getClub() {
		return club;
	}

	/**
	 * @param club the club to set
	 */
	public void setClub(Club club) {
		this.club = club;
	}

	@Override
	public String toString() {
		return "Team [id=" + id + ", name=" + name + ", club=" + club
				+ ", sportType=" + sportType + "]";
	}

	/**
	 * @return the leistungsKlasse
	 */
	public String getLeistungsKlasse() {
		return leistungsKlasse;
	}

	/**
	 * @param leistungsKlasse the leistungsKlasse to set
	 */
	public void setLeistungsKlasse(String leistungsKlasse) {
		this.leistungsKlasse = leistungsKlasse;
	}

	/**
	 * @return the minAge
	 */
	public int getMinAge() {
		return minAge;
	}

	/**
	 * @param minAge the minAge to set
	 */
	public void setMinAge(int minAge) {
		this.minAge = minAge;
	}

	/**
	 * @return the maxAge
	 */
	public int getMaxAge() {
		return maxAge;
	}

	/**
	 * @param maxAge the maxAge to set
	 */
	public void setMaxAge(int maxAge) {
		this.maxAge = maxAge;
	}
}
