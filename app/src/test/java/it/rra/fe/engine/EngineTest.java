package it.rra.fe.engine;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class EngineTest {

    @Test
    public void testFromToJson() {
        // Point
        Point p1 = new Point(32,128);
        String s = p1.toJson();
        System.out.println(s);
        Point p2 = new Point();
        p2.fromJson(s);
        System.out.println("> " + p2.toJson());

        Function f1 = new Function(90,82,13,-23,34);
        f1.setProb(1234);
        s = f1.toJson();
        System.out.println(s);
        Function f2 = new Function();
        f2.fromJson(s);
        System.out.println("> " + f2.toJson());

        String path = "C:\\Users\\taaroro3\\Projects\\androidfe\\ifr\\";
        //String path = "/Users/roberto/AndroidStudioProjects/AndroidFractalEditor/ifr/";
        //String filename = path + "tree.json.ifr";
        //String filename = "/Users/roberto/AndroidStudioProjects/AndroidFractalEditor/ifr/a-tree.json.ifr";
        String filename = path + "triangle.json.ifr";
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(filename)));
        } catch (IOException ioe) {
            System.out.println(ioe.toString());
            content = "{\"funct\":[{\"next\":16,\"cx\":50,\"cy\":50,\"tx\":-50,\"ty\":50,\"rot\":0,\"prob\":7500},{\"next\":32,\"cx\":50,\"cy\":50,\"tx\":50,\"ty\":50,\"rot\":0,\"prob\":7500},{\"next\":48,\"cx\":50,\"cy\":50,\"tx\":-50,\"ty\":-50,\"rot\":0,\"prob\":7500}],\"precision\":2,\"iterations\":6,\"KaosSequence\":\"012\",\"alg\":\"A\",\"unused\":\"\",\"numedges\":3,\"edges\":[[100,100],[-100,100],[-100,-100]]}";
        }
        System.out.println("  " + content);

        s = content;

        IFR ifr2 = new IFR();
        ifr2.fromJson(s);
        System.out.println("> " + ifr2.toJson(""));

        /*
        IFR ifr1 = new IFR();
        s = ifr1.toJson("");
        System.out.println(s);
        IFR ifr2 = new IFR();
        ifr2.fromJson(s);
        System.out.println("> " + ifr2.toJson("\n"));
*/
        IFR ifr = new IFR();
        ifr.fromJson(content);
        Function f = new Function(500/2,500/2,500/2,500/2,0);
        System.out.println("f=" + f.toJson());
        Point[] pnts = ifr.getPoints();
        for(int i=0; i<pnts.length; i++) {
            System.out.print("f(" + pnts[i].toJson() + ")=");
            pnts[i] = f.applyRST(pnts[i]);
            System.out.println("\t" + pnts[i].toJson());
        }

        f = new Function(500/2,500/2,500/2,500/2,45);
        System.out.println("f=" + f.toJson());
        pnts = ifr.getPoints();
        for(int i=0; i<pnts.length; i++) {
            System.out.print("f(" + pnts[i].toJson() + ")=");
            pnts[i] = f.applyRST(pnts[i]);
            System.out.println("\t" + pnts[i].toJson());
        }
    }

    private int myrand(int min, int max) { return (int)(Math.random() * ((max - min) )) + min; }

    @Test
    public void testRand() {
        for(int i=0; i<100; i++) {
            System.out.println(myrand(0,10));
        }
    }
}
