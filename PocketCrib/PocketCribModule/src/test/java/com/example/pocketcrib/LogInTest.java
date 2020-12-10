package com.example.pocketcrib;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class LogInTest {


        @Test
        public void InputTest() {

            LoginActivity test=new LoginActivity();
            assertTrue(test.InputTest("n01329656"," "));

        }

}
