package vn.edu.hcmuaf.fit.travie.core.shared.utils.typedef;

import arrow.core.Either;
import lombok.Getter;

@Getter
public class DataResult<T> {
    private final Either<String, T> value;

    public DataResult(Either<String, T> value) {
        this.value = value;
    }
}


