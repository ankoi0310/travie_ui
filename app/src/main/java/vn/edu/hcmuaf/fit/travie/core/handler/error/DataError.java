package vn.edu.hcmuaf.fit.travie.core.handler.error;

public interface DataError extends RootError {
    enum NETWORK implements DataError {
        REQUEST_TIMEOUT,
        NO_INTERNET_CONNECTION,
        PAYLOAD_TOO_LARGE,
        UNAUTHORIZED,
        INTERNAL_SERVER_ERROR,
        BAD_GATEWAY,
        SERVICE_UNAVAILABLE,
        GATEWAY_TIMEOUT,
        FORBIDDEN, NOT_FOUND, UNKNOWN
    }

    enum LOCAL implements DataError {
        DATABASE_ERROR,
        FILE_NOT_FOUND,
        FILE_IO_ERROR,
        UNKNOWN
    }
}
