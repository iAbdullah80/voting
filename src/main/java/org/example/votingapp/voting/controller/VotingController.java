package org.example.votingapp.voting.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.votingapp.voting.model.Candidate;
import org.example.votingapp.voting.service.VotingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("vote")
public class VotingController {

    private static final Logger logger = LoggerFactory.getLogger(VotingController.class);

    @Autowired
    private VotingService votingService;

    @Tag(name = "GET", description = "GET methods of Voting APIs")
    @GetMapping("candidate/{id}")
    public Candidate getCandidate(@PathVariable int id) {
        logger.info("Request received: Fetch candidate with ID {}", id);
        Candidate candidate = votingService.getCandidate(id);
        logger.info("Candidate with ID {} retrieved: {} {}", id, candidate.getFirstName(), candidate.getLastName());
        return candidate;
    }
    @Tag(name = "GET", description = "GET methods of Voting APIs")
    @GetMapping("candidate/all")
    public List<Candidate> getAllCandidates() {
        logger.info("Request received: Fetch all candidates");
        List<Candidate> candidates = votingService.getCandidates();
        logger.info("Fetched {} candidates from the database", candidates.size());
        return candidates;
    }
    @Tag(name = "POST", description = "POST methods of Voting APIs")
    @PostMapping("candidate/add")
    public void addCandidate(@RequestBody Candidate candidate) {
        logger.info("Request received: Add candidate {} {}", candidate.getFirstName(), candidate.getLastName());
        votingService.addCandidate(candidate);
        logger.info("Candidate {} {} added successfully", candidate.getFirstName(), candidate.getLastName());
    }

    @Tag(name = "GET", description = "GET methods of Voting APIs")
    @GetMapping("/{candidateID}")
    public Candidate getVote(@PathVariable int candidateID) {
        logger.info("Request received: Fetch votes for candidate with ID {}", candidateID);
        Candidate candidate = votingService.getCandidate(candidateID);
        logger.info("Candidate with ID {} has {} votes", candidateID, candidate.getVotes());
        return candidate;
    }
    @Tag(name = "POST", description = "POST methods of Voting APIs")
    @PostMapping()
    public ResponseEntity<Candidate> vote(@RequestBody Candidate candidate) {
        logger.info("Request received: Add vote to candidate with ID {}", candidate.getId());
        Candidate updatedCandidate = votingService.addVote(candidate.getId());
        logger.info("Vote added successfully. Candidate with ID {} now has {} votes",
                updatedCandidate.getId(), updatedCandidate.getVotes());
        return ResponseEntity.ok(updatedCandidate);
    }
}
