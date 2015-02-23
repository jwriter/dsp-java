package com.maxbykov.fourier;

import java.awt.*;
import java.awt.geom.Line2D;


public class Analyzer extends Viewer{

    public Analyzer(int height, int width, String title) {
        super(height, width, title);
        setZero((int)(height * 0.8));
    }

    @Override
    void drawView(Graphics2D g) {
        //zero line
        g.draw(new Line2D.Double(1, zero, width - 1, zero));

        if (data != null) {
            double x1, y1, x2, y2;

            double k = ((double)width)/data.length;
            for (int i = 1; i * k < width; i++) {
                if (i < data.length) {
                    x2 = i * k;
                    y2 = zero - data[i];
                    x1 = x2;
                    y1 = zero;
                    g.draw(new Line2D.Double(x1, y1, x2, y2));
                } else {

                }
            }
        }
    }


   //test client
   private static int SIZE = 100;
   private static int AMPL = 100;
   public static final int FREQUENCY = 20;

   public static void main(String[] args) {

       int[] testSignal = new int[SIZE];
       
       for (int i = 1; i < SIZE; i++) {

           testSignal[i] = (int) (AMPL / i);

       }

       Analyzer analyzer = new Analyzer(400, 800, "spectrum");

       analyzer.drawData(testSignal);
   }
}