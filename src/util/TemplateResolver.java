package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ziyou8513 on 2019/4/17.
 */
public class TemplateResolver {

    /**
     * 解析模板
     *
     * @param templateFile 模板文件名
     * @param params       模板参数
     * @return
     */
    public static String resolver(String templateFile, Map<String, String> params) {
        //从模板文件中读取模板内容
        String template = readTemplate("/templates/" + templateFile);

        //如果参数列表为空，则直接返回模板
        if (params == null || params.size() == 0) {
            return template;
        }

        return render(template, params);
    }

    /**
     * 渲染模板
     *
     * @param template 模板
     * @param params   模板参数
     */
    private static String render(String template, Map<String, String> params) {
        //正则匹配所有变量占位符 ${w+}
        Matcher m = Pattern.compile("\\$\\{\\w+}").matcher(template);
        //替换变量
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String param = m.group();
            //从params中获取变量占位符对应的值
            String value = params.get(param.substring(2, param.length() - 1));
            m.appendReplacement(sb, value == null ? "" : value);
        }
        m.appendTail(sb);
        return sb.toString();
    }

    /**
     * 读取模板内容
     *
     * @param path 模板文件路径
     * @return
     */
    private static String readTemplate(String path) {
        InputStream resource = TemplateResolver.class.getResourceAsStream(path);
        if (resource == null) {
            throw new RuntimeException("读取模板失败：" + path);
        }
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(resource));
            String str;
            // 一次读入一行，直到读入null为文件结束
            while ((str = reader.readLine()) != null) {
                //每行的最后加个换行，保证格式
                stringBuilder.append(str).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringBuilder.toString();
    }
}
