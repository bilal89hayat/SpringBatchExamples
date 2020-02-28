package com.xassure.framework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "locator-strategy")
public class LocatorStrategy
{
    String pageName;
    String elementName;
    String locator;
    Double avgTime;
    String minTime;
    String maxTime;
    String isWorking;
    Integer count;
    Double percentageOfSuccess;
    String recommendation;
}