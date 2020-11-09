package a3;

public interface Player {
    void lastMove(int lastCol);
    void gameOver(Status winner);
    void setInfo(int size, Game gl);
}