package com.dxc.smp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dxc.smp.entity.Post;
import com.dxc.smp.entity.User;
import com.dxc.smp.service.PostService;
import com.dxc.smp.service.StorageService;
import com.dxc.smp.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/api/v0")
@PreAuthorize("hasRole('User')")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private PostService postService;

	@Autowired
	private StorageService storageService;

	@PostMapping({ "/createPostWithFile" })
	public Post createPostWithFile(@RequestParam("type") String type, @RequestParam("caption") String caption,
			@RequestPart("file") MultipartFile multipartFile) {
		Post post = new Post(type, caption, 0);
		return postService.createPost(post, multipartFile);
	}

	@PostMapping({ "/createPostWithHyperlink" })
	public Post createPostWithHyperlink(@RequestBody Post post) {
		return postService.createPostWithHyperlink(post);
	}

	@GetMapping({ "/forUser" })
	public List<Post> forUser() {
		return postService.getPosts();
	}

	@PostMapping({ "/getPostByUserName/{userName}" })
	public List<Post> getAllPosts(@PathVariable("userName") String userName) {
		return postService.getPostsByUserName(userName);
	}

}