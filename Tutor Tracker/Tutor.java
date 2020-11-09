package assignment1;

     // CLASS: Tutor
     //
     // Author: Austin McCormick, 7630047
     //
     // REMARKS: This class extends the Person class and expands upon it giving
     //          increased functionality needed for the Tutor clase (eg. topics list)
     //-----------------------------------------

class Tutor extends Person {
    private int hours;
    private int subjects;
   // What the Tutor can teach
    private List topics;
   // What appointments has the Tutor already booked
    private List teachingHistory;
    
    public Tutor(String data, int num) {
        super(data);
        hours = num;
        topics = new List();
        teachingHistory = new List();
    }
    
    public int getHours() { return hours; }

    public int getSubjects() { return subjects; }
    
    public Node getTopics() { return topics.getHead(); }

    public int addTopic(Case newTopic) {
        int result = topics.insert(newTopic);
        if (result == 1) { subjects++; }
        return result;
    }
    
     //------------------------------------------------------
     // bookHours
     //
     // PURPOSE:    adds an appointment to the list, deducts the hours from available hours
     // PARAMETERS:
     //     Numb = number of hours to book
     //     requset = copy of the reqeust
     //     earnings = total amount earned by the Tutor
     // Returns: the number of hours remaining for the Tutor reqeust
     //------------------------------------------------------    
    public int bookHours(int numb, Case request, int earnings) {
        Report booked = new Report(request.getSubject(), request.getCost(), earnings);
        teachingHistory.insert(booked);
        hours -= numb;
        return hours;
    }  
    
     //------------------------------------------------------
     // printTutorHistory
     //
     // PURPOSE:    Prints the teachingHistory for the Tutor
     // PARAMETERS:
     //------------------------------------------------------
    public void printTutorHistory() {
        Node current = teachingHistory.getHead();
        String subject; 
        int cost;
        int totalCost = 0;
        int tutoringHours;
        int totalHours = 0;
        
        System.out.println("Request History for TUTOR " + this.getName());
        while (current != null && teachingHistory.listSize() > 0) {
            subject = ((Report)current.getData()).getSubject();
            cost = ((Report)current.getData()).getCost();
            tutoringHours = ((Report)current.getData()).getincome(); 
            totalCost += cost;
            totalHours += tutoringHours;
            
            System.out.println("  Appointment booked teaching " + subject + " for " + tutoringHours + " hours the appointment will earn $" + cost);
            
            current = current.getNext();
        }
        System.out.println("  The total hours of tutoring is " + totalHours + " total pay $" + totalCost);
    }    
}
