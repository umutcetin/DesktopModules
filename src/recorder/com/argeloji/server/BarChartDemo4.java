package com.argeloji.server;
/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2004, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc. 
 * in the United States and other countries.]
 *
 * ------------------
 * BarChartDemo4.java
 * ------------------
 * (C) Copyright 2003, 2004, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: BarChartDemo4.java,v 1.7 2004/04/26 19:11:53 taqua Exp $
 *
 * Changes
 * -------
 * 14-Nov-2003 : Version 1 (DG);
 *
 */



import java.awt.Color;
import java.awt.GradientPaint;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import com.argeloji.entity.ServerDispatcher;

/**
 * A bar chart with only two bars - here the 'maxBarWidth' attribute in the renderer prevents
 * the bars from getting too wide.
 *
 */
public class BarChartDemo4 extends ApplicationFrame {

	CategoryDataset dataset;
    JFreeChart chart;
    ChartPanel chartPanel;
    double sa,sb,sc,sd;
    /**
     * Creates a new demo instance.
     *
     * @param title  the frame title.
     */
    public BarChartDemo4(final String title) {
    	
        super(title);
        
        //register to server dispatcher for updates
        ServerDispatcher sd1= ServerDispatcher.getInstance();
        sd1.registerGraphics(this);
        
        sa=sb=sc=sd=0;
        dataset = createDataset();
        chart = createChart(dataset);

        // add the chart to a panel...
        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);

    }
    
    public void updateGraphics(String secenek)
    {
    	if (secenek.equals("a"))
    		sa++;
    	if (secenek.equals("b"))
    		sb++;
    	if (secenek.equals("c"))
    		sc++;
    	if (secenek.equals("d"))
    		sd++;
    	
    		// row keys...
            final String series1 = "A";
            final String series2 = "B";
            final String series3 = "C";
            final String series4 = "D";

            // column keys...
            final String category1 = "Seçenekler";

            // create the dataset...
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            dataset.addValue(sa, series1, category1);
            dataset.addValue(sb, series2, category1);
            dataset.addValue(sc, series3, category1);
            dataset.addValue(sd, series4, category1);
            
            chart = createChart(dataset);

            // add the chart to a panel...
            chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
            setContentPane(chartPanel);
            
            pack();
    }

    /**
     * Returns a sample dataset.
     * 
     * @return The dataset.
     */
    private CategoryDataset createDataset() {
        
        // row keys...
        final String series1 = "A";
        final String series2 = "B";
        final String series3 = "C";
        final String series4 = "D";

        // column keys...
        final String category1 = "Seçenekler";

        // create the dataset...
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(0.0, series1, category1);
        dataset.addValue(0.0, series2, category1);
        dataset.addValue(0.0, series3, category1);
        dataset.addValue(0.0, series4, category1);
        
        return dataset;
        
    }
    
    // ****************************************************************************
    // * JFREECHART DEVELOPER GUIDE                                               *
    // * The JFreeChart Developer Guide, written by David Gilbert, is available   *
    // * to purchase from Object Refinery Limited:                                *
    // *                                                                          *
    // * http://www.object-refinery.com/jfreechart/guide.html                     *
    // *                                                                          *
    // * Sales are used to provide funding for the JFreeChart project - please    * 
    // * support us so that we can continue developing free software.             *
    // ****************************************************************************
    
    /**
     * Creates a sample chart.
     * 
     * @param dataset  the dataset.
     * 
     * @return The chart.
     */
    private JFreeChart createChart(final CategoryDataset dataset) {
        
        // create the chart...
        final JFreeChart chart = ChartFactory.createBarChart(
            "Cevap Daðýlýmý",         // chart title
            "Seçenekler",               // domain axis label
            "Öðrenci Sayýsý",                  // range axis label
            dataset,                  // data
            PlotOrientation.VERTICAL,
            true,                     // include legend
            true,                     // tooltips?
            false                     // URLs?
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

        // set the background color for the chart...
        chart.setBackgroundPaint(new Color(0xFFFFFF));

        // get a reference to the plot for further customisation...
        final CategoryPlot plot = chart.getCategoryPlot();
        
        // set the range axis to display integers only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // disable bar outlines...
        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        //renderer.setMaxBarWidth(0.10);
        
        // set up gradient paints for series...
        final GradientPaint gp0 = new GradientPaint(
            0.0f, 0.0f, Color.GREEN, 
            0.0f, 0.0f, Color.GREEN
        );
        final GradientPaint gp1 = new GradientPaint(
            0.0f, 0.0f, Color.RED, 
            0.0f, 0.0f, Color.RED
        );
        final GradientPaint gp2 = new GradientPaint(
                0.0f, 0.0f, Color.RED, 
                0.0f, 0.0f, Color.RED
            );
            final GradientPaint gp3 = new GradientPaint(
                0.0f, 0.0f, Color.RED, 
                0.0f, 0.0f, Color.RED
            );
        renderer.setSeriesPaint(0, gp0);
        renderer.setSeriesPaint(1, gp1);
        renderer.setSeriesPaint(2, gp2);
        renderer.setSeriesPaint(3, gp3);
        
        // OPTIONAL CUSTOMISATION COMPLETED.
        
        return chart;
        
    }
    
    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
    public static void main(final String[] args) {

        final BarChartDemo4 demo = new BarChartDemo4("Cevap Daðýlýmý");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

//        demo.updateGraphics("a");
//        demo.updateGraphics("a");
//        demo.updateGraphics("a");
        
    }

}