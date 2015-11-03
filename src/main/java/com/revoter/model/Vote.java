package com.revoter.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="vote")
public class Vote implements Serializable {
	private static final long serialVersionUID = -5259472291214419174L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="vote_id")
	private long id;
	
	@ManyToOne
	@JoinColumn(name="restaurant_id")
	private Restaurant restaurant;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	@Override
	public String toString() {
		return "Vote[id=" + id + ", " + restaurant.toString();
	}
}