package s213329913.wrap302.nmmu.assignment03_task1;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.Button;

import java.util.List;

/**
 * Created by s2133 on 2017/08/18.
 */

public class SudokuOptionAdapter extends ArrayAdapter<SudokuOption> {

    public SudokuOptionAdapter(Context context, List<SudokuOption> sudokuOptions) {
        super(context, R.layout.sudoku_option, sudokuOptions);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.sudoku_option, parent, false);
        }

        // keep track of person this view is working with
        convertView.setTag(getItem(position));

        // get views that will hold data
        TextView txtSudokuName = convertView.findViewById(R.id.sudokuName);
        TextView txtHighScore = convertView.findViewById(R.id.highScore);

        final SudokuOption sudokuOption = getItem(position);
        txtSudokuName.setText(sudokuOption.sudokuName);
        if (sudokuOption.highScore == 0) {
            txtHighScore.setText("Unsolved");
        } else txtHighScore.setText(String.valueOf(sudokuOption.highScore));

        // return view to ListView to display
        return convertView;
    }

}
