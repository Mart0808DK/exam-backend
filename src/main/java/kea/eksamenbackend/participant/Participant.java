package kea.eksamenbackend.participant;

import jakarta.persistence.*;
import kea.eksamenbackend.club.Club;
import kea.eksamenbackend.discipline.Discipline;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Integer age;
    @ManyToOne
    private Club club;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Discipline> discipline = new ArrayList<>();

}
