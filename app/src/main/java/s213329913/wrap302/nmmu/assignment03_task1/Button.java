package s213329913.wrap302.nmmu.assignment03_task1;

import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.Switch;

import java.io.Serializable;

/**
 * Created by s2133 on 2017/08/12.
 */

public class Button {
    int txtColour = Color.BLACK;
    int state;
    int btnPosition;
    Region regionBelongedTo;
    android.widget.Button button;
    boolean editable = true;


    public Button(android.widget.Button btn, int btnPosition, Region curRegionOfBtn, int state){
        this.button = btn;
        this.btnPosition = btnPosition;
        button.setBackgroundResource(android.R.drawable.btn_default);
        this.regionBelongedTo = curRegionOfBtn;
        this.state = state;
    }

    public void btnPressed() {
        if(state!=9)
            state++;
        else state = 0;
        update();
        regionBelongedTo.resetRegionColor();
        GameController.getObject().checkConflicts(this);
        GameController.getObject().updateNoOfMoves(this);
        GameController.getObject().checkIfGridComplete();
        regionBelongedTo.checkForDuplicateInRegion();
    }

    public void update(){
        if(state ==0){
            button.setText("");
        }
        else button.setText(""+state);
        button.setTextColor(txtColour);
    }

    @Override
    public boolean equals(Object otherBtn){
        if(this.state==((Button) otherBtn).state)
            return true;
        return false;
    }

    public void disable(){
        button.setEnabled(editable);
    }
    public void isBtnEnabled(boolean bool){
        button.setEnabled(bool);
    }

    public void boldButton(){
        button.setTypeface(Typeface.create("sans-serif-medium", Typeface.BOLD));
    }

}
