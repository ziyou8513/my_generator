package templates;

/**
 * Created by ziyou8513 on 2019/12/19.
 */
public class ServiceImpl {
    public static final String TEMPLATE = "package ${serviceImplPackage};\n" +
            "\n" +
            "import ${daoPackage}.${upperModelName}Dao;\n" +
            "import ${modelDatabase}.${upperModelName};\n" +
            "import ${servicePackage}.${upperModelName}Service;\n" +
            "import org.springframework.beans.factory.annotation.Autowired;\n" +
            "import org.springframework.stereotype.Service;\n" +
            "\n" +
            "import java.util.List;\n" +
            "\n" +
            "@Service\n" +
            "public class ${upperModelName}ServiceImpl implements ${upperModelName}Service {\n" +
            "\n" +
            "    @Autowired\n" +
            "    private ${upperModelName}Dao ${lowerModelName}Dao;\n" +
            "\n" +
            "    @Override\n" +
            "    public int insert(${upperModelName} record) {\n" +
            "        return ${lowerModelName}Dao.insert(record);\n" +
            "    }\n" +
            "\n" +
            "    @Override\n" +
            "    public int update(${upperModelName} record) {\n" +
            "        return ${lowerModelName}Dao.update(record);\n" +
            "    }\n" +
            "\n" +
            "    @Override\n" +
            "    public ${upperModelName} find(String id) {\n" +
            "        return ${lowerModelName}Dao.find(id);\n" +
            "    }\n" +
            "\n" +
            "    @Override\n" +
            "    public List<${upperModelName}> findList(${upperModelName} record) {\n" +
            "        return ${lowerModelName}Dao.findList(record);\n" +
            "    }\n" +
            "}";
}
