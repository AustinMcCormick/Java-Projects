package assignment1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

     //-----------------------------------------
     // NAME		: Austin McCormick 
     // STUDENT NUMBER	: 7630047
     // COURSE		: COMP 2150
     // INSTRUCTOR	: Ali Neshati
     // ASSIGNMENT	: assignment 1
     // QUESTION	: question 1      
     // 
     // REMARKS: This program uses objects and inharitance to match Students looking for Tutors 
     //          with Tutors looking for people to Tutor
     //
     //-----------------------------------------

public class Assignment1 {

    public static void main(String[] args) {
        List tutorList = new List();       
        List studentList = new List();
        boolean done = false;
        
       // Setting up the target file locaation
        String part1 = "./res/";
        //String part2 = "officialData.txt";         
        Scanner scanner = new Scanner(System.in);  
        System.out.println("Enter the file name you would like to process (eg. Testing.txt) ");
        String part2 = scanner.nextLine(); 
        
        String location = part1 + part2;
        
       // Reading in the file
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(location));
            String input = reader.readLine();
            while (input != null) {
            //● QUIT        
            //if end of program is encountered without a "QUIT" command, report that the "QUIT" command was missing and then terminate        
                if (input.equals("QUIT")) {
                    System.out.println("Bye");
                    done = true;
                    break;  
                }
               // passes the input string to process function with required lists
                process(input, tutorList, studentList);

               // read next line
                input = reader.readLine();
            }
        } catch (IOException error) {
            error.printStackTrace();
        }
        
       // Warning message if end of file is reached without a QUIT command
        if ( !done ) { System.out.println("** Warning improper shutdown, exiting without QUIT command **");}
        
        System.out.println("End of Processing..");
    }
    
     //------------------------------------------------------
     // process
     //
     // PURPOSE:    process is fed 1 line at a time from the BufferedReader and processes them
     // PARAMETERS:
     //     input = the input string from the input file
     //     tutorList = List of possible Tutors
     //     studentList = List of possible Students
     // Returns: none
     //------------------------------------------------------    
    static void process (String input, List tutorList, List studentList) {
        Node current = null;
        boolean done = false;
        int result = 0;   
       // COMMENTS
       // Checks to see if the line begins with a # 
        if ((input.charAt(0)) == '#') {
           // Prints the comment (No further processing)
            System.out.println(input);
        }
        else {
           // prep the input string for processing by removing the extra whitespaces
            input = input.trim().replaceAll("\\s+", " ");
           // Using built in Array function only to temperarly handle String segments for further processing 
            String[] segments = input.split(" ");


            switch (segments[0]) {
            //● TUTOR [userid] [hours]
            //● Outcomes: CONFIRMED, DUPLICATE
                case "TUTOR":
                    Tutor newTutor = new Tutor(segments[1], Integer.parseInt(segments[2]));
                    result = tutorList.insert(newTutor);
                    
                    if (result == -1) { System.out.println("DUPLICATE TUTOR entery, " + newTutor.getName() + " already exists"); }
                    else if (result == 1) { System.out.println("CONFIRMED new TUTOR entery " + newTutor.getName()); }
                    
                    break;
                    
            //● STUDENT [userid]
            //● Outcomes: CONFIRMED, DUPLICATE
                case "STUDENT":
                    Student newStudent = new Student(segments[1]);
                    result = studentList.insert(newStudent);
                    
                    if (result == -1) { System.out.println("DUPLICATE STUDENT entery, " + newStudent.getName() + " already exists"); }
                    else if (result == 1) { System.out.println("CONFIRMED new STUDENT entery " + newStudent.getName()); }                    
                    
                    break;
                    
            //● TOPIC [topic name] [tutor id] [price]        
            //● Outcomes: CONFIRMED, DUPLICATE, NOT FOUND 
                case "TOPIC":
                    current = tutorList.getHead();
                    
                    while (current != null && !done) {
                       // Checking to find the Tutor that matches 
                        if (((Tutor)current.getData()).getName().equals(segments[2])) {
                            Case newTopic = new Case(segments[1], Integer.parseInt(segments[3]));
                            result = ((Tutor)current.getData()).addTopic(newTopic);
                            done = true;
                            
                            if (result == -1) { System.out.println("DUPLICATE TOPIC entery, " + newTopic.getSubject() + " already exists for the TUTOR "
                                    + ((Tutor)current.getData()).getName()); }
                            else if (result == 1) { System.out.println("CONFIRMED new TOPIC entery " + newTopic.getSubject() + " added to "
                                    + ((Tutor)current.getData()).getName() + " topic list, for $" + newTopic.getCost()); }      
                        }
                        
                        current = current.getNext();
                    }
                   // Print statment if TUTOR ID NOT FOUND
                    if (!done) { System.out.println("NOT FOUND (TOPIC) unable to find TUTOR " + segments[2]); }
                    
                    break;
                    
            //● REQUEST [student id] [topic] [num hours]  
            //● Outcomes: NOT FOUND, FAIL, SUCCESS        
                case "REQUEST":
                    current = studentList.getHead();
                    Case newRequest = new Case(segments[2], Integer.parseInt(segments[3]));
                    
                    while (current != null && !done) {
                       // Checking to find if there is a [student id] in the list that matches 
                        if (((Student)current.getData()).getName().equals(segments[1])) {
                            done = true;
                            result = ((Student)current.getData()).makeRequest(newRequest, current, tutorList);
                            
                        }
                        
                       // get next Node 
                        current = current.getNext();
                    }
                    
                   // Print statment if TUTOR ID NOT FOUND
                    if (!done) { System.out.println("NOT FOUND (Request) unable to find Student " + segments[1]); }                    

                    break;  
                    
            //● STUDENTREPORT [student id] 
            //● Outcomes: NOT FOUND, REPORT        
                case "STUDENTREPORT":
                    current = studentList.getHead();
                    
                    while (current != null && !done) {
                       // Checking to find if there is a [student id] in the list that matches 
                        if (((Student)current.getData()).getName().equals(segments[1])) {
                            done = true;
                            ((Student)current.getData()).printRequestHistory();                            
                        }
                        
                       // get next Node 
                        current = current.getNext();
                    }
                    
                   // Print statment if TUTOR ID NOT FOUND
                    if (!done) { System.out.println("NOT FOUND (StudentReport) unable to find Student " + segments[1]); }                    

                    break;  
                    
            //● TUTORREPORT [tutor id]
            //● Outcomes: NOT FOUND, REPORT        
                case "TUTORREPORT":
                current = tutorList.getHead();
                    
                    while (current != null && !done) {
                       // Checking to find if there is a [student id] in the list that matches 
                        if (((Tutor)current.getData()).getName().equals(segments[1])) {
                            done = true;
                            ((Tutor)current.getData()).printTutorHistory();                            
                        }
                        
                       // get next Node 
                        current = current.getNext();
                    }
                    
                   // Print statment if TUTOR ID NOT FOUND
                    if (!done) { System.out.println("NOT FOUND (StudentReport) unable to find Student " + segments[1]); }                    

                    break;

            }               
        }
    }
    
}
