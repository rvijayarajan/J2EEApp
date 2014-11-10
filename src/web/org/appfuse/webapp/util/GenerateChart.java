/*
 * 创建日期 2005-10-24
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package org.appfuse.webapp.util;

/**
 * @author szy
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 * 
 * 其实为一张三维表
 * 
 * 平面图最多能展示三维数据的图表
 * 
 */

import java.awt.Color;
import java.awt.Dimension;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.ui.TextAnchor;
import org.jfree.util.TableOrder;

public class GenerateChart extends ApplicationFrame {

    
	DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	DefaultCategoryDataset lineDataset = new DefaultCategoryDataset();
	
	DefaultPieDataset pieDataset = new DefaultPieDataset();
	
	public void clearCategoryDataset(){
		dataset.clear();
	}
	
	public void clearLineDataset(){
		lineDataset.clear();
	}
	
	public void clearPieDataset(){
		pieDataset=null;
		pieDataset = new DefaultPieDataset();
	}
	
	/**
     * Creates a new demo instance.
     *
     * @param title  the frame title.
     */
    public GenerateChart(String title) {

        super(title);
        CategoryDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart, false);
        chartPanel.setPreferredSize(new Dimension(500, 270));
        setContentPane(chartPanel);

    }
    public GenerateChart(){
    	super("");
    }

    /**
     * 
     * @param value			series 的值
     * @param series		系列
     * @param category		类别
     */
    public void addValue(double value,String series,String category){
    	dataset.addValue(value, series, category);
    }
    
    public void addLineDataValue(double value,String series,String category){
    	lineDataset.addValue(value, series, category);
    	//addLineDataValue(1.0, series1, category1);
    }
    
    public void addPieDataValue(double value,String series){
    	pieDataset.setValue(series,value);
    }
    
    /**
     * @param title				标题
     * @param seriesName		系列名称
     * @param categoryName		类别名称
     * @return JFreeChart	返回生成的JFreeChart对象
     * @param width 		生成的图片文件的宽度
     * @param height		生成的图片文件的高度
     * @return
     * @throws Exception
     */
    public JFreeChart genBarChartFile(String title,
    		String seriesName,String categoryName, 
			String jpgFile,
			int width,int height)
    throws Exception
	{
    	JFreeChart chart = ChartFactory.createBarChart3D(
            title,         // chart title
            seriesName,               // domain axis label
            categoryName,                  // range axis label
            dataset,                  // data
            PlotOrientation.VERTICAL, // orientation
            true,                     // include legend
            true,                     // tooltips?
            false                     // URLs?
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

        // set the background color for the chart...
        chart.setBackgroundPaint(Color.white);

        // get a reference to the plot for further customisation...
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.white);
       
        
        // set the range axis to display integers only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // disable bar outlines...
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        		
        renderer.setDrawBarOutline(false);
        renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setPositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE1,TextAnchor.TOP_RIGHT));
        renderer.setItemLabelsVisible(true);
        
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
            CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
        );
        // OPTIONAL CUSTOMISATION COMPLETED.

        ChartUtilities.saveChartAsJPEG(new java.io.File(jpgFile),chart,width,height);

        return chart;	
	
	}
    
    /**
     * @param title				标题
     * @param seriesName		系列名称
     * @param categoryName		类别名称
     * @return JFreeChart	返回生成的JFreeChart对象
     * @param width 		生成的图片文件的宽度
     * @param height		生成的图片文件的高度
     * @return
     * @throws Exception
     */
    public JFreeChart genLine3DChartFile(String title,
    		String seriesName,String categoryName, 
			String jpgFile,
			int width,int height)
    throws Exception
	{
    	JFreeChart chart = ChartFactory.createLineChart3D(
            title,         // chart title
            seriesName,               // domain axis label
            categoryName,                  // range axis label
            lineDataset,                  // data
            PlotOrientation.VERTICAL, // orientation
            true,                     // include legend
            true,                     // tooltips?
            false                     // URLs?
        );

        chart.setBackgroundPaint(Color.white);

        // get a reference to the plot for further customisation...
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.white);
//        crx test...
//        CategoryItemRenderer renderer = plot.getRenderer();
        
        // set the range axis to display integers only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        LineAndShapeRenderer renderer = (LineAndShapeRenderer)plot.getRenderer();
        renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setPositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE1,TextAnchor.BOTTOM_CENTER));
        renderer.setItemLabelsVisible(Boolean.TRUE);
        
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
            CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
        );
//        crx add...
//        plot.addAnnotation();
        // OPTIONAL CUSTOMISATION COMPLETED.

        ChartUtilities.saveChartAsJPEG(new java.io.File(jpgFile),chart,width,height);

        return chart;	
	
	}   

    
    /**
     * @param title				标题
     * @param seriesName		系列名称
     * @param categoryName		类别名称
     * @return JFreeChart	返回生成的JFreeChart对象
     * @param width 		生成的图片文件的宽度
     * @param height		生成的图片文件的高度
     * @return
     * @throws Exception
     */
    public JFreeChart genLineChartFile(String title,
    		String seriesName,String categoryName, 
			String jpgFile,
			int width,int height)
    throws Exception
	{
    	JFreeChart chart = ChartFactory.createLineChart(
            title,         // chart title
            seriesName,               // domain axis label
            categoryName,                  // range axis label
            lineDataset,                  // data
            PlotOrientation.VERTICAL, // orientation
            true,                     // include legend
            true,                     // tooltips?
            false                     // URLs?
        );

        chart.setBackgroundPaint(Color.white);

        // get a reference to the plot for further customisation...
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.white);
       
        
        // set the range axis to display integers only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
            CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
        );
        // OPTIONAL CUSTOMISATION COMPLETED.

        ChartUtilities.saveChartAsJPEG(new java.io.File(jpgFile),chart,width,height);

        return chart;	
	
	}
    
    public JFreeChart genMultiPieChartFile(String title,
    		String seriesName,String categoryName, 
			String jpgFile,
			int width,int height)
    throws Exception
	{
    	JFreeChart chart = ChartFactory.createMultiplePieChart3D(
            title,         // chart title
            dataset,                  // data
			TableOrder.BY_COLUMN,//seriesName,               // domain axis label
            //categoryName,                  // range axis label
            
            //PlotOrientation.VERTICAL, // orientation
            true,                     // include legend
            true,                     // tooltips?
            false                     // URLs?
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

        // set the background color for the chart...
        chart.setBackgroundPaint(Color.white);


        ChartUtilities.saveChartAsJPEG(new java.io.File(jpgFile),chart,width,height);

        return chart;	
	
	}
    
    public JFreeChart genPieChartFile(String title,
    		String seriesName,String categoryName, 
			String jpgFile,
			int width,int height)
    throws Exception
	{
    	
    	
    	JFreeChart chart = ChartFactory.createPieChart3D(
            title,         // chart title
            pieDataset,                  // data
            //categoryName,                  // range axis label
            
            //PlotOrientation.VERTICAL, // orientation
            true,                     // include legend
            true,                     // tooltips?
            false                     // URLs?
        );
        chart.setBackgroundPaint(Color.white);
 
        ChartUtilities.saveChartAsJPEG(new java.io.File(jpgFile),chart,width,height);

        return chart;	
	
	}  
  
    /**
     * Returns a sample dataset.
     * 
     * @return The dataset.
     */
    private  CategoryDataset createDataset() {
        
        // row keys...
        String series1 = "硕士";
        String series2 = "博士";
        String series3 = "本科";

        // column keys...
        String category1 = "北京邮电大学";
        String category2 = "北京语言学院";
        String category3 = "北京大学";
        String category4 = "清华大学";
        String category5 = "外国语学院";

        addLineDataValue(1.0, series1, category1);
        addLineDataValue(4.0, series1, category2);
        addLineDataValue(3.0, series1, category3);
        addLineDataValue(5.0, series1, category4);
        addLineDataValue(5.0, series1, category5);
        

        addLineDataValue(5.0, series2, category1);
        addLineDataValue(7.0, series2, category2);
        addLineDataValue(6.0, series2, category3);
        addLineDataValue(8.0, series2, category4);
        addLineDataValue(4.0, series2, category5);

        addLineDataValue(4.0, series3, category1);
        addLineDataValue(3.0, series3, category2);
        addLineDataValue(2.0, series3, category3);
        addLineDataValue(3.0, series3, category4);
        addLineDataValue(6.0, series3, category5);
        
        return dataset;
        
    }
    
    /**
     * Creates a sample chart.
     * 
     * @param dataset  the dataset.
     * 
     * @return The chart.
     */
    private JFreeChart createChart(CategoryDataset dataset) {
        
        // create the chart...
    	 JFreeChart chart = null;
    	try{
    		addPieDataValue(4,"本科");
			addPieDataValue(6,"博士");
			//chart = genPieChartFile("演示系统", "xueke", "人数", "d:\\bb.jpg",500,270);
    		chart = genLine3DChartFile("柱图演示系统", "学院", "人数", "d:\\bb.jpg",500,270);
         }catch (Exception e){ 
        	e.printStackTrace();
        }
        return chart;
        
    }
    
    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
    public static void main(String[] args) {

    	GenerateChart demo = new GenerateChart("Bar Chart Demo");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }

}
