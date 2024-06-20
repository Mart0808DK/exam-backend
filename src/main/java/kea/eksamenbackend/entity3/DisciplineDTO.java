package kea.eksamenbackend.entity3;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DisciplineDTO {
    private Long id;
    private String name;
    private ResultType resultType;
}
