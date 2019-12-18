package util;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by duanxufei on 2019/4/17.
 */
public class TemplateResolver {
    //渲染模板
    public static String resolver(String template, Map<String, String> params) {
        //如果参数列表为空，则直接返回模板
        if (params == null || params.size() == 0) {
            return template;
        }
        StringBuffer sb = new StringBuffer();
        //正则匹配所有变量占位符 ${w+}
        Matcher m = Pattern.compile("\\$\\{\\w+}").matcher(template);
        while (m.find()) {
            String param = m.group();
            //从params中获取变量占位符对应的值
            String value = params.get(param.substring(2, param.length() - 1));
            m.appendReplacement(sb, value == null ? "" : value);
        }
        m.appendTail(sb);
        return sb.toString();
    }
}
