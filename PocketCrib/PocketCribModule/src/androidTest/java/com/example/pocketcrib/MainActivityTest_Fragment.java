package com.example.pocketcrib;

import junit.framework.TestCase;

import static org.junit.Assert.assertTrue;

public class MainActivityTest_Fragment extends TestCase {


    public void isVisible() {
        MainActivity act=new MainActivity();
        assertTrue(act.isFragmentVisible());
    }

}