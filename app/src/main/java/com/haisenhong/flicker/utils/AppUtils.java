package com.haisenhong.flicker.utils;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.widget.FrameLayout;

/**
 * Created by hison7463 on 10/15/16.
 */

public class AppUtils {

    public static void displayFragment(Context context, FragmentManager fragmentManager, Fragment fragment, int container) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(container, fragment);
        transaction.commit();
    }

}
