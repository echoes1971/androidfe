package it.rra.fe;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import it.rra.fe.engine.IFR;
import it.rra.fe.engine.KaosSequence;

public class IOUtility {
    /** Creates new IOUtility */
    public IOUtility() {
    }

    private static int readAmyLong(DataInputStream in, String status) {
        int temp = 0;
        try { temp |= ( in.readByte() << 24);   temp |= ( in.readByte() << 16);
            temp |= ( in.readByte() << 8 );   temp |= ( in.readByte() << 0);
        } catch(IOException e) {
            status = new String("Error while loading. ");
        }
        return ( temp<0 ? temp+256 : temp );
    }
    private static int readAmyInt(DataInputStream in, String status) {
        byte temp1 = 0;  byte temp2 = 0;
        try { temp1 = in.readByte();  temp2 = in.readByte(); }
        catch(IOException e) { status = new String("Error while loading "); };
        int temp = (temp1<<8) + temp2;
        //if (temp<0)  System.out.println(temp + " --> " + (temp+256) );`
        temp = ( temp<0 ? temp+256 : temp );
        return temp;
    }
    private static char readAmyChar(DataInputStream in, String status) {
        char temp = 0;
        try { temp += in.readByte(); }
        catch(IOException e) { status = new String("Error while loading."); };
        //System.out.print(temp + "\t");
        return temp;
    }
    private static void writeAmyLong(DataOutputStream out, int l) {
        try { out.writeByte( ((byte)(l>>24)) ); out.writeByte( ((byte)(l>>16)) );
            out.writeByte( ((byte)(l>>8)) );  out.writeByte( ((byte)(l>>0)) );
        } catch(IOException e) {
            System.out.println("Error while saving.");
        }
    }
    private static void writeAmyInt(DataOutputStream out, int l) {
        try { out.writeByte( ((byte)(l>>8)) );  out.writeByte( ((byte)(l>>0)) );
        } catch(IOException e) {
            System.out.println("Error while saving.");
        }
    }
    private static void writeAmyChar(DataOutputStream out, char l) {
        try { out.writeByte( ((byte)l) ); }
        catch(IOException e) {
            System.out.println("Error while saving.");
        }
    }

    private static String load_1(DataInputStream in, IFR ifr) {
        String status = "Ok!";
        int temp = 0;
        for(int i=0 ; i<ifr.MAXFUNCTIONS ; i++) {
            ifr.getFunction(i).setBoh( readAmyLong(in,status) );
            ifr.getFunction(i).setCx( readAmyInt(in,status));  ifr.getFunction(i).setCy( readAmyInt(in,status));
            ifr.getFunction(i).setTx( readAmyInt(in,status));  ifr.getFunction(i).setTy( readAmyInt(in,status));
            ifr.getFunction(i).setRot( readAmyInt(in,status)); ifr.getFunction(i).setProb( readAmyInt(in,status));
        }
        ifr.setPrecision( readAmyInt(in,status) );
        ifr.setIterations( readAmyInt(in,status) );
//	ksLen = 0;
        KaosSequence kaosSequence = ifr.getKaosSequence();
        for (int i=0 ; i<ifr.MAXFUNCTIONS ; i++) {
            kaosSequence.addElement( readAmyChar(in,status) );
            //int j = ((int) kaosSequence.getElement(i)) - ((int) '0');  //conto anche la lung. della seq.
            //if ( (j>=0) && (j<=9) ) { ksLen++; }            //mentre la carico.
        }
        ifr.setAlgorythm( readAmyChar(in,status) );
        try {
            temp = in.readByte();
        } catch(IOException e) {
            status = new String("Error while reading " + ifr.getIfrName());
        }
        ifr.setNumEdges( readAmyInt(in,status) );
        for(int i=0 ; i<ifr.MAXEDGES ; i++) {
            ifr.setX(i , readAmyInt(in,status) );
            ifr.setY(i , readAmyInt(in,status) );
        }
        return status;
    }

    /**
     * Load a locale Amy IFR file
     */
    public static IFR load(String path , String filename) {
        String status = "Ok!";
        IFR ifr = new IFR();
        ifr.setIfrName(filename);
        try {
            DataInputStream in = new DataInputStream( new FileInputStream(path + ifr.getIfrName()) );
            status = load_1(in,ifr);
            in.close();
        } catch(IOException e) {
            System.out.println("Error while loading " + ifr.getIfrName());
        }
        LogBean.getInstance().writeln( LogBean.DEBUG , status );
        return ifr;
    }

    /**
     * Load a remote Amy IFR file
     */
    public static String loadURL(String url_address) {
        String status = "Ok";
        /************
         DataInputStream in = null;  URL url = null;
         System.out.println(url_address);
         try {
         //try { if (Class.forName("netscape.security.PrivilegeManager") != null)
         //        netscape.security.PrivilegeManager.enablePrivilege("UniversalConnect");
         //}
         //catch (Throwable t) {};
         url = new URL(url_address);
         if (url!=null) {
         //in = new DataInputStream(url.openConnection().getInputStream());
         try { in = new DataInputStream(url.openStream()); }
         catch(IOException e) { status = new String("Error while loading from: " + url_address ); };
         if (in!=null)  {  status = load_1(in);  in.close(); };
         }
         } catch(Exception e) {
         status = e.getMessage();
         }
         **************/
        return status;
    }

    /**
     * Save an Amy IFR file
     */
    public static boolean save(String path , IFR ifr) {
        //System.out.print("Saving '"+filename+"'...");
//    ifrName = filename;
        try {
            DataOutputStream out = new DataOutputStream( new FileOutputStream( path + ifr.getIfrName() ) );
            int temp = 0;
            for(int i=0 ; i<ifr.MAXFUNCTIONS ; i++) {
                writeAmyLong(out, ifr.getFunction(i).getBoh());
                writeAmyInt (out, ifr.getFunction(i).getCx());  writeAmyInt(out, ifr.getFunction(i).getCy());
                writeAmyInt (out, ifr.getFunction(i).getTx());  writeAmyInt(out, ifr.getFunction(i).getTy());
                writeAmyInt (out, ifr.getFunction(i).getRot()); writeAmyInt(out, ifr.getFunction(i).getProb());
            }
            writeAmyInt(out,ifr.getPrecision());
            writeAmyInt(out,ifr.getIterations());
            for (int i=0 ; i<ifr.MAXFUNCTIONS ; i++)
                writeAmyChar(out,ifr.getKaosSequence().getElement(i));
            writeAmyChar(out,ifr.getAlgorythm());     out.writeByte((byte)0);
            writeAmyInt(out,ifr.getNumEdges());
            for(int i=0 ; i<ifr.MAXEDGES ; i++) {
                writeAmyInt(out,ifr.getX(i));
                writeAmyInt(out,ifr.getY(i));
            }
            out.close();
            return true;
        } catch(IOException e) {
            System.out.println("Error while saving " + ifr.getIfrName());
            return false;
        }
        //System.out.println("done!");
    }

}
