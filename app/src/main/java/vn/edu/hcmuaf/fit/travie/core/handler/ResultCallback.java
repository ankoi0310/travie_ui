package vn.edu.hcmuaf.fit.travie.core.handler;

import vn.edu.hcmuaf.fit.travie.core.handler.Result;
import vn.edu.hcmuaf.fit.travie.core.handler.error.DataError;

public interface ResultCallback<D, E extends DataError> {
    void onComplete(Result<D, E> data);
}
