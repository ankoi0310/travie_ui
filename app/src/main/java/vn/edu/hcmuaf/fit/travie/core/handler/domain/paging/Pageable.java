package vn.edu.hcmuaf.fit.travie.core.handler.domain.paging;

public class Pageable {
    int pageNumber;
    int pageSize;
    Sort sort;
    int offset;
    boolean paged;
    boolean unpaged;
}
