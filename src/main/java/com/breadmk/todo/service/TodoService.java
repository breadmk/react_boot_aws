package com.breadmk.todo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.breadmk.todo.model.TodoEntity;
import com.breadmk.todo.persistence.TodoRespository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TodoService {

	
	@Autowired
	TodoRespository todoResponsitory;
	
	// validation check; 검증
	private void validate(final TodoEntity entity) {
		if(entity == null) {
			log.warn("Entity cannot be null");
			throw new RuntimeException("Entity cannot be null");
		}
		if(entity.getUserid()==null) {
			log.warn("Unknown user");
			throw new RuntimeException("Unknown user");
		}
	}
	
	
	//테스트
	public String testService() {
		
		TodoEntity entity = TodoEntity.builder().title("test repository").build();
		todoResponsitory.save(entity);
		
		TodoEntity savedEntity = todoResponsitory.findById(entity.getId()).get();
		return savedEntity.getTitle();
	}
	
	//등록
	public List<TodoEntity> create(final TodoEntity entity){
		
		validate(entity);
		
		todoResponsitory.save(entity);
		log.info("Entity Id : {} is saved" + entity.getId());
		System.out.println(entity);
		
		return todoResponsitory.findByUserId(entity.getUserid());
	}
	
	//조회
	public List<TodoEntity> retrieve(final String userid){
		return todoResponsitory.findByUserId(userid);
	}
	
	
	//수정
	public List<TodoEntity> update(final TodoEntity entity){
		validate(entity);
		
		final Optional<TodoEntity> original = todoResponsitory.findById(entity.getId());
		
		original.ifPresent(todo->{
			todo.setTitle(entity.getTitle());
			todo.setDone(entity.isDone());
			todoResponsitory.save(todo);
		});

		return retrieve(entity.getUserid());
	}
	
	//삭제
	public List<TodoEntity> delete(final TodoEntity entity){
		validate(entity);
		
		try {
			todoResponsitory.delete(entity);
		} catch (Exception e) {
			log.error("error",entity.getId(),e);
			throw new RuntimeException(entity.getId());
		}
		return retrieve(entity.getUserid());
	}
	
}
