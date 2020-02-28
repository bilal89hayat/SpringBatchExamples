package com.xassure.framework.repository;

import com.xassure.framework.model.LocatorStrategy;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LocatorRepository extends MongoRepository<LocatorStrategy, String> {
}
