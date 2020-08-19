package com.example.demo.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Group;

/**
 * Group repository that will get information about the groups.
 * 
 * @author ClaudiuIova
 *
 */
@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

	@Transactional
	@Modifying
	@Query(value = "delete from Group g where g.userEmail=:email")
	void deleteGroupEntry(@Param("email") String email);

}
