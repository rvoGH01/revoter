package com.revoter.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="dish")
public class Dish extends BaseEntity {
	private static final long serialVersionUID = -3455506950983192342L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="dish_id")
	private long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="price")
	private float price;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
}