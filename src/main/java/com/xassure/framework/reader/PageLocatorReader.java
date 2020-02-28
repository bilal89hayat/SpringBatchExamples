/*
package com.techprimers.springbatchexample1.reader;

import com.techprimers.springbatchexample1.model.PageLocator;
import com.techprimers.springbatchexample1.repository.PageLocatorRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class PageLocatorReader implements ItemReader<PageLocator> {

    @Autowired
    PageLocatorRepository pageLocatorRepository;

    @Override
    public PageLocator read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {


        pageLocatorRepository.findById();
        return null;
    }
}
*/
