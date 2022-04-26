package com.dxc.smp.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxc.smp.entity.Post;
import com.dxc.smp.repository.PostRepository;

@Service
public class PostService {
	@Autowired
	private PostRepository postRepository;

	// create a new post
	public Post createPost(Post post) {
		return postRepository.save(post);
	}

	// read by Id
	public Post getPostById(int id) {
		Post post = postRepository.findById(id);
		return post;
	}

	// read by username
	public List<Post> getPostsByUserName(String userName) {
		List<Post> posts = (List<Post>) postRepository.findByUserName(userName);
		return posts;
	}

	// read all posts
	public List<Post> getAllPost() {
		List<Post> posts = (List<Post>) postRepository.findAll();
		return posts;
	}

	// update
	public void updatePostById(int id, Post post) {
		if (id == post.getId()) {
			postRepository.save(post);
		}
	}
	
	// delete by Id
	public void deletePost(int id) {
		postRepository.deleteById(id);
	}

}
