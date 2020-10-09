package jsonapi.event;

/*
{
  "isError": 0,
  "count": 1000,
  "lastId": 2637733,
  "events": [],
 "firstId": 2639522
}
 */


import java.util.ArrayList;

public class JsonEvents {

    private String isError;
    private String count;
    private String lastId;
    private ArrayList<JsonEvent> events;
    private String firstId;

    public JsonEvents() {
        events = new ArrayList<JsonEvent>();
    }

    public String getIsError() {
        return isError;
    }

    public void setIsError(String isError) {
        this.isError = isError;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getLastId() {
        return lastId;
    }

    public void setLastId(String lastId) {
        this.lastId = lastId;
    }

    public String getFirstId() {
        return firstId;
    }

    public void setFirstId(String firstId) {
        this.firstId = firstId;
    }

    public ArrayList<JsonEvent> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<JsonEvent> events) {
        this.events = events;
    }

}
