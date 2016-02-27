package com.thevarunshah.todolist;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Backup {

    private static final String TAG = "Backup";

    /**
     * creates a new file in internal memory and writes to it
     *
     * @param context the application context
     */
    public static void writeData(Context context, ArrayList<String> list){

        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            //open file and and write the to-do list to it
            fos = context.openFileOutput("todo_list.ser", Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
        } catch (Exception e) {
            Log.i(TAG, "could not write to file");
            e.printStackTrace();
        } finally{
            try{
                oos.close();
                fos.close();
            } catch (Exception e){
                Log.i(TAG, "could not close the file");
                e.printStackTrace();
            }
        }
    }

    /**
     * reads from serialized file in internal memory
     *
     * @param context the application context
     * @return the arraylist of to-do items
     */
    public static ArrayList<String> readData(Context context){

        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            //open file and read the to-do list from it
            fis = context.openFileInput("todo_list.ser");
            ois = new ObjectInputStream(fis);

            ArrayList<String> list = (ArrayList<String>) ois.readObject();
            return list;

        } catch (Exception e) {
            Log.i(TAG, "could not read from file");
            e.printStackTrace();
        } finally{
            try{
                if(ois != null) ois.close();
                if(fis != null) fis.close();
            } catch(Exception e){
                Log.i(TAG, "could not close the file");
                e.printStackTrace();
            }
        }

        return null;
    }
}
