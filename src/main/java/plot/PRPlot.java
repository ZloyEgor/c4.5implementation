package plot;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import test.TestInformation;

public class PRPlot extends ApplicationFrame {

    public PRPlot(String title, TestInformation information) {
        super(title);
        final XYSeries aucSeries = new XYSeries("Precision-Recall curve");
        aucSeries.add(0, 1);
        aucSeries.add(information.getRecall(), information.getPrecision());
        aucSeries.add(1, 0);

        final XYSeriesCollection data = new XYSeriesCollection(aucSeries);
        final JFreeChart chart = ChartFactory.createXYLineChart(
                "Precision-Recall curve",
                "Recall",
                "Precision",
                data,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(320, 320));
        setContentPane(chartPanel);
    }
}
