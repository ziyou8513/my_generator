package templates;

/**
 * Created by ziyou8513 on 2019/12/19.
 */
public class Page {
    public static final String TEMPLATE = "package ${modelHome};\n" +
            "\n" +
            "import java.io.Serializable;\n" +
            "import java.util.List;\n" +
            "\n" +
            "public class Page<T> implements Serializable {\n" +
            "    //current page data\n" +
            "    private List<T> data;\n" +
            "    //total record num\n" +
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
}
