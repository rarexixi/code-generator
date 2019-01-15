package ${basePackage}.common.utils.poi;

import ${basePackage}.common.annotation.ExcelCol;
import ${basePackage}.common.annotation.ExcelCols;
import ${basePackage}.common.model.ExportExcelModel;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelUtils {

    // 默认添加的列宽
    static final int DEFAULT_ADD_WIDTH = 6;

    // 最大单元格宽度
    static final int MAX_COL_WIDTH = 64;

    /**
     * 导出多标签页Excel
     *
     * @param excelModelList
     * @param outputStream
     * @param <T>
     * @throws IllegalAccessException
     * @throws IOException
     */
    public static <T> void exportExcel(List<ExportExcelModel<T>> excelModelList, OutputStream outputStream) throws IllegalAccessException, IOException {

        Workbook workbook = new XSSFWorkbook();
        for (ExportExcelModel<T> excelModel : excelModelList) {
            exportExcel(workbook, excelModel.getSheetName(), excelModel.getClazz(), excelModel.getList(), excelModel.getCondition(), excelModel.getEnumFieldsMap());
        }
        workbook.write(outputStream);
    }

    /**
     * 导出单标签页
     *
     * @param excelModel
     * @param outputStream
     * @param <T>
     * @throws IllegalAccessException
     * @throws IOException
     */
    public static <T> void exportExcel(ExportExcelModel<T> excelModel, OutputStream outputStream) throws IllegalAccessException, IOException {

        exportExcel(excelModel.getSheetName(), excelModel.getClazz(), excelModel.getList(), excelModel.getCondition(), excelModel.getEnumFieldsMap(), outputStream);
    }

    /**
     * 导出单标签页
     *
     * @param sheetName    标签页名称
     * @param clazz        对应的类
     * @param list         对应的列表
     * @param outputStream
     * @param <T>
     * @throws IllegalAccessException
     * @throws IOException
     */
    public static <T> void exportExcel(String sheetName, Class<T> clazz, List<T> list, OutputStream outputStream) throws IllegalAccessException, IOException {

        exportExcel(sheetName, clazz, list, null, null, outputStream);
    }

    /**
     * 导出单标签页
     *
     * @param sheetName    标签页名称
     * @param clazz        对应的类
     * @param list         对应的列表
     * @param condition    相关条件
     * @param outputStream
     * @param <T>
     * @throws IllegalAccessException
     * @throws IOException
     */
    public static <T> void exportExcel(String sheetName, Class<T> clazz, List<T> list, String condition, OutputStream outputStream) throws IllegalAccessException, IOException {

        exportExcel(sheetName, clazz, list, condition, null, outputStream);
    }

    /**
     * 导出单标签页
     *
     * @param sheetName     标签页名称
     * @param clazz         对应的类
     * @param list          对应的列表
     * @param enumFieldsMap 字段枚举Map
     * @param outputStream
     * @param <T>
     * @throws IllegalAccessException
     * @throws IOException
     */
    public static <T> void exportExcel(String sheetName, Class<T> clazz, List<T> list, Map<String, Map<String, String>> enumFieldsMap, OutputStream outputStream) throws IllegalAccessException, IOException {

        exportExcel(sheetName, clazz, list, null, enumFieldsMap, outputStream);
    }

    /**
     * 导出单标签页
     *
     * @param sheetName     标签页名称
     * @param clazz         对应的类
     * @param list          对应的列表
     * @param condition     条件
     * @param enumFieldsMap 字段枚举Map
     * @param outputStream
     * @param <T>
     * @throws IllegalAccessException
     * @throws IOException
     */
    public static <T> void exportExcel(String sheetName, Class<T> clazz, List<T> list, String condition, Map<String, Map<String, String>> enumFieldsMap, OutputStream outputStream) throws IllegalAccessException, IOException {

        Workbook workbook = new XSSFWorkbook();
        exportExcel(workbook, sheetName, clazz, list, condition, enumFieldsMap);
        workbook.write(outputStream);
    }

    /**
     * 导出
     *
     * @param workbook
     * @param sheetName     标签页名称
     * @param clazz         对应的类
     * @param list          对应的列表
     * @param condition     条件
     * @param enumFieldsMap 字段枚举Map
     * @param <T>
     * @throws IllegalAccessException
     */
    private static <T> void exportExcel(Workbook workbook, String sheetName, Class<T> clazz, List<T> list, String condition, Map<String, Map<String, String>> enumFieldsMap) throws IllegalAccessException {

        Sheet sheet = workbook.createSheet(sheetName);
        CellStyle defaultHeaderCellStyle = getDefaultHeaderCellStyle(workbook);
        CellStyle defaultCellStyle = getDefaultCellStyle(workbook);

        Row row;
        Cell cell;
        int rowIndex = 0;
        int colIndex = 0;

        row = sheet.createRow(rowIndex++);
        // 设置列头高度
        row.setHeight((short) 512);
        List<FieldSort> fieldSorts = getFieldSorts(clazz, condition);

        int[] colWidths = new int[fieldSorts.size()];

        for (FieldSort fieldSort : fieldSorts) {
            Field field = fieldSort.getField();
            ExcelCol excelCol = fieldSort.getExcelCol();
            String name = excelCol.value();
            if (name == null || StringUtils.isBlank(name)) {
                name = field.getName();
            }

            cell = row.createCell(colIndex);
            cell.setCellValue(name);
            cell.setCellStyle(defaultHeaderCellStyle);

            // 根据列头设置宽度
            int width = name.getBytes().length + DEFAULT_ADD_WIDTH;
            colWidths[colIndex] = width > MAX_COL_WIDTH ? MAX_COL_WIDTH : width;
            colIndex++;
        }

        Row[] rows = new Row[list.size()];
        for (int i = 0; i < rows.length; i++) {
            Row rowItem = sheet.createRow(rowIndex++);
            // 设置行高
            rowItem.setHeight((short) 512);
            rows[i] = rowItem;
        }

        colIndex = 0;
        for (FieldSort fieldSort : fieldSorts) {
            Field field = fieldSort.getField();
            ExcelCol excelCol = fieldSort.getExcelCol();

            // 获取字段值的映射
            Map<String, String> fieldEnumMap = enumFieldsMap == null ? null : enumFieldsMap.getOrDefault(field.getName(), null);

            boolean hasWidth = excelCol.width() > 0;
            int colWidth = hasWidth ? excelCol.width() : colWidths[colIndex];

            rowIndex = 0;
            for (T model : list) {
                cell = rows[rowIndex++].createCell(colIndex);
                Object val = field.get(model);
                // 格式化字符串
                String pattern = excelCol.formatter();
                // 获取实际输出到Excel的值
                String value = getValue(val, pattern, fieldEnumMap);

                cell.setCellValue(value);
                cell.setCellStyle(defaultCellStyle);

                if (!hasWidth) colWidth = getColWidth(colWidth, value);
            }
            // 设置列宽，这个参数的单位是1/256个字符宽度
            sheet.setColumnWidth(colIndex, colWidth * 256);
            colIndex++;
        }
        fieldSorts.forEach(fieldSort -> fieldSort.getField().setAccessible(false));
    }

    /**
     * 获取格式化后的值
     *
     * @param val          原值
     * @param pattern      格式化字符串
     * @param fieldEnumMap 枚举map
     * @return
     */
    private static String getValue(Object val, String pattern, Map<String, String> fieldEnumMap) {

        if (val == null) return "";
        String value;
        if (StringUtils.isBlank(pattern)) {
            value = val + "";
        } else {
            if (val instanceof Date) {
                // 格式化日期
                SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                value = sdf.format(val);
            } else {
                value = String.format(pattern, val);
            }
        }
        // 字段是否是枚举
        if (fieldEnumMap != null && !fieldEnumMap.isEmpty()) {
            value = fieldEnumMap.getOrDefault(value, value);
        }
        return value;
    }

    /**
     * 获取列宽
     *
     * @param currentWidth 当前的列宽
     * @param value        列的内容
     * @return
     */
    private static int getColWidth(int currentWidth, String value) {

        if (currentWidth >= MAX_COL_WIDTH) return MAX_COL_WIDTH;

        String[] lines = value.split("\n");
        int width = currentWidth;
        // 将内容按行分割后获取最长的一行的宽度
        for (String line : lines) {
            if (StringUtils.isBlank(line)) continue;
            int byteLength = line.getBytes().length + DEFAULT_ADD_WIDTH;
            if (byteLength > MAX_COL_WIDTH) {
                byteLength = MAX_COL_WIDTH;
            }
            if (byteLength > width) width = byteLength;
        }
        return width;
    }

    /**
     * 根据条件获取排序后的列
     *
     * @param clazz
     * @param condition
     * @param <T>
     * @return
     */
    private static <T> List<FieldSort> getFieldSorts(Class<T> clazz, String condition) {

        List<FieldSort> fieldSorts = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            ExcelCol excelCol = getExcelCol(field, condition);
            if (excelCol == null) continue;
            field.setAccessible(true);
            fieldSorts.add(new FieldSort(field, excelCol));
        }

        fieldSorts.sort(Comparator.comparing(fieldSort -> fieldSort.getExcelCol().order()));

        return fieldSorts;
    }

    private static ExcelCol getExcelCol(Field field, String condition) {

        boolean conditionIsBlank = StringUtils.isBlank(condition);

        ExcelCol excelCol = field.getAnnotation(ExcelCol.class);
        if (excelCol != null) {
            if (StringUtils.isBlank(excelCol.condition()) || (!conditionIsBlank && (condition.equals(excelCol.condition())))) {
                return excelCol;
            }
        }
        ExcelCols excelCols = field.getAnnotation(ExcelCols.class);
        if (excelCols == null) return null;
        ExcelCol[] cols = excelCols.value();
        if (cols == null || cols.length == 0) return null;
        for (ExcelCol col : cols) {
            if (StringUtils.isBlank(col.condition()) || (!conditionIsBlank && condition.equals(col.condition()))) {
                return col;
            }
        }
        return null;
    }

    /**
     * 获取表头单元格样式
     *
     * @param wb
     * @return
     */
    private static CellStyle getDefaultHeaderCellStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        Font titleFont = wb.createFont();
        titleFont.setFontHeightInPoints((short) 16);
        titleFont.setFontName("黑体");
        style.setFont(titleFont);
        return style;
    }

    /**
     * 获取默认单元格样式
     *
     * @param wb
     * @return
     */
    private static CellStyle getDefaultCellStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setWrapText(true);
        return style;
    }

    private final static class FieldSort {
        public FieldSort(Field field, ExcelCol excelCol) {
            this.field = field;
            this.excelCol = excelCol;
        }

        private Field field;
        private ExcelCol excelCol;

        public Field getField() {
            return field;
        }

        public void setField(Field field) {
            this.field = field;
        }

        public ExcelCol getExcelCol() {
            return excelCol;
        }

        public void setExcelCol(ExcelCol excelCol) {
            this.excelCol = excelCol;
        }
    }
}