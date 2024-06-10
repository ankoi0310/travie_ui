package vn.edu.hcmuaf.fit.travie.core.common.ui;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private final int space;
    private final int orientation;

    public SpaceItemDecoration(int space, int orientation) {
        this.space = space;
        this.orientation = orientation;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (orientation == RecyclerView.VERTICAL) {
            outRect.top = space;
            outRect.bottom = space;
        } else {
            outRect.left = space;
            outRect.right = space;
        }
    }
}
