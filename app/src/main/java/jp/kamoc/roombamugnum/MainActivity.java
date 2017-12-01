package jp.kamoc.roombamugnum;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.InputStream;

import jp.kamoc.roonroom.lib.midi.MidiUtil;
import jp.kamoc.roonroom.lib.midi.meta.Midi;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Button buttion = findViewById(R.id.button2);

        ((RoombaApplication) getApplication()).createController(this);

        buttion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RoombaActivity.class);
                startActivity(intent);
            }
        });

    }
}
