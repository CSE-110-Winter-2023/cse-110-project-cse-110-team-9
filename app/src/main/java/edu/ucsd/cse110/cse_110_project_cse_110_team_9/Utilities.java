package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

import android.content.Context;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Utilities {


    public static void WriteJsonObject(JSONObject jsonObject, String filename, Context context) throws IOException {
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;

        File file = new File(context.getFilesDir(), filename);
        fileWriter = new FileWriter(file);
        bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(jsonObject.toString());
        bufferedWriter.close();

    }

    public static String readJsonFile(String filename, Context conext) throws IOException {
        File file = new File(conext.getFilesDir(), filename);
        StringBuffer output = new StringBuffer();
        FileReader fileReader  =null;
        String response = null;
        try {
             fileReader = new FileReader(file.getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = "";
        while((line = bufferedReader.readLine()) != null){
            output.append(line + "\n");

        }
        response = output.toString();
        bufferedReader.close();
        return response;
    }
}
