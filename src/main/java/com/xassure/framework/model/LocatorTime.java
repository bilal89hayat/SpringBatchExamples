package com.xassure.framework.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Locator",
        "time",
        "value",
        "isWorking",
        "isUsed"
})
@Document
public class LocatorTime {

    @JsonProperty("Locator")
    String locator;
    Double time;
    String value;
    String isWorking;
    String isUsed;
}
