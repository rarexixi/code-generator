package ${baseCommonPackage}.model;

import java.util.List;
import java.util.Map;

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

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Map<String, Map<String, String>> getEnumFieldsMap() {
        return enumFieldsMap;
    }

    public void setEnumFieldsMap(Map<String, Map<String, String>> enumFieldsMap) {
        this.enumFieldsMap = enumFieldsMap;
    }
}
