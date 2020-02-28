package com.xassure.framework.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PageLocator {

    private String runId;
    private String pageName;
    private String elementName;
    private String locatorTime;
    private String date;
    private String time;


}
