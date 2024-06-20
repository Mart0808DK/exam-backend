package kea.eksamenbackend.participant;

import kea.eksamenbackend.discipline.DisciplineDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantDTO {
    private Long id;
    private String name;
    private Gender gender;
    private Integer age;
    private String clubName;
    private List<DisciplineDTO> discipline = new ArrayList<>();
}
