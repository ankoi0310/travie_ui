package vn.edu.hcmuaf.fit.travie.hotel.data.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    private AppUserReview user;
    private int rating;
    private String comment;

    public static class AppUserReview {
        private String username;
        private String avatar;
    }
}
