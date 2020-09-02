package org.ylc.note.normalpoi.controller;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020-09-02
 */
@RequestMapping("/poi")
@RestController
public class PoiController {

    @GetMapping("/template")
    public void getTemplate() {

    }

    @PostMapping("/import")
    public void importExcel(@RequestParam(value = "file") MultipartFile file) throws IOException {
        Workbook wb = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = wb.getSheetAt(0);
        int maxRow = sheet.getLastRowNum();
        Row row;
        for (int i = 0; i < maxRow; i++) {
            row = sheet.createRow(i);
            System.out.println(row.getCell(0).getStringCellValue());
        }
    }

    @PostMapping("/export")
    public void export(HttpServletResponse response) {

    }

}
