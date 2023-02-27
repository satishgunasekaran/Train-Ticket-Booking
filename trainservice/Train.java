package trainservice;
import java.util.*;

public class Train {
    public String id;
    String from;
    String to;
    int noOfseats;
    int waitingSeats;

    public int[] stations = new int[5];
    public int[] waitingList = new int[5];

    public HashMap<Integer,String> stationNames = new HashMap<>();

    public Train(String id, String from, String to, int seats){
        this.id = id;
        this.from = from;
        this.to = to;
        this.noOfseats = seats;
        this.waitingSeats = 5;

        Arrays.fill(stations,9);

        stationNames.put(0, "cbe");
        stationNames.put(1, "tirupur");
        stationNames.put(2, "erode");
        stationNames.put(3, "salem");
        stationNames.put(4, "chennai");

    }

    public int totalTicketsAvailable(int from, int to){
        int min = Integer.MAX_VALUE;
        for (int i = from; i < to; i++){
            if (stations[i] == noOfseats){
                return 0;
            }
        }

        for (int i = from; i < to; i++){
            int available = noOfseats - stations[i];
            min = Math.min(min, available);
        }
        return min;
    }

    public String bookTicket(int from, int to, HashMap<String, Ticket> tickets){
        for (int i = from; i < to; i++){
            if (stations[i] == noOfseats){
                System.out.println("No seats available in " + stationNames.get(i));
                return "null";
            }
        }

        for (int i = from; i < to; i++){
            stations[i]++;
        }

        //    creating a 4 digit length  random number like TKT1234
        String ticketId = "TKT"+UUID.randomUUID().toString().substring(0, 4);

        Ticket ticket = new Ticket(ticketId, id, from, to,"confirmed" );
        tickets.put(ticketId, ticket);

        return ticketId;

    }


    public int totalWaitingTicketsAvailable(int from, int to) {
        
        int min = Integer.MAX_VALUE;
        for (int i = from; i < to; i++){
            if (waitingList[i] == waitingSeats){
                return 0;
            }
        }

        for (int i = from; i < to; i++){
            int available = waitingSeats - waitingList[i];
            min = Math.min(min, available);
        }
        return min;

    }

    public String bookWaitingTicket(int from, int to, HashMap<String, Ticket> wtickets) {
        for (int i = from; i < to; i++){
            if (waitingList[i] == waitingSeats){
                System.out.println("No seats available in " + stationNames.get(i));
                return "null";
            }
        }

        for (int i = from; i < to; i++){
            waitingList[i]++;
        }

        String wticketId = "WTKT"+UUID.randomUUID().toString().substring(0, 4);

        Ticket wticket = new Ticket(wticketId, id, from, to, "waiting");
        wtickets.put(wticketId, wticket);
        return wticketId;
    }
}
