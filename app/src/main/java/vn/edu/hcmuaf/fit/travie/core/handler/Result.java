package vn.edu.hcmuaf.fit.travie.core.handler;

import lombok.Getter;
import vn.edu.hcmuaf.fit.travie.core.handler.error.RootError;

/**
 * A generic class that holds a result success w/ data or an error exception.
 */
public class Result<D, E extends RootError> {
    private Result() {
    }

    @Getter
    public static final class Success<D, E extends RootError> extends Result<D, E> {
        private final D data;

        public Success(D data) {
            this.data = data;
        }
    }

    @Getter
    public static final class Error<D, E extends RootError> extends Result<D, E> {
        private final E error;
        private final String message;

        public Error(E error) {
            this.error = error;
            this.message = null;
        }

        public Error(E error, String message) {
            this.error = error;
            this.message = message;
        }

        public Error(String message) {
            this.error = null;
            this.message = message;
        }
    }
}