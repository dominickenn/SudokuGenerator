package sudoku;

public class MainApp{
    public static void main(String[] args){
        GameGen game = new GameGen();
        game.generate();
        game.display();
    }
}
