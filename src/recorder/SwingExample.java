/**
 *
 * The MIT License
 *
 * Copyright (c) 2008 the original author or authors.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */


import java.awt.BorderLayout;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.googlecode.charts4j.AxisLabels;
import com.googlecode.charts4j.AxisLabelsFactory;
import com.googlecode.charts4j.AxisStyle;
import com.googlecode.charts4j.AxisTextAlignment;
import com.googlecode.charts4j.BarChart;
import com.googlecode.charts4j.BarChartPlot;
import com.googlecode.charts4j.Color;
import com.googlecode.charts4j.Data;
import com.googlecode.charts4j.DataEncoding;
import com.googlecode.charts4j.DataUtil;
import com.googlecode.charts4j.Fills;
import com.googlecode.charts4j.GCharts;
import com.googlecode.charts4j.LineChart;
import com.googlecode.charts4j.LineStyle;
import com.googlecode.charts4j.LinearGradientFill;
import com.googlecode.charts4j.PieChart;
import com.googlecode.charts4j.Plot;
import com.googlecode.charts4j.Plots;
import com.googlecode.charts4j.RadarChart;
import com.googlecode.charts4j.RadarPlot;
import com.googlecode.charts4j.RadialAxisLabels;
import com.googlecode.charts4j.Shape;
import com.googlecode.charts4j.Slice;


import static com.googlecode.charts4j.Color.*;
import static com.googlecode.charts4j.UrlUtil.normalize;



/**
 * Example code for displaying a chart via Swing. This is an alternative
 * to displaying charts via JSP and Servlet technology.
 *
 * @author Julien Chastang (julien.c.chastang at gmail dot com)
 */

public class SwingExample {

    /**
     * main
     *
     * @param s
     *            args not read.
     * @throws IOException
     */
    public static void main(String...s) throws IOException {
    	
    	// EXAMPLE CODE START
        // Defining data plots.
        BarChartPlot t1 = Plots.newBarChartPlot(Data.newData(70,5,15,10,5,3), LIMEGREEN); 
        

        // Instantiating chart.
        BarChart chart = GCharts.newBarChart(t1);

        // Defining axis info and styles
        AxisStyle axisStyle = AxisStyle.newAxisStyle(BLACK, 13, AxisTextAlignment.CENTER);
        AxisLabels score = AxisLabelsFactory.newAxisLabels("Sayý", 30);
        score.setAxisStyle(axisStyle);
        AxisLabels year = AxisLabelsFactory.newAxisLabels("Þýklar", 5);
        year.setAxisStyle(axisStyle);

        // Adding axis info to chart.
        chart.addXAxisLabels(AxisLabelsFactory.newAxisLabels("A", "B", "C", "D", "E", "Boþ"));
        chart.addYAxisLabels(AxisLabelsFactory.newNumericRangeAxisLabels(0, 30));
        chart.addYAxisLabels(score);
        chart.addXAxisLabels(year);

        chart.setSize(500, 450);
        chart.setBarWidth(50);
        chart.setSpaceWithinGroupsOfBars(20);
        chart.setDataStacked(true);
        chart.setTitle("Cevap Daðýlýmý", BLACK, 16);
        chart.setGrid(100, 5, 5, 5);
        chart.setBackgroundFill(Fills.newSolidFill(ALICEBLUE));
        LinearGradientFill fill = Fills.newLinearGradientFill(0, LAVENDER, 100);
        fill.addColorAndOffset(WHITE, 0);
        chart.setAreaFill(fill);
        String url = chart.toURLString();
        displayUrlString(url);

    	
    	//Internet baðlantýsý gerektiriyor
    	
    	Slice s1 = Slice.newSlice(70, Color.GREEN, "Doðru", "A: %70");
        Slice s2 = Slice.newSlice(25, Color.RED, "Yanlýþ", "B: %25");
        Slice s3 = Slice.newSlice(5, Color.SILVER, "Boþ", "Boþ: %5");

        PieChart chart2 = GCharts.newPieChart(s1, s2, s3);
        chart2.setTitle("Soru Baþarýmý",Color.BLACK, 16);
        chart2.setSize(500, 200);
        chart2.setThreeD(true);
        String url2 = chart2.toURLString();
        displayUrlString(url2);
        
        
     // EXAMPLE CODE START
        RadarPlot plot = Plots.newRadarPlot(Data.newData(80, 50, 50, 80, 60, 80));
        Color plotColor = Color.newColor("CC3366");
        plot.addShapeMarkers(Shape.SQUARE, plotColor, 12);
        plot.addShapeMarkers(Shape.SQUARE, WHITE, 8);
        plot.setColor(plotColor);
        plot.setLineStyle(LineStyle.newLineStyle(4, 1, 0));
        RadarChart chart3 = GCharts.newRadarChart(plot);
        chart3.setTitle("Ders Baþarýmý", BLACK, 20);
        chart3.setSize(500, 400);
        RadialAxisLabels radialAxisLabels = AxisLabelsFactory.newRadialAxisLabels("Matematik", "Türkçe", "Bilgisayar Bilimleri", "Müzik", "Ýngilizce");
        radialAxisLabels.setRadialAxisStyle(BLACK, 12);
        chart3.addRadialAxisLabels(radialAxisLabels);
        AxisLabels contrentricAxisLabels = AxisLabelsFactory.newNumericAxisLabels(Arrays.asList(0, 20, 40, 60, 80, 100));
        contrentricAxisLabels.setAxisStyle(AxisStyle.newAxisStyle(BLACK, 12, AxisTextAlignment.RIGHT));
        chart3.addConcentricAxisLabels(contrentricAxisLabels);
        String url3 = chart3.toURLString();

        displayUrlString(url3);
        
        
     // EXAMPLE CODE START
        // Defining data plots.
        BarChartPlot t2 = Plots.newBarChartPlot(Data.newData(0,0,5,5,5,
        		10,15,10,20,25,35,45,55,60,70,80,50,30,20,10,5), LIMEGREEN); 
        

        // Instantiating chart.
        BarChart chart4 = GCharts.newBarChart(t2);

        // Defining axis info and styles
        AxisStyle axisStyle2 = AxisStyle.newAxisStyle(BLACK, 13, AxisTextAlignment.CENTER);
        AxisLabels score2 = AxisLabelsFactory.newAxisLabels("Sayý", 20);
        score2.setAxisStyle(axisStyle2);
        AxisLabels year2 = AxisLabelsFactory.newAxisLabels("Puan", 5);
        year2.setAxisStyle(axisStyle2);

        // Adding axis info to chart.
        chart4.addXAxisLabels(AxisLabelsFactory.newAxisLabels("0", "5", "10", "15", "20", "25",
        		"30", "35", "40", "45", "50", "55",
        		"60", "65", "70", "75", "80", "85",
        		"90", "95", "100"));
        chart4.addYAxisLabels(AxisLabelsFactory.newNumericRangeAxisLabels(0, 20));
        chart4.addYAxisLabels(score2);
        chart4.addXAxisLabels(year2);

        chart4.setSize(500, 350);
        chart4.setBarWidth(10);
        chart4.setSpaceWithinGroupsOfBars(10);
        chart4.setDataStacked(true);
        chart4.setTitle("Sýnýf Baþarýmý", BLACK, 16);
        chart4.setGrid(100, 5, 5, 5);
        chart4.setBackgroundFill(Fills.newSolidFill(ALICEBLUE));
        LinearGradientFill fill2 = Fills.newLinearGradientFill(0, LAVENDER, 100);
        fill2.addColorAndOffset(WHITE, 0);
        chart4.setAreaFill(fill);
        String url4 = chart4.toURLString();
        displayUrlString(url4);

    }

    /**
     * Display the chart in a swing window.
     *
     *
     * @param urlString
     *            the url string to display.
     * @throws IOException
     */
    private static void displayUrlString(final String urlString) throws IOException{
        JFrame frame = new JFrame();
        JLabel label = new JLabel(new ImageIcon(ImageIO.read(new URL(urlString))));
        frame.getContentPane().add(label, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}
