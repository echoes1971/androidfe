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
        f1.setProb(7500);
        s = f1.toJson();
        System.out.println(s);
        Function f2 = new Function();
        f2.fromJson(s);
        System.out.println("> " + f2.toJson());

        String filename = "/Users/roberto/AndroidStudioProjects/AndroidFractalEditor/ifr/triangle.json.ifr";
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
        Point[] pnts = new Point[ifr.getNumEdges()];
        for(int i=0; i<ifr.getNumEdges(); i++) {
            pnts[i] = new Point(ifr.getX(i),ifr.getY(i));
            System.out.print("f(" + pnts[i].toJson() + ")=");
            pnts[i] = f.apply(pnts[i]);
            System.out.println("\t" + pnts[i].toJson());
        }

    }
}
