package assignment1;

     // CLASS: Case
     //
     // Author: Austin McCormick, 7630047
     //
     // REMARKS: Cases are used to creat the less detailed lists for
     //          Tutor's teaching subject lists
     //-----------------------------------------

class Case {
    private String subject;
    private int value;
    
    Case( String data, int number ) {
        subject = data; 
        this.value = number;
    }
    
    public String getSubject() { return subject; }
    
    public int getCost() { return value; }
    
}
