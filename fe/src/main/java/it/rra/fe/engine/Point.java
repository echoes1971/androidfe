package it.rra.fe.engine;

public class Point {

    /** Holds value of property x. */
    private int x;

    /** Holds value of property y. */
    private int y;

    /** Creates new Point */
    public Point() {
    }
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Getters & Setters

    public int getX() {        return x;    }
    public void setX(int x) {  this.x = x;  }

    public int getY() {        return y;    }
    public void setY(int y) {  this.y = y;  }

    String toJson() {
        return "[" + this.x + "," + this.y + "]";
    }
    public Point fromJson(String s) {
        int start = s.indexOf("[");
        int middle = s.indexOf(",");
        int end = s.indexOf("]");
        this.x = Integer.parseInt(s.substring(start+1,middle));//,middle-start-1);
        this.y = Integer.parseInt(s.substring(middle+1,end));//,-middle-1);
        return this;
    }
}
