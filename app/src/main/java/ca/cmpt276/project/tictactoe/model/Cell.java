package ca.cmpt276.project.tictactoe.model;

public class Cell {

    //Try think of a way to assign int to the symbol
    public enum Symbol{
        X,
        O,
        Empty;
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

    public void setO(){
        sym = Symbol.O;
    }

    public Symbol setX(){
        return Symbol.X;
    }

    public boolean hasSymbol(){
        return sym != Symbol.Empty;
    }
}
