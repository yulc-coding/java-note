package org.ylc.note.normalpoi.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020-09-02
 */
public class ExceUtil {

    public static void createExcel(String xlsType, String fileName, List<String> cList, List<Map<String, Object>> data, HttpServletResponse response) {

        Workbook wb;
        String expFileName;
        if ("XLS".equals(xlsType)) {
            expFileName = fileName + ".xls";
            wb = new HSSFWorkbook();
        } else {
            expFileName = fileName + ".xlsx";
            wb = new XSSFWorkbook();
        }
        Font headFont = wb.createFont();
        headFont.setFontHeightInPoints((short) 12);
        headFont.setBold(true);
        headFont.setFontName("黑体");

        CellStyle headStyle = wb.createCellStyle();
        headStyle.setFont(headFont);

        headStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headStyle.setBorderTop(BorderStyle.THIN);

        headStyle.setBorderLeft(BorderStyle.THIN);

        headStyle.setBorderRight(BorderStyle.THIN);

        headStyle.setBorderBottom(BorderStyle.THIN);

        headStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headStyle.setWrapText(false);

        Font bodyFont = wb.createFont();
        bodyFont.setFontHeightInPoints((short) 12);
        bodyFont.setFontName("华文楷体");

        CellStyle bodyStyle = wb.createCellStyle();
        bodyStyle.setFont(bodyFont);

        bodyStyle.setBorderLeft(BorderStyle.THIN);

        bodyStyle.setBorderRight(BorderStyle.THIN);

        bodyStyle.setBorderBottom(BorderStyle.THIN);

        bodyStyle.setWrapText(false);

        Sheet sheet = wb.createSheet();
        Row sheetHeadRow = sheet.createRow(0);
        sheetHeadRow.setHeight((short) 500);
        int cLen = cList.size();
        int dLen = data.size();
        for (int i = 0; i <= cLen; i++) {
            Cell cell = sheetHeadRow.createCell(i);
            if (i == 0) {
                cell.setCellValue("序号");
            } else {
                cell.setCellValue(cList.get(i - 1));

                sheet.setColumnWidth(i, 6000);
            }
            cell.setCellStyle(headStyle);
        }
        Map<String, Object> rowMap;
        for (int i = 0; i < dLen; i++) {
            Row sheetRow = sheet.createRow(i + 1);
            sheetRow.setHeight((short) 400);
            rowMap = data.get(i);
            for (int j = 0; j <= cLen; j++) {
                Cell cell = sheetRow.createCell(j);
                if (j == 0) {
                    cell.setCellValue(i + 1);
                } else {
                    cell.setCellValue(rowMap.get(cList.get(j - 1)).toString());
                }
                cell.setCellStyle(bodyStyle);
            }
        }
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            wb.write(os);
            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);

            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(expFileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
            ServletOutputStream out = response.getOutputStream();

            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte['?'];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
