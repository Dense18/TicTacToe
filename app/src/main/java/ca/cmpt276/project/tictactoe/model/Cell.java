package ca.cmpt276.project.tictactoe.model;

//Class that contains each cell's information of the TicTacToe board
public class Cell {

    public enum Symbol{
        X,
        O,
        Empty
    }

    Symbol sym;

    public Cell(){
        sym = Symbol.Empty;
    }

    public void setSymbol(Symbol s){
        sym = s;
    }

    public Symbol getSymbol(){
        return sym;
    }

    public boolean hasSymbol(){
        return sym != Symbol.Empty;
    }
}
