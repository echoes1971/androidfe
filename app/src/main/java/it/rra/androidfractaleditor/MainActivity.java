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
    //private String ifr_json = "{\"funct\":[{\"next\":16,\"cx\":50,\"cy\":50,\"tx\":-50,\"ty\":50,\"rot\":0,\"prob\":7500},{\"next\":32,\"cx\":50,\"cy\":50,\"tx\":50,\"ty\":50,\"rot\":0,\"prob\":7500},{\"next\":48,\"cx\":50,\"cy\":50,\"tx\":-50,\"ty\":-50,\"rot\":0,\"prob\":7500}],\"precision\":2,\"iterations\":6,\"KaosSequence\":\"012\",\"alg\":\"A\",\"unused\":\"\",\"numedges\":3,\"edges\":[[100,100],[-100,100],[-100,-100]]}";
    private String ifr_json = "{\"funct\":[{\"next\":16,\"cx\":60,\"cy\":65,\"tx\":-41,\"ty\":7,\"rot\":315,\"prob\":3900},{\"next\":32,\"cx\":12,\"cy\":60,\"tx\":-3,\"ty\":34,\"rot\":180,\"prob\":464},{\"next\":48,\"cx\":11,\"cy\":65,\"tx\":0,\"ty\":29,\"rot\":5,\"prob\":459},{\"next\":64,\"cx\":50,\"cy\":75,\"tx\":36,\"ty\":5,\"rot\":40,\"prob\":3494},{\"next\":80,\"cx\":50,\"cy\":55,\"tx\":-30,\"ty\":-38,\"rot\":320,\"prob\":2494},{\"next\":96,\"cx\":50,\"cy\":55,\"tx\":9,\"ty\":-47,\"rot\":8,\"prob\":2494}],\"precision\":2,\"iterations\":20,\"KaosSequence\":\"120345   \",\"alg\":\"A\",\"unused\":\"\",\"numedges\":11,\"edges\":[[-10,79],[4,81],[7,48],[76,-3],[49,-86],[49,-86],[-78,-65],[-84,-17],[-12,48],[-11,78],[-11,78]]}";

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
