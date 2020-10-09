package jsonapi.tree;

/*
{
    "userDefined6": "Model",
    "userDefined4": "Revision",
    "userDefined5": "Vendor",
    "userDefined2": "User Defined 2",
    "userDefined3": "User Defined 3",
    "userDefined1": "User Defined 1"
  },
 */
public class JsonUdnames {

    private String userDefined1;
    private String userDefined2;
    private String userDefined3;
    private String userDefined4;
    private String userDefined5;
    private String userDefined6;

    public String getUserDefined1() {
        return userDefined1;
    }

    public void setUserDefined1(String userDefined1) {
        this.userDefined1 = userDefined1;
    }

    public String getUserDefined2() {
        return userDefined2;
    }

    public void setUserDefined2(String userDefined2) {
        this.userDefined2 = userDefined2;
    }

    public String getUserDefined3() {
        return userDefined3;
    }

    public void setUserDefined3(String userDefined3) {
        this.userDefined3 = userDefined3;
    }

    public String getUserDefined4() {
        return userDefined4;
    }

    public void setUserDefined4(String userDefined4) {
        this.userDefined4 = userDefined4;
    }

    public String getUserDefined5() {
        return userDefined5;
    }

    public void setUserDefined5(String userDefined5) {
        this.userDefined5 = userDefined5;
    }

    public String getUserDefined6() {
        return userDefined6;
    }

    public void setUserDefined6(String userDefined6) {
        this.userDefined6 = userDefined6;
    }

    @Override
    public String toString() {
        return "JsonUdnames{" +
                "userDefined1='" + userDefined1 + '\'' +
                ", userDefined2='" + userDefined2 + '\'' +
                ", userDefined3='" + userDefined3 + '\'' +
                ", userDefined4='" + userDefined4 + '\'' +
                ", userDefined5='" + userDefined5 + '\'' +
                ", userDefined6='" + userDefined6 + '\'' +
                '}';
    }
}
