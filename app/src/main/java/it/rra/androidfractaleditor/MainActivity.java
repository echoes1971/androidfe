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

    private String[] ifrs = {
        // a-tree
        "{\"funct\":[{\"next\":16,\"cx\":10,\"cy\":40,\"tx\":2,\"ty\":52,\"rot\":355,\"prob\":2744},{\"next\":32,\"cx\":8,\"cy\":75,\"tx\":-2,\"ty\":-16,\"rot\":5,\"prob\":2744},{\"next\":48,\"cx\":10,\"cy\":40,\"tx\":27,\"ty\":20,\"rot\":65,\"prob\":2744},{\"next\":64,\"cx\":10,\"cy\":40,\"tx\":-28,\"ty\":27,\"rot\":295,\"prob\":2744},{\"next\":80,\"cx\":10,\"cy\":40,\"tx\":26,\"ty\":-23,\"rot\":70,\"prob\":2744},{\"next\":96,\"cx\":10,\"cy\":40,\"tx\":-28,\"ty\":-45,\"rot\":290,\"prob\":2744}],\"precision\":2,\"iterations\":5,\"KaosSequence\":\"012345\",\"alg\":\"A\",\"unused\":\"\",\"numedges\":4,\"edges\":[[62,77],[62,-84],[-75,-84],[-75,78]]}"
        // fern2
        ,"{\"funct\":[{\"next\":0,\"cx\":1,\"cy\":25,\"tx\":0,\"ty\":69,\"rot\":0,\"prob\":540},{\"next\":0,\"cx\":35,\"cy\":40,\"tx\":20,\"ty\":43,\"rot\":63,\"prob\":1400},{\"next\":0,\"cx\":35,\"cy\":40,\"tx\":-20,\"ty\":43,\"rot\":300,\"prob\":1400},{\"next\":0,\"cx\":86,\"cy\":86,\"tx\":3,\"ty\":-12,\"rot\":5,\"prob\":7140}],\"precision\":2,\"iterations\":30,\"KaosSequence\":\"0123\",\"alg\":\"A\",\"unused\":\"\",\"numedges\":4,\"edges\":[[-50,90],[50,90],[50,-90],[-50,-90]]}"
        // leaf
        ,"{\"funct\":[{\"next\":16,\"cx\":50,\"cy\":60,\"tx\":-53,\"ty\":-10,\"rot\":315,\"prob\":2744},{\"next\":32,\"cx\":50,\"cy\":50,\"tx\":51,\"ty\":-10,\"rot\":45,\"prob\":2244},{\"next\":48,\"cx\":40,\"cy\":50,\"tx\":1,\"ty\":-36,\"rot\":0,\"prob\":1744},{\"next\":64,\"cx\":50,\"cy\":50,\"tx\":-16,\"ty\":30,\"rot\":180,\"prob\":2244},{\"next\":80,\"cx\":50,\"cy\":50,\"tx\":23,\"ty\":30,\"rot\":180,\"prob\":2244},{\"next\":96,\"cx\":50,\"cy\":50,\"tx\":-3,\"ty\":7,\"rot\":0,\"prob\":2244}],\"precision\":100,\"iterations\":5,\"KaosSequence\":\"012345\",\"alg\":\"B\",\"unused\":\"\",\"numedges\":9,\"edges\":[[-41,57],[45,57],[81,4],[46,-54],[21,-34],[-1,-81],[-31,-36],[-60,-52],[-85,9]]}"
        // Milkyway
        ,"{\"funct\":[{\"next\":16,\"cx\":61,\"cy\":19,\"tx\":61,\"ty\":24,\"rot\":77,\"prob\":903},{\"next\":16,\"cx\":61,\"cy\":19,\"tx\":-57,\"ty\":-10,\"rot\":296,\"prob\":903},{\"next\":16,\"cx\":79,\"cy\":65,\"tx\":0,\"ty\":7,\"rot\":315,\"prob\":5135},{\"next\":16,\"cx\":65,\"cy\":23,\"tx\":0,\"ty\":-14,\"rot\":315,\"prob\":1239},{\"next\":16,\"cx\":72,\"cy\":19,\"tx\":0,\"ty\":38,\"rot\":334,\"prob\":1368},{\"next\":16,\"cx\":26,\"cy\":23,\"tx\":5,\"ty\":10,\"rot\":291,\"prob\":598}],\"precision\":100,\"iterations\":7,\"KaosSequence\":\"01234\",\"alg\":\"B\",\"unused\":\"\",\"numedges\":13,\"edges\":[[-57,12],[-26,38],[27,39],[54,16],[52,46],[68,30],[64,-14],[18,-30],[-40,-22],[-27,-41],[-10,-49],[-55,-44],[-65,-11]]}"
        // Mountains
        ,"{\"funct\":[{\"next\":16,\"cx\":50,\"cy\":75,\"tx\":-49,\"ty\":24,\"rot\":0,\"prob\":3494},{\"next\":32,\"cx\":100,\"cy\":50,\"tx\":0,\"ty\":48,\"rot\":0,\"prob\":4744},{\"next\":48,\"cx\":50,\"cy\":50,\"tx\":49,\"ty\":12,\"rot\":0,\"prob\":2244},{\"next\":64,\"cx\":60,\"cy\":50,\"tx\":-40,\"ty\":7,\"rot\":0,\"prob\":2744},{\"next\":80,\"cx\":50,\"cy\":35,\"tx\":-4,\"ty\":29,\"rot\":315,\"prob\":1494},{\"next\":96,\"cx\":30,\"cy\":50,\"tx\":62,\"ty\":4,\"rot\":20,\"prob\":1244}],\"precision\":100,\"iterations\":6,\"KaosSequence\":\"012345   \",\"alg\":\"B\",\"unused\":\"\",\"numedges\":11,\"edges\":[[-97,99],[99,99],[99,23],[58,18],[37,0],[8,16],[-32,10],[-59,16],[-67,22],[-74,17],[-97,14]]}"
        // new-tree
        ,"{\"funct\":[{\"next\":16,\"cx\":10,\"cy\":40,\"tx\":-2,\"ty\":48,\"rot\":185,\"prob\":2744},{\"next\":32,\"cx\":8,\"cy\":45,\"tx\":1,\"ty\":36,\"rot\":5,\"prob\":2744},{\"next\":48,\"cx\":30,\"cy\":60,\"tx\":26,\"ty\":-4,\"rot\":31,\"prob\":1800},{\"next\":64,\"cx\":30,\"cy\":55,\"tx\":-31,\"ty\":6,\"rot\":302,\"prob\":1650},{\"next\":80,\"cx\":40,\"cy\":55,\"tx\":-31,\"ty\":-31,\"rot\":315,\"prob\":1944},{\"next\":96,\"cx\":55,\"cy\":60,\"tx\":13,\"ty\":-38,\"rot\":10,\"prob\":3044},{\"next\":112,\"cx\":50,\"cy\":50,\"tx\":0,\"ty\":21,\"rot\":0,\"prob\":2244}],\"precision\":2,\"iterations\":20,\"KaosSequence\":\"0123456\",\"alg\":\"A\",\"unused\":\"\",\"numedges\":10,\"edges\":[[-10,81],[5,77],[6,35],[71,-27],[71,-27],[33,-87],[-69,-70],[-78,8],[-12,38],[-11,81]]}"
        // rapa-nui
        ,"{\"funct\":[{\"next\":16,\"cx\":82,\"cy\":75,\"tx\":-5,\"ty\":-14,\"rot\":0,\"prob\":6150},{\"next\":16,\"cx\":47,\"cy\":47,\"tx\":38,\"ty\":-24,\"rot\":19,\"prob\":1953},{\"next\":16,\"cx\":26,\"cy\":51,\"tx\":-52,\"ty\":43,\"rot\":340,\"prob\":1326},{\"next\":16,\"cx\":58,\"cy\":58,\"tx\":-19,\"ty\":-19,\"rot\":0,\"prob\":3364},{\"next\":16,\"cx\":58,\"cy\":58,\"tx\":-10,\"ty\":0,\"rot\":0,\"prob\":3364}],\"precision\":1,\"iterations\":22,\"KaosSequence\":\"01234\",\"alg\":\"A\",\"unused\":\"\",\"numedges\":10,\"edges\":[[-76,53],[-43,-66],[78,-51],[71,-21],[39,-23],[18,-8],[-19,-1],[-40,29],[-43,65],[-71,79]]}"
        // realtree
        ,"{\"funct\":[{\"next\":16,\"cx\":10,\"cy\":45,\"tx\":-1,\"ty\":47,\"rot\":185,\"prob\":2176},{\"next\":32,\"cx\":12,\"cy\":45,\"tx\":1,\"ty\":36,\"rot\":5,\"prob\":1892},{\"next\":48,\"cx\":50,\"cy\":60,\"tx\":38,\"ty\":0,\"rot\":45,\"prob\":2744},{\"next\":64,\"cx\":40,\"cy\":55,\"tx\":-38,\"ty\":15,\"rot\":305,\"prob\":1944},{\"next\":80,\"cx\":40,\"cy\":55,\"tx\":-28,\"ty\":-29,\"rot\":315,\"prob\":1944},{\"next\":96,\"cx\":55,\"cy\":60,\"tx\":14,\"ty\":-35,\"rot\":10,\"prob\":3044}],\"precision\":2,\"iterations\":20,\"KaosSequence\":\"012345   \",\"alg\":\"A\",\"unused\":\"\",\"numedges\":10,\"edges\":[[-11,81],[5,77],[6,35],[71,-27],[71,-27],[33,-87],[-69,-70],[-78,8],[-12,38],[-11,81]]}"
        // tree
        ,"{\"funct\":[{\"next\":16,\"cx\":60,\"cy\":65,\"tx\":-41,\"ty\":7,\"rot\":315,\"prob\":3900},{\"next\":32,\"cx\":12,\"cy\":60,\"tx\":-3,\"ty\":34,\"rot\":180,\"prob\":464},{\"next\":48,\"cx\":11,\"cy\":65,\"tx\":0,\"ty\":29,\"rot\":5,\"prob\":459},{\"next\":64,\"cx\":50,\"cy\":75,\"tx\":36,\"ty\":5,\"rot\":40,\"prob\":3494},{\"next\":80,\"cx\":50,\"cy\":55,\"tx\":-30,\"ty\":-38,\"rot\":320,\"prob\":2494},{\"next\":96,\"cx\":50,\"cy\":55,\"tx\":9,\"ty\":-47,\"rot\":8,\"prob\":2494}],\"precision\":2,\"iterations\":20,\"KaosSequence\":\"120345   \",\"alg\":\"A\",\"unused\":\"\",\"numedges\":11,\"edges\":[[-10,79],[4,81],[7,48],[76,-3],[49,-86],[49,-86],[-78,-65],[-84,-17],[-12,48],[-11,78],[-11,78]]}"
        // tree2
        ,"{\"funct\":[{\"next\":16,\"cx\":60,\"cy\":65,\"tx\":-41,\"ty\":7,\"rot\":315,\"prob\":3900},{\"next\":32,\"cx\":12,\"cy\":60,\"tx\":-3,\"ty\":34,\"rot\":180,\"prob\":464},{\"next\":48,\"cx\":11,\"cy\":65,\"tx\":0,\"ty\":29,\"rot\":5,\"prob\":459},{\"next\":64,\"cx\":50,\"cy\":75,\"tx\":36,\"ty\":5,\"rot\":40,\"prob\":3494},{\"next\":80,\"cx\":50,\"cy\":55,\"tx\":-30,\"ty\":-38,\"rot\":320,\"prob\":2494},{\"next\":96,\"cx\":50,\"cy\":55,\"tx\":9,\"ty\":-47,\"rot\":8,\"prob\":2494}],\"precision\":2,\"iterations\":20,\"KaosSequence\":\"120345\",\"alg\":\"A\",\"unused\":\"\",\"numedges\":11,\"edges\":[[90,179],[104,181],[107,148],[176,97],[149,14],[149,14],[22,35],[16,83],[88,148],[89,178],[89,178]]}"
        // triangle
        ,"{\"funct\":[{\"next\":16,\"cx\":50,\"cy\":50,\"tx\":-50,\"ty\":50,\"rot\":0,\"prob\":7500},{\"next\":32,\"cx\":50,\"cy\":50,\"tx\":50,\"ty\":50,\"rot\":0,\"prob\":7500},{\"next\":48,\"cx\":50,\"cy\":50,\"tx\":-50,\"ty\":-50,\"rot\":0,\"prob\":7500}],\"precision\":2,\"iterations\":6,\"KaosSequence\":\"012\",\"alg\":\"A\",\"unused\":\"\",\"numedges\":3,\"edges\":[[100,100],[-100,100],[-100,-100]]}"
    };
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());
    }

    private int myrand(int min, int max) { return (int)(Math.random() * ((max - min) + 1)) + min; }

    public void sendMessage(View view) {
        // Do something in response to button
        tv.setText("Sta cippa");

        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra(EXTRA_IFR_JSON, ifrs[myrand(0,ifrs.length)]);
        startActivity(intent);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
