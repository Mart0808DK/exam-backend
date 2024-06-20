package kea.eksamenbackend.result;

import kea.eksamenbackend.discipline.Discipline;
import kea.eksamenbackend.utils.ResultType;
import kea.eksamenbackend.participant.Participant;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ResultDTO {
    private Long id;
    private ResultType resultType;
    private LocalDateTime date;
    private Integer resultValue;
    private Participant participant;
    private Discipline discipline;
}
