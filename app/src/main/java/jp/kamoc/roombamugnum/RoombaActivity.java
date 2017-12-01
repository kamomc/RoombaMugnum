package jp.kamoc.roombamugnum;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

import jp.kamoc.roonroom.lib.constants.RRL;
import jp.kamoc.roonroom.lib.controller.Controller;
import jp.kamoc.roonroom.lib.midi.MidiPlayer;
import jp.kamoc.roonroom.lib.midi.MidiUtil;
import jp.kamoc.roonroom.lib.midi.meta.Midi;
import jp.kamoc.roonroom.lib.operation.LEDConfig;
import jp.kamoc.roonroom.lib.operation.SchedulingLEDConfig;

public class RoombaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roomba);

        TextView textView = findViewById(R.id.textView);

        Controller controller = ((RoombaApplication) getApplication()).getController();

        String command = getIntent().getStringExtra("command");
        switch (command) {
            case "mugnum":  // マグナムトルネード
                controller.changeMode(RRL.OPERATIONG_MODE.SAFE);
                controller.driveDirect(500, -500);
                textView.setText("🍥");
                break;
            case "go":  // 前進
                controller.changeMode(RRL.OPERATIONG_MODE.SAFE);
                controller.driveDirect(250, 250);
                textView.setText("👆");
                break;
            case "turbo": // 全速前進
                controller.changeMode(RRL.OPERATIONG_MODE.SAFE);
                controller.driveDirect(500, 500);
                controller.motorsPWM(127, 127, 127);
                textView.setText("😆");
                break;
            case "left": // 左折
                controller.changeMode(RRL.OPERATIONG_MODE.SAFE);
                controller.driveDirect(350, 150);
                textView.setText("👈");
                break;
            case "right": // 右折
                controller.changeMode(RRL.OPERATIONG_MODE.SAFE);
                controller.driveDirect(150, 350);
                textView.setText("👉");
                break;
            case "back": // 後退
                controller.changeMode(RRL.OPERATIONG_MODE.SAFE);
                controller.driveDirect(-200, -200);
                textView.setText("👇");
                break;
            case "wait": // 停止
                controller.changeMode(RRL.OPERATIONG_MODE.SAFE);
                controller.driveDirect(0, 0);
                controller.motorsPWM(0, 0, 0);
                textView.setText("🆗");
                break;
            case "clean": // 掃除
                controller.changeMode(RRL.OPERATIONG_MODE.SAFE);
                controller.clean();
                textView.setText("✨");
                break;
            case "sing": // ジャパリパークへようこそ！
                try {
                    controller.changeMode(RRL.OPERATIONG_MODE.SAFE);
                    InputStream is = getResources().getAssets().open("kemono.mid");
                    final Midi midi = MidiUtil.getMidi(is);
                    final MidiPlayer player = new MidiPlayer(controller);
                    player.setInterrupt(true);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            player.play(midi, 0);
                        }
                    }, 500);
                } catch (IOException e) {
                    textView.setText("Error");
                    e.printStackTrace();
                }
                textView.setText("🐯");
                break;
            default:
                controller.changeMode(RRL.OPERATIONG_MODE.SAFE);
                textView.setText(command);
                break;
        }

        // LED をすべて点灯する
        LEDConfig led = new LEDConfig();
        led.allLighting();
        SchedulingLEDConfig scheduleLED = new SchedulingLEDConfig();
        scheduleLED.allLighting();
        controller.schedulingLed(scheduleLED);
        controller.led(led);
    }
}
