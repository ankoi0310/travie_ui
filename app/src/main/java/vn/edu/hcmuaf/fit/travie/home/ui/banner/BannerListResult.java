package vn.edu.hcmuaf.fit.travie.home.ui.banner;

import java.util.ArrayList;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import vn.edu.hcmuaf.fit.travie.home.data.model.Banner;

@Getter
@RequiredArgsConstructor
public class BannerListResult {
    private final ArrayList<Banner> success;
    private final String error;
}
