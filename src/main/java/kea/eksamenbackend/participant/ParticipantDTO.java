package kea.eksamenbackend.participant;

import kea.eksamenbackend.club.Club;
import kea.eksamenbackend.discipline.Discipline;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class ParticipantDTO {
    private Long id;
    private String name;
    private Gender gender;
    private Integer age;
    private Club club;
    private List<Discipline> discipline = new ArrayList<>();
}
