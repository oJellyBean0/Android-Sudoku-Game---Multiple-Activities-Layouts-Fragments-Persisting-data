package s213329913.wrap302.nmmu.assignment03_task1;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class SudokuActivity extends AppCompatActivity {
    GameController gameController = GameController.getObject();
    SudokuOption sudokuObj;
    boolean gamePreviouslyFinished = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameController.txtNoOfMoves = (TextView) findViewById(R.id.txtNoOfMoves);
        gameController.sudokuActivity = this;

        // obtain the fragment manager
        FragmentManager fragmentManager = getFragmentManager();
        GridLayout mainGrid = (GridLayout) findViewById(R.id.bigGrid);
        mainGrid.setColumnCount(3);
        mainGrid.setRowCount(3);
        // start a transaction that will handle the swapping in/out
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Bundle bundle;
        if (((bundle = getIntent().getExtras()) != null) && (bundle.getSerializable("sudokuGame") != null)) {
            sudokuObj = (SudokuOption) bundle.getSerializable("sudokuGame");
            if (sudokuObj != null) {
                ArrayList sudokuCells = sudokuObj.sudokuCells;

                for (int i = 1; i <= 9; i += 1) {

                    // determine if the fragment has already been loaded (may have happened)
                    View view = new FrameLayout(this);
                    Region curRegion = new Region(view, i);
                    RegionFragment fragmentRegion = new RegionFragment();
                    Bundle newBundle = new Bundle();
                    newBundle.putSerializable("region", curRegion);
                    newBundle.putSerializable("sudokuCells", sudokuCells);
                    newBundle.putInt("loopCounter", i);
                    fragmentRegion.setArguments(newBundle);

                    gameController.regions.add(curRegion);

                    // place fragment into container if not already there
                    if (fragmentRegion != null) {
                        // multiple additions to the transaction can be done so that the
                        // changes will be done simultaneously
                        view.setId(i);
                        fragmentTransaction.add(view.getId(), fragmentRegion);
                        mainGrid.addView(view);
                    }

                }
            }
            // commit the changes, i.e. do it!
            fragmentTransaction.commit();
        }
    }

    public void finishGame() {
        Intent backIntent = new Intent();
        backIntent.putExtra("newestScore", gameController.noOfMoves);
        setResult(RESULT_OK, backIntent);
        String fileName = getSavedFileName();
        File file = new File(this.getFilesDir(), fileName);
        if (file.exists()) {
            file.delete();
        }
       // changeHighScoreInTxt();
        gamePreviouslyFinished = true;
        gameController.resetGamecontroller();

        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        gameController.resetGamecontroller();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!gamePreviouslyFinished) {
            String fileName = getSavedFileName();
            File file = new File(this.getFilesDir(), fileName);
            try {
                if (file.exists())
                    file.delete();
                file.createNewFile();
                FileWriter writer = new FileWriter(file);
                writer.write(sudokuObj.sudokuName + "\n");
                writer.write(gameController.getNoOfMoves() + "\n");
                for (int i = 0; i < 9; i++) {
                    String row = "";
                    for (int ii = 0; ii < 9; ii++) {
                        if (ii != 8) {
                            row = row + gameController.regions.get(i).buttonsInRegion.get(ii).state + ",";
                        } else
                            row = row + gameController.regions.get(i).buttonsInRegion.get(ii).state;
                    }
                    writer.write(row + "\n");
                }
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String fileName = getSavedFileName();
        File file = new File(this.getFilesDir(), fileName);
        if (file.exists()) {
            Scanner in = null;
            try {
                in = new Scanner(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            in.nextLine();
            gameController.noOfMoves = Integer.parseInt(in.nextLine());
            gameController.updateTextOfNoOfMoves();

            String[] rowCells = new String[0];
            for (int i = 0; i < 9; i++) {
                rowCells = in.nextLine().split(",");
                for (int ii = 0; ii < 9; ii++) {
                    if (gameController.regions.get(i).buttonsInRegion.get(ii).state != Integer.parseInt(rowCells[ii])) {
                        gameController.regions.get(i).buttonsInRegion.get(ii).state = Integer.parseInt(rowCells[ii]);
                        gameController.regions.get(i).buttonsInRegion.get(ii).update();
                        gameController.regions.get(i).checkForDuplicateInRegion();
                        gameController.checkConflicts(gameController.regions.get(i).buttonsInRegion.get(ii));
                    }
                    else {
                        if (gameController.regions.get(i).buttonsInRegion.get(ii).state != 0) {
                            gameController.regions.get(i).buttonsInRegion.get(ii).isBtnEnabled(false);
                            gameController.regions.get(i).buttonsInRegion.get(ii).update();
                        }
                    }
                }
            }
            in.close();
        }
    }


    private String getSavedFileName() {
        String gameFileName = sudokuObj.fileName;
        String[] fileNameSplit = gameFileName.split("\\.");
        return fileNameSplit[0] + "Save.txt";
    }



}
