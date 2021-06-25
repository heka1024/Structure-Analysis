package chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class GraphPanel extends JPanel {
    List<Double> xs, ys;
    public GraphPanel(List<Double> xs, List<Double> ys, String title) {
        this.xs = xs;
        this.ys = ys;
        final JPanel chartPanel = createChartPanel(title);
        add(chartPanel);
        setSize(500, 500);
    }

    private JPanel createChartPanel(String title) {
        String xAxisLabel = "X";
        String yAxisLabel = "Y";

        XYDataset dataset = createDataset(title);

        JFreeChart chart = ChartFactory.createXYLineChart(title,
                xAxisLabel, yAxisLabel, dataset);
        chart.getPlot().setBackgroundPaint(Color.lightGray);
        XYItemRenderer renderer = chart.getXYPlot().getRenderer();
        renderer.setSeriesPaint(0, Color.black);
        BufferedImage img = chart.createBufferedImage(500, 600);
        try {
            File f = new File("./" + title + ".png");
            ImageIO.write(img, "png", f);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        JPanel p = new ChartPanel(chart);
        p.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        p.setBackground(Color.CYAN);
        return p;
    }

    private XYDataset createDataset(String title) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series1 = new XYSeries(title);




        for (int i = 0; i < Math.min(xs.size(), ys.size()); i++) {
            series1.add(xs.get(i), ys.get(i));
        }

        dataset.addSeries(series1);

        return dataset;        // creates an XY dataset...
    }
}