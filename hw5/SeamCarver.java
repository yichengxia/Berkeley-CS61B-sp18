import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class SeamCarver {

    private Picture picture;
    private int w;
    private int h;
    private double[][] m;
    private int[][] path;
    private int[] vs;

    public SeamCarver(Picture picture) {
        this.picture = picture;
        this.w = picture.width();
        this.h = picture.height();
        this.m = new double[w][h];
        this.path = new int[w][h];
        this.vs = new int[h];
    }

    // current picture
    public Picture picture() {
        return new Picture(picture);
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

    private void m(int j) {
        if (j == 0) {
            for (int i = 0; i < w; i += 1) {
                path[i][j] = 0;
                m[i][0] = energy(i, 0);
            }
            return;
        }
        for (int i = 0; i < w; i += 1) {
            if (i < 0 || i > w - 1 || j < 0 || j > h - 1) {
                throw new IndexOutOfBoundsException();
            }
            if (i - 1 < 0 && i + 1 > w - 1) {
                path[i][j] = i;
                m[i][j] = energy(i, j) + m[i][j - 1];
            } else if (i - 1 < 0 && i + 1 < w) {
                if (m[i][j - 1] < m[i + 1][j - 1]) {
                    path[i][j] = i;
                    m[i][j] = energy(i, j) + m[i][j - 1];
                } else {
                    path[i][j] = i + 1;
                    m[i][j] = energy(i, j) + m[i + 1][j - 1];
                }
            } else if (i + 1 > w - 1 && i - 1 >= 0) {
                if (m[i - 1][j - 1] < m[i][j - 1]) {
                    path[i][j] = i - 1;
                    m[i][j] = energy(i, j) + m[i - 1][j - 1];
                } else {
                    path[i][j] = i;
                    m[i][j] = energy(i, j) + m[i][j - 1];
                }
            } else {
                double min = min(m[i - 1][j - 1], m[i][j - 1], m[i + 1][j - 1]);
                if (min == m[i - 1][j - 1]) {
                    path[i][j] = i - 1;
                } else if (min == m[i][j - 1]) {
                    path[i][j] = i;
                } else {
                    path[i][j] = i + 1;
                }
                m[i][j] = energy(i, j) + min;
            }
        }
        return;
    }

    private Picture transpose(Picture p) {
        Picture tp = new Picture(h, w);
        for (int i = 0; i < w; i += 1) {
            for (int j = 0; j < h; j += 1) {
                Color color = p.get(i, j);
                tp.set(j, i, color);
            }
        }
        return tp;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        picture = transpose(picture);
        SeamCarver sc = new SeamCarver(picture);
        return sc.findVerticalSeam();
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        for (int j = 0; j < h; j += 1) {
            m(j);
        }
        vs[h - 1] = 0;
        for (int i = 0; i < w; i += 1) {
            vs[h - 1] = m[vs[h - 1]][h - 1] < m[i][h - 1] ? vs[h - 1] : i;
        }
        for (int j = h - 1; j > 0; j -= 1) {
            vs[j - 1] = path[vs[j]][j];
        }
        return vs;
    }

    // remove horizontal seam from picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam.length != w) {
            throw new IndexOutOfBoundsException();
        }
        for (int i = 0; i < w - 1; i += 1) {
            if (seam[i] - seam[i + 1] < -1 || seam[i] - seam[i + 1] > 1) {
                throw new IndexOutOfBoundsException();
            }
        }
        picture = SeamRemover.removeHorizontalSeam(picture, seam);
    }

    // remove vertical seam from picture
    public void removeVerticalSeam(int[] seam) {
        if (seam.length != h) {
            throw new IndexOutOfBoundsException();
        }
        for (int j = 0; j < h - 1; j += 1) {
            if (seam[j] - seam[j + 1] < -1 || seam[j] - seam[j + 1] > 1) {
                throw new IndexOutOfBoundsException();
            }
        }
        picture = SeamRemover.removeVerticalSeam(picture, seam);
    }
}
