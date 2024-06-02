package vn.edu.hcmuaf.fit.travie.core.shared.utils.typedef;

import arrow.core.Either;

public class VoidResult extends DataResult<Void> {
    public VoidResult(Either<String, Void> value) {
        super(value);
    }
}