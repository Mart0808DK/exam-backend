package kea.eksamenbackend.club;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClubDTO {
    private Long id;
    private String name;
    private Integer ranking;
    private Area area;
}
