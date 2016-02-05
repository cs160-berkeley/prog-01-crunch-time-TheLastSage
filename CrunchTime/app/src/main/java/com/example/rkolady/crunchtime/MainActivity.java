package com.example.rkolady.crunchtime;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Spinner chooser = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.excercises, android.R.layout.simple_spinner_dropdown_item);
        chooser.setAdapter(adapter);

        final ListView compare = (ListView) findViewById(R.id.listView);

        final HashMap<String, Integer> ref = new HashMap<String, Integer>();
        ref.put("Pushup", 350);
        ref.put("Situp", 200);
        ref.put("Squats", 225);
        ref.put("Leg-lift", 25);
        ref.put("Plank", 25);
        ref.put("Jumping Jacks", 10);
        ref.put("Pullup", 100);
        ref.put("Cycling", 12);
        ref.put("Walking", 20);
        ref.put("Jogging", 12);
        ref.put("Swimming", 13);
        ref.put("Stair-Climbing", 15);

        compare.setAdapter(updateComp(ref, 100, 150));


        Button refresh = (Button) findViewById(R.id.button);

        View.OnClickListener buttonListener = new View.OnClickListener() {
            final EditText input = (EditText) findViewById(R.id.editText);
            final EditText output = (EditText) findViewById(R.id.textView5);
            final CheckBox which = (CheckBox) findViewById(R.id.checkbox);
            final EditText scalar = (EditText) findViewById(R.id.editText2);

            @Override
            public void onClick(View v) {
                String spinTxt = chooser.getSelectedItem().toString();
                String outTxt= output.getText().toString();
                double val = ref.get(spinTxt);
                double weight = Double.parseDouble(scalar.getText().toString());

                if (which.isChecked()) {
                    double reps = Double.parseDouble(outTxt) * val / 100 * (150 / weight);
                    input.setText(Double.toString(reps));
                } else {
                    String inTxt = input.getText().toString();
                    double cal = (double) Double.parseDouble(inTxt) / val * 100 * (weight / 150);
                    output.setText(Double.toString(cal));
                }

                outTxt = output.getText().toString();
                double cal = Double.parseDouble(outTxt);

                compare.setAdapter(updateComp(ref, cal, weight));
            }
        };

        refresh.setOnClickListener(buttonListener);
    }

    private SimpleAdapter updateComp(HashMap<String, Integer> curr, double calories, double weight) {

        List<HashMap<String, String>> data = new ArrayList<>();

        for (String exer: curr.keySet()) {
            HashMap<String, String> listMap = new HashMap<String, String>();
            listMap.put("text1", exer);
            listMap.put("text2", Double.toString(((double) curr.get(exer) / 100 * calories * ((double) 150 / weight))));
            data.add(listMap);
        }

        SimpleAdapter listAdapt = new SimpleAdapter(this, data, android.R.layout.simple_list_item_2,
                new String[] {"text1", "text2"}, new int[] {android.R.id.text1, android.R.id.text2});

        return listAdapt;
    }
}
