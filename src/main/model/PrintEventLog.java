package model;

/*
 * print all logged events in the console
 */
public class PrintEventLog {

    /*
     * REQURIES: EventLog != null
     * EFFECTS: Iterates through all events in the EventLog and print each event's details with time
     */
    public static void printEventLog() {
        EventLog eventLog = EventLog.getInstance();

        if (eventLog != null) {
            System.out.println("****************Event Log****************");
            for (Event e : eventLog) {
                System.out.println(e.toString());
            }
    
            System.out.print("*****************************************");
        }
    }
}
