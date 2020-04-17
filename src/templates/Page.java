package templates;

/**
 * Created by ziyou8513 on 2019/12/19.
 */
public class Page {
    public static final String TEMPLATE = "package ${modelHome};\n" +
            "\n" +
            "import java.io.Serializable;\n" +
            "import java.util.ArrayList;\n" +
            "import java.util.Collections;\n" +
            "import java.util.List;\n" +
            "\n" +
            "public class Page<T> implements Serializable {\n" +
            "    private List<? extends T> data;\n" +
            "    private Integer totalNum;\n" +
            "\n" +
            "    public Page(List<? extends T> data, Integer totalNum) {\n" +
            "        this.data = data;\n" +
            "        this.totalNum = totalNum;\n" +
            "    }\n" +
            "\n" +
            "    public List<? extends T> getData() {\n" +
            "        return data;\n" +
            "    }\n" +
            "\n" +
            "    public void setData(List<? extends T> data) {\n" +
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
            "\n" +
            "    public static <T> Page<T> makeSinglePage(T t) {\n" +
            "        if (t == null) {\n" +
            "            return new Page<>(new ArrayList<>(), 0);\n" +
            "        }\n" +
            "        List<T> list = Collections.singletonList(t);\n" +
            "        return new Page<>(list, 1);\n" +
            "    }\n" +
            "\n" +
            "    public static <T> Page<T> makeListPage(List<T> tList) {\n" +
            "        if (tList == null) {\n" +
            "            return new Page<>(new ArrayList<>(), 0);\n" +
            "        }\n" +
            "        return new Page<>(tList, tList.size());\n" +
            "    }\n" +
            "}";
}
