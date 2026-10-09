
        package com.example.unitconverter;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Spinner categorySpinner, fromSpinner, toSpinner;
    EditText valueInput;
    Button convertButton;
    TextView resultText;

    String[][] units = {
            {"Centimetres", "Metres", "Kilometres", "Inches", "Feet"},
            {"Grams", "Kilograms", "Pounds", "Ounces"},
            {"Millilitres", "Litres", "Gallons"}
    };

    // Factors convert each unit into its category's base unit.
    // Base units: metre, gram, litre.
    double[][] factors = {
            {0.01, 1.0, 1000.0, 0.0254, 0.3048},
            {1.0, 1000.0, 453.59237, 28.349523125},
            {0.001, 1.0, 3.785411784}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categorySpinner = findViewById(R.id.categorySpinner);
        fromSpinner = findViewById(R.id.fromSpinner);
        toSpinner = findViewById(R.id.toSpinner);
        valueInput = findViewById(R.id.valueInput);
        convertButton = findViewById(R.id.convertButton);
        resultText = findViewById(R.id.resultText);

        String[] categories = {"Length", "Weight", "Volume"};

        ArrayAdapter<String> categoryAdapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        categorySpinner.setOnItemSelectedListener(
                new android.widget.AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(
                            android.widget.AdapterView<?> parent,
                            View view, int position, long id) {
                        updateUnitSpinners(position);
                        resultText.setText("Your result will appear here");
                    }

                    @Override
                    public void onNothingSelected(
                            android.widget.AdapterView<?> parent) {
                    }
                });

        convertButton.setOnClickListener(v -> convertUnits());
    }

    private void updateUnitSpinners(int category) {
        ArrayAdapter<String> unitAdapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, units[category]);

        unitAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        fromSpinner.setAdapter(unitAdapter);

        ArrayAdapter<String> targetAdapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, units[category]);

        targetAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        toSpinner.setAdapter(targetAdapter);
        toSpinner.setSelection(Math.min(1, units[category].length - 1));
    }

    private void convertUnits() {
        String input = valueInput.getText().toString().trim();

        if (input.isEmpty()) {
            valueInput.setError("Please enter a value");
            Toast.makeText(this,
                    "Enter a number first", Toast.LENGTH_SHORT).show();
            return;
        }

        double value;

        try {
            value = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            Toast.makeText(this,
                    "Please enter a valid number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Double.isNaN(value) || Double.isInfinite(value)) {
            Toast.makeText(this,
                    "Please enter a valid number", Toast.LENGTH_SHORT).show();
            return;
        }

        int category = categorySpinner.getSelectedItemPosition();
        int from = fromSpinner.getSelectedItemPosition();
        int to = toSpinner.getSelectedItemPosition();

        double baseValue = value * factors[category][from];
        double result = baseValue / factors[category][to];

        String targetUnit = units[category][to];

        resultText.setText(String.format(
                Locale.getDefault(),
                "Result: %.6f %s",
                result, targetUnit));

        valueInput.setError(null);
    }
}
