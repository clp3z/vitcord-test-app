package com.clp3z.vitcord.mvp.base.global;

import android.Manifest;
import android.support.v4.util.Pair;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Clelia lópez on 9/11/16
 */
public class Constants {

    public static HttpLoggingInterceptor.Level RETROFIT_LOG_LEVEL = HttpLoggingInterceptor.Level.BASIC;

    /**
     * Permission constants
     */
    public static final int READ_EXTERNAL_STORAGE = 10;
    public static final int WRITE_EXTERNAL_STORAGE = 20;
    public static final int CAMERA = 30;
    public static final int RECORD_AUDIO = 40;
    public static final int FINE_LOCATION = 50;
    public static final int COURSE_LOCATION = 60;
    public static final int READ_CONTACTS = 70;
    public static final int WRITE_CONTACTS = 80;
    public static final int READ_PHONE_STATE = 90;
    public static final int CALL_PHONE = 100;

    public static final int ALL = 200;

    private static final String[] PERMISSIONS = new String[] {
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.WRITE_CONTACTS,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.CALL_PHONE
    };

    public static final Pair<String,Integer> READ_STORAGE_PERMISSION =
            new Pair<>(Manifest.permission.READ_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE);

    public static final Pair<String,Integer> WRITE_STORAGE_PERMISSION =
            new Pair<>(Manifest.permission.WRITE_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE);

    public static final Pair<String,Integer> CAMERA_PERMISSION =
            new Pair<>(Manifest.permission.CAMERA, CAMERA);

    public static final Pair<String,Integer> RECORD_AUDIO_PERMISSION =
            new Pair<>(Manifest.permission.RECORD_AUDIO, RECORD_AUDIO);

    public static final Pair<String,Integer> FINE_LOCATION_PERMISSION =
            new Pair<>(Manifest.permission.ACCESS_FINE_LOCATION, FINE_LOCATION);

    public static final Pair<String,Integer> COURSE_LOCATION_PERMISSION =
            new Pair<>(Manifest.permission.ACCESS_COARSE_LOCATION, COURSE_LOCATION);

    public static final Pair<String,Integer> READ_CONTACTS_PERMISSION =
            new Pair<>(Manifest.permission.READ_CONTACTS, READ_CONTACTS);

    public static final Pair<String,Integer> WRITE_CONTACTS_PERMISSION =
            new Pair<>(Manifest.permission.WRITE_CONTACTS, WRITE_CONTACTS);

    public static final Pair<String,Integer> READ_PHONE_STATE_PERMISSION =
            new Pair<>(Manifest.permission.READ_PHONE_STATE, READ_PHONE_STATE);

    public static final Pair<String,Integer> CALL_PHONE_PERMISSION =
            new Pair<>(Manifest.permission.CALL_PHONE, CALL_PHONE);


    public static final String FOLLOWING_PARAMETER = "following_parameter";

    public static final int PAGE_SIZE = 10;
    public static final int TOTAL_PAGES = 200;
    public static final int START_PAGE = 1;
}
