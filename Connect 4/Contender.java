     // CLASS: Game
     //
     // Author: Austin McCormick, 7630047
     //
     // REMARKS: This class is the 2nd of 2 classes to implement the Player interface, it also implements the Human interface. 
     //          This class is responsible for relaying messages between the Game's provided TextUI and the Game class. 
     //
     //-----------------------------------------

package a3;

public class Contender implements Player, Human {
    TextUI gameUI = new TextUI();
    private Game game;
    
     //------------------------------------------------------
     // lastMove
     //
     // PURPOSE:    used for passing information between the Game and TextUI classes   
     //
     // PARAMETERS:
     //     lastCol: States the column the computer player last chose
     // Returns: None
     //------------------------------------------------------   
    public void lastMove(int lastCol){ 
        gameUI.lastMove(lastCol);
    }
    
     //------------------------------------------------------
     // gameOver
     //
     // PURPOSE:    Called at the end of the game for passing information between the Game and TextUI classes   
     //
     // PARAMETERS:
     //      winner: States the Status of the winning player
     // Returns: None
     //------------------------------------------------------  
    public void gameOver(Status winner){
        switch (winner) {
            case ONE: 
                System.out.println("PlayerO Wins - You have defeated the robuts and saved a princess or something");
                break;
            case TWO:
                System.out.println("Nooo! I have failed the earth");
                break;
        }
        gameUI.gameOver(winner);
    }
    
     //------------------------------------------------------
     // setInfo
     //
     // PURPOSE:    Called at the begining of the game to pass information about the game to TextUI class
     // PARAMETERS:
     //        size: The size of the gameBoard determined by the Game classes beginGame()  
     //          gl: reference to the current game
     // Returns: None
     //------------------------------------------------------        
    public void setInfo(int size, Game gl){
        gameUI.setInfo(this, size);
        game = gl;
    }
    
     //------------------------------------------------------
     // setAnswer
     //
     // PURPOSE:    this method is used to pass information about the players move to TextUI class
     //             
     // PARAMETERS:
     //         col: Specifies the chosen column
     // Returns: None
     //------------------------------------------------------   
    public void setAnswer(int col){ 
        game.setAnswer(col);
        game.playerXTurn(col);
    }
}
