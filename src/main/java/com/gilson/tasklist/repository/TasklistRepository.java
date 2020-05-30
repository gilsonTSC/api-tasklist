package com.gilson.tasklist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gilson.tasklist.entity.Tasklist;

@Repository
public interface TasklistRepository extends JpaRepository<Tasklist, Long>{

}