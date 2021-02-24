package by.katsuba.lab1.sample;

import javafx.scene.control.Alert;

public class ColorHandler {
    private final double Xw = 95.047;
    private final double Yw = 100;
    private final double Zw = 108.883;
    public boolean view = false;

    private void viewDialog() {
        if (!view) {
            view = true;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Turned out to go beyond the limits of change of the calculated parameter. Truncation / rounding has occurred.");
            alert.showAndWait();
            view = false;
        } else {
            return;
        }
    }

    public double[] checkRgb(double[] rgb) {
        for (int i = 0; i < rgb.length; i++) {
            if (rgb[i] > 255) {
                viewDialog();
                rgb[i] = 255;
            } else if (rgb[i] < 0) {
                viewDialog();
                rgb[i] = 0;
            }
        }
        return rgb;
    }

    public double[] checkCmyk(double[] cmyk) {
        for (int i = 0; i < cmyk.length; i++) {
            if (cmyk[i] > 100) {
                viewDialog();
                cmyk[i] = 100;
            } else if (cmyk[i] < 0) {
                viewDialog();
                cmyk[i] = 0;
            }
        }
        return cmyk;
    }

    public double[] checkHsv(double[] hsv) {
        if (hsv[0] > 360) {
            viewDialog();
            hsv[0] = 360;
        } else if (hsv[1] < 0) {
            viewDialog();
            hsv[0] = 0;
        }
        if (hsv[1] > 100) {
            viewDialog();
            hsv[1] = 100;
        } else if (hsv[1] < 0) {
            viewDialog();
            hsv[1] = 0;
        }
        if (hsv[2] > 100) {
            viewDialog();
            hsv[2] = 100;
        } else if (hsv[2] < 0) {
            viewDialog();
            hsv[2] = 0;
        }
        return hsv;
    }

    public double[] checkLab(double[] lab) {
        if (lab[0] > 100) {
            viewDialog();
            lab[0] = 100;
        } else if (lab[1] < 0) {
            viewDialog();
            lab[0] = 0;
        }
        if (lab[1] > 128) {
            viewDialog();
            lab[1] = 128;
        } else if (lab[1] < -128) {
            viewDialog();
            lab[1] = -128;
        }
        if (lab[2] > 128) {
            viewDialog();
            lab[2] = 128;
        } else if (lab[2] < -128) {
            viewDialog();
            lab[2] = -128;
        }
        return lab;
    }

    public double[] checkXyz(double[] xyz) {
        if (xyz[0] > 95) {
            viewDialog();
            xyz[0] = 95;
        } else if (xyz[1] < 0) {
            viewDialog();
            xyz[0] = 0;
        }
        if (xyz[1] > 100) {
            viewDialog();
            xyz[1] = 100;
        } else if (xyz[1] < 0) {
            viewDialog();
            xyz[1] = 0;
        }
        if (xyz[2] > 108) {
            viewDialog();
            xyz[2] = 108;
        } else if (xyz[2] < 0) {
            viewDialog();
            xyz[2] = 0;
        }
        return xyz;
    }

    public double[] cmykToRgb(double c, double m, double y, double k) {
        double[] rgb = new double[3];
        rgb[0] = 255 * (1 - c) * (1 - k);
        rgb[1] = 255 * (1 - m) * (1 - k);
        rgb[2] = 255 * (1 - y) * (1 - k);
        return rgb;
    }

    public double[] rgbToCmyk(double r, double g, double b) {
        double[] cmyk = new double[4];
        cmyk[3] = Math.min(Math.min(1 - r / 255d, 1 - g / 255d), 1 - b / 255d);
        cmyk[0] = 100 * (1 - r / 255d - cmyk[3]) / (1 - cmyk[3]);
        cmyk[1] = 100 * (1 - g / 255d - cmyk[3]) / (1 - cmyk[3]);
        cmyk[2] = 100 * (1 - b / 255d - cmyk[3]) / (1 - cmyk[3]);
        cmyk[3] *= 100;
        return cmyk;
    }

    public double[] hsvToRgb(double h, double s, double v) {
        double[] rgb = new double[3];
        int Hi = Math.floorDiv((int) h, 60) % 6;
        double Vmin = (100 - s) * v / 100;
        double a = (v - Vmin) * (h % 60) / 60;
        double Vinc = Vmin + a;
        double Vdec = v - a;
        if (Hi == 0) {
            rgb[0] = v;
            rgb[1] = Vinc;
            rgb[2] = Vmin;
        } else if (Hi == 1) {
            rgb[0] = Vdec;
            rgb[1] = v;
            rgb[2] = Vmin;
        } else if (Hi == 2) {
            rgb[0] = Vmin;
            rgb[1] = v;
            rgb[2] = Vinc;
        } else if (Hi == 3) {
            rgb[0] = Vmin;
            rgb[1] = Vdec;
            rgb[2] = v;
        } else if (Hi == 4) {
            rgb[0] = Vinc;
            rgb[1] = Vmin;
            rgb[2] = v;
        } else if (Hi == 5) {
            rgb[0] = v;
            rgb[1] = Vmin;
            rgb[2] = Vdec;
        }
        return rgb;
    }

    public double[] rgbToHsv(double r, double g, double b) {
        r /= 255;
        g /= 255;
        b /= 255;
        double[] hsv = new double[3];
        double max = Math.max(Math.max(r, g), b);
        double min = Math.min(Math.min(r, g), b);
        if (max == r && g >= b) {
            hsv[0] = 60 * (g - b) / (max - min);
        } else if (max == r && g < b) {
            hsv[0] = 60 * (g - b) / (max - min) + 360;
        } else if (max == g) {
            hsv[0] = 60 * (b - r) / (max - min) + 120;
        } else if (max == b) {
            hsv[0] = 60 * (r - g) / (max - min) + 240;
        }
        if (max == 0) {
            hsv[1] = 0;
        } else {
            hsv[1] = 100 * (1 - min / max);
        }
        hsv[2] = 100 * max;
        return hsv;
    }

    public double[] xyzToLab(double x, double y, double z) {
        double[] lab = new double[3];
        lab[0] = 116 * funcXyzLab(y / Yw) - 16;
        lab[1] = 500 * (funcXyzLab(x / Xw) - funcXyzLab(y / Yw));
        lab[2] = 200 * (funcXyzLab(y / Yw) - funcXyzLab(z / Zw));
        return lab;
    }

    public double[] labToXyz(double l, double a, double b) {
        double[] xyz = new double[3];
        xyz[1] = funcLabXyz((l + 16) / 116) * Xw;
        xyz[0] = funcLabXyz(a / 500 + (l + 16) / 116) * Yw;
        xyz[2] = funcLabXyz((l + 16) / 116 - b / 200) * Zw;
        return xyz;
    }

    private double funcXyzLab(double x) {
        double res;
        if (x >= 0.008856) {
            res = Math.pow(x, 1d / 3);
        } else {
            res = 7.787 * x + 16d / 116;
        }
        return res;
    }

    private double funcLabXyz(double x) {
        double res;
        if (x >= 0.008856) {
            res = Math.pow(x, 3);
        } else {
            res = (x - 16d / 116) / 7.787;
        }
        return res;
    }

    public double[] xyzToRgb(double x, double y, double z) {
        double[] rgb = new double[3];
        double Rn = 3.2406 * x / 100 - 1.5372 * y / 100 - 0.4986 * z / 100;
        double Gn = -0.9689 * x / 100 + 1.8758 * y / 100 + 0.0415 * z / 100;
        double Bn = 0.0557 * x / 100 - 0.2040 * y / 100 + 1.0570 * z / 100;
        rgb[0] = funcXyzRgb(Rn) * 255;
        rgb[1] = funcXyzRgb(Gn) * 255;
        rgb[2] = funcXyzRgb(Bn) * 255;
        return rgb;
    }

    public double[] rgbToXyz(double r, double g, double b) {
        double[] xyz = new double[3];
        double Rn = funcRgbXyz(r / 255) * 100;
        double Gn = funcRgbXyz(g / 255) * 100;
        double Bn = funcRgbXyz(b / 255) * 100;
        xyz[0] = 0.412453 * Rn + 0.357580 * Gn + 0.180423 * Bn;
        xyz[1] = 0.212671 * Rn + 0.715160 * Gn + 0.072169 * Bn;
        xyz[2] = 0.019334 * Rn + 0.119193 * Gn + 0.950227 * Bn;
        return xyz;
    }

    private double funcXyzRgb(double x) {
        double res;
        if (x >= 0.0031308) {
            res = 1.055 * Math.pow(x, 1d / 2.4) - 0.055;
        } else {
            res = 12.92 * x;
        }
        return res;
    }

    private double funcRgbXyz(double x) {
        double res;
        if (x >= 0.04045) {
            res = Math.pow((x + 0.055) / 1.055, 2.4);
        } else {
            res = x / 12.92;
        }
        return res;
    }
}
