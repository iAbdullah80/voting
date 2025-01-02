package org.example.scheduler.service;

import org.example.scheduler.repository.CandidateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ResetVotesScheduler {

    private static final Logger logger = LoggerFactory.getLogger(ResetVotesScheduler.class);
    private boolean schedulerActiveResetVote = true;
    private boolean schedulerActiveDeleteVote = true;

    @Autowired
    private CandidateRepository candidateRepository;

    @Scheduled(cron = "")
    @Async
    public void resetVotes() {
        if (!schedulerActiveResetVote) {
            logger.info("Reset votes scheduler is currently stopped.");
            return;
        }
        logger.info("Starting scheduled task: Resetting votes for all candidates...");

        try {
            candidateRepository.findAll().forEach(candidate -> {
                logger.debug("Resetting votes for candidate ID {}: {} {}",
                        candidate.getId(), candidate.getFirstName(), candidate.getLastName());
                candidate.setVotes(0);
                candidateRepository.save(candidate);
            });
            logger.info("Scheduled task completed: All votes have been reset successfully.");
        } catch (Exception e) {
            logger.error("Error occurred during the vote reset process", e);
        }
    }

    @Scheduled(cron = "0 0/5 * * * *")
    @Async
    public void deleteCandidates() {
        if (!schedulerActiveDeleteVote) {
            logger.info("Delete votes scheduler is currently stopped.");
            return;
        }

        logger.info("Starting scheduled task: Deleting all candidates...");

        try {
            candidateRepository.deleteAll(candidateRepository.findAll());
            logger.info("Scheduled task completed: All candidates have been deleted.");
        } catch (Exception e) {
            logger.error("Error occurred during candidate deletion process", e);
        }
    }

    public void stopSchedulerResetVotes() {
        logger.info("Stopping the reset votes scheduler.");
        schedulerActiveResetVote = false;
    }

    public void stopSchedulerDeleteVotes() {
        logger.info("Stopping the delete votes scheduler.");
        schedulerActiveDeleteVote = false;
    }

    public void startSchedulerResetVotes() {
        logger.info("Starting the reset votes scheduler.");
        schedulerActiveResetVote = true;
    }
    public void startSchedulerDeleteVotes() {
        logger.info("Starting the delete votes scheduler.");
        schedulerActiveDeleteVote = true;
    }

}
