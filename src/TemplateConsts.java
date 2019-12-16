/**
 * Created by duanxufei on 2019/4/17.
 */
public class TemplateConsts {

    public static final String PAGEABLE_TEMPLATE = "package ${modelHome};\n" +
            "\n" +
            "public class Pageable {\n" +
            "    private Integer page;\n" +
            "    private Integer limit;\n" +
            "\n" +
            "    public Pageable(Integer page, Integer limit) {\n" +
            "        this.page = page;\n" +
            "        this.limit = limit;\n" +
            "    }\n" +
            "\n" +
            "    public Pageable() {\n" +
            "    }\n" +
            "\n" +
            "    public Integer getPage() {\n" +
            "        return page;\n" +
            "    }\n" +
            "\n" +
            "    public void setPage(Integer page) {\n" +
            "        this.page = page;\n" +
            "    }\n" +
            "\n" +
            "    public Integer getLimit() {\n" +
            "        return limit;\n" +
            "    }\n" +
            "\n" +
            "    public void setLimit(Integer limit) {\n" +
            "        this.limit = limit;\n" +
            "    }\n" +
            "}";

    public static final String PAGE_TEMPLATE = "package ${modelHome};\n" +
            "\n" +
            "import java.io.Serializable;\n" +
            "import java.util.List;\n" +
            "\n" +
            "public class Page<T> implements Serializable {\n" +
            "\t  //current page data\n" +
            "    private List<T> data;\n" +
            "\t  //total record num\n" +
            "    private Integer totalNum;\n" +
            "\n" +
            "    public Page(List<T> data, Integer totalNum) {\n" +
            "        this.data = data;\n" +
            "        this.totalNum = totalNum;\n" +
            "    }\n" +
            "\n" +
            "    public List<T> getData() {\n" +
            "        return data;\n" +
            "    }\n" +
            "\n" +
            "    public void setData(List<T> data) {\n" +
            "        this.data = data;\n" +
            "    }\n" +
            "\n" +
            "    public Integer getTotalNum() {\n" +
            "        return totalNum;\n" +
            "    }\n" +
            "\n" +
            "    public void setTotalNum(Integer totalNum) {\n" +
            "        this.totalNum = totalNum;\n" +
            "    }\n" +
            "}";

    public static final String DAO_TEMPLATE = "package ${daoPackage};\n" +
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

    public static final String DAO_IMPL_TEMPLATE = "package ${daoImplPackage};\n" +
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
            "        return ${lowerModelName}Mapper.selectByExample(new ${upperModelName}Example());\n" +
            "    }\n" +
            "\n" +
            "    @Override\n" +
            "    public Page<${upperModelName}> findPage(${upperModelName} record, Pageable pageable) {\n" +
            "        if (pageable == null) {\n" +
            "            return new Page<>(null, 0);\n" +
            "        }\n" +
            "        ${upperModelName}Example example = new ${upperModelName}Example();\n" +
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

    public static final String DAO_TEMPLATE_WITHOUT_PAGE = "package ${daoPackage};\n" +
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

    public static final String DAO_IMPL_TEMPLATE_WITHOUT_PAGE = "package ${daoImplPackage};\n" +
            "\n" +
            "import ${daoPackage}.${upperModelName}Dao;\n" +
            "import ${daoPackage}.mapper.${upperModelName}Mapper;\n" +
            "import ${modelDatabase}.${upperModelName};\n" +
            "import ${modelDatabase}.${upperModelName}Example;\n" +
            "import org.springframework.beans.factory.annotation.Autowired;\n" +
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
            "        return ${lowerModelName}Mapper.selectByExample(new ${upperModelName}Example());\n" +
            "    }\n" +
            "}\n";

    public static final String SERVICE_TEMPLATE = "package ${servicePackage};\n" +
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

    public static final String SERVICE_IMPL_TEMPLATE = "package ${serviceImplPackage};\n" +
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
