package jsonapi.tree;

/*
 "tooltip": {
    "tooltip1": "1",
    "tooltip2": "0"
  },
 */
public class JsonTooltip {
    private String tooltip1;
    private String tooltip2;

    public String getTooltip1() {
        return tooltip1;
    }

    public void setTooltip1(String tooltip1) {
        this.tooltip1 = tooltip1;
    }

    public String getTooltip2() {
        return tooltip2;
    }

    public void setTooltip2(String tooltip2) {
        this.tooltip2 = tooltip2;
    }

    @Override
    public String toString() {
        return "JsonTooltip{" +
                "tooltip1='" + tooltip1 + '\'' +
                ", tooltip2='" + tooltip2 + '\'' +
                '}';
    }
}
