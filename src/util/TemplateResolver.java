package util;

import java.io.*;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ziyou8513 on 2019/4/17.
 */
public class TemplateResolver {
    //渲染模板
    public static String resolver(String templateFile, Map<String, String> params) {
        //从模板文件中读取模板内容
        String template = readFile("/templates/" + templateFile);

        //如果参数列表为空，则直接返回模板
        if (params == null || params.size() == 0) {
            return template;
        }

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

    private static String readFile(String path) {
        InputStream resource = TemplateResolver.class.getResourceAsStream(path);
        if (resource == null) {
            System.out.println("无法读取模板信息");
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
//        File file = new File(path);
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
