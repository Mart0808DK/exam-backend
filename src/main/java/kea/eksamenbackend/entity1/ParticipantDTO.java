package kea.eksamenbackend.entity1;

import kea.eksamenbackend.entity2.Club;
import kea.eksamenbackend.entity3.Discipline;
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
