package org.example.scheduler.controller;

import org.example.scheduler.service.ResetVotesScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin("*")
@RequestMapping("/scheduler")
public class SchedulerController {
    private static final Logger logger = LoggerFactory.getLogger(SchedulerController.class);

    @Autowired
    private ResetVotesScheduler resetVotesScheduler;

    @PostMapping("/start")
    public String startScheduler() {
        logger.info("Received request to start the reset votes scheduler.");
        resetVotesScheduler.startSchedulerResetVotes();
        return "Reset votes scheduler has been started.";
    }

    @PostMapping("/stop")
    public String stopScheduler() {
        logger.info("Received request to stop the reset votes scheduler.");
        resetVotesScheduler.stopSchedulerResetVotes();
        return "Reset votes scheduler has been stopped.";
    }
    @PostMapping("start/delete")
    public String startDeleteScheduler() {
        logger.info("Received request to start the delete votes scheduler.");
        resetVotesScheduler.startSchedulerDeleteVotes();
        return "Reset votes scheduler has been deleted.";
    }
    @PostMapping("stop/delete")
    public String stopDeleteScheduler() {
        logger.info("Received request to stop the delete votes scheduler.");
        resetVotesScheduler.stopSchedulerDeleteVotes();
        return "Reset votes scheduler has been deleted.";
    }


}
