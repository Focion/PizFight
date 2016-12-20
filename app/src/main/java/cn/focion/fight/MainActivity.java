package cn.focion.fight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FightView fightView = (FightView) findViewById(R.id.fight_view);
        fightView.setLevels(10, 2, 1, 9, 4);
    }
}
