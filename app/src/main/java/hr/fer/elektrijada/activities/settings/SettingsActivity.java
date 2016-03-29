package hr.fer.elektrijada.activities.settings;

import android.os.Bundle;

import hr.fer.elektrijada.MenuHandler;
import hr.fer.elektrijada.R;
import hr.fer.elektrijada.activities.BaseMenuActivity;

/**
 * Created by b on 17.11.2015..
 */
public class SettingsActivity extends BaseMenuActivity {
    @Override
    protected int getContentLayoutId(){
        return R.layout.content_settings_activity;
    }

    @Override
    protected int belongingToMenuItemId() {
        return MenuHandler.SETTINGS_ID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}
