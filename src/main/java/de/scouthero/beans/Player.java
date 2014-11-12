/**
 * 
 */
package de.scouthero.beans;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * @author rgesell
 *
 */
@Entity
public class Player implements Serializable{

	
	private static final long serialVersionUID = 3259463591980461324L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String description;
	
	@OneToOne(mappedBy="player")
	private Ad ad;
	
	public Player() {
		
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
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the ad
	 */
	public Ad getAd() {
		return ad;
	}

	/**
	 * @param ad the ad to set
	 */
	public void setAd(Ad ad) {
		this.ad = ad;
	}
}
