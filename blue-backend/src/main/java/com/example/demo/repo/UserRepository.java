package com.example.demo.repo;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.User;

/**
 * User repository that will get information about our users.
 * 
 * @author ClaudiuIova
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE user SET `group`=:group WHERE `email`=:email", nativeQuery = true)
	void updateByEmail(@Param("email") String email, @Param("group") String group);

}
