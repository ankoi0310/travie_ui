package vn.edu.hcmuaf.fit.travie.core.handler.domain.paging;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/*
* This class is used to handle paging result from server
*/
@Data
public class Page<T> {
    private ArrayList<T> content;
    private Pageable pageable;
    private boolean last;
    private int totalElements;
    private int totalPages;
    private int size;
    private int number;
    private Sort sort;
    private boolean first;
    private int numberOfElements;
    private boolean empty;
}
