package chart;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.ThermometerPlot;
import org.jfree.data.general.DefaultValueDataset;

import javax.swing.*;
import java.awt.*;

public class PanelController extends JPanel {
    private static final int W = 200;
    private static final int H = 2 * W;

    public PanelController(double value) {
        this.setLayout(new GridLayout());
        DefaultValueDataset dataset = new DefaultValueDataset(value);
        ThermometerPlot plot = new ThermometerPlot(dataset);
        plot.setSubrangePaint(0, Color.green.darker());
        plot.setSubrangePaint(1, Color.orange);
        plot.setSubrangePaint(2, Color.red.darker());
        JFreeChart chart = new JFreeChart("Demo",
                JFreeChart.DEFAULT_TITLE_FONT, plot, true);
        this.add(new ChartPanel(chart, W, H, W, H, W, H,
                false, true, true, true, true, true));
    }
}
