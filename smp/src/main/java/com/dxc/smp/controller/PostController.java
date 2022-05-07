package com.dxc.smp.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.dxc.smp.entity.Post;
import com.dxc.smp.service.PostService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/api/v0")
@PreAuthorize("hasAnyRole('Admin','User')")
public class PostController {

	@Autowired
	private PostService postService;

	@GetMapping("/getAllPosts")
	public ResponseEntity<Map<String, Object>> getAllPosts(@RequestParam(required = false) String title,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		try {
			System.out.println("/getAllPosts");
			List<Post> posts = new ArrayList<>();
			System.out.println("enter sorting post api");
			Pageable paging = PageRequest.of(page, size, Sort.by("createdDate").descending());

			Page<Post> pagePosts = postService.findAll(paging);
			posts = pagePosts.getContent();
			Map<String, Object> response = new HashMap<>();
			response.put("posts", posts);
			response.put("currentPage", pagePosts.getNumber());
			response.put("totalItems", pagePosts.getTotalElements());
			response.put("totalPages", pagePosts.getTotalPages());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping({ "/getPostById/{id}" })
	@ResponseBody
	public Post getPostById(@PathVariable("id") int id) {
		return postService.getPostById(id);
	}

	@DeleteMapping({ "/deletePost/{id}" })
	public void deletePostById(@PathVariable("id") int id) {
		postService.deletePost(id);
	}

	@PutMapping({ "/updatePost/{id}" })
	public void updatePostById(@PathVariable("id") int id, @RequestBody Post post) {
		postService.updatePostById(id, post);
	}
	
	@PostMapping({ "/updatePostWithFile/{id}" })
	public void updatePostWithFile(@PathVariable("id") int id, @RequestPart("type") String type, @RequestPart("caption") String caption, @RequestPart("file") MultipartFile multipartFile) {
		postService.updatePostByIdWithFile(id, type,caption,multipartFile);
	}

	@PutMapping({ "/addViewsCount/{id}" })
	public int addViewsCount(@PathVariable("id") int id) {
		return postService.increaseViews(id);
	}
}