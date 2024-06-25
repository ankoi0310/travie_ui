package vn.edu.hcmuaf.fit.travie.booking.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class PayOSResponse {
    private String code;
    private String desc;

    @Data
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class Error extends PayOSResponse { }

    @Data
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class Success<T> extends PayOSResponse {
        private T data;
        private String signature;
    }
}
