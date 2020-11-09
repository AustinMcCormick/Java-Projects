     // CLASS: Game
     //
     // Author: Austin McCormick, 7630047
     //
     // REMARKS: This class implements the gameLogic interface. This class is responsible for setting up the 
     //          gameBoard and players at the begining of the game, as well as managing players turns and the pieces in the gameBoard.  
     //          This class most importantly checks the gameBoard to see if a player has 'connected 4' or if there are no winners
     //
     //-----------------------------------------

package a3;

public class Game implements GameLogic {
    private Status[][] gameBoard;
    private int size;
    private int turn;
    private int limit = 1;
    private Contender playerO;
    private AIPlayer playerX;

     //------------------------------------------------------
     // beginGame
     //
     // PURPOSE:    This method creates new gameBoard, and players and sets up the 1st turn 
     // PARAMETERS:
     //     None
     //------------------------------------------------------    
    public void beginGame() {
       // randomly generates a number between 6-12 and creates a gameBoard of that size 
        size = (int) ((Math.random() * (13 - 6)) + 6);
        gameBoard = new Status[size][size];
        
       // Sets the status of all place holders in the gameBoard to NEITHER
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++) {
                gameBoard[j][i] = Status.NEITHER;            
            }
        }
        
        System.out.println("Game board size: " + size);
        
       // Creating the human player
        playerO = new Contender();
        playerO.setInfo(size, this);
        
       // Creating the AI player
        playerX = new AIPlayer();
        playerX.setInfo(size, this);
        
       // Choose which player goes first 
        int flip = (int) ((Math.random() * 2) + 1);
        System.out.print("Coin flip to determin who goes first..\n...\n");
        if (flip > 1) {
            System.out.println("Heads: Human Player 'O' goes first");
            turn = 1;
            playerO.lastMove(-1);
        }
        else {
            System.out.println("Tails: Ai Player 'X' goes first");
            turn = 2;
            playerX.lastMove(-1);
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
    
    /**  (cut from TextUI)
     * drop - a private helper method that finds the position of a marker
     * when it is dropped in a column. 
     * @param col the column where the piece is dropped
     * @return the row where the piece lands
     */    
    private int drop( int col) {
        int posn = 0;
        while (posn < gameBoard.length && gameBoard[posn][col] == Status.NEITHER) {
            posn ++;
        }
        return posn-1;
    }
    
     //------------------------------------------------------
     // winCheck
     //
     // PURPOSE:    Uses 4 helper methods to check for 'connected 4'
     // PARAMETERS:
     //          player: is used to check ether the X or O players Status tokens
     // Returns: the return value is a boolean that states if there is a winner stopping play
     //------------------------------------------------------      
    
    public boolean winCheck(int player) {
        boolean gameOver = false;
        int winner = -1;
       // checks for Horizontal, Virtical and, Diagioinal wins using specialized helper methods 
        int hWinner = checkHorizontalWin(player);
        int vWinner = checkVirticalWin(player); 
        int rdWinner = checkRightDiagonalWin(player);
        int ldWinner = checkLeftDiagonalWin(player);
        
       // Used when testing a limited range of win conditions
       // int hWinner = -1; 
       // int vWinner = -1;
       // int ldWinner = -1;
       // int rdWinner = -1;
        
       // Checks to see if one of the checks was successful and sets that player as winner 
        if (hWinner != -1) {
            System.out.println("\nWe have a Horizontal winner");
            gameOver = true;
            winner = hWinner;
        }
        else if (vWinner != -1) {
            System.out.println("\nWe have a Virtical winner");
            gameOver = true;
            winner = vWinner;
        }
        else if (rdWinner != -1) {
            System.out.println("\nWe have a Diagonal winner");
            gameOver = true;
            winner = rdWinner;
        }
        else if (ldWinner != -1) {
            System.out.println("\nWe have a Diagonal winner");
            gameOver = true;
            winner = ldWinner;
        }
        else if (limit == size*size) {
            winner = 3;
            gameOver = true;
        }
        
       // processes result from the checks and declairs the winner if a player has connected 4 
        switch (winner) {
            case 1:
                playerO.gameOver(Status.ONE);
                playerX.gameOver(Status.ONE);
                break;
            case 2: 
                playerX.gameOver(Status.TWO);
                playerO.gameOver(Status.TWO);
                break;
            case 3: 
                playerO.gameOver(Status.NEITHER);
                break;
        }
        
       // return true if the game is over
        return gameOver;
    }
    
     //------------------------------------------------------
     // checkHorizontalWin
     //
     // PURPOSE:    1st of 4 helper methods used by winCheck(int) to check for 'connected 4'
     // PARAMETERS:
     //          player: is used to check ether the X or O players Status tokens
     // Returns: the return value is an int stating the winning players number or -1 if there are no winners
     //------------------------------------------------------           
    private int checkHorizontalWin(int player) {
        int result = -1;
        
       // set check to the current players Status [ONE or TWO]
        Status check = null;
        if (player == 1) { check = Status.ONE; }
        else if (player == 2) { check = Status.TWO; }
        
       // For Loop: checks for horizontal wins and if a win is found sets result to the player who won
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++) {
               // checks to see if the current place holder has the same Status as player
                if(gameBoard[i][j] == check && (j+4) <= size) {
                    boolean testing = true;
                    int count = j+1;
                   // System.out.print("Checking for a Horizontal Winner: ");
                    while ( count < j+4 ) {
                       // System.out.print( count + " ");
                       // checks to see if the next horizontal place holder is also the same Status
                        if ( gameBoard[i][count] == check ) {
                           // System.out.print("Passed, ");
                            count++;
                        }
                        else {
                           // System.out.print("Failed, ");
                            testing = false;
                            break;
                        }
                    }
                    if ( testing ) {
                        return player;
                    }
                   // System.out.println("");
                }
            }
        }
        return result;
    }
    
     //------------------------------------------------------
     // checkVirticalWin
     //
     // PURPOSE:    2nd of 4 helper methods used by winCheck(int) to check for 'connected 4'
     // PARAMETERS:
     //          player: is used to check ether the X or O players Status tokens
     // Returns: the return value is an int stating the winning players number or -1 if there are no winners
     //------------------------------------------------------          
    private int checkVirticalWin(int player) {
        int result = -1;
        
       // set check to the current players Status [ONE or TWO] 
        Status check = null;
        if (player == 1) { check = Status.ONE; }
        else if (player == 2) { check = Status.TWO; }
        
       // For Loop: checks for virtical wins and if a win is found sets result to the player who won 
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++) {
               // checks to see if the current place holder has the same Status as player
                if(gameBoard[i][j] == check && (i+4) <= size) {
                    boolean testing = true;
                    int count = i+1;
                   // System.out.print("Checking for a Virtical Winner: ");
                    while ( count < i+4 ) {
                       // System.out.print( count + " ");
                       // checks to see if the next virtical place holder is also the same Status
                        if ( gameBoard[count][j] == check ) {
                           // System.out.print("Passed, ");
                            count++;
                        }
                        else {
                           // System.out.print("Failed, ");
                            testing = false;
                            break;
                        }
                    }
                    if ( testing ) {
                        return player;
                    }
                   // System.out.println("");
                }
            }
        }
      
        return result;
    }

     //------------------------------------------------------
     // checkRightDiagonalWin
     //
     // PURPOSE:    3rd of 4 helper methods used by winCheck(int) to check for 'connected 4'
     // PARAMETERS:
     //          player: is used to check ether the X or O players Status tokens
     // Returns: the return value is an int stating the winning players number or -1 if there are no winners
     //------------------------------------------------------        
    private int checkRightDiagonalWin(int player) {
        int result = -1;
        
       // set check to the current players Status [ONE or TWO] 
        Status check = null;
        if (player == 1) { check = Status.ONE; }
        else if (player == 2) { check = Status.TWO; }
        
       // For Loop: checks for diagonal wins and if a win is found sets result to the player who won 
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++) {
               // checks to see if the current place holder has the same Status as player
                if(gameBoard[i][j] == check && (i+4) <= size && (j+4) <= size) {
                    boolean testing = true;
                    int upCount = i+1;
                    int sideCount = j+1;
                   // System.out.print("Checking for a Right Diagonal Winner: ");
                    while ( upCount < i+4 ) {
                       // System.out.print( upCount + " ");
                       // checks to see if the next virtical place holder is also the same Status
                        if ( gameBoard[upCount][sideCount] == check ) {
                           // System.out.print("Passed, ");
                            upCount++;
                            sideCount++;
                        }
                        else {
                           // System.out.print("Failed, ");
                            testing = false;
                            break;
                        }
                    }
                    if ( testing ) {
                        return player;
                    }
                   // System.out.println("");
                }
            }
        }
        return result;
    }
    
     //------------------------------------------------------
     // checkLeftDiagonalWin
     //
     // PURPOSE:    3rd of 4 helper methods used by winCheck(int) to check for 'connected 4'
     // PARAMETERS:
     //          player: is used to check ether the X or O players Status tokens
     // Returns: the return value is an int stating the winning players number or -1 if there are no winners
     //------------------------------------------------------   
    private int checkLeftDiagonalWin(int player) {
        int result = -1;
        
       // set check to the current players Status [ONE or TWO] 
        Status check = null;
        if (player == 1) { check = Status.ONE; }
        else if (player == 2) { check = Status.TWO; }
        
       // For Loop: checks for diagonal wins and if a win is found sets result to the player who won 
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++) {
               // checks to see if the current place holder has the same Status as player
                if(gameBoard[i][j] == check && (i+4) <= size && (j-4) >= 0) {
                    boolean testing = true;
                    int upCount = i+1;
                    int sideCount = j-1;
                   // System.out.print("Checking for a Left Diagonal Winner: ");
                    while ( upCount < i+4 ) {
                       // System.out.print( upCount + " ");
                       // checks to see if the next virtical place holder is also the same Status
                        if ( gameBoard[upCount][sideCount] == check ) {
                           // System.out.print("Passed, ");
                            upCount++;
                            sideCount--;
                        }
                        else {
                           // System.out.print("Failed, ");
                            testing = false;
                            break;
                        }
                    }
                    if ( testing ) {
                        return player;
                    }
                   // System.out.println("");
                }
            }
        }
        return result;
    }
    
     //------------------------------------------------------
     // playerXTurn 
     //
     // PURPOSE:    This method and playerOTurn(int) are used to pass the turn back and forth while checking for a 
     //             game winner. 
     // PARAMETERS:
     //          last: is used to pass on information about the column the opposing players last chose
     // Returns: None
     //------------------------------------------------------   
    public void playerXTurn(int last) {
        boolean gameOver1 = winCheck(1);
        boolean gameOver2 = winCheck(2);
        limit++;
        
       // If there is no winner, playerX's turn
        if ( !gameOver1 && !gameOver2 ) {
            System.out.println("PlayerX's turn");
            playerX.lastMove(last);
        }
    }
 
     //------------------------------------------------------
     // playerOTurn 
     //
     // PURPOSE:    This method and playerXTurn(int) are used to pass the turn back and forth while checking for a 
     //             game winner. 
     // PARAMETERS:
     //          last: is used to pass on information about the column the opposing players last chose
     // Returns: None
     //------------------------------------------------------       
    public void playerOTurn(int last) {
        boolean gameOver1 = winCheck(1);
        boolean gameOver2 = winCheck(2);
        limit++;
        
        // If there is no winner, playerO's turn
        if ( !gameOver1 && !gameOver2 ) {
            System.out.println("PlayerO's turn ");
            playerO.lastMove(last);
        }
    }
}

