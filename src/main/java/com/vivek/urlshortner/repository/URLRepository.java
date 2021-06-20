package com.vivek.urlshortner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vivek.urlshortner.model.URLModel;

@Repository
public interface URLRepository extends JpaRepository<URLModel, Integer> {
	
	URLModel findByshorturl(String shorturl);
	URLModel findBylongurl(String longurl);

}
