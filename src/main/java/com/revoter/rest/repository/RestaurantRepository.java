package com.revoter.rest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.revoter.model.Restaurant;

/**
 * Restaurant DAO interface.
 */
@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {

}