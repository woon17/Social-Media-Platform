package com.dxc.smp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.dxc.smp.entity.MediaPost;
import com.dxc.smp.entity.Post;
import com.dxc.smp.payload.response.MessageResponse;
import com.dxc.smp.service.PostService;
import com.dxc.smp.service.StorageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.dxc.smp.service.FileUploadService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private StorageService storageService;

	@PostMapping({ "/createPost" })
	@PreAuthorize("hasRole('User')")
	public Post createPost(@RequestParam("type") String type, @RequestParam("caption") String caption,
			@RequestPart("file") MultipartFile multipartFile) {
		Post post = new Post(type, caption, 0);
		return postService.createPost(post, multipartFile);
	}

	@GetMapping({ "/getAllPosts" }) // all posts show at home page
	public List<Post> getAllPosts() {
		System.out.println("----------------------reach here------------------------");
		return postService.getAllPost();
	}

	@PostMapping({ "/getPostByUserName/{userName}" })
	@PreAuthorize("hasRole('User')")
	public List<Post> getAllPosts(@PathVariable("userName") String userName) {
		return postService.getPostsByUserName(userName);
	}

	@GetMapping({ "/getPostById/{id}" })
	@PreAuthorize("hasAnyRole('Admin','User')")
	@ResponseBody
	public Post getPostById(@PathVariable("id") int id) {
		return postService.getPostById(id);
	}

	@DeleteMapping({ "/deletePost/{id}" })
	@PreAuthorize("hasRole('Admin')")
	public void deletePostById(@PathVariable("id") int id) {
		postService.deletePost(id);
	}

	@PutMapping({ "/updatePost/{id}" })
	@PreAuthorize("hasAnyRole('Admin','User')")
	public void updatePostById(@PathVariable("id") int id, @RequestBody Post post) {
		postService.updatePostById(id, post);
	}

	@GetMapping({ "/forUser" })
	@PreAuthorize("hasRole('User')")
	public List<Post> forUser() {
		return postService.getPosts();
	}

	@PostMapping({ "/addViewsCount/{id}" })
	public int addViewsCount(@PathVariable("id") int id) {
		return postService.increaseViews(id);
	}
}