package templates;

/**
 * Created by sheldon on 2019/12/19.
 */
public class Pageable {
    public static final String TEMPLATE = "package ${modelHome};\n" +
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
}
