package trainservice;
import java.util.*;

public class Train {
    public String id;
    String from;
    String to;
    int noOfseats;

    public int[] stations = new int[5];
    public HashMap<Integer,String> stationNames = new HashMap<>();

    public Train(String id, String from, String to, int seats){
        this.id = id;
        this.from = from;
        this.to = to;
        this.noOfseats = seats;
        Arrays.fill(stations, 0);

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

    public void bookTicket(int from, int to, HashMap<String, Ticket> tickets){
        for (int i = from; i < to; i++){
            if (stations[i] == noOfseats){
                System.out.println("No seats available in " + stationNames.get(i));
                return;
            }
        }

        for (int i = from; i < to; i++){
            stations[i]++;
        }

        //    create a 4 digit length  random number like TKT1234
        String ticketId = "TKT"+UUID.randomUUID().toString().substring(0, 4);

        Ticket ticket = new Ticket(ticketId, id, from, to);
        tickets.put(ticketId, ticket);

        System.out.println("Ticket booked successfully from " + stationNames.get(from) + " to " + stationNames.get(to));

        System.out.println("Ticket ID : " + ticketId);

        System.out.println(Arrays.toString(stations));

    }
}
