package com.example.sportspie.bounded_context.auth.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.sportspie.bounded_context.auth.config.S3Config;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class S3Service {
	private final S3Config s3Config;
	private final AmazonS3 s3Client;

	public String uploadFile(MultipartFile multipartFile) {

		String filePath = "user/" + getUUidFileName(multipartFile.getOriginalFilename());

		try {
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(multipartFile.getSize());
			metadata.setContentType(multipartFile.getContentType());
			InputStream inputStream = multipartFile.getInputStream();

			s3Client.putObject(new PutObjectRequest(s3Config.getBucketName(), filePath, inputStream, metadata)
					.withCannedAcl(CannedAccessControlList.PublicRead));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// https://kr.object.ncloudstorage.com/bucket-name/user/image.png
		return "https://kr.object.ncloudstorage.com/" + s3Config.getBucketName() + "/" + filePath;
	}

	private String getUUidFileName(String fileName) {
		String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
		return UUID.randomUUID().toString() + "." + ext;
	}
}
