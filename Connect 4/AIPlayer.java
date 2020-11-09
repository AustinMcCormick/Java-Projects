     // CLASS: AIPlayer
     //
     // Author: Austin McCormick, 7630047
     //
     // REMARKS: This class is 1 of 2 that implements the Player interface. It is responsible for managing the 
     //          computer player. This class has private methods used for both offensive and defensive play. 
     //
     //-----------------------------------------

package a3;

public class AIPlayer implements Player {
    private Game game;
    private int boardSize;
    private Status[][] gameBoard;
    private int turn = 1;
    
    
     //------------------------------------------------------
     // lastMove
     //
     // PURPOSE:    This method uses 3 specialized helper methods to calculate that turn's move
     //             1st thinking Defensively, 2nd Offensively, and finally Randomly 
     // PARAMETERS:
     //          lastCol: is used to pass on information about the column the opposing players last chose
     // Returns: None
     //------------------------------------------------------          
    public void lastMove(int lastCol){
        if ( lastCol == -1 ) {
            turn++;
        }
        else {
            setAnswer(lastCol);
        }
        
        int AIMove = -1;
       // Stage 1: Defence options
        AIMove = playDefensively();
        
       // Stage 2: if no Defensive moves are found move onto Offence options
        if ( AIMove == -1 ) {
            AIMove = playOffensively();
        }
            
       // Stage 3: if no Defensive or Offensive moves are found move onto Random moves 
        if ( AIMove == -1 ) {
            AIMove = playSomethingRandom();
        }
        
       // Make move and pass turn to the next player
        System.out.println("Robot Overlord plays column " + AIMove);
        setAnswer(AIMove);
        game.setAnswer(AIMove);
        game.playerOTurn(AIMove);
    }

     //------------------------------------------------------
     // gameOver
     //
     // PURPOSE:    This method is called at the end of the game 
     // PARAMETERS:
     //          winner: Status of the game's winner
     // Returns: None
     //------------------------------------------------------          
    public void gameOver(Status winner){
        switch (winner) {
            case ONE: 
                System.out.println("'Robot Overlord' Curse you human.. You haven't see the last of meee!!");
                break;
            case TWO:
                System.out.println("Robot's have taken over the world, hide yo kids, hide yo wife!");
                break;
        }
    }
    
     //------------------------------------------------------
     // setInfo
     //
     // PURPOSE:    Called at the begining of the game to setup the gameBoard  
     // PARAMETERS:
     //        size: The size of the gameBoard determined by the Game classes beginGame()  
     //          gl: reference to the current game
     // Returns: None
     //------------------------------------------------------      
    public void setInfo(int size, Game gl){
        game = gl;
        boardSize = size;
        gameBoard = new Status[size][size];
        
       // Sets the status of all place holders in the gameBoard to NEITHER
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++) {
                gameBoard[j][i] = Status.NEITHER;            
            }
        }
    }
    
     //------------------------------------------------------
     // setAnswer
     //
     // PURPOSE:    this method calls the helper method drop()[From TestUI] and sets the players Status marker into the 
     //             chosen column
     // PARAMETERS:
     //         col: Specifies the chosen column
     // Returns: None
     //------------------------------------------------------    
    public void setAnswer(int col) {
        Status set = null;
        
       // Determin what piece is to be set
       // PlayerO's turn
        if ( turn == 1 ) {
            set = Status.ONE;
            turn++;
        } 
       // PlayerX's turn
        else if ( turn == 2 ) {
            set = Status.TWO;
            turn--;
        }
        
        int posn = drop(col);
        gameBoard[posn][col] =  set;
        
    }
    
    private int drop(  int col) {
        int posn = 0;
        while (posn < gameBoard.length && gameBoard[posn][col] == Status.NEITHER) {
            posn ++;
        }
        
        return posn-1;
    }
    
     //------------------------------------------------------
     // playDefensively
     //
     // PURPOSE:    this method calls 3 specialized helper method to determin if there is a line of 3 tokens 
     //             
     // PARAMETERS:
     //         None
     // Returns: the -1st or 4th placing in the line (if it's valid) for blocking the opponent 
     //------------------------------------------------------       
    private int playDefensively(){
        int move = -1;
        System.out.println("\n - The Robot Overlord is calculating Defensive moves - ");
        
        move = checkHorizontal(1);
        
        if ( move == -1 ) {
            move = checkVertical(1);
        }
        
        if ( move == -1 ) {
            move = checkDiagonal(1);
        }
        
        return move;
    }
    
     //------------------------------------------------------
     // playOffensively
     //
     // PURPOSE:    this method calls 3 specialized helper method to determin if there is a line of 3 tokens 
     //             
     // PARAMETERS:
     //         None
     // Returns: the -1st or 4th placing in the line (if it's valid) for 'connecting 4'
     //------------------------------------------------------    
    private int playOffensively(){
        int move = -1;
        System.out.println("\n - The Robot Overlord is calculating Offensive moves - ");
        
        move = checkHorizontal(2);
        
        if ( move == -1 ) {
            move = checkVertical(2);
        }
        
        if ( move == -1 ) {
            move = checkDiagonal(2);
        }
        
        return move;
    }
   
     //------------------------------------------------------
     // playSomethingRandom
     //
     // PURPOSE:    this method picks a random column to play 
     //             
     // PARAMETERS:
     //         None
     // Returns: a valid random column for playing
     //------------------------------------------------------        
    private int playSomethingRandom(){
        System.out.println("\n - The Robot Overlord is calculating a random move -");
        int move = (int) ((Math.random() * (boardSize - 0)) + 0);
        
       // verifies that the colum selected is valid for play before returning the random move
        while (!verifyCol(move)) {
            move = (int) ((Math.random() * (boardSize - 0)) + 0);
        }
        
        return move;
    }
    
     //------------------------------------------------------
     // checkHorizontal
     //
     // PURPOSE:    1st of 3 helper methods used by playOffensively() and playDefensively() to check for 3 lines
     //             
     // PARAMETERS:
     //         type: Determins if the check is Offensive or Defensive
     // Returns: -1st or 4th place (if it's valid), for the AI's next move. If there are no 3 lines found returns -1 
     //------------------------------------------------------        
    private int checkHorizontal(int type){
        int move = -1;
        Status open = Status.NEITHER;
        
       // sets check to the type of move that is required [ Defensive or Offensive ]
        Status check = null;
       // checking for PlayerO's pieces [Used for Defensive moves]
        if (type == 1) { check = Status.ONE; }
       // checking for PlayerX's pieces [Used for Offensive moves] 
        else if (type == 2) { check = Status.TWO; }
        
       // For Loop: checks for horizontal lines of 3 and if a line of 3 is found sets result to the 4th value if the space is open
        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++) {
               // checks to see if the current place holder has the same Status as check
               // System.out.println("Checking for a Horizontal 3 line starting at i = " + i + ",  j = " + j);
                if(gameBoard[i][j] == check && (j+4) <= boardSize) {
                    int count = j+1;
                    
                    while ( count < j+4 ) {
                       // System.out.print( count + " ");
                       // checks to see if the 4th place holder is open to play on
                        if (count == j+3 && gameBoard[i][count] == open) {
                           // check to see if it is on the bottom row
                           // System.out.println("3 line found:\n  Checking 4th place holder ");
                            if ( i == boardSize-1 ) {
                               // System.out.println("    Case 1");
                                return count;
                            }
                           // check to see if there is a piece under the open 4th place holder to drop onto
                            else if ( i >= 0 && gameBoard[i+1][count] != open) {
                               // System.out.println("    Case 2");
                                return count;
                            }
                        }   
                        else if (count == j+3 && j-1 >= 0 && gameBoard[i][j-1] == open) {
                           // System.out.println("  Checking -1st place holder ");
                            if ( i == boardSize-1 && gameBoard[i][j-1] == open) {
                               // System.out.println("    Case 3");
                                return j-1;
                            }
                           // check to see if there is a piece under the open 4th place holder to drop onto
                            else if ( i >= 0 && gameBoard[i+1][j-1] != open) {
                               // System.out.println("    Case 4");
                                return j-1;
                            }
                        }
                       // checks to see if the next horizontal place holder is also the same Status for first 2 iterations
                        else if ( gameBoard[i][count] == check ) {
                           // System.out.print("Passed, ");
                            count++;
                        }
                        else {
                           // System.out.print("Failed, ");
                            break;
                        }
                    }
                   // System.out.println("");
                }  
            }
        }
        return move;
    }
    
     //------------------------------------------------------
     // checkVertical
     //
     // PURPOSE:    2nd of 3 helper methods used by playOffensively() and playDefensively() to check for 3 lines
     //             
     // PARAMETERS:
     //         type: Determins if the check is Offensive or Defensive
     // Returns: -1st or 4th place (if it's valid), for the AI's next move. If there are no 3 lines found returns -1 
     //------------------------------------------------------     
    private int checkVertical(int type){
        int move = -1;
        
       // sets check to the type of move that is required [ Defensive or Offensive ]
        Status check = null;
       // checking for PlayerO's pieces [Used for Defensive moves]
        if (type == 1) { check = Status.ONE; }
       // checking for PlayerX's pieces [Used for Offensive moves] 
        else if (type == 2) { check = Status.TWO; }
        
        // For Loops: checks for vertical 3 lines and if a 3 lines is found sets move to the 4th open place holder 
        for(int i = boardSize-1; i >= 0 ; i--) {
            for(int j = 0; j < boardSize; j++) {
                
               // checks to see if the current place holder has the same Status as check
                if(gameBoard[i][j] == check  && (i-4) >= 0) {
                    int count = i-1;
                    
                   // System.out.println("Checking for a Virtical 3 line: i = " + i + " ,  j = " + j);
                    while ( count > i-4 ) {
                       // System.out.print( count + " ");
                       // checks to see if the 4th place holder is open to play on
                        if (count == i-3 && gameBoard[count][j] == Status.NEITHER) {
                           // System.out.println("Checking 4th vertical open i = " + i + " ,  j = " + j);
                            return j;
                        }
                       // checks to see if the next virtical place holder is also the same Status
                        else if ( gameBoard[count][j] == check ) {
                           // System.out.print("Passed, ");
                            count--;
                        }
                        else {
                           // System.out.print("Failed, ");
                            break;
                        }
                    }
                   // System.out.println("");
                }
            }
        }
        return move;
    }

     //------------------------------------------------------
     // checkDiagonal
     //
     // PURPOSE:    3rd of 3 helper methods used by playOffensively() and playDefensively() to check for 3 lines
     //             This method has 2 helper methods to check both possible diagonal angles        
     //
     // PARAMETERS:
     //         type: Determins if the check is Offensive or Defensive
     // Returns: -1st or 4th place (if it's valid), for the AI's next move. If there are no 3 lines found returns -1 
     //------------------------------------------------------         
    private int checkDiagonal(int type){
        int move = -1;
        
        move = checkLeftDiagonal(type);
        
        if ( move == -1) {
            move = checkRightDiagonal(type);
        }
        
        return move;
    }

     //------------------------------------------------------
     // checkLeftDiagonal
     //
     // PURPOSE:    1st of 2 helper methods used by checkDiagonal() to check for 3 lines   
     //
     // PARAMETERS:
     //         type: Determins if the check is Offensive or Defensive
     // Returns: -1st or 4th place (if it's valid), for the AI's next move. If there are no 3 lines found returns -1 
     //------------------------------------------------------      
    private int checkLeftDiagonal(int type) {
        int move = -1;
        Status open = Status.NEITHER;
        
       // sets check to the type of move that is required [ Defensive or Offensive ]
        Status check = null;
       // checking for PlayerO's pieces [Used for Defensive moves]
        if (type == 1) { check = Status.ONE; }
       // checking for PlayerX's pieces [Used for Offensive moves] 
        else if (type == 2) { check = Status.TWO; }
        
       // System.out.println("Cnecking left diagonal");
       // For Loop: checks for diagonal 3 lines and if a 3 lines is found checks the -1th and 4th place holder to see if it is playable 
        for(int i = boardSize-1; i >= 0; i--){
            for(int j = 0; j < boardSize; j++) {
               // checks to see if the current place holder has the same Status as player
               // System.out.println("i = " + i + ",  j = " + j);
                if(gameBoard[i][j] == check  &&  (i-4) >= 0  &&  (j+4) <= boardSize) {
                    int upCount = i-1;
                    int sideCount = j-1;
                   // System.out.print("\nChecking for a Left Diagonal 3 line: ");
                    while ( upCount > i-4 ) {
                       // System.out.print( upCount + " ");
                       // check to see if the 4th place holder in the 3 line is open and there is a piece under the 4th place holder to play on
                        if ( upCount == i-3  &&  gameBoard[upCount][sideCount] == open  &&  gameBoard[upCount+1][sideCount] != open  ) {
                           // System.out.println("  Case 1");
                            return sideCount;
                        }
                       // checks to see if the -1st place holder is empty 
                        else if ( upCount == i-3  &&  j+1 < boardSize  &&  i+1 < boardSize  &&  gameBoard[upCount][sideCount] == open ) {
                           // checks to see if the -1st place holder is on the bottom row 
                            if ( i == boardSize-1 ) {
                               // System.out.println("  Case 2");
                                return j-1;
                            }
                           // checks to see if there is a piece under the -1st place holder
                            else if (gameBoard[i+1][j-1] == open) {
                               // System.out.println("  Case 3");
                                return j-1;
                            }
                            upCount--;
                        }
                       // checks to see if the next Diagonal place holder is also the same Status 
                        else if ( upCount >= 0  && sideCount >= 0 && upCount <= boardSize  && sideCount <= boardSize && gameBoard[upCount][sideCount] == check ) {
                           // System.out.print("Passed, ");
                            upCount--;
                            sideCount--;
                        }
                        else {
                           // System.out.print("Failed, ");
                            break;
                        }
                    }
                }
            }
        }
        return move;
    }
    
     //------------------------------------------------------
     // checkLeftDiagonal
     //
     // PURPOSE:    2nd of 2 helper methods used by checkDiagonal() to check for 3 lines   
     //
     // PARAMETERS:
     //         type: Determins if the check is Offensive or Defensive
     // Returns: -1st or 4th place (if it's valid), for the AI's next move. If there are no 3 lines found returns -1 
     //------------------------------------------------------   
    private int checkRightDiagonal(int type) {
        int move = -1;
        Status open = Status.NEITHER;
        
       // sets check to the type of move that is required [ Defensive or Offensive ]
        Status check = null;
       // checking for PlayerO's pieces [Used for Defensive moves]
        if (type == 1) { check = Status.ONE; }
       // checking for PlayerX's pieces [Used for Offensive moves] 
        else if (type == 2) { check = Status.TWO; }
        
       // System.out.println("Cnecking left diagonal");
        // For Loop: checks for diagonal 3 lines and if a 3 lines is found checks the -1th and 4th place holder to see if it is playable 
        for(int i = boardSize-1; i >= 0; i--){
            for(int j = 0; j < boardSize; j++) {
               // checks to see if the current place holder has the same Status as player
               // System.out.println("i = " + i + ",  j = " + j);
                if(gameBoard[i][j] == check  &&  (i-4) >= 0  &&  (j+4) <= boardSize) {
                    int upCount = i-1;
                    int sideCount = j+1;
                   // System.out.print("\nChecking for a Right Diagonal 3 line: ");
                    while ( upCount > i-4 ) {
                       // System.out.print( upCount + " ");
                       // check to see if the 4th place holder in the 3 line is open and there is a piece under the 4th place holder to play on
                        if ( upCount == i-3  &&  gameBoard[upCount][sideCount] == open  &&  gameBoard[upCount+1][sideCount] != open  ) {
                           // System.out.println("  Case 1");
                            return sideCount;
                        }
                       // checks to see if the -1st place holder is empty 
                        else if ( upCount == i-3  &&  j-1 >= 0  &&  i+1 < boardSize  &&  gameBoard[upCount][sideCount] == open ) {
                           // checks to see if the -1st place holder is on the bottom row 
                            if ( i == 0 ) {
                               // System.out.println("  Case 2");
                                return j-1;
                            }
                           // checks to see if there is a piece under the -1st place holder
                            else if (gameBoard[i+1][j-1] == open) {
                               // System.out.println("  Case 3");
                                return j-1;
                            }
                            upCount--;
                        }
                       // checks to see if the next virtical place holder is also the same Status 
                        else if ( gameBoard[upCount][sideCount] == check ) {
                           // System.out.print("Passed, ");
                            upCount--;
                            sideCount++;
                        }
                        else {
                           // System.out.print("Failed, ");
                            break;
                        }
                    }
                }
            }
        }
        
        return move;
    }
    
    /**
     * verifyCol - private helper method to determine if an integer is a valid
     * column that still has spots left.
     * @param col - integer (potential column number)
     * @return - is the column valid?
     */
    private boolean verifyCol(int col) {
        return (col >= 0 && col < gameBoard[0].length && gameBoard[0][col] == Status.NEITHER);
    }
}
