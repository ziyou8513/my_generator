package templates;

/**
 * Created by ziyou8513 on 2019/12/19.
 */
public class DaoPageable {
    public static final String TEMPLATE = "package ${daoPackage};\n" +
            "\n" +
            "import ${modelHome}.Page;\n" +
            "import ${modelDatabase}.${upperModelName};\n" +
            "import ${modelHome}.Pageable;\n" +
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
            "\n" +
            "    //get pagination records by condition\n" +
            "    Page<${upperModelName}> findPage(${upperModelName} record, Pageable pageable);\n" +
            "}";
}
