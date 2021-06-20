package com.vivek.urlshortner.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vivek.urlshortner.dto.URLDto;
import com.vivek.urlshortner.model.URLModel;
import com.vivek.urlshortner.service.URLService;

@RestController
@CrossOrigin
@RequestMapping("/")
public class URLController {
	
	@Autowired
	URLService urlService;
	
	@GetMapping("/urls")
	public List<URLModel> getUrls(){
		return urlService.getUrl();
	}
	
	@GetMapping("/url/{shorturl}")
	public ResponseEntity<URLDto> getLongUrlByShortUrl(@PathVariable String shorturl){
		ResponseEntity<URLDto> res;
		
		if(StringUtils.isEmpty(shorturl)) {
			URLDto returlDto = new URLDto();
			returlDto.setErrorMessage("Invalid URL");
			res = new ResponseEntity<URLDto>(returlDto,HttpStatus.OK);
			return res;
		}
		
		URLDto urlDto = urlService.getLongURL(shorturl);
		
		res = new ResponseEntity<URLDto>(urlDto,HttpStatus.OK);
		return res;
	}
	
	@PostMapping("/save")
	public ResponseEntity<URLDto> saveUrl(@RequestBody URLDto urlDto){
		ResponseEntity<URLDto> res;
		
		if(StringUtils.isEmpty(urlDto.getLongurl())) {
			URLDto returlDto = new URLDto();
			returlDto.setErrorMessage("Please enter URL to shorten");	
			res = new ResponseEntity<URLDto>(returlDto,HttpStatus.OK);
			return res;
		}
		
		URLDto result = urlService.saveUrl(urlDto);
		res = new ResponseEntity<URLDto>(result,HttpStatus.OK);
		return res;
		
	}
}
