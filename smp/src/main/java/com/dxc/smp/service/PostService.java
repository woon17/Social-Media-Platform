package com.dxc.smp.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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


	// create a new post
	public Post createPost(Post post) {
		System.out.println("post.getCreatedBy: " + post.getCreatedBy());
		User user = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
		post.setUser(user);
		return postRepository.save(post);
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

	// read all posts
	public List<Post> getAllPost() {
		List<Post> posts = (List<Post>) postRepository.findAll();
		return posts;
	}

	// update: front end should send post with all fields
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

}