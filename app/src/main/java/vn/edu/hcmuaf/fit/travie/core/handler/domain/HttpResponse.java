package vn.edu.hcmuaf.fit.travie.core.handler.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HttpResponse<T> {
    private boolean success;
    private int statusCode;
    private String message;
    private T data;
}
