package com.revoter.rest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.revoter.model.Dish;

/**
 * Dish DAO interface.
 */
@Repository
public interface DishRepository extends CrudRepository<Dish, Long> {

}