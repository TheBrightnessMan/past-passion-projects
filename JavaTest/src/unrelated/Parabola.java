package unrelated;

public class Parabola {

    private final double a, b, c;

    /**
     * @param a Coefficient of x²
     * @param b Coefficient of x¹
     * @param c Coefficient of x⁰
     */
    public Parabola(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public double getSlopeOfTangentAtPoint(double t) {
        return 2 * a * t + b;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getC() {
        return c;
    }
}
