package com.slm.criminalintent;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Leaves on 2016/7/12.
 * 单例CrimeLab，应用在内存的存在多久，单例就存在多久
 */
public class CrimeLab {

    private static final String TAG = "CrimeLab";
    private static final String FILENAME = "crimes.json";

    private ArrayList<Crime> crimes;
    private CriminalIntentJSONSerializer jsonSerializer;

    private static CrimeLab crimeLab;
    private Context context;

    private CrimeLab(Context context) {
        this.context = context;
        jsonSerializer = new CriminalIntentJSONSerializer(context, FILENAME);
        try {
            crimes = jsonSerializer.loadCrimes();
        } catch (Exception e) {
            crimes = new ArrayList<Crime>();
            Log.e(TAG, "Error loading crimes: ", e);
        }
    }

    public static CrimeLab get(Context context) {
        if (crimeLab == null) {
            // context.getApplicationContext()针对应用的全局性Context
            crimeLab = new CrimeLab((context.getApplicationContext()));
        }
        return crimeLab;
    }

    public void addCrime(Crime crime) {
        crimes.add(crime);
    }

    public boolean saveCrimes() {
        try {
            jsonSerializer.saveCrimes(crimes);
            Log.d(TAG, "crimes saved to file.");
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "Error saving crimes: ", e);
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "Error saving crimes: ", e);
            return false;
        }
    }

    public ArrayList<Crime> getCrimes() {
        return crimes;
    }

    public Crime getCrime(UUID id) {
        for (Crime c : crimes) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }
}
