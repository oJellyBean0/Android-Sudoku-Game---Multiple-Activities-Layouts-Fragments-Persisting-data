package s213329913.wrap302.nmmu.assignment03_task1;

import android.graphics.Color;
import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by s2133 on 2017/08/12.
 */

public class Region implements Serializable {
    RegionFragment curRegion;
    int regionPosition;
    boolean regionCompleted;
    ArrayList<s213329913.wrap302.nmmu.assignment03_task1.Button> buttonsInRegion = new ArrayList<>();
    public Region(View curView, int regionPosition){
        this.regionPosition = regionPosition;
    }

    public boolean checkForDuplicateInRegion(){
        boolean isThereDuplicates = false;
        for(int i = 0; i < buttonsInRegion.size();i++){
            s213329913.wrap302.nmmu.assignment03_task1.Button curButton = buttonsInRegion.get(i);
            for (int ii = i+1; ii<buttonsInRegion.size();ii++){
                if (curButton.equals(buttonsInRegion.get(ii))) {
                    changeBtnColor(curButton, buttonsInRegion.get(ii));
                    isThereDuplicates = true;
                }
            }
        }
        return isThereDuplicates;
    }

    public boolean checkIfDeplicateOfThisBtn(s213329913.wrap302.nmmu.assignment03_task1.Button curBtn){
        boolean isThereDuplicates = false;
        for(int i = 0; i < buttonsInRegion.size();i++){
                if (!curBtn.equals(buttonsInRegion.get(i))&&curBtn.state==buttonsInRegion.get(i).state) {
                    isThereDuplicates = true;
                }
            }
        return isThereDuplicates;
    }

    public void resetRegionColor(){
        for (int i = 0; i < buttonsInRegion.size();i++){
            buttonsInRegion.get(i).txtColour = Color.BLACK;
            buttonsInRegion.get(i).update();
        }
    }

    public void changeBtnColor(s213329913.wrap302.nmmu.assignment03_task1.Button curBtn, s213329913.wrap302.nmmu.assignment03_task1.Button otherBtn){
        curBtn.txtColour = Color.RED;
        otherBtn.txtColour = Color.RED;
        curBtn.update();
        otherBtn.update();
    }

    public boolean checkIfRegionComplete(){
        for(int i = 0; i<9;i++){
            if (buttonsInRegion.get(i).state==0||checkForDuplicateInRegion()==true){
                return false;
            }
        }
        return true;
    }

}
