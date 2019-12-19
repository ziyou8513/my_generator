package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by ziyou8513 on 2019/4/17.
 */
public class FileGenerator {
    //写文件
    public static void generateFile(String fileFolder, String fileName, String suffix, String content) {
        try {
            //判断路径是否存在，不存在则创建
            File folder = new File(fileFolder);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            File file = new File(fileFolder + File.separator + fileName + "." + suffix);
            //文件不存在则创建文件
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();

            System.out.println("success generate file: " + file.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
