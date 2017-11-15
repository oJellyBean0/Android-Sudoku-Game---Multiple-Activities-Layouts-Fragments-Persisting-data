package s213329913.wrap302.nmmu.assignment03_task1;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;

import java.util.ArrayList;


public class RegionFragment extends Fragment {

    public RegionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        savedInstanceState=getArguments();
        Region curRegion = (Region)savedInstanceState.getSerializable("region");
        ArrayList sudokuCells = (ArrayList)savedInstanceState.getSerializable("sudokuCells");
        int loopCounter = savedInstanceState.getInt("loopCounter");
        View view =  inflater.inflate(R.layout.fragment_region, container, false);

        GridLayout regionGrid = (GridLayout) view.findViewById(R.id.regionGridLayout);
        regionGrid.setColumnCount(3);
        regionGrid.setRowCount(3);
        for (int ii = 1; ii <=9; ii++) {
            int position = (((loopCounter-1)*9)+ii-1);
            if (!sudokuCells.get(position).equals(".")) {
                s213329913.wrap302.nmmu.assignment03_task1.Button myBtn = buildButton(ii, curRegion, Integer.parseInt(String.valueOf(sudokuCells.get(position))));
                regionGrid.addView(myBtn.button);
                myBtn.boldButton();
                myBtn.isBtnEnabled(false);
                myBtn.update();

            }
            else{
                s213329913.wrap302.nmmu.assignment03_task1.Button myBtn = buildButton(ii, curRegion, 0);
                regionGrid.addView(myBtn.button);

            }
        }

        return view;
    }
    public s213329913.wrap302.nmmu.assignment03_task1.Button buildButton(int position, Region curRegion, int state){
        Button btn = new Button(getActivity());
        btn.setMinHeight(110);
        btn.setMaxWidth(110);
        btn.setWidth(110);
        btn.setMinimumWidth(110);
        final s213329913.wrap302.nmmu.assignment03_task1.Button myBtn = new s213329913.wrap302.nmmu.assignment03_task1.Button(btn, position, curRegion,state);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myBtn.btnPressed();

            }
        });
        curRegion.buttonsInRegion.add(myBtn);

        return myBtn;

    }
}
