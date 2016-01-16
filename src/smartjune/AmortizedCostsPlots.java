package smartjune;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/*
 * 1.5.16
 */
public class AmortizedCostsPlots extends JFrame {
	private int N, i, cost, total;
	private UFA ufa;
	

	public AmortizedCostsPlots(int N, String s2, UFA ufa) {
		super("Amortized Cost Plots");				
		this.N = N;	
		this.ufa = ufa;
		
        JPanel chartPanel = createChartPanel(s2);
        add(chartPanel, BorderLayout.CENTER);
 
        setSize(640, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
	}
	
	private JPanel createChartPanel(String chartTitle) {
		String xAxisLabel = "number of connections";
		String yAxisLabel = "number of array accesses";
		
		XYDataset dataset = createDataset();
		
		JFreeChart chart = ChartFactory.createScatterPlot(chartTitle, 
				xAxisLabel, yAxisLabel, dataset);
		
		// 描点
		XYPlot plot = chart.getXYPlot();
		XYDotRenderer renderer = new XYDotRenderer();
		renderer.setDotWidth(3);
		renderer.setDotHeight(3);
		renderer.setSeriesPaint(0, Color.BLACK);
		renderer.setSeriesPaint(1, Color.RED);
		plot.setRenderer(renderer);
		
		return new ChartPanel(chart);
	}
	
	private XYDataset createDataset() {
		return quickUnionDataset();
	}
	
	private XYDataset quickFindDataset() {
		XYSeriesCollection dataset = new XYSeriesCollection();
		
		XYSeries series0 = new XYSeries("union() operations");
		XYSeries series1 = new XYSeries("cumulative average");
		
		QuickFindUF uf = (QuickFindUF) ufa;
		// 对每一组 connection 需要做一次计算、画两个点
		while (!StdIn.isEmpty()) {
			i++;
			cost = 2;  // every connected() operation accesses id[] twice
			
			int p = StdIn.readInt();
			int q = StdIn.readInt();
			if (!uf.connected(p, q))
				cost += uf.unionWithCostReturned(p, q);			
			total += cost;

			series0.add(i, cost);
			series1.add(i, 1.0 * total / i);
		}
		
		dataset.addSeries(series0);
		dataset.addSeries(series1);
		
		return dataset;
	}

	private XYDataset quickUnionDataset() {
		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries series0 = new XYSeries("find() operations");
		XYSeries series1 = new XYSeries("cumulative average");
		
		
		QuickUnionUF uf = (QuickUnionUF) ufa;
		while (!StdIn.isEmpty()) {	
			i++;
			cost = 0;
			
			int p = StdIn.readInt();
			int q = StdIn.readInt();
			if (!uf.connected(p, q))
				cost += uf.unionWithCostReturned(p, q);	
			else 
				cost += uf.costOfFind;
			total += cost;
						
			series0.add(i, cost);
			series1.add(i, 1.0 * total / i);
		}
		
/*		while (!StdIn.isEmpty()) {
			int p = StdIn.readInt();
			int q = StdIn.readInt();
			if (!uf.connected(p, q)) {
				uf.union(p, q);
			}
		}
		
		for (int k = 0; k < N; k++) {
			
			i = k + 1;
			
			uf.find(k);
			cost = uf.costOfFind;
			total += cost;
			
			series0.add(i, cost);
			series1.add(i, 1.0 * total / i);
		}*/
		
		dataset.addSeries(series0);
		dataset.addSeries(series1);
		
		return dataset;	
		
	}
	
	public static void main(String[] args) {
		int N = StdIn.readInt();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new AmortizedCostsPlots(N, "quick-union",
						new QuickUnionUF(N)).setVisible(true);
			}
		});
	}

}
