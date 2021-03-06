package com.dxc.smp.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import com.dxc.smp.entity.Post;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {
	private final Path root = Paths.get("Storage");

	@Override
	public void init() {
		try {
			Files.createDirectory(root);
		} catch (IOException e) {
			System.out.println("root(Storage folder) is already existing");
		}
	}
	
	public Path save(MultipartFile file, Post post) {
		try {
			System.out.println("enter uploadToLocal");
			Path mediaPath = getPostStorageFolder(post).resolve(file.getOriginalFilename());
			System.out.println("mediaPath: " + mediaPath);
			Path postFolder = root.resolve(getPostStorageFolder(post));
			if (!Files.exists(postFolder)) {
				Files.createDirectories(root.resolve(getPostStorageFolder(post)));
			}
			Files.copy(file.getInputStream(), root.resolve(mediaPath));
			return mediaPath;
		} catch (Exception e) {
			throw new RuntimeException("FAIL!");
		}
	}

	public Path getPostStorageFolder(Post post) {
		Path postFolder = Paths.get(post.getUser().getUserName() + "//" + post.getId());
		return postFolder;
	}


	@Override
	public Resource load(String filename) {
		System.out.println("load filename" + filename);
		try {

			Path file = root.resolve((filename));
			System.out.println("file: " + file.toString());
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("Could not read the file!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("Error: " + e.getMessage());
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(root.toFile());
	}

	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
		} catch (IOException e) {
			throw new RuntimeException("Could not load the files!");
		}
	}

	@Override
	public void deletePost(Post post) {
		// TODO Auto-generated method stub
		Path postFolder = root.resolve(Paths.get(post.getLink()).getParent());
		System.out.println("postFolder: " + postFolder);
		FileSystemUtils.deleteRecursively(postFolder.toFile());
	}

	@Override
	public void deleteUserByUserName(String userName) {
		// TODO Auto-generated method stub
		Path userFolder = root.resolve(Paths.get(userName));
		System.out.println("userFolder: " + userFolder);
		FileSystemUtils.deleteRecursively(userFolder.toFile());
	}

	@Override
	public Path updatePostFile(MultipartFile file, Post post) {
		try {
			System.out.println("enter uploadToLocal");
			Path mediaPath = getPostStorageFolder(post).resolve(file.getOriginalFilename());
			System.out.println("mediaPath: " + mediaPath);
			Files.createDirectories(root.resolve(getPostStorageFolder(post)));
			Files.copy(file.getInputStream(), root.resolve(mediaPath));
			return mediaPath;
		} catch (Exception e) {
			throw new RuntimeException("FAIL!");
		}
	}

	@Override
	public void deleteMediaFile(String mediaLink) {
		// TODO Auto-generated method stub
		try {
			System.out.println("delete before");
			System.out.println(Paths.get(mediaLink));
			FileSystemUtils.deleteRecursively(root.resolve(Paths.get(mediaLink)));
			System.out.println("delete after");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}