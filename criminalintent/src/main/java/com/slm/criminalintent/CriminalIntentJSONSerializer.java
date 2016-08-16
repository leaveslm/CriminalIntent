package com.slm.criminalintent;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by Leaves on 2016/8/16.
 */
public class CriminalIntentJSONSerializer {

    private Context context;
    private String filename;

    public CriminalIntentJSONSerializer(Context context, String filename) {
        this.context = context;
        this.filename = filename;
    }

    public ArrayList<Crime> loadCrimes() throws JSONException, IOException {
        ArrayList<Crime> crimes = new ArrayList<Crime>();
        BufferedReader reader = null;
        try {
            // 打开并读取文件到StringBuilder
            InputStream inputStream = context.openFileInput(filename);
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
            // 使用JSONTokener解析JSON
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            for (int i=0; i<array.length(); i++) {
                crimes.add(new Crime(array.getJSONObject(i)));
            }
        } catch (FileNotFoundException e) {
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return crimes;
    }

    public void saveCrimes(ArrayList<Crime> crimes) throws JSONException, IOException {
        // 创建JSON数组
        JSONArray array = new JSONArray();
        for (Crime crime : crimes) {
            array.put(crime.toJSON());
        }

        // 写入文件
        Writer writer = null;
        try {
            OutputStream outputStream = context.openFileOutput(filename, context.MODE_PRIVATE);
            writer = new OutputStreamWriter(outputStream);
            writer.write(array.toString());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
