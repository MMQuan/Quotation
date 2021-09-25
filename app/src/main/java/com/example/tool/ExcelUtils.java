package com.example.tool;


import android.content.Context;
import com.example.database.bean.AlreadyBean;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.write.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dmrfcoder
 * @date 2018/8/9
 */
public class ExcelUtils {
    private static WritableFont arial14font = null;

    private static WritableCellFormat arial14format = null;
    private static WritableFont arial10font = null;
    private static WritableCellFormat arial10format = null;
    private static WritableFont arial12font = null;
    private static WritableCellFormat arial12format = null;
    private static WritableFont arial6font = null;
    private static WritableCellFormat arial6format = null;

    private static WritableFont arial8font = null;
    private static WritableCellFormat arial8format = null;
    private final static String UTF8_ENCODING = "UTF-8";

    /**
     * 单元格的格式设置 字体大小 颜色 对齐方式、背景颜色等...
     */
    private static void format() {
        try {
            arial14font = new WritableFont(WritableFont.ARIAL, 16, WritableFont.BOLD);
            arial14font.setColour(Colour.BLACK);
            arial14format = new WritableCellFormat(arial14font);
            arial14format.setAlignment(jxl.format.Alignment.CENTRE);
            arial14format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            arial14format.setBackground(Colour.WHITE);


            arial10font = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
            arial10format = new WritableCellFormat(arial10font);
            arial10format.setAlignment(jxl.format.Alignment.CENTRE);
            arial10format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            arial10format.setBackground(Colour.GRAY_25);
            //对齐格式
            arial10format.setAlignment(jxl.format.Alignment.CENTRE);


            arial12font = new WritableFont(WritableFont.ARIAL, 11);
            arial12format = new WritableCellFormat(arial12font);
            arial12format.setAlignment(Alignment.CENTRE);
            //设置边框
            arial12format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);


            arial6font = new WritableFont(WritableFont.ARIAL, 11);
            arial6font.setColour(Colour.RED);
            arial6format = new WritableCellFormat(arial6font);
            arial6format.setAlignment(Alignment.CENTRE);
            //设置边框
            arial6format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);


            arial8font = new WritableFont(WritableFont.ARIAL, 11);
            arial8format = new WritableCellFormat(arial12font);
            //设置边框
            arial8format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);

        } catch (WriteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化Excel
     *
     * @param path    导出excel存放的地址（目录）
     * @param colName excel中包含的列名（可以有多个）
     */
    public static void initExcel(String path, String name, String[] sheetName, String[] colName) {
        format();
        WritableWorkbook workbook = null;
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            workbook = Workbook.createWorkbook(file);
            for (int i = 0; i < 2; i++) {
                //设置表格的名字
                WritableSheet sheet = workbook.createSheet(sheetName[i], i);
                //创建标题栏
                sheet.mergeCells(0, 0, 12, 0);
                sheet.addCell((WritableCell) new Label(0, 0, name, arial14format));
                for (int col = 0; col < colName.length; col++) {
                    sheet.addCell(new Label(col, 1, colName[col], arial10format));
                }
                //设置行高
                sheet.setRowView(0, 450);
                sheet.setRowView(1, 350);
            }
            workbook.write();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> void writeObjListToExcel(List<T> objList, String fileName, Context c, int sheetNum, String sum) {
        if (objList != null && objList.size() > 0) {
            WritableWorkbook writebook = null;
            InputStream in = null;
            try {
                WorkbookSettings setEncode = new WorkbookSettings();
                setEncode.setEncoding(UTF8_ENCODING);
                in = new FileInputStream(new File(fileName));
                Workbook workbook = Workbook.getWorkbook(in);
                writebook = Workbook.createWorkbook(new File(fileName), workbook);
                WritableSheet sheet = writebook.getSheet(sheetNum);

                for (int j = 0; j < objList.size(); j++) {
                    AlreadyBean projectBean = (AlreadyBean) objList.get(j);
                    List<String> list = new ArrayList<>();
                    list.add(String.valueOf(j + 1));
                    list.add(projectBean.getAlreadyName());
                    list.add(projectBean.getHeadName());
                    list.add(projectBean.getEndDate());
                    list.add(String.valueOf(projectBean.getPrice()));
                    list.add(String.valueOf(projectBean.getNumber()));
                    list.add(projectBean.getUnit());
                    list.add(String.valueOf(projectBean.getTotal()));
                    list.add(projectBean.getWorkName());
                    list.add(projectBean.getEndDate());
                    list.add(null);
                    list.add(null);
                    list.add(null);

                    for (int i = 0; i < list.size(); i++) {
                        if (i == 1) {
                            sheet.addCell(new Label(i, j + 2, list.get(i), arial8format));
                            sheet.setColumnView(i, 40);
                        } else if (i == 3 || i == 9) {
                            sheet.addCell(new Label(i, j + 2, list.get(i), arial12format));
                            sheet.setColumnView(i, 12);
                        } else {
                            sheet.addCell(new Label(i, j + 2, list.get(i), arial12format));
                            sheet.setColumnView(i, 9);
                        }
                    }
                    //设置行高
                    sheet.setRowView(j + 2, 350);
                }
                for (int i = 0; i < 13; i++) {
                    if (i == 6) {
                        sheet.addCell(new Label(6, objList.size() + 2, "合计", arial6format));
                    } else if (i == 7) {
                        sheet.addCell(new Label(7, objList.size() + 2, sum, arial6format));
                    } else {
                        sheet.addCell(new Label(i,objList.size()+2,"",arial6format));
                    }
                    sheet.setRowView(objList.size()+2, 350);
                }
                writebook.write();
                new ToastWei().showToast(c, "导出Excel-" + (sheetNum + 1) + "-成功");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (writebook != null) {
                    try {
                        writebook.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        } else {
            new ToastWei().showToast(c, "当前EXCEL-" + (sheetNum + 1) + "-没有数据需要导出");
        }
    }
}
