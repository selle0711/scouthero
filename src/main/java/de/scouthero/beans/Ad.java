package de.scouthero.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 * @author rgesell
 *
 */
@Entity
public class Ad implements Serializable {

	private static final long serialVersionUID = 4947999407413215862L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date creationTime;
	
	private String description;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	private int type;
	
	@OneToOne
	@JoinColumn(name="player_id")
	private Player player;
	
	@ManyToOne
	@JoinColumn(name="team_id")
	private Team team;
	
	public Ad() {
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
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
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
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @param player the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
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

}
