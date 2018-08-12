package it.rra.fe.engine;

import it.rra.fe.LogBean;

public class Function {
    /**Unused*/
    private int boh;
    /**Size % compared to the original on both axis.*/
    private int cx,cy;
    /**Traslation Vector*/
    private int tx,ty;
    /**Rotation Degree*/
    private int rot;
    /**Probability Weight*/
    private int prob;

    private LogBean log;
    public Function() {
        log = LogBean.getInstance();
        boh = 16;
        cx = 100;  cy = 100;
        tx = 0;    ty = 0;
        this.setRot(0);
        prob = 10000;
    }
    public Function(int cx,int cy, int tx, int ty, int rot) {
        log = LogBean.getInstance();
        boh = 16;
        this.setCx(cx);  this.setCy(cy);
        this.tx = tx;    this.ty = ty;
        this.setRot(rot);
        //prob = 10000;
    }

    public String toString() {
        String ret = "( ";
        ret += "boh="+boh+", ";
        ret += "cx="+cx+", ";
        ret += "cy="+cy+", ";
        ret += "tx="+tx+", ";
        ret += "ty="+ty+", ";
        ret += "rot="+rot+", ";
        ret += "prob="+prob+" ";
        ret += " )";
        ret += "\n";
        ret += "RST:\n";
        ret += "(\tcx*cos_w="+((cx*cos_w)>>14)+"\tcy*(-sin_w)="+cy*(-sin_w)+"\ttx="+tx+"\t)\n";
        ret += "(\tcx*sin_w="+cx*sin_w+"\tcy*cos_w="+cy*cos_w+"\tty="+ty+"\t)\n";
        ret += "(\t0\t\t0\t\t1\t)\n";
        return ret;
    }

    //
    private int sin_w, cos_w;
    //    private tempPoint = new Point();
    public Point apply(int x, int y) {
//      if (tempPoint==null) tempPoint = new Point();
        // Nota: sembra uno spreco di memoria MA la funzione potrebbe essere acceduta
        //        concorrentemente da + parti dell'applicativo.
        Point tempPoint = new Point();
        tempPoint.setX(x);    tempPoint.setY(y);
        return this.apply(tempPoint);
    }
    /**
     *    Calcola:
     *    - tx1 = ((point.x*cx)/100
     *    - ty1 = ((point.x*cx)/100
     *    - point.x = (tx1*cos_w-ty1*sin_w) + tx
     *    - point.y = (ty1*cos_w+ty1*sin_w) + ty
     */
    public Point apply(Point point) {
        if (!this.isIdentity()) {
            //log.write(LogBean.DEBUG, "Function.apply: f("+point.getX()+","+point.getY()+") = ");
            int tx1 = ((point.getX()*this.cx)/100);
            int ty1 = ((point.getY()*this.cy)/100);
            point.setX( ((tx1*cos_w-ty1*sin_w)>>14) + this.tx );
            point.setY( ((tx1*sin_w+ty1*cos_w)>>14) + this.ty );
            //log.writeln(LogBean.DEBUG, "("+point.getX()+","+point.getY()+")");
        }
        return point;
    }
    /**
     *    Calcola:
     *    f * g
     *    e memorizza il risultato nella funzione stessa
     *    ovvero:   f.apply(g) modifica f con risultato del prodotto
     */
    public boolean apply(Function f) {
        // Trasformo T = (tx,ty)
        Point t = new Point(this.tx,this.ty);
        t = f.apply(t);
        this.tx = t.getX(); this.ty = t.getY();
        // Aggiorno S
        if (cx!=0) this.setCx ( (this.cx * f.getCx()) / 100 );
        if (cy!=0) this.setCy ( (this.cy * f.getCy()) / 100 );
        // Aggiorno R
        this.setRot( this.rot + f.getRot() );
        return true;
    }
    /**
     *    Applica solo le matrici R ed S (Rotazione e Scale)
     *    Calcola:
     *    - tx1 = ((point.x*cx)/100
     *    - ty1 = ((point.x*cx)/100
     *    - point.x = (tx1*cos_w-ty1*sin_w)
     *    - point.y = (ty1*cos_w+ty1*sin_w)
     */
    public Point applyRS(Point point) {
        if (!this.isIdentity()) {
            int tx1 = ((point.getX()*this.cx)/100);
            int ty1 = ((point.getY()*this.cy)/100);
            point.setX( ((tx1*cos_w-ty1*sin_w)>>14)  );
            point.setY( ((tx1*sin_w+ty1*cos_w)>>14)  );
        }
        return point;
    }
    /**
     *    Applica solo le matrici R ed S (Rotazione e Scale) della funzione e T (Traslazione) custom.
     */
    public Point applyRST(Point point, Point t) {
        if (!this.isIdentity()) {
            int tx1 = ((point.getX()*this.cx)/100);
            int ty1 = ((point.getY()*this.cy)/100);
            point.setX( ((tx1*cos_w-ty1*sin_w)>>14) + t.getX() );
            point.setY( ((tx1*sin_w+ty1*cos_w)>>14) + t.getY() );
        }
        return point;
    }

    /**Clona la funzione ritornandone una NUOVA con gli stessi valori*/
    public Object clone() {
        return new Function(this.cx,this.cy,this.tx,this.ty,this.rot);
    }

    /**Is the current function the Identity function?*/
    public boolean isIdentity() {
        return (cx==100 && cy==100 && tx==0 && ty==0 && rot==0);
    }
    // Getters & Setters
    public int getBoh() { return boh; }
    public void setBoh(int newValue) { boh=newValue; }
    public int getCx() { return cx; }
    public void setCx(int newValue) {
        if (newValue>100)     newValue=100;
        if (newValue<-100)    newValue=-100;
        cx=newValue;
    }
    public int getCy() { return cy; }
    public void setCy(int newValue) {
        if (newValue>100)    newValue=100;
        if (newValue<-100)   newValue=-100;
        cy=newValue;
    }
    public int getTx() { return tx; }
    public void setTx(int newValue) { tx=newValue; }
    public int getTy() { return ty; }
    public void setTy(int newValue) { ty=newValue; }
    public int getRot() { return rot; }
    /**Setta anche sin_w e cos_w per questa funzione.*/
    public void setRot(int newValue) {
        rot=newValue;
        rot = ( rot<0   ? rot+360 : rot );
        rot = ( rot>360 ? rot%360 : rot );
        sin_w = Sinus.sin(this.rot);
        cos_w = Sinus.cos(this.rot);
    }
    public int getProb() { return prob; }
    public void setProb(int newValue) { prob=newValue; }

    String toJson() { return toJson(""); }
    String toJson(String sep) {
        String ret = "{" + sep;
        ret += "\"next\":" + boh + "," + sep;
        ret += "\"cx\":" + cx + "," + sep;
        ret += "\"cy\":" + cy + "," + sep;
        ret += "\"tx\":" + tx + "," + sep;
        ret += "\"ty\":" + ty + "," + sep;
        ret += "\"rot\":" + rot + "," + sep;
        ret += "\"prob\":" + prob + sep;
        ret += "}";
        return ret;
    }
    Function fromJson(String s)
    {
        int start = 0, end = 0;
        String tmp;

        start = s.indexOf("\"next\":");    end = s.indexOf(",");
        tmp = s.substring(start + 7, end);
        this.boh = Integer.parseInt(tmp);

        start = s.indexOf("\"cx\":",start);   end = s.indexOf(",",start); tmp = s.substring(start + 5, end);
        this.cx = Integer.parseInt(tmp);

        start = s.indexOf("\"cy\":",start); end = s.indexOf(",",start);  tmp = s.substring(start + 5, end);
        this.cy = Integer.parseInt(tmp);

        start = s.indexOf("\"tx\":",start); end = s.indexOf(",",start);  tmp = s.substring(start + 5, end);
        this.tx = Integer.parseInt(tmp);

        start = s.indexOf("\"ty\":",start); end = s.indexOf(",",start);  tmp = s.substring(start + 5, end);
        this.ty = Integer.parseInt(tmp);

        start = s.indexOf("\"rot\":",start); end = s.indexOf(",",start); tmp = s.substring(start + 6, end);
        this.rot = Integer.parseInt(tmp);
        if(this.rot>360 || this.rot<0) this.rot = (this.rot)%360;

        start = s.indexOf("\"prob\":",start);    end = s.length()-1;  tmp = s.substring(start + 7, end);
        this.prob = Integer.parseInt(tmp);

        return this;
    }
}
