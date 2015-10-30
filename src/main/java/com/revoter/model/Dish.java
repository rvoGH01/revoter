package com.revoter.model;

public class Dish {
	private long id;
	private String name;
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
		return "Dish [name=" + name + ", price=" + price + "]";
	}
}