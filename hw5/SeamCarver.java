import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class SeamCarver {

    private Picture picture;
    private int w;
    private int h;
    private double[][] m;

    public SeamCarver(Picture picture) {
        this.picture = picture;
        this.w = picture.width();
        this.h = picture.height();
        this.m = new double[w][h];
    }

    // current picture
    public Picture picture() {
        return picture;
    }

    // width of current picture
    public int width() {
        return w;
    }

    // height of current picture
    public int height() {
        return h;
    }

    private int xGradient(int x, int y) {
        if (x < 0 || x > w - 1 || y < 0 || y > h - 1) {
            throw new IndexOutOfBoundsException();
        }
        Color cxplus = x + 1 <= w - 1 ? picture.get(x + 1, y) : picture.get(0, y);
        Color cxminus = x - 1 >= 0 ? picture.get(x - 1, y) : picture.get(w - 1, y);
        int rx = cxplus.getRed() - cxminus.getRed();
        int gx = cxplus.getGreen() - cxminus.getGreen();
        int bx = cxplus.getBlue() - cxminus.getBlue();
        return rx * rx + gx * gx + bx * bx;
    }

    private int yGradient(int x, int y) {
        if (x < 0 || x > w - 1 || y < 0 || y > h - 1) {
            throw new IndexOutOfBoundsException();
        }
        Color cyplus = y + 1 <= h - 1 ? picture.get(x, y + 1) : picture.get(x, 0);
        Color cyminus = y - 1 >= 0 ? picture.get(x, y - 1) : picture.get(x, h - 1);
        int ry = cyplus.getRed() - cyminus.getRed();
        int gy = cyplus.getGreen() - cyminus.getGreen();
        int by = cyplus.getBlue() - cyminus.getBlue();
        return ry * ry + gy * gy + by * by;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x > w - 1 || y < 0 || y > h - 1) {
            throw new IndexOutOfBoundsException();
        }
        return (double) this.xGradient(x, y) + this.yGradient(x, y);
    }

    private double min(double a, double b, double c) {
        if (a < b) {
            if (b < c) {
                return a;
            } else if (a < c) {
                return a;
            } else {
                return c;
            }
        } else {
            if (b > c) {
                return c;
            } else {
                return b;
            }
        }
    }

    private int m(int i) {
        if (i == 0) {
            for (int j = 0; j < w; j += 1) {
                m[0][j] = energy(0, j);
            }
            int min = 0;
            for (int j = 0; j < w; j += 1) {
                min = m[0][min] < m[0][j] ? min : j;
            }
            return min;
        }
        for (int j = 0; j < w; j += 1) {
            m[i][j] += energy(i, j) + min(m[i - 1][j - 1], m[i][j - 1], m[i + 1][j - 1]);
        }
        int min = 0;
        for (int j = 0; j < w; j += 1) {
            min = m[i][min] < m[i][j] ? min : j;
        }
        return min;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        int[] hs = new int[h];
        for (int i = 0; i < h; i += 1) {
            hs[i] = m(i);
        }
        return hs;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int[] ws = new int[w];
        for (int i = 0; i < w; i += 1) {
            ws[i] = m(i);
        }
        return ws;
    }

    // remove horizontal seam from picture
    public void removeHorizontalSeam(int[] seam) {
        picture = SeamRemover.removeHorizontalSeam(picture, seam);
    }

    // remove vertical seam from picture
    public void removeVerticalSeam(int[] seam) {
        picture = SeamRemover.removeVerticalSeam(picture, seam);
    }
}
