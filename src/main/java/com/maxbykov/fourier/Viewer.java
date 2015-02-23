package com.maxbykov.fourier;

import javax.swing.*;
import java.awt.*;

public abstract class Viewer {
    protected double[] data;
    protected int zero;
    private JComponent component;
    protected int width;
    protected int height;

    abstract void drawView(Graphics2D g);

    public Viewer(final int height, final int width, final String title) {
        this.width = width;
        this.height = height;
        zero = (int) (height * 0.5);

        EventQueue.invokeLater(new Runnable() {
            public void run() {

                JFrame frame = new JFrame();
                frame.setTitle(title);
                frame.setSize(width, height);

                component = new JComponent() {
                    public void paintComponent(Graphics g) {
                        Graphics2D g2 = (Graphics2D) g;
                        drawView(g2);
                    }
                };

                frame.add(component);

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }

    public void drawData(int[] signal) {
        this.data = new double[signal.length];
        for (int i = 0; i < signal.length; i++) {
            data[i] = (double) signal[i];
        }
        if (component != null)
            component.repaint();
    }

    public void drawData(byte[] signal) {
        this.data = new double[signal.length];
        for (int i = 0; i < signal.length; i++) {
            data[i] = (double) signal[i];
        }
        if (component != null)
            component.repaint();
    }

    public void drawData(double[] signal) {
        this.data = new double[signal.length];
        for (int i = 0; i < signal.length; i++) {
            data[i] = signal[i];
        }
        if (component != null)
            component.repaint();
    }

    public void setZero(int newZero) {
        System.out.println("before:" + zero + ", nz:" + newZero);
        this.zero = newZero;
        System.out.println("after:" + zero);
    }

}
