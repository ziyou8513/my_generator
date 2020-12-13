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
        if (content == null || content.isEmpty()) {
            throw new RuntimeException("无内容，终止写入文件");
        }
        try {
            //判断路径是否存在，不存在则创建
            File folder = new File(fileFolder);
            if (!folder.exists()) {
                boolean mkdirs = folder.mkdirs();
                if (!mkdirs) {
                    throw new RuntimeException("创建目录失败");
                }
            }

            File file = new File(fileFolder + File.separator + fileName + "." + suffix);
            //文件不存在则创建文件
            if (!file.exists()) {
                boolean newFile = file.createNewFile();
                if (!newFile) {
                    throw new RuntimeException("创建文件失败");
                }
            }

            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();

            System.out.println("写入文件成功：" + file.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
