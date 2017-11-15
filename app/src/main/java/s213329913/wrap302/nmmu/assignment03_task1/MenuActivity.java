package s213329913.wrap302.nmmu.assignment03_task1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class MenuActivity extends AppCompatActivity {

    public static final int list = 1;
    int curOptionSelected = 0;
    SudokuOptionAdapter adapter;
    boolean hitBackIntent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ListView sudokuList = (ListView) findViewById(R.id.lstGames);

        ArrayList<SudokuOption> sudokuOptions = new ArrayList<>();


        File txt1 = new File(this.getFilesDir(), "1.txt");
        File txt2 = new File(this.getFilesDir(), "2.txt");
        File txt3 = new File(this.getFilesDir(), "3.txt");
        File txt4 = new File(this.getFilesDir(), "4.txt");
        File txt5 = new File(this.getFilesDir(), "5.txt");

        try {

            sudokuOptions.add(new SudokuOption(txt1));
            sudokuOptions.add(new SudokuOption(txt2));
            sudokuOptions.add(new SudokuOption(txt3));
            sudokuOptions.add(new SudokuOption(txt4));
            sudokuOptions.add(new SudokuOption(txt5));
        } catch (FileNotFoundException e) {
            e.printStackTrace();

            try {

                for (int i = 1; i <= 5; i++) {
                    InputStream inputStream = getResources().getAssets().open(String.valueOf(i) + ".txt");
                    OutputStream outputStream = new FileOutputStream(new File(this.getFilesDir(), String.valueOf(i) + ".txt"));

                    byte[] buffer = new byte[500];
                    int lengthOfBytesRead;
                    while ((lengthOfBytesRead = inputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, lengthOfBytesRead);
                    }

                    inputStream.close();
                    outputStream.close();
                }
                sudokuOptions.add(new SudokuOption(txt1));
                sudokuOptions.add(new SudokuOption(txt2));
                sudokuOptions.add(new SudokuOption(txt3));
                sudokuOptions.add(new SudokuOption(txt4));
                sudokuOptions.add(new SudokuOption(txt5));

            } catch (IOException ee) {
                ee.printStackTrace();
            }
        }
        adapter = new SudokuOptionAdapter(this, sudokuOptions);
        sudokuList.setAdapter(adapter);


        sudokuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                curOptionSelected = i;
                Intent intent = new Intent(MenuActivity.this, SudokuActivity.class);
                intent.putExtra("sudokuGame", (SudokuOption) view.getTag());
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, list);
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            int newestScore = Integer.parseInt(String.valueOf(data.getSerializableExtra("newestScore")));
            if (newestScore < adapter.getItem(curOptionSelected).highScore||adapter.getItem(curOptionSelected).highScore==0) {
                SudokuOption curSudokuOption = adapter.getItem(curOptionSelected);
                curSudokuOption.highScore = newestScore;
                changeHighScoreInTxt();
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void changeHighScoreInTxt(){
        String sudokuName;
        int highScore;
        ArrayList<String> sudokuCells;
        String gameFileName = adapter.getItem(curOptionSelected).fileName;
        File file = new File(this.getFilesDir(), gameFileName);
        Scanner in = null;
        try {
            in = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        sudokuName = in.nextLine();
        in.nextLine(); //do not need file high score
        highScore = adapter.getItem(curOptionSelected).highScore;
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

        File newFile = new File(this.getFilesDir(), gameFileName);
        try {
            if (file.exists())
                file.delete();
            file.createNewFile();
            FileWriter writer = new FileWriter(newFile);
            writer.write(sudokuName + "\n");
            writer.write(highScore + "\n");
            for (int i = 1; i <=9; i++) {
                String row = "";
                for (int ii = 1; ii <= 9; ii++) {
                    int position = (((i-1)*9)+ii-1);
                    if (ii != 9) {
                        row = row + sudokuCells.get(position) + ",";
                    } else
                        row = row + sudokuCells.get(position);
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
