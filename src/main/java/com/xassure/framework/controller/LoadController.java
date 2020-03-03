package com.xassure.framework.controller;

import com.xassure.framework.model.Response;
import com.xassure.framework.repository.PageLocatorCsvRepo;
import com.xassure.framework.service.LocatorService;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/batch")
public class LoadController {

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job jobCsvToDb;
    @Autowired
    private PageLocatorCsvRepo pageLocatorCsvRepo;
    @Value(value = "${xassure.config.reportpath}")
    private String reportPath;
    @Autowired
    private LocatorService locatorService;


    @GetMapping("/loadcsv/{runId}")
    public ResponseEntity<Response> load(@PathVariable String runId) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        Map<String, JobParameter> maps = new HashMap<>();
        maps.put("time", new JobParameter(System.currentTimeMillis()));

        JobParametersBuilder jobBuilder = new JobParametersBuilder();
        jobBuilder.addString("fileLocation", reportPath.concat(runId).concat("/LocatorTime.csv"));
        jobBuilder.addParameter("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = jobBuilder.toJobParameters();

        //JobParameters parameters = new JobParameters(maps);
        JobExecution jobExecution = null;

        if (pageLocatorCsvRepo.existsByRunId(runId)) {
            return new ResponseEntity<Response>(new Response("Run Id Exists in Db ..."),HttpStatus.OK);
        }

        jobExecution = jobLauncher.run(jobCsvToDb, jobParameters);
        System.out.println("JobExecution: " + jobExecution.getStatus());

        System.out.println("Batch is Running...");
        while (jobExecution.isRunning()) {
            System.out.println("...");
        }

        return new ResponseEntity<Response>(new Response("Completed with run id " + runId), HttpStatus.OK);
    }


    @GetMapping("/saveToDb")
    public ResponseEntity<?> saveToDb(){

        try
        {
            locatorService.saveToDb();
            return new ResponseEntity<Response>(new Response("saved successfully"), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<Response>(new Response(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
