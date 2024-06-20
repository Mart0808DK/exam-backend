package kea.eksamenbackend.result;

import kea.eksamenbackend.discipline.Discipline;
import kea.eksamenbackend.discipline.DisciplineDTO;
import kea.eksamenbackend.participant.ParticipantDTO;
import kea.eksamenbackend.utils.ResultType;
import kea.eksamenbackend.participant.Participant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResultDTO {
    private Long id;
    private ResultType resultType;
    private LocalDateTime date;
    private Integer resultValue;
    private ResultParticipantDTO participant;
    private DisciplineDTO discipline;
}
