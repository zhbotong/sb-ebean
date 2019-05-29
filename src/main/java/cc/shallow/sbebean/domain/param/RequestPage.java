package cc.shallow.sbebean.domain.param;

import lombok.Data;

/**
 * 分页参数
 */
@Data
public class RequestPage {
    private Integer firstRow;
    private Integer maxRow;
    private String sort;
}
