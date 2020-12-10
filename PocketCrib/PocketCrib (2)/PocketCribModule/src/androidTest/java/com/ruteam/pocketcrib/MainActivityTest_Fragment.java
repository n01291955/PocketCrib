package com.ruteam.pocketcrib;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest_Fragment {
    @Test
    public void isVisible() {
        MainActivity act=new MainActivity();
        assertTrue(act.isFragmentVisible());
    }

}