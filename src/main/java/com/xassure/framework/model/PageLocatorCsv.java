package com.xassure.framework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "page-locatorcsv")
public class PageLocatorCsv {
    @Id
    private String id;
    private String runId;
    private String pageName;
    private String elementName;
    List<LocatorTime> locatorTimeList;
    private String date;
    private String time;
}
