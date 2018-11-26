package ${baseCommonPackage}.utils.poi;

import ${baseCommonPackage}.annotation.ExcelCol;
import ${baseCommonPackage}.model.ExportExcelModel;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtils {

    // 默认单元格宽度
    static final int DEFAULT_COL_WIDTH = 20;

    public static <T> void exportExcel(List<ExportExcelModel<T>> excelModelList, OutputStream outputStream) throws IllegalAccessException, IOException {

        exportExcel(excelModelList, null, outputStream);
    }

    public static <T> void exportExcel(ExportExcelModel<T> excelModel, OutputStream outputStream) throws IllegalAccessException, IOException {

        exportExcel(excelModel, null, outputStream);
    }

    public static <T> void exportExcel(String sheetName, Class<T> clazz, List<T> list, OutputStream outputStream) throws IllegalAccessException, IOException {

        exportExcel(sheetName, clazz, list, null, outputStream);
    }

    public static <T> void exportExcel(List<ExportExcelModel<T>> excelModelList, String condition, OutputStream outputStream) throws IllegalAccessException, IOException {

        Workbook workbook = new XSSFWorkbook();
        for (ExportExcelModel<T> excelModel : excelModelList) {
            exportExcel(workbook, excelModel.getSheetName(), excelModel.getClazz(), excelModel.getList(), condition);
        }
        workbook.write(outputStream);
    }

    public static <T> void exportExcel(ExportExcelModel<T> excelModel, String condition, OutputStream outputStream) throws IllegalAccessException, IOException {

        exportExcel(excelModel.getSheetName(), excelModel.getClazz(), excelModel.getList(), condition, outputStream);
    }

    public static <T> void exportExcel(String sheetName, Class<T> clazz, List<T> list, String condition, OutputStream outputStream) throws IllegalAccessException, IOException {

        Workbook workbook = new XSSFWorkbook();
        exportExcel(workbook, sheetName, clazz, list, condition);
        workbook.write(outputStream);
    }

    private static <T> void exportExcel(Workbook workbook, String sheetName, Class<T> clazz, List<T> list) throws IllegalAccessException {
        exportExcel(workbook, sheetName, clazz, list, null);
    }

    private static <T> void exportExcel(Workbook workbook, String sheetName, Class<T> clazz, List<T> list, String condition) throws IllegalAccessException {

        Sheet sheet = workbook.createSheet(sheetName);
        CellStyle defaultHeaderCellStyle = getDefaultHeaderCellStyle(workbook);
        CellStyle defaultCellStyle = getDefaultCellStyle(workbook);

        Row row;
        Cell cell;
        int rowIndex = 0;
        int colIndex = 0;

        List<Field> fields = new ArrayList<>();
        row = sheet.createRow(rowIndex++);
        // 设置header 高度
        row.setHeight((short) 512);
        for (Field field : clazz.getDeclaredFields()) {
            ExcelCol excelCol = field.getAnnotation(ExcelCol.class);
            if (excelCol == null) continue;
            if (StringUtils.isNotBlank(condition) && !condition.equals(excelCol.condition())) {
                continue;
            }
            String name = (excelCol).value();
            if (name == null || name.isEmpty()) {
                name = field.getName();
            }
            // 设置每列宽度为20个字符，这个参数的单位是1/256个字符宽度
            int colWidth = excelCol.width() > 0 ? excelCol.width() : DEFAULT_COL_WIDTH;
            sheet.setColumnWidth(colIndex, colWidth * 256);

            cell = row.createCell(colIndex++);
            cell.setCellValue(name);
            cell.setCellStyle(defaultHeaderCellStyle);
            field.setAccessible(true);
            fields.add(field);
        }

        for (T model : list) {
            row = sheet.createRow(rowIndex++);
            colIndex = 0;
            for (Field field : fields) {
                cell = row.createCell(colIndex++);
                Object val = field.get(model);
                cell.setCellValue(val == null ? "" : val + "");
                cell.setCellStyle(defaultCellStyle);
            }
        }
        for (Field field : fields) {
            field.setAccessible(false);
        }
    }

    private static CellStyle getDefaultHeaderCellStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        Font titleFont = wb.createFont();
        titleFont.setFontHeightInPoints((short) 16);
        titleFont.setFontName("黑体");
        style.setFont(titleFont);
        return style;
    }

    private static CellStyle getDefaultCellStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setWrapText(true);
        return style;
    }
}
