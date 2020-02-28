package com.xassure.framework.service.impl;

import com.xassure.framework.model.LocatorStrategy;
import com.xassure.framework.repository.LocatorRepository;
import com.xassure.framework.service.LocatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.stereotype.Service;

import java.util.List;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class LocatorServiceImpl implements LocatorService {

    @Autowired
    private LocatorRepository locatorRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<LocatorStrategy> saveToDb() {

        GroupOperation groupOperation = group("pageName", "elementName", "locatorTimeList.locator")
                .min("locatorTimeList.time").as("minTime")
                .max("locatorTimeList.time").as("maxTime")
                .avg("locatorTimeList.time").as("avgTime");
        Aggregation aggregation = newAggregation(unwind("locatorTimeList"), groupOperation);
        AggregationResults<?> aggregationResults = mongoTemplate.aggregate(aggregation, "page-locatorcsv", LocatorStrategy.class);
        List<LocatorStrategy> locatorStrategyList = (List<LocatorStrategy>) aggregationResults.getMappedResults();
        locatorRepository.deleteAll();
        return locatorRepository.saveAll(locatorStrategyList);
    }
}
