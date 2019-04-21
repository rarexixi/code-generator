package ${baseCommonPackage}.model;

import lombok.*;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ExportExcelModel<T> {

    /**
     * 标签页名称
     */
    private String sheetName;
    /**
     * 要导出的类
     */
    private Class<T> clazz;
    /**
     * 要导出的列表
     */
    private List<T> list;
    /**
     * 导出的条件
     */
    private String condition;
    /**
     * 导出的枚举字段Map
     */
    private Map<String, Map<String, String>> enumFieldsMap;
}
