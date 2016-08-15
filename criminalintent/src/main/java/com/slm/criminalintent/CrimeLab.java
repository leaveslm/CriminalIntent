package com.slm.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Leaves on 2016/7/12.
 * 单例CrimeLab，应用在内存的存在多久，单例就存在多久
 */
public class CrimeLab {

    private ArrayList<Crime> crimes;

    private static CrimeLab crimeLab;
    private Context context;

    private CrimeLab(Context context) {
        this.context = context;
        crimes = new ArrayList<Crime>();
//        for (int i=0; i<50; i++) {
//            Crime c = new Crime();
//            c.setTitle("Crime #" + i);
//            c.setSolved(i%2 == 0);
//            crimes.add(c);
//        }
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
