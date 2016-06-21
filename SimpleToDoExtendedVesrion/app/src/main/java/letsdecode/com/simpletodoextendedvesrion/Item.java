package letsdecode.com.simpletodoextendedvesrion;



public class Item {

    private String itemName;
    private String status;

    public long getTime() {
        return time;
    }

    private long time;
    private String priority;
    private final int id;

    public String getPriority() {
        return priority;
    }

    public Item(String itemName, long time, String priority, String status, int id){
        this.itemName = itemName;
        this.status = status;
        this.time = time;
        this.priority = priority;
        this.id = id;


    }

    @Override
    public String toString() {
        return "Item{" +
                "itemName='" + itemName + '\'' +
                ", status='" + status + '\'' +
                ", time=" + time +
                ", priority='" + priority + '\'' +
                ", id=" + id +
                '}';
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }






}
