package org.ylc.note.freemarker.util;

import freemarker.template.Configuration;
import freemarker.template.Template;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020-03-19
 */
public class FreemarkerUtil {

    /**
     * 导出文件
     *
     * @param dataMap      填充数据
     * @param templateName 模板的名称
     * @param templatePath 模板路径
     * @param fileName     导出后的文件名称
     * @param response     response
     */
    public static void exportFile(Map<String, Object> dataMap, String templateName, String templatePath, String fileName, HttpServletResponse response) {
        try {
            Configuration configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
            configuration.setDefaultEncoding("UTF-8");
            // 设置模本装置方法和路径,FreeMarker支持多种模板装载方法。可以重servlet，classpath，数据库装载，
            configuration.setClassForTemplateLoading(FreemarkerUtil.class, templatePath);
            //设置模板所在文件夹
            // configuration.setDirectoryForTemplateLoading(new File(templatePath));
            //获取模板
            Template template = configuration.getTemplate(templateName);
            // 告诉浏览器用什么软件可以打开此文件
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.setContentType("application/msword");// 定义输出类型
            template.process(dataMap, new OutputStreamWriter(response.getOutputStream()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
