package com.dxc.smp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dxc.smp.entity.Post;
import com.dxc.smp.service.PostService;

import java.util.List;


@RestController
public class PostController {

	@Autowired
	private PostService postService;

	@PostMapping({ "/createPost" })
	@PreAuthorize("hasRole('User')")
	public Post createPost(@RequestBody Post post) {
		return postService.createPost(post);
	}

	@GetMapping({ "/getAllPosts" }) // all posts show at home page
	public List<Post> getAllPosts() {
		return postService.getAllPost();
	}

	@PostMapping({ "/getPostByUserName/{userName}" })
	@PreAuthorize("hasRole('User')")
	public List<Post> getAllPosts(@PathVariable("userName") String userName) {
		return postService.getPostsByUserName(userName);
	}

	@PostMapping({ "/getPostById/{id}" })
	@PreAuthorize("hasAnyRole('Admin','User')")
	public Post getPostById(@PathVariable("id") int id) {
		return postService.getPostById(id);
	}

	@PostMapping({ "/deletePost/{id}" })
	@PreAuthorize("hasRole('Admin')")
	public void deletePostById(@PathVariable("id") int id) {
		postService.deletePost(id);
	}

	@PostMapping({ "/updatePost/{id}" })
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