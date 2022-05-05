package com.dxc.smp.service;

import java.nio.file.Path;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	// @Autowired
	// private StorageService storageService;

	@Autowired
	private FilesStorageService filesStorageService;

	private final String uploadFolderPath = "D:\\final-assessment\\uploaded";

	// create a new post
	public Post createPostWithFile(Post post, MultipartFile multipartFile) {
		filesStorageService.init();
		System.out.println("create media file post... for: " + post);
		User user = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
		post.setUser(user);
		postRepository.save(post); // save to get the correct new post id to create storage folder
		Path linkPath = filesStorageService.save(multipartFile, post);
		post.setLink(linkPath.toString());
		postRepository.save(post); // save correct link
		System.out.println("create post successfully");
		return post;
	}

	public Post createPostWithHyperlink(Post post) {
		System.out.println("create hyperlink post... for: " + post);
		User user = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
		post.setUser(user);
		post.setLink(post.getLink());
		postRepository.save(post);
		System.out.println("create hyperlink post successfully");
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

//		File file = new File("src/test/resources/input.txt");
//		FileInputStream input = new FileInputStream(file);
//		MultipartFile multipartFile = new MockMultipartFile("file",
//		            file.getName(), "text/plain", IOUtils.toByteArray(input));
//		
		return posts;
	}

	// read all posts
	public List<Post> getAllPost() {
		System.out.println("before");
		List<Post> posts = (List<Post>) postRepository.findAll();
		return posts;
	}

	public void updatePostById(int id, Post post) {
		System.out.println("new post: " + post);
		Post oldPost = getPostById(id);

		post.setUser(oldPost.getUser());
		post.setId(id);
		postRepository.save(post);
	}

	// delete by Id
	public void deletePost(int id) {
		Post post = postRepository.findById(id);
		System.out.println("---------" + post);
		if (!post.getType().equals("hyperlink")) {
			filesStorageService.deletePost(post);
		}
		postRepository.deleteById(id);
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
		Post post = getPostById(postId);
		filesStorageService.init();
		System.out.println("update media file post... for: " + post);
		User user = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
		post.setCaption(caption);
		post.setType(type);
		filesStorageService.deleteMediaFile(post.getLink());
		Path linkPath = filesStorageService.save(multipartFile, post);
		System.out.println("new linkPath: " + linkPath);
		post.setLink(linkPath.toString());
		postRepository.save(post); // save correct link
		System.out.println("create post successfully");
	}

//	public Path uploadToLocal(MultipartFile file, Post post) {
//		try {
//			System.out.println("enter uploadToLocal");
//			Path postFolder = Paths.get(uploadFolderPath + "//" + post.getUser().getUserName() + "//" + post.getId());
//			Files.createDirectories(postFolder);
//			Path mediaPath = postFolder.resolve(file.getOriginalFilename());
//			Files.copy(file.getInputStream(), mediaPath);
//			return mediaPath;
//		} catch (Exception e) {
//			throw new RuntimeException("FAIL!");
//		}
//	}

}
