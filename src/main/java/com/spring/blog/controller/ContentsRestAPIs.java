package com.spring.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.blog.model.Contents;
import com.spring.blog.repository.ContentsRepository;

@RestController
@RequestMapping("/blog")
public class ContentsRestAPIs {
	
	@Autowired
	private ContentsRepository contentsRepository;
	
	@PostMapping("/insert")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> insertContents(@RequestBody Contents contents) {
		contentsRepository.save(contents);
		return ResponseEntity.ok("success");
	}
	
	@GetMapping("/contentsList")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> getContentsList() {
		return ResponseEntity.ok(contentsRepository.findAll());
	}
	
	@GetMapping("/contentsView")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> getContentsView(@RequestBody long contIdx) {
		
		Contents contents =	contentsRepository.getOne(contIdx);
		
		if(contents == null) {
		   return new ResponseEntity<String>("Fail -> contIdx is not vaild!", HttpStatus.BAD_REQUEST);
		}
		
		return ResponseEntity.ok("success");
	}
	
	@PutMapping("/update")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> updateContents(@RequestBody long contIdx ,@RequestBody String title, @RequestBody String contents){
		
		Contents selectedContents =	contentsRepository.getOne(contIdx);
		
		if(selectedContents == null) {
		   return new ResponseEntity<String>("Fail -> contIdx is not vaild!", HttpStatus.BAD_REQUEST);
		}
		
		selectedContents.setTitle(title);
		selectedContents.setContents(contents);
		
		contentsRepository.save(selectedContents);
		
		return ResponseEntity.ok("success");
	}
	
	@DeleteMapping("/delete")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> deleteContents(@RequestBody long contIdx){
		contentsRepository.deleteById(contIdx);
		return ResponseEntity.ok("success");
	}
}
