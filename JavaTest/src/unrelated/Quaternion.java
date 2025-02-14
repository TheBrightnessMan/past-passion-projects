package unrelated;

import java.text.DecimalFormat;

public class Quaternion {

    private final double w, i, j, k;

    public Quaternion(double w, double i, double j, double k) {
        this.w = w;
        this.i = i;
        this.j = j;
        this.k = k;
    }

    public static Quaternion X_AXIS = new Quaternion(0, 1, 0, 0);
    public static Quaternion Y_AXIS = new Quaternion(0, 0, 1, 0);
    public static Quaternion Z_AXIS = new Quaternion(0, 0, 0, 1);

    public String toString() {
        DecimalFormat format = new DecimalFormat("0.00");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(format.format(w));
        if (i < 0) {
            stringBuilder.append(" - ").append(format.format(Math.abs(i)));
        } else {
            stringBuilder.append(" + ").append(format.format(i));
        }
        stringBuilder.append("i");
        if (j < 0) {
            stringBuilder.append(" - ").append(format.format(Math.abs(j)));
        } else {
            stringBuilder.append(" + ").append(format.format(j));
        }
        stringBuilder.append("j");
        if (k < 0) {
            stringBuilder.append(" - ").append(format.format(Math.abs(k)));
        } else {
            stringBuilder.append(" + ").append(format.format(k));
        }
        stringBuilder.append("k");
        return stringBuilder.toString();
    }

    public Quaternion inverse() {
        return new Quaternion(w, -i, -j, -k);
    }

    public double getNorm() {
        return Math.sqrt(w * w + i * i + j * j + k * k);
    }

    public Quaternion normalize() {
        double norm = this.getNorm();
        return new Quaternion(w / norm, i / norm, j / norm, k / norm);
    }

    public Quaternion add(Quaternion b) {
        return new Quaternion(this.w + b.getW(), this.i + b.getI(), this.j + b.getJ(), this.k + b.getK());
    }

    public Quaternion multiply(Quaternion b) {
        double w1, i1, j1, k1;
        w1 = w * b.getW() - i * b.getI() - j * b.getJ() - k * b.getK();
        i1 = w * b.getI() + i * b.getW() + j * b.getK() - k * b.getJ();
        j1 = w * b.getJ() - i * b.getK() + j * b.getW() + k * b.getI();
        k1 = w * b.getK() + i * b.getJ() - j * b.getI() + k * b.getW();
        return new Quaternion(w1, i1, j1, k1);
    }

    public Quaternion rotate(Quaternion axis, double degree) {
        if (axis.getNorm() == 0) {
            return null;
        }
        Quaternion finalAxis = axis.normalize();
        double radian = Math.toRadians(degree);
        Quaternion rotationQuaternion = new Quaternion(Math.cos(radian / 2),
                Math.sin(radian / 2) * finalAxis.getI(),
                Math.sin(radian / 2) * finalAxis.getJ(),
                Math.sin(radian / 2) * finalAxis.getK());

        return rotationQuaternion.multiply(this).multiply(rotationQuaternion.inverse()).setW(0);
    }

    public double getW() {
        return w;
    }

    public double getI() {
        return i;
    }

    public double getJ() {
        return j;
    }

    public double getK() {
        return k;
    }

    public Quaternion setW(double w) {
        return new Quaternion(w, i, j, k);
    }

    public Quaternion setI(double i) {
        return new Quaternion(w, i, j, k);
    }

    public Quaternion setJ(double j) {
        return new Quaternion(w, i, j, k);
    }

    public Quaternion setK(double k) {
        return new Quaternion(w, i, j, k);
    }
}
