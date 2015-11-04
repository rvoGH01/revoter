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
	// select v.* from Vote v where v.restaurant_id = ?1
	@Query(value="select v.* from Restaurant r, Vote v where r.restaurant_id = ?1 and v.restaurant_id = r.restaurant_id", nativeQuery = true)
	public Iterable<Vote> findByRestaurant(Long restaurantId);
	
	@Query(value = "select v.* from vote v, users u where v.user_id = u.user_id and u.user_id = ?1", nativeQuery = true)
	public Vote findByUser(Long userId);
}