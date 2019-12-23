import templates.*;
import util.FileGenerator;
import util.TemplateResolver;

import java.io.File;
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
        String PASCAL_MODEL_NAME_LIST = pro.getProperty("PASCAL_MODEL_NAME");

        //获取其他固定属性
        Map<String, String> params = makeParams(pro);

        //循环处理多个类的情况
        for (String PASCAL_MODEL_NAME : PASCAL_MODEL_NAME_LIST.split(",")) {
            params.put("upperModelName", PASCAL_MODEL_NAME);
            //类名大驼峰转小驼峰
            String camelModelName = Character.toLowerCase(PASCAL_MODEL_NAME.charAt(0)) + PASCAL_MODEL_NAME.substring(1);
            params.put("lowerModelName", camelModelName);
            //循环生成文件
            loop(params, PASCAL_MODEL_NAME);
        }

        //如果需要分页，则生成Page & Pageable
        if (Boolean.parseBoolean(params.get("pagination"))) {
            resolveTemplateAndGenerateFile(Page.TEMPLATE, params, params.get("modelFilePath"), "Page");
            resolveTemplateAndGenerateFile(Pageable.TEMPLATE, params, params.get("modelFilePath"), "Pageable");
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
        //获取基础包名
        String BASE_PACKAGE = pro.getProperty("BASE_PACKAGE");
        //获取各类文件包路径
        String DAO_PACKAGE = BASE_PACKAGE + ".dao";
        String DAO_IMPL_PACKAGE = DAO_PACKAGE + ".impl";
        String SERVICE_PACKAGE = BASE_PACKAGE + "service";
        String SERVICE_IMPL_PACKAGE = SERVICE_PACKAGE + ".impl";
        String MODEL_HOME = BASE_PACKAGE + ".model";
        String MODEL_DATABASE = MODEL_HOME + ".database";

        //获取基础路径
        String[] packageNames = BASE_PACKAGE.split("\\.");

        //hack 在项目中使用的时候，需要调整生成文件的路径
        //判断当前路径
        String userDir = System.getProperty("user.dir");
        String BASE_PATH = ".\\src\\main\\java\\" + String.join(File.separator, packageNames);
        if (userDir.contains("tool") || userDir.contains("tools")) {
            BASE_PATH = "..\\." + BASE_PATH;
        }

        //获取各类文件生成路径
        String DAO_FILE_PATH = BASE_PATH + "\\dao";
        String DAO_IMPL_FILE_PATH = DAO_FILE_PATH + "\\impl";
        String SERVICE_FILE_PATH = BASE_PATH + "\\service";
        String SERVICE_IMPL_FILE_PATH = SERVICE_FILE_PATH + "\\impl";
        String MODEL_FILE_PATH = BASE_PATH + "\\model";

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
        params.put("modelFilePath", MODEL_FILE_PATH);
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
            daoTemplate = DaoPageable.TEMPLATE;
            daoImplTemplate = DaoImplPageable.TEMPLATE;
        } else {
            daoTemplate = Dao.TEMPLATE;
            daoImplTemplate = DaoImpl.TEMPLATE;
        }
        //dao
        resolveTemplateAndGenerateFile(daoTemplate, params, params.get("daoFilePath"), UPPER_MODEL_NAME + "Dao");
        //daoImpl
        resolveTemplateAndGenerateFile(daoImplTemplate, params, params.get("daoImplFilePath"), UPPER_MODEL_NAME + "DaoImpl");

        //判断是否生成service
        if (Boolean.parseBoolean(params.get("generateService"))) {
            //service
            resolveTemplateAndGenerateFile(Service.TEMPLATE, params, params.get("serviceFilePath"), UPPER_MODEL_NAME + "Service");
            //serviceImpl
            resolveTemplateAndGenerateFile(ServiceImpl.TEMPLATE, params, params.get("serviceImplFilePath"), UPPER_MODEL_NAME + "ServiceImpl");
        }
    }

    private static void resolveTemplateAndGenerateFile(String template, Map<String, String> params, String fileFolder, String fileName) {
        //渲染模板
        String serviceImplContent = TemplateResolver.resolver(template, params);
        //写文件
        FileGenerator.generateFile(fileFolder, fileName, SUFFIX, serviceImplContent);
    }
}
