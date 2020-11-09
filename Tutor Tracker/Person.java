package assignment1;

     // CLASS: Person
     //
     // Author: Austin McCormick, 7630047
     //
     // REMARKS: Basic class that is built upon in Students and Tutors adding 
     //          increased functionality
     //-----------------------------------------

class Person {
    private String userid;
    
    Person(String data) {
        userid = data;
    }
    
    public String getName() { return userid; }
}
