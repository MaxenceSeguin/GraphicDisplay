package GraphicDisplay.Tools;
import java.util.Objects;

import static java.lang.Math.pow;

public class Complex {
    private double re;   // the real part
    private double im;   // the imaginary part

    // create a new object with the given real and imaginary parts
    public Complex(double real, double imag) {
        re = real;
        im = imag;
    }

    public void set(double real, double imag){
        this.re = real;
        this.im = imag;
    }

    // return a string representation of the invoking Complex object
    public String toString() {
        if (im == 0) return re + "";
        if (re == 0) return im + "i";
        if (im <  0) return re + " - " + (-im) + "i";
        return re + " + " + im + "i";
    }

    // return abs/modulus/magnitude
    public double abs() {
        return Math.hypot(re, im);
    }

    // return angle/phase/argument, normalized to be between -pi and pi
    public double phase() {
        return Math.atan2(im, re);
    }

    // return a new Complex object whose value is (this + b)
    public void plus(Complex b) {
        this.re += b.re();
        this.im += b.im();
    }

    // return a new Complex object whose value is (this - b)
    public void minus(Complex b) {
        this.re -= b.re;
        this.im -= b.im;
    }

    // return a new Complex object whose value is (this * b)
    public void times(Complex b) {
        double real = this.re * b.re - this.im * b.im;
        double imag = this.re * b.im + this.im * b.re;
        this.re = real;
        this.im = imag;
    }

    // return a new object whose value is (this * alpha)
    public void scale(double alpha) {
        this.re *= alpha;
        this.im *= alpha;
    }

    // return a new Complex object whose value is the conjugate of this
    public void conjugate() {
        this.im *= -1;
    }

    // return a new Complex object whose value is the reciprocal of this
    public Complex reciprocal() {
        double scale = re*re + im*im;
        return new Complex( re/scale, im/scale);
    }

    // return the real or imaginary part
    public double re() { return re; }
    public double im() { return im; }

    // return a / b
    public void divides(Complex b) {
        b = b.reciprocal();
        this.times(b);
    }

    public void sinh(){
        Complex x = new Complex(-re, -im);
        this.exp();
        x.exp();
        this.minus(x);
        this.scale(0.5);
    }

    public void cubic(){
        set(pow(re,3) - 3*re*im*im, -pow(im, 3) + 3*im*re*re);
    }

    // return a new Complex object whose value is the complex exponential of this
    public void exp() {
        double reb = this.re;
        double imb = this.im;
        this.re = Math.exp(reb) * Math.cos(imb);
        this.im = Math.exp(reb) * Math.sin(imb);
    }

    // return a new Complex object whose value is the complex sine of this
    public void sin() {
        double reb = this.re;
        double imb = this.im;
        this.re = Math.sin(reb) * Math.cosh(imb);
        this.im = Math.cos(reb) * Math.sinh(imb);
    }

    // return a new Complex object whose value is the complex cosine of this
    public void cos() {
        double reb = this.re;
        double imb = this.im;
        this.re = Math.cos(reb) * Math.cosh(imb);
        this.im = -Math.sin(reb) * Math.sinh(imb);
    }

    // return a new Complex object whose value is the complex tangent of this
    public void tan() {
        Complex a = this;
        a.cos();
        this.sin();
        this.divides(a);
    }



    // a static version of plus
    public static Complex plus(Complex a, Complex b) {
        double real = a.re + b.re;
        double imag = a.im + b.im;
        Complex sum = new Complex(real, imag);
        return sum;
    }

    // See Section 3.3.
    public boolean equals(Object x) {
        if (x == null) return false;
        if (this.getClass() != x.getClass()) return false;
        Complex that = (Complex) x;
        return (this.re == that.re) && (this.im == that.im);
    }

    // See Section 3.3.
    public int hashCode() {
        return Objects.hash(re, im);
    }

}