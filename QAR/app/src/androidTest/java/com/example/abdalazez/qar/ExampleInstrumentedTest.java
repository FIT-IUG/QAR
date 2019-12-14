package com.example.abdalazez.qar;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void testNumExams() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();

        SharedPreferences pref = appContext.getSharedPreferences("JunitTestQar", appContext.MODE_PRIVATE);
        int valueExam = pref.getInt("JunitTestQarNumExams", -1);

        assertEquals(valueExam, 3);
    }
    @Test
    public void testNumNotification() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();

        SharedPreferences pref = appContext.getSharedPreferences("JunitTestQar", appContext.MODE_PRIVATE);
        int valueNotification = pref.getInt("JunitTestQarNumNoti", -1);
        assertEquals(valueNotification, 15);
    }
    @Test
    public void testNumStudents() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();

        SharedPreferences pref = appContext.getSharedPreferences("JunitTestQar", appContext.MODE_PRIVATE);
        int valueStudent = pref.getInt("JunitTestQarNumStu", -1);

        assertEquals(valueStudent, 5);
    }

    @Test
    public void testNumStudentsIsAttendance() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();

        SharedPreferences pref = appContext.getSharedPreferences("JunitTestQar", appContext.MODE_PRIVATE);
        int valueStudent = pref.getInt("JunitTestQarNumStudentsIsAttendance", -1);

        assertEquals(valueStudent, 3);
    }
}
