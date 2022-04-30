package com.dxc.smp.service;

import java.io.Console;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dxc.smp.entity.Post;
import com.dxc.smp.entity.User;
import com.dxc.smp.repository.PostRepository;
import com.dxc.smp.repository.UserRepository;

@Service
@Transactional
public class PostService {
	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepository userRepository;

	private final String uploadFolderPath = "D:\\final-assessment\\uploaded";

	// create a new post
	public Post createPost(Post post, MultipartFile multipartFile) {
		System.out.println("create post... for: " + post.getId());
		User user = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
		post.setUser(user);
		postRepository.save(post); // save to get the correct new post id to create storage folder
		Path linkPath = uploadToLocal(multipartFile, post.getId());
		post.setLink(linkPath.toString());
		postRepository.save(post); // save correct link
		System.out.println("create post successfully");
		return post;
	}

	// read by Id
	public Post getPostById(int id) {
		Post post = postRepository.findById(id);
		return post;
	}

	// read by username
	public List<Post> getPostsByUserName(String userName) {
		User user = userRepository.findByUserName(userName);
		List<Post> posts = (List<Post>) postRepository.findByUser(user);
		return posts;
	}

	public List<Post> getPosts() {
		String loginUserName = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.findByUserName(loginUserName);
		List<Post> posts = (List<Post>) postRepository.findByUser(user);
		return posts;
	}

	// read all posts
	public List<Post> getAllPost() {
		System.out.println("before");
		List<Post> posts = (List<Post>) postRepository.findAll();
		System.out.println("after");
		System.out.println("posts" + posts);
		return posts;
	}

	// update: front end should send post with all fields
	// update post: type, caption, link, view
	public void updatePostById(int id, Post post) {
		System.out.println("new post: " + post);
		Post oldPost = getPostById(id);

		post.setUser(oldPost.getUser());
		post.setId(id);
		postRepository.save(post);
	}

	// delete by Id
	public void deletePost(int id) {
		postRepository.deleteById(id);
	}

	public int increaseViews(int postId) {
		Post post = getPostById(postId);
		int newView = post.getViews() + 1;
		post.setViews(newView);
		postRepository.save(post);
		return newView;

	}

	public Path uploadToLocal(MultipartFile file, int postId) {
		try {
			System.out.println("enter uploadToLocal");
			Files.createDirectories(Paths.get(uploadFolderPath + "//" + postId));
			Path mediaPath = Paths.get(uploadFolderPath + "//" + postId).resolve(file.getOriginalFilename());
			Files.copy(file.getInputStream(), mediaPath);
			return mediaPath;
		} catch (Exception e) {
			throw new RuntimeException("FAIL!");
		}
	}

}
