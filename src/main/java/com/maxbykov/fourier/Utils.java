package com.maxbykov.fourier;

import org.apache.commons.math3.complex.Complex;

public class Utils {
    
    public static void normalize(Complex[] spectrum) {
        for (int i = 0; i < spectrum.length; i++) {
            spectrum[i] = spectrum[i].divide(spectrum.length);
        }
    }
    
    public static double[] magnitude(Complex[] data) {
        double[] res = new double[data.length];
        
        for (int i = 0; i < 1024; i++) {
            Complex x = data[i];
            double Re = x.getReal();
            double Im = x.getImaginary();
            res[i] = Math.sqrt(Re * Re + Im * Im) * 100;
        }
        
        return res;
    }
    
    public static double[] phase(Complex[] data) {
        double[] res = new double[data.length];
        
        for (int i = 0; i < 1024; i++) {
            Complex x = data[i];
            double Re = x.getReal();
            double Im = x.getImaginary();
            res[i] = Math.atan(Im / Re);
        }
        
        return res;
    }
}
