package jsonapi.tree;

/*
  "overlimit": [
          {
          "string": "1"
          }
          ],

 */

public class JsonOverlimit {
    private String string;

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
/*
NOTE:  This probably needs to by an ArrayList in JsonDevicesTree ??
 */
/*
public class JsonOverlimitArrayOld {
    ArrayList<String> string;

    public JsonOverlimit() {
        ArrayList<String> string = new ArrayList<>();
    }

    public ArrayList<String> getString() {
        return string;
    }

    public void setString(ArrayList<String> string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return "JsonOverlimit{" +
                "string=" + string +
                '}';
    }
}
*/