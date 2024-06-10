package vn.edu.hcmuaf.fit.travie.invoice.data.model;

import android.app.Activity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LinkTextItem {
    private String text;
    private Class<? extends Activity> activity;
}
