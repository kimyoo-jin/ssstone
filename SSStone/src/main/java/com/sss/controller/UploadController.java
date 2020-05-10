package com.sss.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sss.domain.ProductImgDTO;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@Log4j
public class UploadController {
	
	//메인 사진 업로드
	@GetMapping("/uploadMainProduct")
	public void uploadMain()
	{
		log.info("upload main");
	}
//	@PreAuthorize("isAuthenticated()")
	@PostMapping(value = "/uploadMainProduct", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<ProductImgDTO>>uploadaminPost(MultipartFile[] uploadMainFile){
		List<ProductImgDTO>list = new ArrayList<ProductImgDTO>();
		String uploadFolder = "C://sts-bundle/stswk/SSStone/src/main/webapp/resources/img/upload";
		String uploadFolderPath = getFolder();							//forder maker
		File uploadPath = new File(uploadFolder, getFolder());			//저장되는 폴더 경로
		if(uploadPath.exists() == false){
			uploadPath.mkdirs();
			System.out.println("폴더 만듬");
		}
		for(MultipartFile multipartFile : uploadMainFile){
			ProductImgDTO productDTO = new ProductImgDTO();
			String uploadFileName = multipartFile.getOriginalFilename();
			uploadFileName = uploadFileName.substring(uploadFileName.indexOf("//")+1);
			uploadFileName = "main_" + uploadFileName;
			log.info("only file name : " + uploadFileName);
			productDTO.setP_filename(uploadFileName);
			UUID uuid = UUID.randomUUID();
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			
			try{
				File saveFile = new File(uploadPath,uploadFileName);
				multipartFile.transferTo(saveFile);
				productDTO.setP_uuid(uuid.toString());
				productDTO.setP_filepath(uploadFolderPath);
				if(checkImageType(saveFile)){		//섬네일 만듦
					productDTO.setP_filetype(true);
					
					FileOutputStream thumnail = new FileOutputStream(new File(uploadPath,"s_"+uploadFileName));
					log.info("썸네일 만들었음");
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumnail, 100,100);
					thumnail.close();
				}
				list.add(productDTO);
			}catch(Exception e)
			{
				log.error(e.getMessage());
			}
		}
		log.info("리턴함");
		return new ResponseEntity<List<ProductImgDTO>>(list,HttpStatus.OK);
	}

//	@PreAuthorize("isAuthenticated()")
	@PostMapping(value = "/uploadSubProduct", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)		//서브파일 업로드
	@ResponseBody
	public ResponseEntity<List<ProductImgDTO>>uploadSubPost(MultipartFile[] uploadFile){
		log.info("들어옴");
		List<ProductImgDTO>list = new ArrayList<ProductImgDTO>();
		String uploadFolder = "C://sts-bundle/stswk/SSStone/src/main/webapp/resources/img/upload";
		
		String uploadFolderPath = getFolder();							//forder maker
		File uploadPath = new File(uploadFolder, getFolder());			//저장되는 폴더 경로
		if(uploadPath.exists() == false){
			uploadPath.mkdirs();
			System.out.println("폴더 만듬");
		}
		
		for(MultipartFile multipartFile : uploadFile){
			ProductImgDTO productDTO = new ProductImgDTO();
			
			String uploadFileName = multipartFile.getOriginalFilename();
			uploadFileName = uploadFileName.substring(uploadFileName.indexOf("//")+1);
			uploadFileName = "sub_" + uploadFileName;
			log.info("only file name : " + uploadFileName);
			productDTO.setP_filename(uploadFileName);
			
			UUID uuid = UUID.randomUUID();
			
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			
			try{
				File saveFile = new File(uploadPath,uploadFileName);
				multipartFile.transferTo(saveFile);
				productDTO.setP_uuid(uuid.toString());
				productDTO.setP_filepath(uploadFolderPath);
				if(checkImageType(saveFile)){		//섬네일 만듦
					productDTO.setP_filetype(true);
				}
				
				list.add(productDTO);
			}catch(Exception e)
			{
				log.error(e.getMessage());
			}
		}
		return new ResponseEntity<List<ProductImgDTO>>(list,HttpStatus.OK);
	}
	//서브 파일 올리기 끝
	
	@GetMapping("/display")		//보여주기
	@ResponseBody
	public ResponseEntity<byte[]>getFile(String fileName){
		log.info("fileName : " + fileName);
		File file = new File("C://sts-bundle/stswk/SSStone/src/main/webapp/resources/img/upload/"+fileName);
		log.info("file : " + file);
		ResponseEntity<byte[]> result = null;
		try{
			HttpHeaders header = new HttpHeaders();
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file),header,HttpStatus.OK);
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	//폴더 가져오기
	private String getFolder()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);
		return str.replace("-", File.separator);
	}
	private boolean checkImageType(File file){			//이미지 체크
		try{
			String contentType = Files.probeContentType(file.toPath());
			return contentType.startsWith("image");
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	@GetMapping(value="/download", produces= MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent")String userAgent,String fileName){		//다운로드
		Resource resource = new FileSystemResource("c://upload//" + fileName);
		if(resource.exists() == false)
		{
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		}
		String resourceName = resource.getFilename();
		String resourceOriginalName = resourceName.substring(resourceName.lastIndexOf("_")+1);
		HttpHeaders headers = new HttpHeaders();
		try{																				//브라우저에 따라서 경로 이름 바꿔주기
			String downloadName = null;
			if(userAgent.contains("Trident"))
			{
				log.info("IE browser");
				downloadName = URLEncoder.encode(resourceOriginalName,"UTF-8").replace("//+", " ");
			}
			else if(userAgent.contains("Edge"))
			{
				log.info("Edge browser");
				downloadName = URLEncoder.encode(resourceOriginalName,"UTF-8");
				log.info("Edge name : " + downloadName);
			}else{
				log.info("Chrome browser");
				downloadName = new String(resourceOriginalName.getBytes("UTF-8"),"ISO-8859-1");
			}
			
			headers.add("Content-Disposition", "attachment; filename="+downloadName);
		}catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return new ResponseEntity<Resource>(resource,headers,HttpStatus.OK);
	}
	
//	@PreAuthorize("isAuthenticated()")
	@PostMapping("/deleteFile")
	@ResponseBody
	public ResponseEntity<String>deleteFile(String fileName, String type){			//파일 지우기
		log.info("deleteFile : " + fileName);
		File file;
		try{
			file = new File("C://sts-bundle/stswk/SSStone/src/main/webapp/resources/img/upload//" + URLDecoder.decode(fileName,"UTF-8"));
			file.delete();
			if(type.equals("image")){
				String largeFileName = file.getAbsolutePath().replace("s_", "");
				largeFileName = file.getAbsolutePath().replace("sub_s_", "");
				log.info("largeFileName : " + largeFileName);
				file = new File(largeFileName);
				file.delete();
			}
			
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("deleted",HttpStatus.OK);
	}
}
