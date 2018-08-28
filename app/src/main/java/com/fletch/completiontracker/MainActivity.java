package com.fletch.completiontracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView m_numbers;
    private TextView m_percentage;
    private int m_completions = 0;
    private int m_incompletions = 0;

    private static final String COMPLETIONS_KEY = "completions";
    private static final String INCOMPLETIONS_KEY = "incompletions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_numbers = findViewById(R.id.completions);
        m_percentage = findViewById(R.id.completion_percentage);

        if(savedInstanceState != null) {
            m_completions = savedInstanceState.getInt(COMPLETIONS_KEY);
            m_incompletions = savedInstanceState.getInt(INCOMPLETIONS_KEY);
            updateNumbers();
            updateCompletionPercentage();
        }

        Button completeButton = findViewById(R.id.complete);
        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_completions++;
                updateNumbers();
                updateCompletionPercentage();
            }
        });

        Button incompleteButton = findViewById(R.id.incomplete);
        incompleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_incompletions++;
                updateNumbers();
                updateCompletionPercentage();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateNumbers();
        updateCompletionPercentage();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(COMPLETIONS_KEY, m_completions);
        outState.putInt(INCOMPLETIONS_KEY, m_incompletions);

        super.onSaveInstanceState(outState);
    }

    private void updateNumbers() {
        int totalPasses = m_completions + m_incompletions;
        m_numbers.setText(getString(R.string.completions, m_completions, totalPasses));
    }

    private void updateCompletionPercentage() {
        int totalPasses = m_completions + m_incompletions;

        if(totalPasses == 0) {
            m_percentage.setText(getString(R.string.completion_percentage, "0.00"));
            m_percentage.setTextColor(getColor(R.color.colorPrimary));
        } else {
            float percentage = ((float) m_completions / (float) totalPasses) * 100f;
            String stringPercentage = String.format(Locale.getDefault(), "%.2f", percentage);
            m_percentage.setText(getString(R.string.completion_percentage, stringPercentage));

            if(percentage < 60.0f) {
                m_percentage.setTextColor(getColor(R.color.colorPrimary));
            } else {
                m_percentage.setTextColor(getColor(R.color.colorAccent));
            }
        }
    }
}
