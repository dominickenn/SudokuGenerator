package sudoku;

import java.util.ArrayList;
import java.util.Random;

public class Cell{

    public int value;
    public ArrayList<Integer> domain;

    public Cell(){
        value = 0;
        initializeDomain();
    }

    private void initializeDomain() {
        domain = new ArrayList();
        for(int i = 1; i <= GameGen.GAMESIZE; i++) {
            domain.add(i);
        }
    }

    public Cell getDefensiveCopy() {
        Cell newCell = new Cell();
        newCell.value = this.value;
        newCell.domain = new ArrayList(domain);
        return newCell;
    }

    public void tryFill() {
        value = domain.get(new Random().nextInt(domain.size()));
        domain.remove(new Integer(value));
    }
}