package assignment1;

     // CLASS: List
     //
     // Author: Austin McCormick, 7630047
     //
     // REMARKS: What is the purpose of this class? 
     //
     //-----------------------------------------

class List {
    private Node head;
    private int list_size;
    
    List () {
        head = new Node(null);
        list_size = 0;
    }

    public int listSize() { return list_size; }
    
    public Node getHead() { return head; }    
    
     //------------------------------------------------------
     // insert
     //
     // PURPOSE:    inserts objects into the List for future processing
     // PARAMETERS:
     //     data = the object to be stored in the List (Can be: Person, Student, Tutor, Case or, Report )
     // Returns: returns 1 if the insert was successful 
     //          returns -1 if the insert was unsuccessful
     //------------------------------------------------------    
    public int insert (Object data) {
        Node ins_Node = new Node(data);
        Node current = head;   
        boolean duplacate = false;
       // checks if the list is empty
        if (list_size == 0) {
            head = ins_Node;
            list_size++;
            return 1;
        }
       // if the list isn't empty  
        else {
            for (int i = 1; i < list_size; i++) {
                // comparison for duplicates break if found. 
                if (current.getData() instanceof Student || current.getData() instanceof Tutor) {
                    if (((Person)current.getData()).getName().equals(((Person)ins_Node.getData()).getName())) {
                        duplacate = true;
                        break;
                    }
                }
                else if (current.getData() instanceof Case) {
                    if (((Case)current.getData()).getSubject().equals(((Case)ins_Node.getData()).getSubject())) {
                        duplacate = true;
                        break;
                    }
                }
                
                current = current.getNext();
            }
            
            if ( !duplacate ) {
                current.setNext(ins_Node);
                list_size++;
                return 1;
            }
            else { return -1; }
        }
    }
}

