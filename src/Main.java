import util.CodeGenerator;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by sheldon on 2019/12/16.
 */
public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("未指定配置文件，将使用缺省值");
            args = new String[]{"config=config.properties"};
        }

        //加载配置文件
        String path = args[0].substring(args[0].indexOf("=") + 1);
        Properties pro = readProperties(path);
        if (pro == null) {
            throw new RuntimeException("加载配置文件失败");
        }

        //从配置文件读取参数
        String modelList = pro.getProperty("PASCAL_MODEL_NAME");
        String basePackage = pro.getProperty("BASE_PACKAGE");
        String pagination = pro.getProperty("PAGINATION");
        String generateService = pro.getProperty("GENERATE_SERVICE");

        boolean genPage = Boolean.parseBoolean(pagination);
        boolean genService = Boolean.parseBoolean(generateService);
        //开始生成代码
        CodeGenerator.genAll(modelList, basePackage, genPage, genService);

        System.out.println("执行完成");
    }

    private static Properties readProperties(String path) {
        Properties pro = new Properties();
        FileInputStream in = null;
        try {
            in = new FileInputStream(path);
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
}
