package com.dxc.smp.service;

import java.nio.file.Path;
import java.util.stream.Stream;

import com.dxc.smp.entity.Post;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageService {
	public void init();

	public Path save(MultipartFile file, Post post);

	public Resource load(String filename);

	public void deleteAll();

	public Stream<Path> loadAll();

	public void deletePost(Post post);

	public void deleteUserByUserName(String userName);

	public Path updatePostFile(MultipartFile multipartFile, Post post);

	public void deleteMediaFile(String link);

//	public Path getPostStorageVideo(String string);
}