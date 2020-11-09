package assignment1;

     // CLASS: Report
     //
     // Author: Austin McCormick, 7630047
     //
     // REMARKS: Reports are used when creating the detailed history lists 
     //          for both Students and Tutors
     //-----------------------------------------

class Report extends Case{
    private int income;
    
    public Report (String data, int earned, int numbHours) {
        super(data, numbHours);
        income = earned;
    }
          
    public int getincome() { return income; }
}
