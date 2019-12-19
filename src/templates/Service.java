package templates;

/**
 * Created by sheldon on 2019/12/19.
 */
public class Service {
    public static final String TEMPLATE = "package ${servicePackage};\n" +
            "\n" +
            "import ${modelDatabase}.${upperModelName};\n" +
            "\n" +
            "import java.util.List;\n" +
            "\n" +
            "public interface ${upperModelName}Service {\n" +
            "    int insert(${upperModelName} record);\n" +
            "\n" +
            "    int update(${upperModelName} record);\n" +
            "\n" +
            "    ${upperModelName} find(String id);\n" +
            "\n" +
            "    List<${upperModelName}> findList(${upperModelName} record);\n" +
            "}";
}
