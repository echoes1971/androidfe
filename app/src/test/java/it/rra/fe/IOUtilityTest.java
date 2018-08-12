package it.rra.fe;


import org.junit.Test;

import it.rra.fe.engine.IFR;

public class IOUtilityTest {
    @Test
    public void test1() {
        System.out.println("Cippa");

        IFR ifr = IOUtility.load("../../../../../../ifr/","TRIANGLE.IFR");
    }
}
