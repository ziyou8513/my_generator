package templates;

/**
 * Created by sheldon on 2019/12/19.
 */
public class Dao {
    public static final String TEMPLATE = "package ${daoPackage};\n" +
            "\n" +
            "import ${modelDatabase}.${upperModelName};\n" +
            "\n" +
            "import java.util.List;\n" +
            "\n" +
            "public interface ${upperModelName}Dao {\n" +
            "    //add a record\n" +
            "    int insert(${upperModelName} record);\n" +
            "\n" +
            "    //update a record\n" +
            "    int update(${upperModelName} record);\n" +
            "\n" +
            "    //get record by primary key\n" +
            "    ${upperModelName} find(String id);\n" +
            "\n" +
            "    //get records by condition\n" +
            "    List<${upperModelName}> findList(${upperModelName} record);\n" +
            "}";
}
