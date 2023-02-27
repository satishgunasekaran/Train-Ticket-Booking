package trainservice;

public class Ticket {
    public String id;
    public String trainId;
    public int from;
    public int to;
    public String type;
    public Ticket(String id, String trainId, int from, int to, String type){
        this.id = id;
        this.trainId = trainId;
        this.from = from;
        this.to = to;
        this.type = type;
    }

}
