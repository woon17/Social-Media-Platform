package com.dxc.smp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import com.dxc.smp.service.FilesStorageService;

@Controller
//@CrossOrigin(origins = "http://localhost:4200/")
public class FilesController {
	@Autowired
	FilesStorageService storageService;

	@GetMapping("/files/{user}/{postid}/{filename}")
	@ResponseBody
//	@PreAuthorize("hasAnyRole('Admin','User')")
	public ResponseEntity<Resource> getFile(@PathVariable String user, @PathVariable String postid, @PathVariable String filename) {
		System.out.println("enter getFile in controller");
		// storageService.init();

		Resource file = storageService.load(user+"//"+postid+"//"+filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}
	
//	method 2
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


}