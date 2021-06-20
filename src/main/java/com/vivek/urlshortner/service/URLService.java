package com.vivek.urlshortner.service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.hash.Hashing;
import com.vivek.urlshortner.dto.URLDto;
import com.vivek.urlshortner.model.URLModel;
import com.vivek.urlshortner.repository.URLRepository;

@Service
public class URLService {
	
	@Autowired
	URLRepository urlRepository;
	
	public List<URLModel> getUrl(){
		return (List<URLModel>) urlRepository.findAll();
	}
	
	public URLDto getLongURL(String shorturl) {
		URLDto urlDto;
		URLModel urlModel = urlRepository.findByshorturl(shorturl);
		
		if(urlModel != null) {
			urlModel.setCount(urlModel.getCount()+1);
			urlRepository.save(urlModel);
			urlDto = convertURLModelToURLDto(urlModel);
		}else {
			urlDto = new URLDto();
			urlDto.setErrorMessage("short URL does not exist in database");
		}
		return urlDto;
	}
	
	public URLDto saveUrl(URLDto urlDto) {
		URLDto returlDto;
		URLModel urlModel = urlRepository.findBylongurl(urlDto.getLongurl());
		if(urlModel != null) {
			returlDto = new URLDto();
			returlDto.setErrorMessage("short URL already exists");
			return returlDto;
		}
		urlModel = new URLModel();
		urlModel.setLongurl(urlDto.getLongurl());
		urlModel.setShorturl(encodeUrl(urlDto.getLongurl()));
		
		return convertURLModelToURLDto(urlRepository.save(urlModel));
	}
	
	private String encodeUrl(String url) {
		
		String encodedUrl = "";
		LocalDateTime time = LocalDateTime.now();
		encodedUrl = Hashing.murmur3_32().hashString(url.concat(time.toString()), StandardCharsets.UTF_8).toString();
		return encodedUrl;
	}
	
	private URLDto convertURLModelToURLDto(URLModel urlModel) {
		URLDto urlDto = new URLDto();
		urlDto.setCount(urlModel.getCount());
		urlDto.setLongurl(urlModel.getLongurl());
		urlDto.setShorturl(urlModel.getShorturl());
		
		return urlDto;
	}
}
