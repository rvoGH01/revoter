package com.revoter.rest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.revoter.model.Vote;

/**
 * Vote DAO interface.
 */
@Repository
public interface VoteRepository extends CrudRepository<Vote, Long> {

}
