package com.xassure.framework.repository;

import com.xassure.framework.model.PageLocatorCsv;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PageLocatorRepository extends MongoRepository<PageLocatorCsv, String> {
}
