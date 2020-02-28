package com.xassure.framework.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocatorStrategyWrapper {

    private List<LocatorStrategy> locatorStrategyList;
}
