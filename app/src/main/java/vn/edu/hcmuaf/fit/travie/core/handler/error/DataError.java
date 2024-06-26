package vn.edu.hcmuaf.fit.travie.core.handler.error;

public interface DataError extends RootError {
    enum NETWORK implements DataError {
        BAD_REQUEST,
        FORBIDDEN,
        NOT_FOUND,
        UNAUTHORIZED,
        SERVICE_UNAVAILABLE,
        INTERNAL_SERVER_ERROR,
        REQUEST_TIMEOUT,
        NO_INTERNET_CONNECTION,
        PAYLOAD_TOO_LARGE,
        BAD_GATEWAY,
        GATEWAY_TIMEOUT,
        UNKNOWN
    }

    enum LOCAL implements DataError {
        DATABASE_ERROR,
        FILE_NOT_FOUND,
        FILE_IO_ERROR,
        UNKNOWN
    }
}