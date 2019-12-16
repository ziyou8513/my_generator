import util.FileGenerator;
import util.TemplateResolver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by sheldon on 2019/12/16.
 */
public class Main {
    private static final String SUFFIX = "java";

    public static void main(String[] args) {

        //加载配置文件
        Properties pro = readProperties();

        if (pro == null) {
            System.out.println("can not read properties!");
            return;
        }

        //获取需要生成的model类
        String UPPER_MODEL_NAME_LIST = pro.getProperty("UPPER_MODEL_NAME");

        //获取其他固定属性
        Map<String, String> params = makeParams(pro);

        //循环处理多个类的情况
        for (String UPPER_MODEL_NAME : UPPER_MODEL_NAME_LIST.split(",")) {
            params.put("upperModelName", UPPER_MODEL_NAME);
            //类名大驼峰转小驼峰
            params.put("lowerModelName", Character.toLowerCase(UPPER_MODEL_NAME.charAt(0)) + UPPER_MODEL_NAME.substring(1));
            //循环生成文件
            loop(params, UPPER_MODEL_NAME);
        }
        System.out.println("all done!");
    }

    private static Properties readProperties() {
        Properties pro = new Properties();
        FileInputStream in = null;
        try {
            in = new FileInputStream(".\\config.properties");
            pro.load(in);
            return pro;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private static Map<String, String> makeParams(Properties pro) {
        //获取包路径
        String DAO_PACKAGE = pro.getProperty("DAO_PACKAGE");
        String DAO_IMPL_PACKAGE = pro.getProperty("DAO_IMPL_PACKAGE");
        String SERVICE_PACKAGE = pro.getProperty("SERVICE_PACKAGE");
        String SERVICE_IMPL_PACKAGE = pro.getProperty("SERVICE_IMPL_PACKAGE");
        String MODEL_HOME = pro.getProperty("MODEL_HOME");
        String MODEL_DATABASE = pro.getProperty("MODEL_DATABASE");
        //获取文件生成位置
        String DAO_FILE_PATH = pro.getProperty("DAO_FILE_PATH");
        String DAO_IMPL_FILE_PATH = pro.getProperty("DAO_IMPL_FILE_PATH");
        String SERVICE_FILE_PATH = pro.getProperty("SERVICE_FILE_PATH");
        String SERVICE_IMPL_FILE_PATH = pro.getProperty("SERVICE_IMPL_FILE_PATH");
        //是否分页
        String pagination = pro.getProperty("PAGINATION");
        //是否生成service
        String generateService = pro.getProperty("GENERATE_SERVICE");

        //封装成map
        Map<String, String> params = new HashMap<>();
        //包路径
        params.put("daoPackage", DAO_PACKAGE);
        params.put("daoImplPackage", DAO_IMPL_PACKAGE);
        params.put("servicePackage", SERVICE_PACKAGE);
        params.put("serviceImplPackage", SERVICE_IMPL_PACKAGE);
        params.put("modelHome", MODEL_HOME);
        params.put("modelDatabase", MODEL_DATABASE);
        //文件生成位置
        params.put("daoFilePath", DAO_FILE_PATH);
        params.put("daoImplFilePath", DAO_IMPL_FILE_PATH);
        params.put("serviceFilePath", SERVICE_FILE_PATH);
        params.put("serviceImplFilePath", SERVICE_IMPL_FILE_PATH);
        //是否分页
        params.put("pagination", pagination);
        //是否生成service
        params.put("generateService", generateService);

        return params;
    }

    private static void loop(Map<String, String> params, String UPPER_MODEL_NAME) {
        //判断dao层是否分页
        String daoTemplate;
        String daoImplTemplate;
        if (Boolean.parseBoolean(params.get("pagination"))) {
            daoTemplate = TemplateConsts.DAO_TEMPLATE;
            daoImplTemplate = TemplateConsts.DAO_IMPL_TEMPLATE;
        } else {
            daoTemplate = TemplateConsts.DAO_TEMPLATE_WITHOUT_PAGE;
            daoImplTemplate = TemplateConsts.DAO_IMPL_TEMPLATE_WITHOUT_PAGE;
        }
        //dao
        resolveTemplateAndGenerateFile(daoTemplate, params, params.get("daoFilePath"), UPPER_MODEL_NAME + "Dao");
        //daoImpl
        resolveTemplateAndGenerateFile(daoImplTemplate, params, params.get("daoImplFilePath"), UPPER_MODEL_NAME + "DaoImpl");

        //判断是否生成service
        if (Boolean.parseBoolean(params.get("generateService"))) {
            //service
            resolveTemplateAndGenerateFile(TemplateConsts.SERVICE_TEMPLATE, params, params.get("serviceFilePath"), UPPER_MODEL_NAME + "Service");
            //serviceImpl
            resolveTemplateAndGenerateFile(TemplateConsts.SERVICE_IMPL_TEMPLATE, params, params.get("serviceImplFilePath"), UPPER_MODEL_NAME + "ServiceImpl");
        }
    }

    private static void resolveTemplateAndGenerateFile(String template, Map<String, String> params, String fileFolder, String fileName) {
        //渲染模板
        String serviceImplContent = TemplateResolver.resolver(template, params);
        //写文件
        FileGenerator.generateFile(fileFolder, fileName, SUFFIX, serviceImplContent);
    }
}
