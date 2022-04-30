package com.dxc.smp.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService{
	private final String uploadFolderPath = "D:\\final-assessment\\uploaded";
	public void uploadToLocal(MultipartFile file) {
        try {
        	System.out.println("enter uploadToLocal");
            Files.copy(file.getInputStream(), Paths.get(uploadFolderPath).resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException("FAIL!");
        }
	}
}
