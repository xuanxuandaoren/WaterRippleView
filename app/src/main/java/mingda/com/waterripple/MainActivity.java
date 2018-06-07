package mingda.com.waterripple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import mingda.com.waterripple.view.WaterRippleView;

public class MainActivity extends AppCompatActivity {

    private WaterRippleView wrv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        wrv = (WaterRippleView) findViewById(R.id.wrv);
        wrv.startMoving();
    }
}
