import java.util.*;

import trainservice.*;
public class Main {

    public static void main(String[] args) {
        HashMap<String, Ticket> tickets = new HashMap<>();

        Train train = new Train("TRN101", "cbe", "chennai", 10);

        Scanner sc = new Scanner(System.in);

        while (true){
            System.out.println("Welcome to DIL Express");
            System.out.println("1. Book Ticket");
            System.out.println("2. Cancel Ticket");
            System.out.println("3. Print Ticket");
            System.out.println("4. Exit");

            int choice = sc.nextInt();

            if (choice == 1){
                System.out.println("Enter from place : ");
                
                for (int i = 0; i < train.stations.length; i++){
                    System.out.println((i+1) + " " + train.stationNames.get(i));
                }
                int from = sc.nextInt()-1;
                System.out.println("Enter the destination : ");
                
                for (int i = from+1; i < train.stations.length; i++){
                    System.out.println((i+1)+ " " + train.stationNames.get(i));
                }
                int to = sc.nextInt()-1;

                int totalTicketsAvailable = train.totalTicketsAvailable(from, to);

                System.out.println("You are booking " + from + " tickets "+to);

                if (totalTicketsAvailable == 0){
                    System.out.println("No tickets available");
                }else {
                    System.out.println("Total tickets available : " + totalTicketsAvailable);
                    System.out.println("Press 1 to confirm Booking 0 to cancel : ");
                    int confirm = sc.nextInt();

                    if (confirm == 1){
                        train.bookTicket(from, to, tickets);
                        String ticketId = UUID.randomUUID().toString();
                        Ticket ticket = new Ticket(ticketId, train.id, from-1, to-1);
                        tickets.put(ticketId, ticket);
                    }
                }
            }
            else if (choice == 2){
                System.out.println("Enter the ticket id : ");
                String ticketId = sc.next();
                Ticket ticket = tickets.get(ticketId);
                if (ticket == null){
                    System.out.println("Invalid ticket id");
                }else {
                    System.out.println("Press 1 to confirm cancel 0 to cancel : ");
                    int confirm = sc.nextInt();
                    if (confirm == 1){
                        tickets.remove(ticketId);
                        for (int i = ticket.from; i < ticket.to; i++){
                            train.stations[i]--;
                        }
                    }
                    System.out.println(Arrays.toString(train.stations));
                    System.out.println("Ticket cancelled successfully");
                }
            }
            else if (choice == 4){
                break;
            }
            else if (choice == 3){
                System.out.println("Enter the ticket id : ");
                String ticketId = sc.next();
                Ticket ticket = tickets.get(ticketId);
                if (ticket == null){
                    System.out.println("Invalid ticket id");
                }else { 
                    System.out.println("********** Ticket details **********");
                    System.out.println("Ticket id : " + ticket.id);
                    System.out.println("Train id : " + ticket.trainId);
                    System.out.println("From : " + train.stationNames.get(ticket.from));
                    System.out.println("To : " + train.stationNames.get(ticket.to+1));
                    System.out.println("************************************");

                }
            }











        
        }

    }
}

