package hr.fer.elektrijada.activities.events;

import hr.fer.elektrijada.MenuHandler;
import hr.fer.elektrijada.R;
import hr.fer.elektrijada.activities.BaseMenuActivity;

/**
 * Created by Ivica Brebrek
 */
public class ViewEventActivity extends BaseMenuActivity {

    @Override
    protected int getContentLayoutId() {
        return R.layout.event_view_activity;
    }

    @Override
    protected int belongingToMenuItemId() {
        return MenuHandler.EVENTS_ID;
    }


}
