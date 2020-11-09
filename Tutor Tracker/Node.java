package assignment1;

     // CLASS: Node
     //
     // Author: Austin McCormick, 7630047
     //
     // REMARKS: used as a holder for items in the List class 
     //
     //-----------------------------------------

class Node {
    private Object data; 
    private Node next;    
    
    // Node Constructor 
    Node(Object val) { 
        data = val; 
        next = null;
    }
    
    public Object getData() { return data; }
    
    public void setData(String new_Data) { data = new_Data; }   
    
    public Node getNext() { return next; }  
    
    public void setNext(Node new_Node) { next = new_Node; } 
}

