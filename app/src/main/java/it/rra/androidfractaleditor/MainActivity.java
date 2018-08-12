package it.rra.androidfractaleditor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    public static final String EXTRA_IFR_JSON = "it.rra.androidfractaleditor.IFR_JSON";
    private String ifr_json = "{\"funct\":[{\"next\":16,\"cx\":50,\"cy\":50,\"tx\":-50,\"ty\":50,\"rot\":0,\"prob\":7500},{\"next\":32,\"cx\":50,\"cy\":50,\"tx\":50,\"ty\":50,\"rot\":0,\"prob\":7500},{\"next\":48,\"cx\":50,\"cy\":50,\"tx\":-50,\"ty\":-50,\"rot\":0,\"prob\":7500}],\"precision\":2,\"iterations\":6,\"KaosSequence\":\"012\",\"alg\":\"A\",\"unused\":\"\",\"numedges\":3,\"edges\":[[100,100],[-100,100],[-100,-100]]}";

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());
    }

    public void sendMessage(View view) {
        // Do something in response to button
        tv.setText("Sta cippa");

        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra(EXTRA_IFR_JSON, ifr_json);
        startActivity(intent);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
