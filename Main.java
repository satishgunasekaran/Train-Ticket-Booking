import java.util.*;

import trainservice.*;

public class Main {
    static Train train = new Train("TRN101", "cbe", "chennai", 10);
    static Scanner sc = new Scanner(System.in);
    static HashMap<String, Ticket> tickets = new HashMap<>();
    static LinkedHashMap<String, Ticket> wtickets = new LinkedHashMap<>();

    public static void main(String[] args) {

        while (true) {
            System.out.println("Welcome to DIL Express");
            System.out.println("1. Book Ticket");
            System.out.println("2. Cancel Ticket");
            System.out.println("3. Print Ticket");
            System.out.println("4. Exit");

            int choice = sc.nextInt();

            if (choice == 1) {

                bookTicket();

            } else if (choice == 2) {
                cancelTicket();
            }

            else if (choice == 3) {
                printTicket();
            }

            else if (choice == 4) {
                break;

            } else if (choice == 111) {
                stationInfo();
            }

        }

    }

    private static void bookTicket() {
        System.out.println("Enter from place : ");

        for (int i = 0; i < train.stations.length; i++) {
            System.out.println((i + 1) + " " + train.stationNames.get(i));
        }
        int from = sc.nextInt() - 1;
        System.out.println("Enter the destination : ");

        for (int i = from + 1; i < train.stations.length; i++) {
            System.out.println((i + 1) + " " + train.stationNames.get(i));
        }
        int to = sc.nextInt() - 1;

        int totalTicketsAvailable = train.totalTicketsAvailable(from, to);
        int totalWaitingTicketsAvailable = train.totalWaitingTicketsAvailable(from, to);

        System.out.println("Total tickets available : " + totalTicketsAvailable);

        System.out.println("Enter the number of tickets to book : ");
        int noOfTickets = sc.nextInt();

        ArrayList<Ticket> res = new ArrayList<>();

        if (noOfTickets <= totalTicketsAvailable) {

            for (int i = 0; i < noOfTickets; i++) {
                String ticketId = train.bookTicket(from, to, tickets);
                Ticket ticket = tickets.get(ticketId);
                res.add(ticket);
                totalTicketsAvailable--;
            }

        } else if( noOfTickets <= (totalTicketsAvailable + totalWaitingTicketsAvailable )) {

            System.out.println(totalTicketsAvailable + " will be confirmed and " + (noOfTickets - totalTicketsAvailable)
                    + " will be in waiting list");

            System.out.println("Enter 1 to confirm : ");
            int confirm = sc.nextInt();
            
            if(confirm != 1) {
                System.out.println("Booking cancelled");
                return;
            }

            for (int i = 0; i < totalTicketsAvailable; i++) {
                String ticketId = train.bookTicket(from, to, tickets);
                Ticket ticket = tickets.get(ticketId);
                res.add(ticket);
            }

            for (int i = 0; i < (noOfTickets - totalTicketsAvailable); i++) {
                String ticketId = train.bookWaitingTicket(from, to, wtickets);
                Ticket ticket = wtickets.get(ticketId);
                res.add(ticket);
            }
        }else {
            System.out.println("No seats available");
            return;
        }

        System.out.println("********** Ticket Details **********");
        for (Ticket ticket : res) {
            System.out.println(ticket.id + " " + ticket.type);
        }
        System.out.println("************************************");

    }

    private static void cancelTicket() {
        System.out.println("Enter the ticket id : ");
        String ticketId = sc.next();
        Ticket ticket = tickets.get(ticketId);

        if (ticket == null) {
            ticket = wtickets.get(ticketId);
        }

        if (ticket == null) {
            System.out.println("Invalid ticket id");
        } else {
            System.out.println("Press 1 to confirm cancel 0 to cancel : ");
            int confirm = sc.nextInt();
            if (confirm == 1) {
                if (ticket.type == "confirmed") {
                    tickets.remove(ticketId);
                    for (int i = ticket.from; i < ticket.to; i++) {
                        train.stations[i]--;
                    }
                    System.out.println("Ticket cancelled successfully");

                    // iterate over waiting list

                    for (String key : wtickets.keySet()) {
                        Ticket wticket = wtickets.get(key);
                        if (wticket.from >= ticket.from && wticket.to <= ticket.to) {
                            wticket.type = "confirmed";
                            tickets.put(wticket.id, wticket);
                            wtickets.remove(wticket.id);

                            for (int i = wticket.from; i < wticket.to; i++) {
                                train.stations[i]++;
                                train.waitingList[i]--;
                            }

                            System.out.println("Ticket " + wticket.id + " is confirmed");

                            break;
                        }
                    }

                } else {
                    wtickets.remove(ticketId);
                    System.out.println("Ticket cancelled successfully");
                }
            }
            System.out.println(Arrays.toString(train.stations));

        }
    }

    private static void stationInfo() {
        // print station names with its count
        System.out.println("\n********** Station details **********");
        for (int i = 0; i < train.stations.length; i++) {
            System.out.println(train.stationNames.get(i) + " : " + train.stations[i]);
        }

        System.out.println("************************************\n");

        System.out.println("********** Waiting List details **********");

        for (int i = 0; i < train.stations.length; i++) {
            System.out.println(train.stationNames.get(i) + " : " + train.waitingList[i]);
        }
        System.out.println("************************************\n");

    }

    private static void printTicket() {
        System.out.println("Enter the ticket id : ");
        String ticketId = sc.next();
        Ticket ticket = tickets.get(ticketId);

        if (ticket != null) {
            System.out.println("********** Ticket details **********");
            System.out.println("Ticket id : " + ticket.id);
            System.out.println("Train id : " + ticket.trainId);
            System.out.println("From : " + train.stationNames.get(ticket.from));
            System.out.println("To : " + train.stationNames.get(ticket.to));
            System.out.println("TicketType : " + ticket.type);
            System.out.println("************************************");

        } else {
            System.out.println("Invalid ticket id");
        }

    }
}
