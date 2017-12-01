package jp.kamoc.roombamugnum;

import android.app.Activity;
import android.app.Application;

import jp.kamoc.roonroom.lib.constants.RRL;
import jp.kamoc.roonroom.lib.controller.Controller;
import jp.kamoc.roonroom.lib.serial.AndroidSerialAdapter;

/**
 * Created by kamo on 2017/11/28.
 */

public class RoombaApplication extends Application {
    private Controller mController;

    public void createController(Activity activity) {
        AndroidSerialAdapter adapter = new AndroidSerialAdapter(activity);
        mController = new Controller(adapter);
        mController.start();
    }

    public Controller getController() {
        return mController;
    }
}
