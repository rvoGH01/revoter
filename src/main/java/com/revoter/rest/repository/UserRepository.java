package com.revoter.rest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.revoter.model.User;

/**
 * User DAO interface.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	public User findByUsername(String username);
}