package com.xassure.framework.writer;


import com.xassure.framework.model.PageLocatorCsv;
import com.xassure.framework.repository.PageLocatorRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;


@Component
public class PageLocatorCsvWriter implements ItemWriter<PageLocatorCsv> {

    @Autowired
    private PageLocatorRepository pageLocatorRepository;


    @Override
    public void write(List<? extends PageLocatorCsv> list) throws Exception {
        pageLocatorRepository.saveAll(list);
    }
}
