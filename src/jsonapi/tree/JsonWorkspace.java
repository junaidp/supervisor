package jsonapi.tree;

/*
"workspace": {
    "backgroundURL": "plant_layout/Olive_Plant2.jpg",
    "layoutSize": "D",
    "width": "10200",
    "x": "32882.47037435003",
    "y": "41689.36089533533",
    "height": "6600"
  },
 */
public class JsonWorkspace {

    private String backgroundURL;
    private String layoutSize;
    private String width;
    private String x;
    private String y;
    private String height;

    public String getBackgroundURL() {
        return backgroundURL;
    }

    public void setBackgroundURL(String backgroundURL) {
        this.backgroundURL = backgroundURL;
    }

    public String getLayoutSize() {
        return layoutSize;
    }

    public void setLayoutSize(String layoutSize) {
        this.layoutSize = layoutSize;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "JsonWorkspace{" +
                "backgroundURL='" + backgroundURL + '\'' +
                ", layoutSize='" + layoutSize + '\'' +
                ", width='" + width + '\'' +
                ", x='" + x + '\'' +
                ", y='" + y + '\'' +
                ", height='" + height + '\'' +
                '}';
    }
}
