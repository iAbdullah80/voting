package org.example.votingapp.voting.service;

import jakarta.transaction.Transactional;
import org.example.votingapp.voting.model.Candidate;
import org.example.votingapp.voting.repository.CandidateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VotingService {

    private static final Logger logger = LoggerFactory.getLogger(VotingService.class);

    @Autowired
    private CandidateRepository candidateRepository;

    @Transactional
    public Candidate getCandidate(int candidateId) {
        logger.info("Fetching candidate with ID: {}", candidateId);
        return candidateRepository.findById(candidateId)
                .orElseThrow(() -> {
                    logger.error("Candidate with ID {} not found", candidateId);
                    return new RuntimeException("Candidate not found");
                });
    }

    @Transactional
    public List<Candidate> getCandidates() {
        logger.info("Fetching all candidates");
        List<Candidate> candidates = candidateRepository.findAll();
        logger.info("Retrieved {} candidates from the database", candidates.size());
        return candidates;
    }

    @Transactional
    public Candidate addCandidate(Candidate candidate) {
        logger.info("Adding a new candidate: {} {}", candidate.getFirstName(), candidate.getLastName());
        Candidate savedCandidate = candidateRepository.save(candidate);
        logger.info("Candidate added successfully with ID: {}", savedCandidate.getId());
        return savedCandidate;
    }

    @Transactional
    public void deleteCandidate(int candidateId) {
        logger.info("Deleting candidate with ID: {}", candidateId);
        if (!candidateRepository.existsById(candidateId)) {
            logger.error("Cannot delete: Candidate with ID {} not found", candidateId);
            throw new RuntimeException("Candidate not found");
        }
        candidateRepository.deleteById(candidateId);
        logger.info("Candidate with ID {} deleted successfully", candidateId);
    }

    @Transactional
    public Candidate addVote(int candidateId) {
        logger.info("Adding a vote to candidate with ID: {}", candidateId);
        Candidate candidate = candidateRepository.findById(candidateId).orElseThrow(() -> {
            logger.error("Candidate with ID {} not found", candidateId);
            return new RuntimeException("Candidate not found");
        });

        int previousVotes = candidate.getVotes();
        candidate.setVotes(previousVotes + 1);
        Candidate updatedCandidate = candidateRepository.save(candidate);

        logger.info("Vote added. Candidate {} now has {} votes (previously {}).",
                candidate.getId(), updatedCandidate.getVotes(), previousVotes);

        return updatedCandidate;
    }
}
