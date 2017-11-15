package s213329913.wrap302.nmmu.assignment03_task1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by s2133 on 2017/08/12.
 */

public class GameController {
    ArrayList<Region> regions = new ArrayList<>();
    private static GameController gameController = null;
    int noOfMoves;
    Button lastBtnPressed=null;
    TextView txtNoOfMoves = null;
    SudokuActivity sudokuActivity = null;

    public static GameController getObject(){
        if(gameController==null)
            gameController = new GameController();
        return gameController;
    }

    public void checkConflicts(Button curBtnPressed){
        int btnPosition = curBtnPressed.btnPosition;
        int regionPositionBelogedTo = curBtnPressed.regionBelongedTo.regionPosition;

        switch(regionPositionBelogedTo){
            case 1:
                checkRowOfRegions(1,2,3, btnPosition);
                checkColumnOfRegions(1,4,7, btnPosition);
                break;
            case 2:
                checkRowOfRegions(1,2,3, btnPosition);
                checkColumnOfRegions(2,5,8, btnPosition);
                break;
            case 3:
                checkRowOfRegions(1,2,3, btnPosition);
                checkColumnOfRegions(3,6,9, btnPosition);
                break;
            case 4:
                checkRowOfRegions(4,5,6, btnPosition);
                checkColumnOfRegions(1,4,7, btnPosition);
                break;
            case 5:
                checkRowOfRegions(4,5,6, btnPosition);
                checkColumnOfRegions(2,5,8, btnPosition);
                break;
            case 6:
                checkRowOfRegions(4,5,6, btnPosition);
                checkColumnOfRegions(3,6,9, btnPosition);
                break;
            case 7:
                checkRowOfRegions(7,8,9, btnPosition);
                checkColumnOfRegions(1,4,7, btnPosition);
                break;
            case 8:
                checkRowOfRegions(7,8,9, btnPosition);
                checkColumnOfRegions(2,5,8, btnPosition);
                break;
            case 9:
                checkRowOfRegions(7,8,9, btnPosition);
                checkColumnOfRegions(3,6,9, btnPosition);
                break;
        }

    }

    public boolean checkRowOfRegions(int regionBlock1, int regionBlock2, int regionBlock3, int btnPosition){
        boolean isThereDuplicates = false;
        Region region1 = regions.get(regionBlock1-1);
        Region region2 = regions.get(regionBlock2-1);
        Region region3 = regions.get(regionBlock3-1);

        ArrayList<Integer> neighboursHoriz = neighboursHoriz(btnPosition);
        ArrayList<Button> allNeighboursHoriz = new ArrayList<>();

        for(int i = 0; i<neighboursHoriz.size();i++){
            allNeighboursHoriz.add(region1.buttonsInRegion.get(neighboursHoriz.get(i)-1));
            allNeighboursHoriz.add(region2.buttonsInRegion.get(neighboursHoriz.get(i)-1));
            allNeighboursHoriz.add(region3.buttonsInRegion.get(neighboursHoriz.get(i)-1));
        }

        for(int i = 0; i < allNeighboursHoriz.size();i++){
            boolean isDuplicatesForCurBtn = false;
            int curButtonState = allNeighboursHoriz.get(i).state;
            for (int ii = i+1; ii<allNeighboursHoriz.size();ii++){
                if (curButtonState ==allNeighboursHoriz.get(ii).state) {
                    Button changeColorBtn1 = allNeighboursHoriz.get(i);
                    Button changeColorBtn2 = allNeighboursHoriz.get(ii);
                    changeBtnColor(changeColorBtn1);
                    changeBtnColor(changeColorBtn2);
                    //allNeighboursHoriz.get(ii).regionBelongedTo.changeBtnColor(allNeighboursHoriz.get(i), allNeighboursHoriz.get(ii));
                    isThereDuplicates = true;
                    isDuplicatesForCurBtn = true;
                    allNeighboursHoriz.remove(ii);
                }
            }
            if(isDuplicatesForCurBtn==false&&(allNeighboursHoriz.get(i).regionBelongedTo.checkIfDeplicateOfThisBtn(allNeighboursHoriz.get(i))==false)){
                allNeighboursHoriz.get(i).txtColour = Color.BLACK;
                allNeighboursHoriz.get(i).update();
            }
        }

        return isThereDuplicates;
    }

    public boolean checkColumnOfRegions(int regionBlock1, int regionBlock2, int regionBlock3,int btnPosition){
        boolean isThereDuplicates = false;
        Region region1 = regions.get(regionBlock1-1);
        Region region2 = regions.get(regionBlock2-1);
        Region region3 = regions.get(regionBlock3-1);
        ArrayList<Integer> neighboursVert = neighboursVert(btnPosition);
        ArrayList<Button> allNeighboursVert = new ArrayList<>();

        for(int i = 0; i<neighboursVert.size();i++){
            allNeighboursVert.add(region1.buttonsInRegion.get(neighboursVert.get(i)-1));
            allNeighboursVert.add(region2.buttonsInRegion.get(neighboursVert.get(i)-1));
            allNeighboursVert.add(region3.buttonsInRegion.get(neighboursVert.get(i)-1));
        }

        for(int i = 0; i < allNeighboursVert.size();i++){
            boolean isDuplicatesForCurBtn = false;
            int curButtonState = allNeighboursVert.get(i).state;
            for (int ii = i+1; ii<allNeighboursVert.size();ii++){
                if (curButtonState ==allNeighboursVert.get(ii).state) {
                    Button changeColorBtn1 = allNeighboursVert.get(i);
                    Button changeColorBtn2 = allNeighboursVert.get(ii);
                    changeBtnColor(changeColorBtn1);
                    changeBtnColor(changeColorBtn2);
                    //allNeighboursVert.get(ii).regionBelongedTo.changeBtnColor(allNeighboursVert.get(i), allNeighboursVert.get(ii));
                    isThereDuplicates = true;
                    isDuplicatesForCurBtn = true;
                    allNeighboursVert.remove(ii);
                }
            }
            if(isDuplicatesForCurBtn==false&&(allNeighboursVert.get(i).regionBelongedTo.checkIfDeplicateOfThisBtn(allNeighboursVert.get(i))==false)){
                allNeighboursVert.get(i).txtColour = Color.BLACK;
                allNeighboursVert.get(i).update();
            }
        }
        return isThereDuplicates;
    }

    public void changeBtnColor(Button curBtn){
        curBtn.txtColour = Color.RED;
        curBtn.update();
    }

    public ArrayList<Integer> neighboursHoriz(int curBlock){
        ArrayList<Integer> neightbours = new ArrayList<>();
        if (curBlock ==1||curBlock ==2 || curBlock ==3){
            neightbours.add(1);
            neightbours.add(2);
            neightbours.add(3);
        }
        else if (curBlock ==4||curBlock ==5 || curBlock ==6){
            neightbours.add(4);
            neightbours.add(5);
            neightbours.add(6);
        }
        else{
            neightbours.add(7);
            neightbours.add(8);
            neightbours.add(9);

        }
        return neightbours;
    }
    public ArrayList<Integer> neighboursVert(int curBlock){
        ArrayList<Integer> neightbours = new ArrayList<>();
        if (curBlock ==1||curBlock ==4 || curBlock ==7){
            neightbours.add(1);
            neightbours.add(4);
            neightbours.add(7);
        }
        else if (curBlock ==2||curBlock ==5 || curBlock ==8){
            neightbours.add(2);
            neightbours.add(5);
            neightbours.add(8);
        }
        else{
            neightbours.add(3);
            neightbours.add(6);
            neightbours.add(9);

        }
        return neightbours;
    }

    public int getNoOfMoves(){
        return noOfMoves;
    }
    public void updateNoOfMoves(Button curBtnPressed){
        if(lastBtnPressed!=curBtnPressed){
            lastBtnPressed = curBtnPressed;
            noOfMoves++;
            txtNoOfMoves.setText(String.valueOf(noOfMoves));
        }
    }
    public void updateTextOfNoOfMoves(){
            txtNoOfMoves.setText(String.valueOf(noOfMoves));

    }

    public void checkIfGridComplete(){
        boolean isGridComplete = true;
        for(int i = 0;i<9;i++){
            if(!regions.get(i).checkIfRegionComplete()){
                isGridComplete = false;
            }
        }
        if(isGridComplete){
            AlertDialog gameCompleteDialog = new AlertDialog.Builder(sudokuActivity).create();
            gameCompleteDialog.setTitle("Game Complete");
            gameCompleteDialog.setMessage("Congratulations! You solved the puzzle in "+getNoOfMoves()+ " moves :) ");
            gameCompleteDialog.setButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    sudokuActivity.finishGame();
                }
            });
            gameCompleteDialog.show();
        }
    }

    public void resetGamecontroller(){
        gameController = new GameController();
    }





}
