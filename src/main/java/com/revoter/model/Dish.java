package com.revoter.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="dish")
public class Dish implements Serializable {
	private static final long serialVersionUID = 3637392225067938936L;

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
	
	// TODO: implement 'equals', 'hashCode' and 'toString'
	
	@Override
	public String toString() {
		return "Dish [id=" + id + ", name=" + name + ", price=" + price + "]";
	}
}