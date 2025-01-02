package org.example.votingapp.voting.repository;


import org.example.votingapp.voting.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidate, Integer> {

}
