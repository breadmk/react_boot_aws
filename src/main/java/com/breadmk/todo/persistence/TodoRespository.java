package com.breadmk.todo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.breadmk.todo.model.TodoEntity;

@Repository
public interface TodoRespository extends JpaRepository<TodoEntity, String>{

	@Query(value =  "select * from Todo t where t.userid=?1", nativeQuery = true)
	List<TodoEntity> findByUserId(String userid);
}
