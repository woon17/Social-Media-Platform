package com.dxc.smp.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dxc.smp.entity.Post;

@Service
public class StorageService {

	private final Path  rootLocation = Paths.get("upload-dir");
	private final String uploadFolderPath = "D:\\final-assessment\\uploaded";
	public Path uploadToLocal(MultipartFile file, Post post) {
		try {
			System.out.println("enter uploadToLocal");
			Path postFolder = getPostStorageFolder(post);
			Files.createDirectories(postFolder);
			Path mediaPath = postFolder.resolve(file.getOriginalFilename());
			Files.copy(file.getInputStream(), mediaPath);
			return mediaPath;
		} catch (Exception e) {
			throw new RuntimeException("FAIL!");
		}
	}
	
	public Path getUserStorageFolder(Post post) {
		Path userFolder = Paths.get(uploadFolderPath + "//" + post.getUser().getUserName());
		return userFolder;
	}
	
	public Path getPostStorageFolder(Post post) {
		Path postFolder = Paths.get(uploadFolderPath + "//" + post.getUser().getUserName() + "//" + post.getId());
		return postFolder;
	}
	public Resource loadFile(String filename) {
		try {
			Path file = rootLocation.resolve(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("FAIL!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("FAIL!");
		}
	}

	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	public void init() {
		try {
			System.out.println("enter init");
			Files.createDirectory(rootLocation);
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize storage!");
		}
	}
}