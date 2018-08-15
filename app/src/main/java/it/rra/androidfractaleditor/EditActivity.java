package it.rra.androidfractaleditor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import it.rra.fe.engine.Function;
import it.rra.fe.engine.IFR;
import it.rra.fe.engine.Point;

public class EditActivity extends AppCompatActivity {
    private static int BITMAP_WIDTH=500;
    private static int BITMAP_HEIGHT=500;

    private ImageView mImageView;
    private IFR ifr;

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

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView2);
        textView.setText(ifr_string);

        //DAQUI: https://android--code.blogspot.com/2015/11/android-how-to-draw-line-on-canvas.html
        mImageView = (ImageView) findViewById(R.id.imageView);

//        String mysize = mImageView.getWidth() + "x" + mImageView.getHeight();
//        textView.setText(textView.getText() + "\n" + mysize);

        Bitmap bitmap = Bitmap.createBitmap(
                BITMAP_WIDTH, // Width
                BITMAP_HEIGHT, // Height
                Bitmap.Config.ARGB_8888 // Config
        );
        // Initialize a new Canvas instance
        Canvas canvas = new Canvas(bitmap);
        // Draw a solid color on the canvas as background
        canvas.drawColor(Color.LTGRAY);
        // Initialize a new Paint instance to draw the line
        Paint paint = new Paint();
        // Line color
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        // Line width in pixels
        paint.setStrokeWidth(4);
        paint.setAntiAlias(true);


        Point[] pnts = ifr.getPoints();
        this.paintPolygon(canvas, paint, pnts);

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


        pnts = ifr.getPoints();
        paint.setColor(Color.GREEN);
        this.paintPolygon(canvas, paint, pnts);
        // f(g)
        Function f = ifr.getFunction(myrand(0,ifr.getKaosSequence().getLength()));
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

    private int myrand(int min, int max) { return (int)(Math.random() * (max - min)) + min; }
}
