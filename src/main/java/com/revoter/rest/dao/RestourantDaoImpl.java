package com.revoter.rest.dao;

import org.springframework.stereotype.Repository;

import com.revoter.model.Dish;

@Repository
public class RestourantDaoImpl implements RestourantDao {

	@Override
	public void addRestourant() {
		
	}

	// TODO: change it
	
	public Dish getDish(long id) {
		Dish d = new Dish();
		d.setId(id);
		d.setName("Pizza");
		d.setPrice(12.56f);
		return d;
	}
}
