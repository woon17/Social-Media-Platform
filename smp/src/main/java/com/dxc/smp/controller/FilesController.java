package com.dxc.smp.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.dxc.smp.entity.FileInfo;
import com.dxc.smp.payload.response.MessageResponse;
import com.dxc.smp.service.FilesStorageService;
import org.springframework.core.io.ByteArrayResource;

import java.io.IOException;
import java.nio.file.Files;
@Controller
@CrossOrigin(origins="http://localhost:4200")
public class FilesController {
	@Autowired
	FilesStorageService storageService;

	// @PostMapping("/upload")
	// public ResponseEntity<MessageResponse> uploadFile(@RequestParam("file") MultipartFile file) {
	// 	String message = "";
	// 	try {
	// 		System.out.println("enter uploadFile in controller");
	// 		storageService.init();
	// 		storageService.save(file);
	// 		message = "Uploaded the file successfully: " + file.getOriginalFilename();
	// 		return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
	// 	} catch (Exception e) {
	// 		message = "Could not upload the file: " + file.getOriginalFilename() + "!";
	// 		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
	// 	}
	// }

//	@GetMapping("/files")
//	@PreAuthorize("hasAnyRole('Admin','User')")
//	public ResponseEntity<List<FileInfo>> getListFiles() {
//		List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
//			String filename = path.getFileName().toString();
//			String url = MvcUriComponentsBuilder
//					.fromMethodName(FilesController.class, "getFile", path.getFileName().toString()).build().toString();
//			return new FileInfo(filename, url);
//		}).collect(Collectors.toList());
//		return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
//	}

	// @GetMapping("/files/{filename:.+}")
	// @ResponseBody
	// public ResponseEntity<Resource> getFile(@PathVariable String filename) {
	// 	System.out.println("enter getFile in controller");
	// 	// storageService.init();
	// 	Resource file = storageService.load(filename);
	// 	return ResponseEntity.ok()
	// 			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
	// 			.body(file);
	// }

	@GetMapping("/files/{user}/{postid}/{filename}")
	@ResponseBody
	public ResponseEntity<Resource> getFile(@PathVariable String user, @PathVariable String postid, @PathVariable String filename) {
		System.out.println("enter getFile in controller");
		// storageService.init();

		Resource file = storageService.load(user+"//"+postid+"//"+filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}
	
//	@GetMapping("/files/{user}/{postid}/{filename}")
//	@ResponseBody
//	public ResponseEntity<Resource> getVideo(@PathVariable String user, @PathVariable String postid, @PathVariable String filename) {
//		System.out.println("enter getFile in controller");
//		// storageService.init();
//		
////		Resource file = storageService.load(user+"//"+postid+"//"+filename);
//		byte[] bytes = null;
//		try {
//			bytes = Files .readAllBytes(storageService.getPostStorageVideo(user+"//"+postid+"//"+filename));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return ResponseEntity.ok(new ByteArrayResource(bytes));
//	}

//	@GetMapping("/files/{id}")
//	@ResponseBody
//	@PreAuthorize("hasAnyRole('User')")
//	public ResponseEntity<?> getFile(@PathVariable("id") int id) {
//		Resource file = storageService.loadFile(id);
//		return ResponseEntity.ok()
//				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
//						file.getFilename() + "\"")
//				.body(file);
//	}
}