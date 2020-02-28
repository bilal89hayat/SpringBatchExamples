package com.xassure.framework.repository;

import com.xassure.framework.model.PageLocatorCsv;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PageLocatorCsvRepo extends MongoRepository<PageLocatorCsv, String> {

    Boolean existsByRunId(String runId);
}
