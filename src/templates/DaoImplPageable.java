package templates;

/**
 * Created by ziyou8513 on 2019/12/19.
 */
public class DaoImplPageable {
    public static final String TEMPLATE = "package ${daoImplPackage};\n" +
            "\n" +
            "import ${modelHome}.Page;\n" +
            "import ${daoPackage}.${upperModelName}Dao;\n" +
            "import ${daoPackage}.mapper.${upperModelName}Mapper;\n" +
            "import ${modelDatabase}.${upperModelName};\n" +
            "import ${modelDatabase}.${upperModelName}Example;\n" +
            "import org.springframework.beans.factory.annotation.Autowired;\n" +
            "import ${modelHome}.Pageable;\n" +
            "import org.springframework.stereotype.Repository;\n" +
            "\n" +
            "import java.util.List;\n" +
            "\n" +
            "@Repository\n" +
            "public class ${upperModelName}DaoImpl implements ${upperModelName}Dao {\n" +
            "\n" +
            "    @Autowired\n" +
            "    private ${upperModelName}Mapper ${lowerModelName}Mapper;\n" +
            "\n" +
            "    @Override\n" +
            "    public int insert(${upperModelName} record) {\n" +
            "        return ${lowerModelName}Mapper.insert(record);\n" +
            "    }\n" +
            "\n" +
            "    @Override\n" +
            "    public int update(${upperModelName} record) {\n" +
            "        return ${lowerModelName}Mapper.updateByPrimaryKeySelective(record);\n" +
            "    }\n" +
            "\n" +
            "    @Override\n" +
            "    public ${upperModelName} find(String id) {\n" +
            "        return ${lowerModelName}Mapper.selectByPrimaryKey(id);\n" +
            "    }\n" +
            "\n" +
            "    @Override\n" +
            "    public List<${upperModelName}> findList(${upperModelName} record) {\n" +
            "        ${upperModelName}Example example = new ${upperModelName}Example();\n" +
            "        ${upperModelName}Example.Criteria criteria = example.createCriteria();\n" +
            "        //TODO query condition\n" +
            "        return ${lowerModelName}Mapper.selectByExample(example);\n" +
            "    }\n" +
            "\n" +
            "    @Override\n" +
            "    public Page<${upperModelName}> findPage(${upperModelName} record, Pageable pageable) {\n" +
            "        ${upperModelName}Example example = new ${upperModelName}Example();\n" +
            "        ${upperModelName}Example.Criteria criteria = example.createCriteria();\n" +
            "        //TODO query condition\n" +
            "\n" +
            "        int totalNum = ${lowerModelName}Mapper.countByExample(example);\n" +
            "        if (pageable.getPage() != null && pageable.getLimit() != null) {\n" +
            "            example.setOrderByClause(String.format(\"create_time desc limit %s,%s\", (pageable.getPage() - 1) * pageable.getLimit(), pageable.getLimit()));\n" +
            "        }\n" +
            "        List<${upperModelName}> data = ${lowerModelName}Mapper.selectByExample(example);\n" +
            "        return new Page<>(data, totalNum);\n" +
            "    }\n" +
            "}\n";
}
