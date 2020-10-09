package jsonapi.tree;

/*
  "layout": {
    "backgroundURL": "",
    "layoutSize": "",
    "layoutStatus": "{\"topology\":true,\"plant\":true}",    <<<< NOTE: this is actually a STRING that contains a map
    "topologyStatus": ""
  },

  "layout":{"backgroundURL":"","layoutSize":"","layoutStatus":"{\"topology\":true,\"plant\":true}","topologyStatus":""}

 */
public class JsonLayout {

    private String backgroundURL;
    private String layoutSize;
    private String layoutStatus;   // note to use this you will need to parse the data that just looks like its a map

    private String topologyStatus;

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


    public String getTopologyStatus() {
        return topologyStatus;
    }

    public void setTopologyStatus(String topologyStatus) {
        this.topologyStatus = topologyStatus;
    }

    public String getLayoutStatus() {
        return layoutStatus;
    }

    public void setLayoutStatus(String layoutStatus) {
        this.layoutStatus = layoutStatus;
    }

}
