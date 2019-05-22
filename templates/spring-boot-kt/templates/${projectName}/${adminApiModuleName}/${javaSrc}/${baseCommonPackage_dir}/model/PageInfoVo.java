package ${baseCommonPackage}.model;

import lombok.*;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PageInfoVo<T> implements Serializable {

    private Integer pageIndex;
    private Integer pageSize;
    private Long total;
    private List<T> list;
}