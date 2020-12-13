package util;

import model.TemplatesConts;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sheldon on 2020/12/13.
 */
public class CodeGenerator {
    /**
     * @param models      要生成的类（模块）名，多个用英文逗号’,‘分隔
     * @param basePackage 基础包路径
     * @param genPage     是否生成分页文件
     * @param genService  是否生成service
     */
    public static void genAll(String models, String basePackage, boolean genPage, boolean genService) {
        //参数校验
        if (models == null || models.trim().isEmpty()) {
            throw new RuntimeException("缺少必要参数");
        }
        if (basePackage == null || basePackage.trim().isEmpty()) {
            throw new RuntimeException("缺少必要参数");
        }
        //整理模板参数
        Map<String, String> params = makeTemplateParams(basePackage);

        //循环生成每个类的代码
        String[] modelList = models.split(",");
        for (String PASCAL_MODEL_NAME : modelList) {
            params.put("upperModelName", PASCAL_MODEL_NAME);
            //类名大驼峰转小驼峰
            String camelModelName = Character.toLowerCase(PASCAL_MODEL_NAME.charAt(0)) + PASCAL_MODEL_NAME.substring(1);
            params.put("lowerModelName", camelModelName);
            //生成代码，并写入文件
            gen(params, PASCAL_MODEL_NAME, genPage, genService);
        }

        //如果需要分页，则生成Sort & Page & Pageable
        //这三个文件没必要重复生成，所以写在循环外面
        if (genPage) {
            resolveTemplateAndGenerateFile(TemplatesConts.SORT, params, params.get("modelFilePath"), "Sort");
            resolveTemplateAndGenerateFile(TemplatesConts.PAGE, params, params.get("modelFilePath"), "Page");
            resolveTemplateAndGenerateFile(TemplatesConts.PAGEABLE, params, params.get("modelFilePath"), "Pageable");
        }
    }

    /**
     * 整理模板参数
     */
    private static Map<String, String> makeTemplateParams(String basePackage) {
        //封装成map
        Map<String, String> params = new HashMap<>();
        //包路径
        params.put("daoPackage", basePackage + ".dao");
        params.put("daoImplPackage", basePackage + ".dao.impl");
        params.put("servicePackage", basePackage + ".service");
        params.put("serviceImplPackage", basePackage + ".service.impl");
        params.put("modelHome", basePackage + ".model");
        params.put("modelDatabase", basePackage + ".model.database");

        //获取基础路径
        String[] packageNames = basePackage.split("\\.");

        //hack 在项目中使用的时候，需要调整生成文件的路径
        //判断当前路径
        String userDir = System.getProperty("user.dir");
        String BASE_PATH = ".\\src\\main\\java\\" + String.join(File.separator, packageNames);
        if (userDir.contains("tool") || userDir.contains("tools")) {
            BASE_PATH = "..\\." + BASE_PATH;
        }

        //文件生成位置
        params.put("daoFilePath", BASE_PATH + "\\dao");
        params.put("daoImplFilePath", BASE_PATH + "\\dao\\impl");
        params.put("serviceFilePath", BASE_PATH + "\\service");
        params.put("serviceImplFilePath", BASE_PATH + "\\service\\impl");
        params.put("modelFilePath", BASE_PATH + "\\model");
        return params;
    }

    /**
     * 生成代码，并写入文件
     */
    private static void gen(Map<String, String> params, String UPPER_MODEL_NAME, boolean genPage, boolean genService) {
        //判断dao层是否分页
        String daoTemplate = genPage ? TemplatesConts.DAO_PAGEABLE : TemplatesConts.DAO;
        String daoImplTemplate = genPage ? TemplatesConts.DAO_IMPL_PAGEABLE : TemplatesConts.DAO_IMPL;
        //dao
        resolveTemplateAndGenerateFile(daoTemplate, params, params.get("daoFilePath"), UPPER_MODEL_NAME + "Dao");
        //daoImpl
        resolveTemplateAndGenerateFile(daoImplTemplate, params, params.get("daoImplFilePath"), UPPER_MODEL_NAME + "DaoImpl");

        //判断是否生成service
        if (genService) {
            //service
            resolveTemplateAndGenerateFile(TemplatesConts.SERVICE, params, params.get("serviceFilePath"), UPPER_MODEL_NAME + "Service");
            //serviceImpl
            resolveTemplateAndGenerateFile(TemplatesConts.SERVICE_IMPL, params, params.get("serviceImplFilePath"), UPPER_MODEL_NAME + "ServiceImpl");
        }
    }

    private static void resolveTemplateAndGenerateFile(String templateFile, Map<String, String> params, String fileFolder, String fileName) {
        //渲染模板
        String content = TemplateResolver.resolver(templateFile, params);
        //写文件
        FileGenerator.generateFile(fileFolder, fileName, "java", content);
    }
}
