package com.xassure.framework.processor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xassure.framework.model.LocatorTime;
import com.xassure.framework.model.PageLocator;
import com.xassure.framework.model.PageLocatorCsv;
import com.xassure.framework.repository.PageLocatorCsvRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j2
@Component
public class PageLocatorToPageLocatorCsvProcessor implements ItemProcessor<PageLocator, PageLocatorCsv> {

    @Autowired
    private PageLocatorCsvRepo pageLocatorCsvRepo;
    private PageLocatorCsv pageLocatorCsv;


    @Override
    public PageLocatorCsv process(PageLocator pageLocator) throws Exception {

        if (!pageLocatorCsvRepo.findById(pageLocator.getRunId()).isPresent()) {
            pageLocatorCsv = new PageLocatorCsv();
            ObjectMapper objectMapper = new ObjectMapper();
            pageLocatorCsv.setRunId(pageLocator.getRunId());
            String[] pageNameSplitter = pageLocator.getPageName().split("[.]");
            String pageName = pageNameSplitter[pageNameSplitter.length - 1];
            pageLocatorCsv.setPageName(pageName);
            pageLocatorCsv.setElementName(pageLocator.getElementName());

            String locatorTimeJson = pageLocator.getLocatorTime();
            log.error("locationtime json ====" + locatorTimeJson);
            List<LocatorTime> locatorTimeList = objectMapper.readValue(locatorTimeJson, new TypeReference<List<LocatorTime>>() {
            });
            pageLocatorCsv.setLocatorTimeList(locatorTimeList);
            pageLocatorCsv.setDate(pageLocator.getDate());
            pageLocatorCsv.setTime(pageLocator.getTime());
        }
        return pageLocatorCsv;
    }
}
