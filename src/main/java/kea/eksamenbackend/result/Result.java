package kea.eksamenbackend.result;

import jakarta.persistence.*;
import kea.eksamenbackend.discipline.Discipline;
import kea.eksamenbackend.utils.ResultType;
import kea.eksamenbackend.participant.Participant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private ResultType resultType;
    private LocalDateTime date;
    private Integer resultValue;
    @ManyToOne
    private Discipline discipline;
    @ManyToOne
    private Participant participant;

}
