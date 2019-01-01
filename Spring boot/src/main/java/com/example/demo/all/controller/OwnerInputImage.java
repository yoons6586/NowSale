package com.example.demo.all.controller;

import com.example.demo.all.mapper.AllMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/all")
public class OwnerInputImage {

	private AllMapper allMapper;

	public OwnerInputImage(AllMapper allMapper){
		this.allMapper = allMapper;
	}

	@GetMapping("/owner/signUp")
	public String ownerSignUp(){ return "ownerSignUp"; }

	@GetMapping("/owner/input/menu/img")
	public String ownerInputMenuImg(){ return "ownerMenuImg"; }

	@PostMapping("/api/upload/menu")
	public ResponseEntity<?> uploadFileMulti(
			@RequestParam("id") String id,
			@RequestParam("files") MultipartFile[] uploadfiles,
			@RequestParam("name") String name,
			@RequestParam("price") String price
	) {
		Integer owner_key;
		try {
			 owner_key= allMapper.getOwnerKey(id);
			System.out.println("owner_key : "+owner_key);
		} catch(Exception e){
			e.printStackTrace();
			return new ResponseEntity<>("No ID",HttpStatus.UNAUTHORIZED);
		}

		// Get file name
		String uploadedFileName = Arrays.stream(uploadfiles).map(x -> x.getOriginalFilename())
				.filter(x -> !StringUtils.isEmpty(x)).collect(Collectors.joining(" , "));

		if (StringUtils.isEmpty(uploadedFileName)) {
			return new ResponseEntity("please select a file!", HttpStatus.OK);
		}

		try {
			saveUploadedFiles(Arrays.asList(uploadfiles),owner_key,name,price);

		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity("Successfully uploaded - "
				+ uploadedFileName, HttpStatus.OK);

	}

	private void saveUploadedFiles(List<MultipartFile> files,int owner_key,String name,String price) throws IOException {

		for (MultipartFile file : files) {
			if (file.isEmpty()) {
				continue; //next pls
			}

			String filename = owner_key+"_"+file.getOriginalFilename();
			String directory = "src/main/resources/static/drawable/owner/menu";
			String filepath = Paths.get(directory, filename).toString();

			BufferedOutputStream stream =
					new BufferedOutputStream(new FileOutputStream(new File(filepath)));
			stream.write(file.getBytes());

			allMapper.insertMenuImg(owner_key,"/drawable/owner/menu/"+filename,name,price);

			stream.close();




			/*byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
			Files.write(path, bytes);
			*/
		}

	}
}
