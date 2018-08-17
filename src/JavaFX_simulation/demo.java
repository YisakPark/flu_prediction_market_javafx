/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaFX_simulation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class demo extends Application {

  @Override
  public void start(Stage primaryStage) {
    //below 5 int variables are index
    int DAY = 0;
    int S_RATE = 1;
    int I_RATE = 2;
    int R_RATE = 3;
    int ESTIMATED_RATE = 4;

    int total_buildings = 20;
      
    FlowPane root = new FlowPane();
    ScrollPane sp = new ScrollPane();
    sp.setContent(root);    
    //defining the axes
    final NumberAxis xAxis = new NumberAxis();
    final NumberAxis yAxis = new NumberAxis();
    xAxis.setLabel("Day");
    yAxis.setLabel("Population Rate (%)");
    
    for(int i=0; i<total_buildings; i++){        
        String FILE_NAME = "./result/population_rates_building_" + i + ".csv";
        String line = "";
        String cvsSplitBy = ",";
        //creating the chart
        final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

        lineChart.setTitle("In building " + (i+1));
        //defining a series
        XYChart.Series<Number, Number> S_series = new LineChart.Series<>();
        XYChart.Series<Number, Number> I_series = new LineChart.Series<>();
        XYChart.Series<Number, Number> R_series = new LineChart.Series<>();
        XYChart.Series<Number, Number> estimated_flu_rate_series = new LineChart.Series<>();
        S_series.setName("population rate of group 'S'");
        I_series.setName("population rate of group 'I'");
        R_series.setName("population rate of group 'R'");
        estimated_flu_rate_series.setName("estimated flu population rate");
        
        //open and read the file
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            //skip the first line
            br.readLine();
            
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] data = line.split(cvsSplitBy);
                S_series.getData().add(
                        new LineChart.Data<>(Float.parseFloat(data[DAY]),Float.parseFloat(data[S_RATE])));
                I_series.getData().add(
                        new LineChart.Data<>(Float.parseFloat(data[DAY]),Float.parseFloat(data[I_RATE])));
                R_series.getData().add(
                        new LineChart.Data<>(Float.parseFloat(data[DAY]),Float.parseFloat(data[R_RATE])));
                estimated_flu_rate_series.getData().add(
                        new LineChart.Data<>(Float.parseFloat(data[DAY]),Float.parseFloat(data[ESTIMATED_RATE])));
            }

        } catch (IOException e) {
            System.out.println(e);
        }
        lineChart.getData().add(S_series);
        lineChart.getData().add(I_series);
        lineChart.getData().add(R_series);
        lineChart.getData().add(estimated_flu_rate_series);
        root.getChildren().add(lineChart);

    }
    Scene scene = new Scene(sp, 800, 600);

    primaryStage.setTitle("");
    primaryStage.setScene(scene);
    primaryStage.show();
}

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }
}