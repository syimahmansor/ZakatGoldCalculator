package com.example.zakatcalculator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CalculatorActivity extends AppCompatActivity {

    Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(R.string.app_name);

        EditText weightInput = findViewById(R.id.weightInput);
        EditText goldValueInput = findViewById(R.id.goldValueInput);
        RadioGroup goldTypeGroup = findViewById(R.id.goldTypeGroup);
        Button calculateButton = findViewById(R.id.calculateButton);
        TextView outputView = findViewById(R.id.outputView);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (weightInput.getText().toString().isEmpty() || goldValueInput.getText().toString().isEmpty()) {
                        outputView.setText("Please fill in all the fields.");
                        outputView.setVisibility(View.VISIBLE);
                        return;
                    }

                    double weight = Double.parseDouble(weightInput.getText().toString());
                    double goldValue = Double.parseDouble(goldValueInput.getText().toString());

                    int selectedType = goldTypeGroup.getCheckedRadioButtonId();
                    if (selectedType == -1) {
                        outputView.setText("Please select the gold type.");
                        outputView.setVisibility(View.VISIBLE);
                        return;
                    }

                    double uruf = selectedType == R.id.keepGold ? 85 : 200;

                    double zakatPayableValue = Math.max(0, weight - uruf) * goldValue;
                    double totalZakat = zakatPayableValue * 0.025;

                    String result = "Zakat Payable Value: RM " + zakatPayableValue +
                            "\nTotal Zakat: RM " + totalZakat;
                    outputView.setText(result);
                    outputView.setVisibility(View.VISIBLE);

                } catch (NumberFormatException e) {
                    outputView.setText("Invalid input. Please enter valid numbers.");
                    outputView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId(); // Get the selected menu item's ID

        if (id == R.id.item_share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Please use my application - https://github.com/syimahmansor/ZakatGoldCalculator");
            startActivity(Intent.createChooser(shareIntent, null));
            return true;

        } else if (id == R.id.item_about) {
            Intent aboutIntent = new Intent(this, AboutActivity.class);
            startActivity(aboutIntent);
            return true;

        } else if (id == R.id.item_instruction) {
            Intent instructionIntent = new Intent(this, InstructionActivity.class);
            startActivity(instructionIntent);
            return true;
        }

        return super.onOptionsItemSelected(item); // Let the parent class handle other menu items
    }
}
