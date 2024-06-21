package kea.eksamenbackend.result;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepository extends JpaRepository<Result, Long>{
    void deleteByParticipantId(Long id);
}
