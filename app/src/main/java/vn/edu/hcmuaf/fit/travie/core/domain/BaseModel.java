package vn.edu.hcmuaf.fit.travie.core.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseModel implements Serializable {
    private long id;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
