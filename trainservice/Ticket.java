package trainservice;

public class Ticket {
    public String id;
    public String trainId;
    public int from;
    public int to;
    public Ticket(String id, String trainId, int from, int to){
        this.id = id;
        this.trainId = trainId;
        this.from = from;
        this.to = to;
    }

}
