package it.rra.fe;


import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import it.rra.fe.engine.IFR;

public class IOUtilityTest {

    @Test
    public void test1() {
        String path = "C:\\Users\\taaroro3\\Projects\\androidfe\\ifr\\";
        String[] files = {"TREE2.IFR"};

        for (String filename: files) {
            //C:\Users\taaroro3\Projects\androidfe\ifr
            IFR ifr = IOUtility.load(path, filename);
            //IFR ifr = IOUtility.load("..\\..\\..\\..\\..\\..\\ifr\\","TRIANGLE.IFR");
            System.out.println(ifr.toJson("\n"));
            String out_filename = filename.split("\\.")[0].toLowerCase() + ".json.ifr";
            System.out.println("> " + out_filename);

            try {
                Files.write(Paths.get(path + out_filename), ifr.toJson("").getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
