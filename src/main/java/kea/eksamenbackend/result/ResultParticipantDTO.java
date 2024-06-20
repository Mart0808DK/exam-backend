package kea.eksamenbackend.result;

import kea.eksamenbackend.club.ClubDTO;
import kea.eksamenbackend.participant.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResultParticipantDTO {
    private Long id;
    private String name;
    private Gender gender;
    private Integer age;
    private ClubDTO club;
}
