package jsonapi.tree;

import java.util.ArrayList;

public class JsonDevicesTree {

    private JsonUdnames udnames;
    private JsonWorkspace workspace;
    private JsonTree tree;
    private JsonTooltip tooltip;  //TODO make this a map
    private String viewNumber;
    //private JsonOverlimit overlimit;
    private ArrayList<JsonOverlimit> overlimit;
    private JsonLayout layout;
    private ArrayList<String> ivStatus;  // may be a String[]
    private String isError;
    private JsonSvcStatus svcStatus;
    private ArrayList<String> containers;
    private String pk;
    private int age;
    private JsonSupervisor supervisor;

    public JsonDevicesTree() {
        ivStatus = new ArrayList<String>();
        containers = new ArrayList<String>();
        overlimit = new ArrayList<JsonOverlimit>();
    }

    public JsonUdnames getUdnames() {
        return udnames;
    }

    public void setUdnames(JsonUdnames udnames) {
        this.udnames = udnames;
    }

    public JsonWorkspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(JsonWorkspace workspace) {
        this.workspace = workspace;
    }

    public JsonTree getTree() {
        return tree;
    }

    public void setTree(JsonTree tree) {
        this.tree = tree;
    }

    public JsonTooltip getTooltip() {
        return tooltip;
    }

    public void setTooltip(JsonTooltip tooltip) {
        this.tooltip = tooltip;
    }

    public String getViewNumber() {
        return viewNumber;
    }

    public void setViewNumber(String viewNumber) {
        this.viewNumber = viewNumber;
    }

    /*
    public JsonOverlimit getOverlimit() {
        return overlimit;
    }

    public void setOverlimit(JsonOverlimit overlimit) {
        this.overlimit = overlimit;
    }
     */

    public ArrayList<JsonOverlimit> getOverlimit() {
        return overlimit;
    }

    public void setOverlimit(ArrayList<JsonOverlimit> overlimit) {
        this.overlimit = overlimit;
    }

    public JsonLayout getLayout() {
        return layout;
    }

    public void setLayout(JsonLayout layout) {
        this.layout = layout;
    }

    public ArrayList<String> getIvStatus() {
        return ivStatus;
    }

    public void setIvStatus(ArrayList<String> ivStatus) {
        this.ivStatus = ivStatus;
    }

    public String getIsError() {
        return isError;
    }

    public void setIsError(String isError) {
        this.isError = isError;
    }

    public JsonSvcStatus getSvcStatus() {
        return svcStatus;
    }

    public void setSvcStatus(JsonSvcStatus svcStatus) {
        this.svcStatus = svcStatus;
    }

    public ArrayList<String> getContainers() {
        return containers;
    }

    public void setContainers(ArrayList<String> containers) {
        this.containers = containers;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public JsonSupervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(JsonSupervisor supervisor) {
        this.supervisor = supervisor;
    }
}
