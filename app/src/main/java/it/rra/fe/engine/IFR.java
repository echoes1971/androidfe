/**
 *      IFR.Class v00.91 - 23.03.1999
 *      -----------------------------
 *
 *      Author:     Roberto Rocco Angeloni.
 *      Copyright:  (c) 1999 by Roberto Rocco Angeloni.
 *      Comment:    Definition of the IFR Fractals.
 *      To Do:      - scorporare l'IFR dalle funzioni che
 *					lo caricano e lo salvano
 *					- migliorare save() e load();
 *      Future:     - loadASCII, saveASCII
 *      History:
 *        v00.91 - loadURL added.
 *
 */

package it.rra.fe.engine;

import java.io.PrintStream;

public class IFR {
    public static int MAXFUNCTIONS = 10;
    public static int MAXEDGES = 20;

    private char algorythm;
    private int numEdges;
    private int precision;
    private int iterations;

//  public char[] kaosSequence = new char[MAXFUNCTIONS];
//  public int ksLen = 0;

    private Function[] functions = new Function[MAXFUNCTIONS];
    private int[] xArray = new int[MAXEDGES];
    private int[] yArray = new int[MAXEDGES];

    private String ifrName;

    /** Holds value of property kaosSequence. */
    private KaosSequence kaosSequence = new KaosSequence(MAXFUNCTIONS);

    public IFR() {
        algorythm = 'A';  numEdges = 0;  precision = 1;  iterations = 1;
        kaosSequence = new KaosSequence( this.MAXFUNCTIONS );
        kaosSequence.setElement(0, (char) '0');
        for (int i=1 ; i<MAXFUNCTIONS ; i++)	kaosSequence.setElement(i, '\0');
        for (int i=0 ; i<MAXFUNCTIONS ; i++) 	functions[i] = new Function();
        for (int i=0 ; i<MAXEDGES ; i++) {
            xArray[i] = 0;
            yArray[i] = 0;
        }
    }

    /**
     * Print Info about the fractal on a PrintStream.
     */
    public void info(PrintStream out) {
        out.println("\nFractal:\t" + ifrName);     out.println("Algoritmo:\t" + algorythm );
        out.println("Num.Edges:\t" + numEdges);     out.println("Precision:\t" + precision);
        out.println("Iterations:\t" + iterations);  out.println("Kaos Sequence:\t" + kaosSequence);
        for (int i=0 ; i<MAXFUNCTIONS ; i++) {
            int j = ((int) kaosSequence.getElement(i)) - ((int) '0');
            if ( (j>=0) && (j<=9) ) {
                out.print("Function["+j+"]:");
                out.println("  \tCx,Cy:\t" + functions[j].getCx()  + "," + functions[j].getCy());
                out.println("\t\tTx,Ty:\t" + functions[j].getTx()  + "," + functions[j].getTy());
                out.println("\t\tRot:\t"   + functions[j].getRot() + "\n\t\tProb:\t" + functions[j].getProb());
            } else {
                i = MAXFUNCTIONS;
            }
        }
        if (numEdges>0) {
            out.println("Edges:");
        } else  {
            out.println("No Edges.");
        }
        //for (int i=0 ; i<MAXEDGES ; i++)
        for (int i=0 ; i<numEdges ; i++) {
            out.print("\t" + i + ":(" + xArray[i] + "," + yArray[i] + ")");
            if ( (i%4)==3 )	out.println("");
        }
        out.println("");
    }

    /**
     * Print Info about the fractal on a PrintStream.
     */
    public String toString() {
        String ret = "";
        ret += "Fractal:\t" + ifrName + "\n";
        ret += "Algoritmo:\t" + algorythm + "\n";
        ret += "Num.Edges:\t" + numEdges + "Precision:\t" + precision + "\n";
        ret += "Iterations:\t" + iterations + "Kaos Sequence:\t" + kaosSequence + "\n";
        for (int i=0 ; i<MAXFUNCTIONS ; i++) {
            int j = ((int) kaosSequence.getElement(i)) - ((int) '0');
            if ( (j>=0) && (j<=9) ) {
                ret += "Function["+j+"]:" + "\n";
                ret += "  \tCx,Cy:\t" + functions[j].getCx()  + "," + functions[j].getCy() + "\n";
                ret += "\t\tTx,Ty:\t" + functions[j].getTx()  + "," + functions[j].getTy() + "\n";
                ret += "\t\tRot:\t"   + functions[j].getRot() + "\n\t\tProb:\t" + functions[j].getProb() + "\n";
            } else { i = MAXFUNCTIONS; };
        }
        if (numEdges>0) {
            ret += "Edges:" + numEdges + "\n";
        } else  {
            ret += "No Edges.";
        }
        //for (int i=0 ; i<MAXEDGES ; i++)
        for (int i=0 ; i<numEdges ; i++) {
            ret += "\t" + i + ":(" + xArray[i] + "," + yArray[i] + ")";
            if ( (i%4)==3 )	ret += "\n";
        }
        ret += "\n";
        return ret;
    }

    /**
     *    Cancella il vertice desiderato dalla definizione del frattale
     */
    public void deleteVertex(int vertexToDelete) {
        if (this.numEdges>0) this.numEdges--;
        for (int i=vertexToDelete ; i<this.numEdges ; i++) {
            this.xArray[i] = this.xArray[i+1];
            this.yArray[i] = this.yArray[i+1];
        }
    }

    public void increaseNumEdges() {
        numEdges++;
    }
/********************* GET & SET: inizio. ************************/

    /**Setta la coordinata X del vertice n-esimo*/
    public void setX(int n, int newX) {      this.xArray[n] = newX;  }
    /**Setta la coordinata Y del vertice n-esimo*/
    public void setY(int n, int newY) {      this.yArray[n] = newY;  }
    /**Ritorna la coordinata X del vertice n-esimo*/
    public int getX(int n) {      return this.xArray[n];  }
    /**Ritorna la coordinata X del vertice n-esimo*/
    public int getY(int n) {      return this.yArray[n];  }

    public int getNumEdges() {      return this.numEdges;  }
    public void setNumEdges(int ne) {      this.numEdges = ne;  }
    public char getAlgorythm() {      return this.algorythm;  }
    public void setAlgorythm(char newValue) {      this.algorythm = newValue;  }
    public Function getFunction(int n) {      return this.functions[n];  }
    public void setFunction(int n, Function newValue) {      this.functions[n] = newValue;  }
    public String getIfrName() {      return this.ifrName;  }
    public void setIfrName(String newValue) {      this.ifrName = newValue;  }
    public int getIterations() {      return this.iterations;  }
    public void setIterations(int newValue) {      this.iterations = newValue;  }
    public int getPrecision() {      return this.precision;  }
    public void setPrecision(int newValue) {      this.precision = newValue;  }
    public KaosSequence getKaosSequence() {      return kaosSequence;  }
    public void setKaosSequence(KaosSequence kaosSequence) {      this.kaosSequence = kaosSequence;  }

/********************* GET & SET: fine. ************************/

    public String toJson(String sep) {
        String tab = sep.length()>0 ? " " : "";
        String ret = "{" + sep;
        ret += tab + "\"funct\":[" + sep;
        for(int i=0; i<MAXFUNCTIONS; i++) {
            if(this.functions[i].isIdentity()) continue;
            ret += tab+tab + this.functions[i].toJson();
            if(i<(MAXFUNCTIONS-1) && !this.functions[i+1].isIdentity()) ret += ",";
            ret += sep;
        }
        ret += tab + "]," + sep;
        ret += tab + "\"precision\":" + this.precision + "," + sep;
        ret += tab + "\"iterations\":" + this.iterations + "," + sep;
        ret += tab + "\"KaosSequence\":\"" + this.kaosSequence + "\"," + sep;
        ret += tab + "\"alg\":\"" + this.algorythm + "\"," + sep;
        ret += tab + "\"unused\":\"\"," + sep;
        ret += tab + "\"numedges\":" + this.numEdges + "," + sep;
        ret += tab + "\"edges\":[" + sep;
        ret += tab+tab;
        for(int i=0; i<this.numEdges; i++) {
            Point p = new Point(xArray[i],yArray[i]);
            ret += p.toJson();
            if(i<(this.numEdges-1)) ret += ",";
        }
        ret += sep;
        ret += tab + "]" + sep;
        ret += "}" + sep;
        return ret;
    }
    IFR fromJson(String s)
    {
        int start = s.indexOf("{")+1;
        int end = s.lastIndexOf("}");

        String tmp = s.substring(start,end);
        //System.out.println("tmp: " + tmp);
        start = tmp.indexOf("\"funct\":[",start);
        end = tmp.indexOf("],",start)+1;
        String functions = tmp.substring(start+9,end);
        //System.out.println("functions: " + functions );
        tmp = tmp.substring(end+1);
        int start_func = functions.indexOf("{");
        int end_func = functions.indexOf("}",start_func)+1;
        String s_func = "";
        int fn = 0;
        while(fn<MAXFUNCTIONS && start_func>=0 && end_func>=0) {
            s_func = functions.substring(start_func,end_func);
            functions = functions.substring(end_func+1);
            this.functions[fn].fromJson(s_func);
            //System.out.println("start_func="+start_func+"\tend_func="+end_func);
            //System.out.println("s_func: " + s_func);
            //System.out.println("    ==> " + this.functions[fn].toJson("") );
            //System.out.println("functions: " + functions);
            start_func = functions.indexOf("{");
            end_func = functions.indexOf("}")+1;
            fn++;
        }
        //System.out.println("tmp: " + tmp);
        String tmp2;

        start = tmp.indexOf("\"precision\":");
        end = tmp.indexOf(",");
        tmp2 = tmp.substring(start+12, end);
        tmp = tmp.substring(end+1);
        this.precision = Integer.parseInt(tmp2);

        start = tmp.indexOf("\"iterations\":");
        end = tmp.indexOf(",");
        tmp2 = tmp.substring(start + 13, end);
        tmp = tmp.substring(end+1);
        this.iterations = Integer.parseInt(tmp2);

        start = tmp.indexOf("\"KaosSequence\":\"");
        end = tmp.indexOf("\",");
        tmp2 = tmp.substring(start + 16, end);
        tmp = tmp.substring(end+2);
        for(int i=0; i<tmp2.length() && i<MAXFUNCTIONS; i++) {
            this.kaosSequence.addElement(tmp2.charAt(i));
        }
        //System.out.println("KaosSequence: " + this.kaosSequence.toString());

        start = tmp.indexOf("\"alg\":\"");
        end = tmp.indexOf("\",");
        tmp2 = tmp.substring(start + 7, end);
        tmp = tmp.substring(end+2);
        this.algorythm = tmp2.charAt(0);

        start = tmp.indexOf("\"unused\":\"");
        end = tmp.indexOf("\",");
        tmp2 = tmp.substring(start + 10, end);
        tmp = tmp.substring(end+2);
        //this.unused='\0';

        start = tmp.indexOf("\"numedges\":");
        end = tmp.indexOf(",");
        tmp2 = tmp.substring(start + 11, end);
        tmp = tmp.substring(end+1);
        this.numEdges = Integer.parseInt(tmp2);

        start = tmp.indexOf("\"edges\":[");
        end = tmp.lastIndexOf("]");
        tmp2 = tmp.substring(start + 9, end);
        tmp = tmp.substring(end+1);
        //System.out.println("tmp2: " + tmp2);
        //System.out.println("tmp: " + tmp);

        Point p = new Point();
        for(int i=0; i<this.numEdges; i++) {
            int pnt_start, pnt_end; String tmp3;
            pnt_start = tmp2.indexOf("[");
            pnt_end = tmp2.indexOf("]")+1;
            tmp3 = tmp2.substring(pnt_start,pnt_end);
            tmp2 = (pnt_end+1) < tmp2.length() ? tmp2.substring(pnt_end+1) : "";
            //System.out.println("tmp3[" + i + "]=" + tmp3);
            //System.out.println("tmp2[" + i + "]=" + tmp2);
            p.fromJson(tmp3);
            this.xArray[i] = p.getX();
            this.yArray[i] = p.getY();
        }

        return this;
    }

}
