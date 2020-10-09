package jsonapi.tree;

/*
  "supervisor": {
    "supervisor": "0"
  }
 */
public class JsonSupervisor {
    private String supervisor;

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    @Override
    public String toString() {
        return "JsonSupervisor{" +
                "supervisor='" + supervisor + '\'' +
                '}';
    }
}
