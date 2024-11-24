package com.example.loms;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ProgressChart extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_chart);

        PieChart pieChart = findViewById(R.id.pieChart);

        // Sample data: Replace with dynamic data from Intent or database
        int completed = getIntent().getIntExtra("completed", 40);
        int remaining = getIntent().getIntExtra("remaining", 60);

        if (completed + remaining != 100) {
            throw new IllegalArgumentException("Progress data must sum up to 100%");
        }

        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(completed, getString(R.string.completed)));
        entries.add(new PieEntry(remaining, getString(R.string.remaining)));

        PieDataSet dataSet = new PieDataSet(entries, getString(R.string.progress));
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS); // Use Material color palette
        dataSet.setValueTextSize(14f); // Text size for pie slices

        PieData pieData = new PieData(dataSet);
        pieChart.setData(pieData);

        // Chart styling
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(58f);
        pieChart.setCenterText(getString(R.string.progress_chart_title));
        pieChart.setCenterTextSize(18f);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);

        // Legend styling
        Legend legend = pieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setTextSize(12f);

        pieChart.invalidate(); // Refresh chart
    }
}
