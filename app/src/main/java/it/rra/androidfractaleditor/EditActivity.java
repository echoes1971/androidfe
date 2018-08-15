package it.rra.androidfractaleditor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import it.rra.fe.engine.Function;
import it.rra.fe.engine.IFR;
import it.rra.fe.engine.Point;

public class EditActivity extends AppCompatActivity {
    private static int BITMAP_WIDTH=500;
    private static int BITMAP_HEIGHT=500;

    private ImageView mImageView;
    private TextView textFunction;
    private TextView textSizeX, textSizeY, textTX, textTY, textRot;
    private IFR ifr;
    private int current_function;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String ifr_string = intent.getStringExtra(MainActivity.EXTRA_IFR_JSON);

        ifr = new IFR();
        ifr.fromJson(ifr_string);

        textFunction = findViewById(R.id.textFunction);
        textSizeX = findViewById(R.id.textSizeX);
        textSizeY = findViewById(R.id.textSizeY);
        textTX = findViewById(R.id.textTX);
        textTY = findViewById(R.id.textTY);
        textRot = findViewById(R.id.textRot);

        //DAQUI: https://android--code.blogspot.com/2015/11/android-how-to-draw-line-on-canvas.html
        mImageView = (ImageView) findViewById(R.id.imageView);

        current_function = 0;
        draw_ifr();
    }

    private void draw_ifr() {
        Bitmap bitmap = Bitmap.createBitmap(
                BITMAP_WIDTH, // Width
                BITMAP_HEIGHT, // Height
                Bitmap.Config.ARGB_8888 // Config
        );
        Canvas canvas = new Canvas(bitmap);
        // Draw a solid color on the canvas as background
        canvas.drawColor(Color.LTGRAY);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        paint.setAntiAlias(true);

        // Main shape
        Point[] pnts = ifr.getPoints();
        this.paintPolygon(canvas, paint, pnts);

        // First iteration
        paint.setStrokeWidth(1);
        paint.setColor(Color.BLUE);
        Point[] pnts2 = new Point[ifr.getNumEdges()];
        for(int j=0; j<ifr.getKaosSequence().getLength(); j++) {
            pnts = ifr.getPoints();
            Function f = ifr.getFunction(j);
            for(int i=0; i<ifr.getNumEdges(); i++) {
                pnts2[i] = f.applyRST(pnts[i]);
            }
            this.paintPolygon(canvas, paint, pnts2);
        }

        // Second iteration
        pnts = ifr.getPoints();
        paint.setColor(Color.GREEN);
        this.paintPolygon(canvas, paint, pnts);
        // f(g)
        Function f = ifr.getFunction(current_function);
        for(int j=0; j<ifr.getKaosSequence().getLength(); j++) {
            pnts = ifr.getPoints();
            Function g = ifr.getFunction(j);
            Function fg = f.apply(g);
            for(int i=0; i<ifr.getNumEdges(); i++) {
                pnts2[i] = fg.applyRST(pnts[i]);
            }
            this.paintPolygon(canvas, paint, pnts2);
        }

        // Display the newly created bitmap on app interface
        mImageView.setImageBitmap(bitmap);

        textFunction.setText(""+current_function);
        textSizeX.setText("Size X: "+ifr.getFunction(current_function).getCx());
        textSizeY.setText("Size Y: "+ifr.getFunction(current_function).getCy());
        textTX.setText("Move X: "+ifr.getFunction(current_function).getTx());
        textTY.setText("Move Y: "+ifr.getFunction(current_function).getTy());
        textRot.setText("Angle: "+ifr.getFunction(current_function).getRot());
    }
    public void paintPolygon(Canvas canvas, Paint paint, Point[] pnts) {

        //Point center = new Point(BITMAP_WIDTH/2,BITMAP_HEIGHT/2);
        Function f = new Function(BITMAP_WIDTH/2,BITMAP_HEIGHT/2,BITMAP_WIDTH/2,BITMAP_HEIGHT/2,0);
        //Function f = new Function(200,-200,BITMAP_WIDTH/2,BITMAP_HEIGHT/2,0);
        for(int i=0; i<pnts.length; i++) {
            pnts[i] = f.apply(pnts[i]);
        }

        // Draw polygon
        for(int i=0; i<pnts.length; i++) {
            Point p1 = pnts[i];
            Point p2 = pnts[(i+1)<pnts.length ? i+1 : 0];
            canvas.drawLine(
                    p1.getX(), // startX
                    p1.getY(), // startY
                    p2.getX(), // stopX
                    p2.getY(), // stopY
                    paint // Paint
            );
        }

        // Draw points
        for(int i=0; i<pnts.length; i++) {
            pnts[i] = f.apply(pnts[i]);
            canvas.drawLine(
                    pnts[i].getX() - 1, // startX
                    pnts[i].getY() - 1, // startY
                    pnts[i].getX() + 1, // stopX
                    pnts[i].getY() + 1, // stopY
                    paint // Paint
            );
        }
    }


    public void btn_prev(View view) {
        if(current_function==0) {
            current_function = ifr.getKaosSequence().getLength() - 1;
        } else {
            current_function -= 1;
        }
        draw_ifr();
    }
    public void btn_next(View view) {
        if(current_function==(ifr.getKaosSequence().getLength() - 1)) {
            current_function = 0;
        } else {
            current_function += 1;
        }
        draw_ifr();
    }
    public void btn_sizex_less(View view) {
        int cx = ifr.getFunction(current_function).getCx();
        if(cx<=-100)
            return;
        ifr.getFunction(current_function).setCx(cx-1);
        draw_ifr();
    }
    public void btn_sizex_more(View view) {
        int cx = ifr.getFunction(current_function).getCx();
        if(cx>=100)
            return;
        ifr.getFunction(current_function).setCx(cx+1);
        draw_ifr();
    }
    public void btn_sizey_less(View view) {
        int cy = ifr.getFunction(current_function).getCy();
        if(cy<=-100)
            return;
        ifr.getFunction(current_function).setCy(cy-1);
        draw_ifr();
    }
    public void btn_sizey_more(View view) {
        int cy = ifr.getFunction(current_function).getCy();
        if(cy>=100)
            return;
        ifr.getFunction(current_function).setCy(cy+1);
        draw_ifr();
    }
    public void btn_tx_less(View view) {
        int x = ifr.getFunction(current_function).getTx();
        if(x<=-100)
            return;
        ifr.getFunction(current_function).setTx(x-1);
        draw_ifr();
    }
    public void btn_tx_more(View view) {
        int x = ifr.getFunction(current_function).getTx();
        if(x>=100)
            return;
        ifr.getFunction(current_function).setTx(x+1);
        draw_ifr();
    }
    public void btn_ty_less(View view) {
        int x = ifr.getFunction(current_function).getTy();
        if(x<=-100)
            return;
        ifr.getFunction(current_function).setTy(x-1);
        draw_ifr();
    }
    public void btn_ty_more(View view) {
        int x = ifr.getFunction(current_function).getTy();
        if(x>=100)
            return;
        ifr.getFunction(current_function).setTy(x+1);
        draw_ifr();
    }
    public void btn_rot_less(View view) {
        int x = ifr.getFunction(current_function).getRot();
        if(x<=0)
            x=360;
        ifr.getFunction(current_function).setRot(x-1);
        draw_ifr();
    }
    public void btn_rot_more(View view) {
        int x = ifr.getFunction(current_function).getRot();
        if(x>=360)
            x=-1;
        ifr.getFunction(current_function).setRot(x+1);
        draw_ifr();
    }

    private int myrand(int min, int max) { return (int)(Math.random() * (max - min)) + min; }
}
