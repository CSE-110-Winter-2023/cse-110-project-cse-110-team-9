package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

import android.content.Context;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
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
}
