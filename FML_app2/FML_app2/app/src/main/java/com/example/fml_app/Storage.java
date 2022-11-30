package com.example.fml_app;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class Storage {

    public static void writeToStorage(Context context, PersonalData personalData) {
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = context.openFileOutput("userdata", Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(personalData);
            objectOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PersonalData loadFromStorage(Context context) {

        File file = context.getFileStreamPath("userdata");
        if(file == null || !file.exists()) {
            return new PersonalData();
        }
        FileInputStream fileInputStream;
        PersonalData userData = null;
        try {
            fileInputStream = context.openFileInput("userdata");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            userData = (PersonalData) objectInputStream.readObject();
            objectInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return userData;
    }
}
