package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.calculator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    double firstNumber = 0;
    String operation = "";
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Setup click listeners for number buttons
        setupNumberButtons();

        // Setup click listeners for operation buttons
        setupOperationButtons();

        // Setup click listeners for other buttons
        binding.ac.setOnClickListener(v -> clearScreen());
        binding.delete.setOnClickListener(v -> deleteDigit());
        binding.point.setOnClickListener(v -> addDecimalPoint());
        binding.equal.setOnClickListener(v -> calculateResult());
        binding.off.setOnClickListener(v -> binding.screen.setVisibility(View.GONE));
        binding.on.setOnClickListener(v -> {
            binding.screen.setVisibility(View.VISIBLE);
            binding.screen.setText("0");
        });
    }

    private void setupNumberButtons() {
        for (int i = 0; i <= 9; i++) {
            int resId = getResources().getIdentifier("number" + i, "id", getPackageName());
            Button button = findViewById(resId);
            int finalI = i;
            button.setOnClickListener(v -> {
                String currentText = binding.screen.getText().toString();
                if (currentText.equals("0")) {
                    binding.screen.setText(String.valueOf(finalI));
                } else {
                    binding.screen.append(String.valueOf(finalI));
                }
            });
        }
    }

    private void setupOperationButtons() {
        binding.div.setOnClickListener(v -> setOperation("/"));
        binding.times.setOnClickListener(v -> setOperation("X"));
        binding.plus.setOnClickListener(v -> setOperation("+"));
        binding.min.setOnClickListener(v -> setOperation("-"));
    }

    private void setOperation(String op) {
        if (!operation.isEmpty()) {
            calculateResult(); // Calculate if there's already an operation in progress
        }
        operation = op;
        firstNumber = Double.parseDouble(binding.screen.getText().toString());
        binding.screen.setText("0");
    }

    private void clearScreen() {
        firstNumber = 0;
        operation = "";
        binding.screen.setText("0");
    }

    private void deleteDigit() {
        String number = binding.screen.getText().toString();
        if (number.length() > 1) {
            binding.screen.setText(number.substring(0, number.length() - 1));
        } else {
            binding.screen.setText("0");
        }
    }

    private void addDecimalPoint() {
        String currentText = binding.screen.getText().toString();
        if (!currentText.contains(".")) {
            binding.screen.append(".");
        }
    }

    private void calculateResult() {
        if (operation.isEmpty()) {
            return; // No operation selected
        }
        double secondNumber = Double.parseDouble(binding.screen.getText().toString());
        double result = 0;
        switch (operation) {
            case "/":
                result = firstNumber / secondNumber;
                break;
            case "X":
                result = firstNumber * secondNumber;
                break;
            case "+":
                result = firstNumber + secondNumber;
                break;
            case "-":
                result = firstNumber - secondNumber;
                break;
        }
        binding.screen.setText(String.valueOf(result));
        firstNumber = result;
        operation = ""; // Reset operation
    }
}
