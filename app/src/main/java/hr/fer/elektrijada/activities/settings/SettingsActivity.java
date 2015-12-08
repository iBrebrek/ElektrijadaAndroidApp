package hr.fer.elektrijada.activities.settings;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import hr.fer.elektrijada.R;
import hr.fer.elektrijada.activities.BaseMenuActivity;
import hr.fer.elektrijada.util.DatePicker;
import hr.fer.elektrijada.util.TimePicker;

/**
 * Created by b on 17.11.2015..
 */
public class SettingsActivity extends BaseMenuActivity {
    @Override
    protected int getContentLayoutId(){
        return R.layout.content_settings_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        //ovo obrisite kada cete raditi Settings, samo sam nesto testirao
        TextView date = (TextView) findViewById(R.id.testDateTextView);
        new DatePicker(getFragmentManager(), date);
        Button time = (Button) findViewById(R.id.testTimeButton);
        new TimePicker(getFragmentManager(), time);
    }
}
