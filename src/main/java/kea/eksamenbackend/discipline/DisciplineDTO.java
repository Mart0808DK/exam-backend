package kea.eksamenbackend.discipline;

import kea.eksamenbackend.utils.ResultType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DisciplineDTO {
    private Long id;
    private String name;
    private ResultType resultType;
}
