package com.demo.tyexternalstoragevowelconsonantcount;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    EditText Input;
    Button Save,Read;

    private String filename = "SampleFile.txt";
    File myExternalFile;
    String myData = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Input = findViewById(R.id.etInput);
        Save = findViewById(R.id.btnSave);
        Read = findViewById(R.id.btnRead);

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileOutputStream fos = new FileOutputStream(myExternalFile);
                    fos.write(Input.getText().toString().getBytes());
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Input.setText("");
            }
        });

        Read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileInputStream fis = new FileInputStream(myExternalFile);
                    DataInputStream in = new DataInputStream(fis);
                    BufferedReader br =
                            new BufferedReader(new InputStreamReader(in));
                    String strLine;
                    int vCount = 0;
                    int cCount = 0;
                    while ((strLine = br.readLine()) != null) {
                        myData = strLine;
                        for(int i = 0; i < strLine.length(); i++) {
                            if(strLine.charAt(i) == 'a' || strLine.charAt(i) == 'e' || strLine.charAt(i) == 'i' || strLine.charAt(i) == 'o' || strLine.charAt(i) == 'u') {
                                vCount++;
                            }
                            else if(strLine.charAt(i) >= 'a' && strLine.charAt(i)<='z') {
                                cCount++;
                            }
                        }

                        Input.setText(myData + "\nVowels: " + vCount  + "\nConsonants: " + cCount);

                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Save.setEnabled(false);
        }
        else {
            myExternalFile = new File(getExternalFilesDir(filename), filename);
        }
    }

    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }
}