package vn.edu.hcmuaf.fit.travie.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    private AppUser user;
    private int rating;
    private String comment;
    private Hotel hotel;
}
