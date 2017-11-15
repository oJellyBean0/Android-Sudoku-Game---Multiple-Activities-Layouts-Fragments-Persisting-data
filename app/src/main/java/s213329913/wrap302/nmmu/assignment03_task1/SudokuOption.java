package s213329913.wrap302.nmmu.assignment03_task1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by s2133 on 2017/08/14.
 */

public class SudokuOption implements Serializable {
    public String sudokuName;
    public int highScore;
    public ArrayList<String> sudokuCells;
    public String fileName;

    public SudokuOption(File file) throws FileNotFoundException {
        Scanner in = new Scanner(file);
        fileName = file.getName();
        sudokuName = in.nextLine();
        highScore = Integer.parseInt(in.nextLine());
        sudokuCells = new ArrayList<>();

        while (in.hasNextLine()) {
            String[] rowCells = in.nextLine().split(",");
            for (int i = 0; i < 9; i++) {
                if (!rowCells[i].equals(".")) {
                    sudokuCells.add(String.valueOf(Integer.parseInt(rowCells[i])));
                }
                else{
                    sudokuCells.add(rowCells[i]);
                }
            }
        }
    }


}
