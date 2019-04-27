package it.rra.fe;

public class RenderOpt {
    public int zoomx,zoomy;
    public int tx,ty;
    public int d;
    public int sg;
    public int fh;

    public RenderOpt(int zx,int zy, int ttx, int tty, int dd, int ssg, int ffh)
    {
        zoomx = zx; zoomy = zy; tx = ttx; ty = tty; d = dd;
        sg = ssg;   fh = ffh;
    }
}
