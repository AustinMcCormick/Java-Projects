package assignment1;

     // CLASS: Student
     //
     // Author: Austin McCormick, 7630047
     //
     // REMARKS: This class extends the Person class and expands upon it giving
     //          increased functionality needed for the Student class (eg. requestHistory list)
     //-----------------------------------------

class Student extends Person {
    private List requestHistory;

    public Student(String data) {
        super(data);
        requestHistory = new List();
    }

     //------------------------------------------------------
     // makeRequest
     //
     // PURPOSE:    Looks through the list of Tutors to see if there are matches for 
     //             the subject and the total hours of the reqeust can be filled 
     //             only after confirming the reqeust can be filled will it create the bookings
     // PARAMETERS:
     //     request = copy of the reqeust
     //     student = student who is looking for tutoring
     //     tutors = list of possible Tutors to fill the request
     // Returns: returns 1 if the insert was successful 
     //          returns -1 if the insert was unsuccessful
     //------------------------------------------------------    
    public int makeRequest(Case request, Node student, List tutors) {
        List requestTutors = new List();
        Node current = tutors.getHead();
        Node currentSubject = null;
        Node duplicateCheck = null;
        Tutor bestTutor = null;
        Case bestSubject = null;
        int tutorsNeeded = 0;
        int bookedHours = 0;
        int income = 0;
        int remainingHours = request.getCost();
        boolean done = false;
        boolean duplicate = false;
        
        
       // 3 Loops create a list of all the TUTORS needed to fill the request;
       // First loop checks to see if the REQUEST is filled, Second loops over list of all Tutors, Third loops over each Tutors subject list
        while (!done) {
            while (current != null && !done) {
                if ( ((Tutor)current.getData()).getSubjects() > 0 ) {
                    currentSubject = ((Tutor)current.getData()).getTopics();

                    while (currentSubject != null && !done) {
                        // found a possable Tutor for the Subject with hours still available
                        if (((Case)currentSubject.getData()).getSubject().equals(request.getSubject()) && ((Tutor)current.getData()).getHours() > 0 ) {
                           // Check to see if TUTOR is already on the list of requestTutors
                            duplicateCheck = requestTutors.getHead();
                            while ( duplicateCheck != null && tutorsNeeded > 0) {
                                if ( ((Tutor)current.getData()).getName().equals(((Tutor)duplicateCheck.getData()).getName())) {
                                    duplicate = true;
                                }  
                                duplicateCheck = duplicateCheck.getNext();
                            }
                           // if the TUTOR is not already on the list
                            if ( !duplicate ) {
                               // If there is no current bestTutor this iteration
                                if (bestTutor == null) {
                                    bestTutor = (Tutor)current.getData();
                                    bestSubject = (Case)currentSubject.getData();
                                    break;
                                }
                               // compare to current bestChoice for lower cost
                                else if (bestSubject.getCost() > ((Case)currentSubject.getData()).getCost()) {
                                    bestTutor = (Tutor)current.getData();
                                    bestSubject = (Case)currentSubject.getData();
                                    break;
                                }
                            }
                        }
                       // Move to next Subject on the list
                        currentSubject = currentSubject.getNext();
                    }
                }
               // Move to next TUTOR on the list
                current = current.getNext();
                
                duplicate = false;
            }
            
           // condition for exiting the First loop REQUEST cannot be filled no Subject matches or not enough hours to fill request
            if (bestTutor == null) { System.out.println("REQUEST Failed"); return -1; }
            
           // Add current bestTutor to the list of requestTutors
            requestTutors.insert(bestTutor);     
            tutorsNeeded++;
           // Check to see if the request has been filled (remainingHours = 0)
            System.out.println( "REQUEST processing booking " + bestTutor.getName() + " for " + bestTutor.getHours() + " hours. Remaining hours = " + remainingHours + " - " + bestTutor.getHours() + " = "
                    + (remainingHours - bestTutor.getHours()));
            
            remainingHours = remainingHours - bestTutor.getHours();
            
           // condition for exiting the First loop REQUEST filled
            if (remainingHours <= 0) { done = true; }

           // Prep variables for next loop
            bestTutor = null;
            bestSubject = null;
            current = tutors.getHead();
        }

       // After the list of requestTutors is created the request is processed 
       // Booking the appointments and creating the Student and Tutor request history enteries
        current = requestTutors.getHead();
        remainingHours = request.getCost();
        System.out.println("REQUEST SUCCESS: ");
        while (current != null) {
            income = (((Tutor)current.getData()).getHours() * remainingHours);
            
           // How many hours can be booked with the Tutor 
            if (((Tutor)current.getData()).getHours() > remainingHours) { bookedHours = remainingHours; }
            else { bookedHours = ((Tutor)current.getData()).getHours(); }
            
           // Add appointment to the TUTOR's teachingHistory List 
            ((Tutor)current.getData()).bookHours(bookedHours, request, income);
            
           // Add appointment to the STUDENT's requestHistory List
            Report booked = new Report(((Tutor)current.getData()).getName(), bookedHours, income);  
            requestHistory.insert(booked);
            
            System.out.println("    Booked " + bookedHours + " hours of studying " + request.getSubject()
               + " with TUTOR " + ((Tutor)current.getData()).getName() + " for $" + income );         
            
            remainingHours = remainingHours - bookedHours;
            
           // move to next requiredTutor
            current = current.getNext();
        }
    
        return 1;
    }

     //------------------------------------------------------
     // printRequestHistory
     //
     // PURPOSE:    Prints the requestHistory for the Student
     // PARAMETERS:
     //------------------------------------------------------    
    public void printRequestHistory() {
        Node current = requestHistory.getHead();
        String tutor = ""; 
        int cost = 0;
        int totalCost = 0;
        int hours = 0;
        int totalHours = 0;
        
        System.out.println("Request History for STUDENT " + this.getName());
        while (current != null && requestHistory.listSize() > 0) {
            tutor = ((Report)current.getData()).getSubject();
            cost = ((Report)current.getData()).getCost();
            hours = ((Report)current.getData()).getincome(); 
            totalCost += cost;
            totalHours += hours;
            
            System.out.println("  Appointment booked with " + tutor + " for " + hours + " the appointment costs $" + cost);
            
            current = current.getNext();
        }
        System.out.println("  The total hours of tutoring is " + totalHours + " costing $" + totalCost);
    }
}
