package com.revoter.rest.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.revoter.model.Vote;

/**
 * Vote DAO interface.
 */
@Repository
public interface VoteRepository extends CrudRepository<Vote, Long> {
	@Query(value="select v.* from Dish d, Vote v where d.restaurant_id = ?1 and v.dish_id = d.dish_id", nativeQuery = true)
	public Iterable<Vote> findByRestaurant(Long restaurantId);
}
