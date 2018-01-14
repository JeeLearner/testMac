package com.lyd.utils.excel;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
/**
 * 
 * <p>Title: ExcelUtil.java</p>
 * <p>Description: 导出excel工具类</p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: </p>
 * @author lyd
 * @date 2017年7月25日
 * @version 1.0
 */
public class ExcelUtil_xlsx<T> {   
   
	/**
	 * 
	 * <p>Title: exportExcel</p>
	 * <p>Description: 导出excel的方法</p>
	 * @author lyd
	 * @date 2017年7月25日
	 * @param title excel中的sheet名称 
	 * @param headers 表头 
	 * @param columns 
	 * @param result 结果集
	 * @param out 输出流
	 * @param pattern 时间格式 
	 * @throws Exception
	 */
    public boolean exportExcel(String title, String[] headers,String[] columns, Collection<T> result, OutputStream out, String pattern){   
        try {
    	// 声明一个工作薄   
        XSSFWorkbook workbook = new XSSFWorkbook(); 
       
        // 生成一个表格   
        XSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为20个字节   
        sheet.setDefaultColumnWidth(18);
           
        // 生成一个样式   
        XSSFCellStyle style = workbook.createCellStyle();   
        // 设置这些样式   
        style.setFillForegroundColor(HSSFColor.GOLD.index);   
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);   
        style.setBorderBottom(XSSFCellStyle.BORDER_THIN);   
        style.setBorderLeft(XSSFCellStyle.BORDER_THIN);   
        style.setBorderRight(XSSFCellStyle.BORDER_THIN);   
        style.setBorderTop(XSSFCellStyle.BORDER_THIN);   
        style.setAlignment(XSSFCellStyle.ALIGN_CENTER);   
        // 生成一个字体   
        XSSFFont font = workbook.createFont();   
        font.setColor(HSSFColor.VIOLET.index);   
        //font.setFontHeightInPoints((short) 12);   
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);   
        // 把字体应用到当前的样式   
        style.setFont(font);   
           
        // 指定当单元格内容显示不下时自动换行   
        style.setWrapText(true);   
         
        // 声明一个画图的顶级管理器  
        XSSFDrawing patriarch = sheet.createDrawingPatriarch();
        /* 
          
                           以下可以用于设置导出的数据的样式 
           
        // 生成并设置另一个样式 
        XSSFCellStyle style2 = workbook.createCellStyle(); 
        style2.setFillForegroundColor(XSSFColor.LIGHT_YELLOW.index); 
        style2.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND); 
        style2.setBorderBottom(XSSFCellStyle.BORDER_THIN); 
        style2.setBorderLeft(XSSFCellStyle.BORDER_THIN); 
        style2.setBorderRight(XSSFCellStyle.BORDER_THIN); 
        style2.setBorderTop(XSSFCellStyle.BORDER_THIN); 
        style2.setAlignment(XSSFCellStyle.ALIGN_CENTER); 
        style2.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); 
        // 生成另一个字体 
        XSSFFont font2 = workbook.createFont(); 
        font2.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL); 
        // 把字体应用到当前的样式 
        style2.setFont(font2); 
        // 声明一个画图的顶级管理器 
        XSSFPatriarch patriarch = sheet.createDrawingPatriarch(); 
         
        // 定义注释的大小和位置,详见文档 
        XSSFComment comment = patriarch.createComment(new XSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5)); 
        // 设置注释内容 
        comment.setString(new XSSFRichTextString("可以在POI中添加注释！")); 
        // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容. 
        comment.setAuthor("leno");*/   
           
           
        // 产生表格标题行   
        //表头的样式 
        XSSFCellStyle titleStyle = workbook.createCellStyle();// 创建样式对象 
        titleStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER_SELECTION);// 水平居中 
        titleStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 垂直居中 
        // 设置字体 
        XSSFFont titleFont = workbook.createFont(); // 创建字体对象 
        titleFont.setFontHeightInPoints((short) 15); // 设置字体大小 
        titleFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);// 设置粗体 
      //  titleFont.setFontName("黑体"); // 设置为黑体字 
        titleStyle.setFont(titleFont);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headers.length-1));
        //sheet.addMergedRegion(new Region(0,(short)0,0,(short)(headers.length-1)));//指定合并区域  
        XSSFRow rowHeader = sheet.createRow(0);   
        XSSFCell cellHeader = rowHeader.createCell(0);   //只能往第一格子写数据，然后应用样式，就可以水平垂直居中 
        XSSFRichTextString textHeader = new XSSFRichTextString(title);   
        cellHeader.setCellStyle(titleStyle); 
        cellHeader.setCellValue(textHeader); 
         
        XSSFRow row = sheet.createRow(1);   
        for (int i = 0; i < headers.length; i++) {   
            XSSFCell cell = row.createCell(i);   
            cell.setCellStyle(style);   
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);   
            cell.setCellValue(text);   
         }   
         // 遍历集合数据，产生数据行   
         if(result != null && !result.isEmpty()){   
             int index = 2;   
             for(T t:result){  
             //  Field[] fields = t.getClass().getDeclaredFields(); 
                 row = sheet.createRow(index);   
                 index++; 
                 for(int i = 0; i < columns.length; i++) { 
                     XSSFCell cell = row.createCell(i);
//                   Field field = fields[i]; 
//                   String fieldName = field.getName(); 
                     String fieldName = columns[i]; 
                     String getMethodName = "get" 
                         + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1); 
                     Class tCls = t.getClass();
                     Method getMethod = tCls.getMethod(getMethodName, new Class[]{}); 
                    // getMethod.getReturnType().isInstance(obj);
                     Object value = getMethod.invoke(t, new Class[]{}); 
                     String textValue = null; 
                     if(value == null) { 
                         textValue = ""; 
                     }else if (value instanceof Date) { 
                         Date date = (Date) value; 
                         SimpleDateFormat sdf = new SimpleDateFormat(pattern); 
                          textValue = sdf.format(date); 
                      }  else if (value instanceof byte[]) { 
                         // 有图片时，设置行高为60px; 
                         row.setHeightInPoints(60); 
                         // 设置图片所在列宽度为80px,注意这里单位的一个换算 
                         sheet.setColumnWidth(i, (short) (35.7 * 80)); 
                         // sheet.autoSizeColumn(i); 
                         byte[] bsValue = (byte[]) value; 
                         XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 
                               1023, 255, (short) 6, index, (short) 6, index);
                         anchor.setAnchorType(2);
                         patriarch.createPicture(anchor, workbook.addPicture( 
                               bsValue, XSSFWorkbook.PICTURE_TYPE_JPEG)); 
                      } else{ 
                         //其它数据类型都当作字符串简单处理 
                         textValue = value.toString(); 
                      } 
                      
                     if(textValue!= null){ 
                         Pattern p = Pattern.compile("^//d+(//.//d+)?$");   
                         Matcher matcher = p.matcher(textValue); 
                         if(matcher.matches()){ 
                            //是数字当作double处理 
                            cell.setCellValue(Double.parseDouble(textValue)); 
                         }else{ 
                            XSSFRichTextString richString = new XSSFRichTextString(textValue); 
//                            XSSFFont font3 = workbook.createFont(); 
//                            font3.setColor(XSSFColor.BLUE.index); 
//                            richString.applyFont(font3); 
                            cell.setCellValue(richString); 
                         } 
                      } 
                 } 
             }      
         }
         out.flush();
         workbook.write(out);
         out.close();
         return true;
        } catch (Exception e) {
    		e.printStackTrace();
    		return false;
    	}
     }   
   
 } 