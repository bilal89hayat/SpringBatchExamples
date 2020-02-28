package com.xassure.framework.config;

import com.xassure.framework.model.PageLocator;
import com.xassure.framework.model.PageLocatorCsv;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.mongodb.core.MongoTemplate;


@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {


    @Autowired
    private MongoTemplate mongoTemplate;

    @Bean
    public Job jobCsvToDb(JobBuilderFactory jobBuilderFactory,
                   StepBuilderFactory stepBuilderFactory,
                   ItemReader<PageLocator> readPageLocator,
                   ItemProcessor<PageLocator, PageLocatorCsv> processPageLocator,
                   ItemWriter<PageLocatorCsv> writePageLocatorCsv) {

        Step step1 = stepBuilderFactory.get("ETL-file-load")
                .<PageLocator, PageLocatorCsv>chunk(100)
                .reader(readPageLocator(null))
                .processor(processPageLocator)
                .writer(writePageLocatorCsv)
                .build();


        return jobBuilderFactory.get("ETL-Load")
                .incrementer(new RunIdIncrementer())
                .start(step1)
                .build();
    }



    @Bean
    @StepScope
    @Value("#{jobParameters[fileLocation]}")
    public FlatFileItemReader<PageLocator> readPageLocator( String fileLocation) {
        //private File input;
        //input = new File(reportPath.concat(runId).concat("/LocatorTime.csv"));
        FlatFileItemReader<PageLocator> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new FileSystemResource(fileLocation));
        flatFileItemReader.setName("CSV-Reader");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(lineMapper());
        System.out.println("file path is :" + fileLocation);
        return flatFileItemReader;
    }


    @Bean
    public LineMapper<PageLocator> lineMapper() {

        DefaultLineMapper<PageLocator> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(new String[]{"RunId", "PageName", "ElementName", "LocatorTime", "Date", "Time"});

        BeanWrapperFieldSetMapper<PageLocator> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(PageLocator.class);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }
}
