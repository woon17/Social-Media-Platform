package com.dxc.smp.service;

import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.dxc.smp.entity.Post;
import com.dxc.smp.entity.Role;
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

	@Autowired
	private FilesStorageService filesStorageService;

	// create a new post
	public void createPostWithFile(Post post, MultipartFile multipartFile) {
		filesStorageService.init();
		System.out.println("create media file post... for: " + post);
		User user = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
		post.setUser(user);
		postRepository.save(post); // save to get the correct new post id to create storage folder
		Path linkPath = filesStorageService.save(multipartFile, post);
		post.setLink(linkPath.toString());
		postRepository.save(post); // save correct link
		System.out.println("create post successfully");

	}

	public void createPostWithHyperlink(Post post) {
		System.out.println("create hyperlink post... for: " + post);
		User user = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
		post.setUser(user);
		post.setLink(post.getLink());
		postRepository.save(post);
		System.out.println("create hyperlink post successfully");

	}

	// read by Id
	public Post getPostById(int id) {
		Post post = postRepository.findById(id);
		return post;
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
		return posts;
	}

	public void updatePostById(int id, Post post) {

		String loginUserName = SecurityContextHolder.getContext().getAuthentication().getName();
		System.out.println(userRepository.findByUserName(loginUserName).getRole());
		if (loginUserName.equals(post.getUser().getUserName()) || checkRole(loginUserName, "Admin")) {
			System.out.println("new post: " + post);
			Post oldPost = getPostById(id);

			// unless the link change, otherwise ignore the type change
			if (oldPost.getLink().equals(post.getLink())) {
				post.setType(oldPost.getType());
			}else {
				if(!oldPost.getType().equals("hyperlink")) {// has media file in storage
					filesStorageService.deleteMediaFile(oldPost.getLink());
				}
			}
			post.setUser(oldPost.getUser());
			post.setId(id);
			postRepository.save(post);
		}
	}

	// delete by Id
	public void deletePost(int id) {
		Post post = postRepository.findById(id);
		System.out.println("---------" + post);
		postRepository.deleteById(id);
		if (!post.getType().equals("hyperlink")) {
			filesStorageService.deletePost(post);
		}
	}

	public int increaseViews(int postId) {
		Post post = getPostById(postId);
		int newView = post.getViews() + 1;
		post.setViews(newView);
		postRepository.save(post);
		return newView;

	}

	public Page<Post> findAll(Pageable paging) {
		// TODO Auto-generated method stub
		return postRepository.findAll(paging);
	}

	public void updatePostByIdWithFile(int postId, String type, String caption, MultipartFile multipartFile) {
		String loginUserName = SecurityContextHolder.getContext().getAuthentication().getName();
		Post post = getPostById(postId);
		if (loginUserName.equals(post.getUser().getUserName()) || checkRole(loginUserName, "Admin")) {
			filesStorageService.init();
			System.out.println("update media file post... for: " + post);
			post.setCaption(caption);
			if (!post.getType().equals("hyperlink")) {
				filesStorageService.deleteMediaFile(post.getLink());
			}
			post.setType(type);
			Path linkPath = filesStorageService.save(multipartFile, post);
			System.out.println("new linkPath: " + linkPath);
			post.setLink(linkPath.toString());
			postRepository.save(post); // save correct link
			System.out.println("create post successfully");
		}
	}


	boolean checkRole(String loginUserName, String roleName) {
		Set<Role> roleSet = userRepository.findByUserName(loginUserName).getRole();

		Iterator<Role> it = roleSet.iterator();
		while (it.hasNext()) {
			Role role = it.next();
			if (role.getRoleName().equals(roleName)) {
				return true;
			}
		}
		return false;
	}

}
