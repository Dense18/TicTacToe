package ca.cmpt276.project.tictactoe.model;

public class TicTacToeGame {
    int x_size;
    int y_size;
    int grid[][];

    public enum Symbol{
        X,
        O
    }
    public TicTacToeGame(int x_size, int y_size){
        this.x_size = x_size;
        this.y_size = y_size;
    }

}
