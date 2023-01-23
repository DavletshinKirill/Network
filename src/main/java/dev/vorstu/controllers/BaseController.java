package dev.vorstu.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.vorstu.db.entities.AuthUserEntity;
import dev.vorstu.db.repositories.AuthUserRepo;

@RestController
@RequestMapping("api/base") 
class BaseController {
	
	@Autowired
	private AuthUserRepo authUserRepo;
	
	private Long counter = 0L;
	
	
	@GetMapping("check")
	public String getAllUsers()
	{
		return "Hello world" + new Date();
	}
	
	@GetMapping("students")
	public ArrayList<AuthUserEntity> getAllStudents()
	{
		return (ArrayList<AuthUserEntity>) authUserRepo.findAll();
	}
	
	@PostMapping(value="students", produces = MediaType.APPLICATION_JSON_VALUE)
	public AuthUserEntity createStudent(@RequestBody AuthUserEntity newStudent)
	{
		return authUserRepo.save(newStudent);
	}
	
//	@PutMapping(value = "students", produces = MediaType.APPLICATION_JSON_VALUE)
//	public AuthUserEntity changeStudent(@RequestBody AuthUserEntity student)
//	{
//		AuthUserEntity changingStudent = new AuthUserEntity(true, student.getName(), student.getMainPhoto(), student.getPassword());		
//		return authUserRepo.save(changingStudent);
//	}
	
	
	@DeleteMapping(value = "students/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Long deleteStudent(@PathVariable("id")Long id)
	{
		authUserRepo.deleteById(id);
		return id;
	}
}
	

	