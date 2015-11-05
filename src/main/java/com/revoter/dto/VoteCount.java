package com.revoter.dto;

import com.revoter.model.BaseType;

public class VoteCount extends BaseType {
	private static final long serialVersionUID = 6937439276055809388L;
	
	private long restaurantId;
	private int count;
	
	public VoteCount(long restaurantId) {
		this.restaurantId = restaurantId;
	}
	
	public long getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(long restaurantId) {
		this.restaurantId = restaurantId;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}