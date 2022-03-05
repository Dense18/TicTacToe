package ca.cmpt276.project.tictactoe.model;

//Class to handle information regarding the TicToeGame
public class TicTacToeGame {
    int size;
    Cell grid[][];

    int numElement = 0;
    boolean isOver = false;

    public TicTacToeGame(int size){
        this.size = size;

        grid = new Cell[size][size];
        for (int i = 0; i < size; i++){
            for (int j = 0 ; j < size; j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    private boolean equalCellSymbol_notEmpty(Cell cell1, Cell cell2){
        return (cell1.getSymbol() == cell2.getSymbol()) && cell1.hasSymbol();
    }

    public Cell.Symbol checkWinner(){
        //Check Horizontal
        for (int i = 0 ; i < size; i++){
            boolean flag = false;
            for (int j = 0; j < size - 1; j++){
                if (equalCellSymbol_notEmpty(grid[i][j], grid[i][j+1])){
                    flag = true;
                }
                else{
                    flag = false;
                    break;
                }
            }
            if(flag) {
                return grid[i][0].getSymbol();
            }
        }

        //Check Vertical
        for (int i = 0 ; i < size; i++){
            boolean flag = false;
            for (int j = 0; j < size - 1; j++){
                if (equalCellSymbol_notEmpty(grid[j][i], grid[j+1][i])){
                    flag = true;
                }
                else{
                    flag = false;
                    break;
                }
            }
            if(flag){
                return grid[0][i].getSymbol();
            }
        }

        boolean flag = false;

        //Check diagonal "\"
        for (int i = 0 ; i < size - 1; i++){
            if (equalCellSymbol_notEmpty(grid[i][i], grid[i+1][i+1])){
                flag = true;
            }
            else{
                flag = false;
                break;
            }
        }
        if(flag){
            return grid[0][0].getSymbol();
        }

        //Check diagonal "/"
        for (int i = 0 ; i < size - 1; i++){
            if (equalCellSymbol_notEmpty(grid[size - 1 - i][i], grid[size - 1 - (i + 1)][i + 1])){
                flag = true;
            }
            else{
                flag = false;
                break;
            }
        }

        if(flag){
            return grid[0][size - 1].getSymbol();
        }

        return null;
    }

    public boolean hasSymbol(int x, int y){
        return grid[x][y].hasSymbol();
    }

    public void setSymbol(int x, int y, Cell.Symbol s){
        if (hasSymbol(x, y)){
            grid[x][y].setSymbol(s);

            if(s == Cell.Symbol.Empty){
                numElement--;
            }

            return;
        }

        if (!hasSymbol(x, y)){
            grid[x][y].setSymbol(s);

            if(s != Cell.Symbol.Empty){
                numElement++;
            }

        }

    }

    public void setGameOver(){
        isOver = true;
    }

    public boolean isFull(){
        return numElement >= size * size;
    }

    public boolean isOver(){
        return isOver;
    }

}
