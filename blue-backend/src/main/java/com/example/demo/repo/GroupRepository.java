package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
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

}
