package com.maxbykov.fourier;

import org.apache.commons.math3.complex.Complex;

public class Butterfly {

    public final static double SinglePi = Math.PI;
    public final static double DoublePi = 2*Math.PI;

    public static Complex[] DecimationInTime(Complex[] frame, boolean direct)
    {
        if (frame.length == 1) return frame;
        int frameHalfSize = frame.length / 2;
        int frameFullSize = frame.length;

        Complex[] frameOdd = new Complex[frameHalfSize];
        Complex[] frameEven = new Complex[frameHalfSize];
        for (int i = 0; i < frameHalfSize; i++)
        {
            int j = i * 2;
            frameOdd[i] = frame[j + 1];
            frameEven[i] = frame[j];
        }

        Complex[] spectrumOdd = DecimationInTime(frameOdd, direct);
        Complex[] spectrumEven = DecimationInTime(frameEven, direct);

        double arg = direct ? -DoublePi/frameFullSize : DoublePi/frameFullSize;
        Complex omegaPowBase = new Complex(Math.cos(arg), Math.sin(arg));
        Complex omega = Complex.ONE;
        Complex[] spectrum = new Complex[frameFullSize];

        for (int j = 0; j < frameHalfSize; j++)
        {
            spectrum[j] = spectrumEven[j].add(omega.multiply(spectrumOdd[j]));
            spectrum[j + frameHalfSize] = 
                    spectrumEven[j].subtract(omega.multiply(spectrumOdd[j]));
            omega = omega.multiply(omegaPowBase);
        }

        return spectrum;
    }

    public static Complex[] DecimationInFrequency(Complex[] frame,
                                                                boolean direct)
    {
        if (frame.length == 1) return frame;
        int halfSampleSize = frame.length / 2;
        int fullSampleSize = frame.length;

        double arg = direct ? -DoublePi/fullSampleSize : DoublePi/fullSampleSize;
        Complex omegaPowBase = new Complex(Math.cos(arg), Math.sin(arg));
        Complex omega = Complex.ONE;
        Complex[] spectrum = new Complex[fullSampleSize];

        for (int j = 0; j < halfSampleSize; j++)
        {
            spectrum[j] = frame[j].add(frame[j + halfSampleSize]);
            spectrum[j + halfSampleSize] = 
                   omega.multiply(frame[j].subtract(frame[j + halfSampleSize]));
            omega = omega.multiply(omegaPowBase);
        }

        Complex[] yTop = new Complex[halfSampleSize];
        Complex[] yBottom = new Complex[halfSampleSize];
        for (int i = 0; i < halfSampleSize; i++)
        {
            yTop[i] = spectrum[i];
            yBottom[i] = spectrum[i + halfSampleSize];
        }

        yTop = DecimationInFrequency(yTop, direct);
        yBottom = DecimationInFrequency(yBottom, direct);
        for (int i = 0; i < halfSampleSize; i++)
        {
            int j = i * 2;
            spectrum[j] = yTop[i];
            spectrum[j + 1] = yBottom[i];
        }

        return spectrum;
    }
    
    //простой тест
    public static void main(String[] args) {
        
        Complex[] signal = new Complex[2048];
        int F_SIGNAL = 440; // частота синусоиды
        int F_DISKR = 2000; // частота дискретизации
        
        for (int i = 0; i < signal.length; i++) {
            signal[i] = new Complex(Math.sin(2 * Math.PI 
                                                * F_SIGNAL * i / F_DISKR));
        }
        
        Complex[] spectrum = Butterfly.DecimationInFrequency(signal, true);
        Utils.normalize(spectrum);
        DrawTest.drawSpectrum(Utils.magnitude(spectrum));
        
    }
}
