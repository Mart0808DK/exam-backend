package kea.eksamenbackend.entity2;

import kea.eksamenbackend.errorhandling.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClubService {
    private final ClubRepository entity1Repository;

    public ClubService(ClubRepository entity1Repository) {
        this.entity1Repository = entity1Repository;
    }

    public List<ClubDTO> findAllClubs() {
        return entity1Repository.findAll().stream().map(this::toDTO).toList();
    }

    public Optional<ClubDTO> findClubById(Long id) {
        if (!entity1Repository.existsById(id)) throw new NotFoundException("Entity1 not found");
        return entity1Repository.findById(id).map(this::toDTO);
    }

    public ClubDTO toDTO (Club club) {
        return new ClubDTO(club.getId(), club.getName(), club.getRanking(), club.getArea());
    }

}
