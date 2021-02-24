package by.katsuba.lab1.sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField cmykCtext;

    @FXML
    private TextField cmykMtext;

    @FXML
    private TextField cmykYtext;

    @FXML
    private TextField cmykKtext;

    @FXML
    private TextField labLtext;

    @FXML
    private TextField labAtext;

    @FXML
    private TextField labBtext;

    @FXML
    private TextField hsvHtext;

    @FXML
    private TextField hsvStext;

    @FXML
    private TextField hsvVtext;

    @FXML
    private ColorPicker bar;

    @FXML
    private Slider cmykC;

    @FXML
    private Slider cmykM;

    @FXML
    private Slider cmykY;

    @FXML
    private Slider cmykK;

    @FXML
    private Slider labL;

    @FXML
    private Slider labA;

    @FXML
    private Slider labB;

    @FXML
    private Slider hsvH;

    @FXML
    private Slider hsvS;

    @FXML
    private Slider hsvV;

    @FXML
    private Rectangle colorField;

    private ColorHandler handler = new ColorHandler();
    private boolean lock = false;

    @FXML
    void barAction(ActionEvent event) {
        if (!lock) {
            lock = true;
            double r = bar.getValue().getRed() * 255;
            double g = bar.getValue().getGreen() * 255;
            double b = bar.getValue().getBlue() * 255;
            if (r == 255 && g == 255 && b == 255) {
                handler.view = true;
            }
            double[] rgb = handler.checkRgb(new double[]{r, g, b});
            double[] xyz = handler.checkXyz(handler.rgbToXyz(rgb[0], rgb[1], rgb[2]));
            double[] cmyk = handler.checkCmyk(handler.rgbToCmyk(rgb[0], rgb[1], rgb[2]));
            double[] lab = handler.checkLab(handler.xyzToLab(xyz[0], xyz[1], xyz[2]));
            double[] hsv = handler.checkHsv(handler.rgbToHsv(rgb[0], rgb[1], rgb[2]));
            cmykUpdate(cmyk);
            hsvUpdate(hsv);
            labUpdate(lab);
            colorField.setFill(bar.getValue());
            lock = false;
            handler.view = false;
        }
    }

    private void cmykUpdate(double[] cmyk) {
        cmykCtext.setText(Long.toString(Math.round(cmyk[0])));
        cmykMtext.setText(Long.toString(Math.round(cmyk[1])));
        cmykYtext.setText(Long.toString(Math.round(cmyk[2])));
        cmykKtext.setText(Long.toString(Math.round(cmyk[3])));
    }

    private void labUpdate(double[] lab) {
        labLtext.setText(Long.toString(Math.round(lab[0])));
        labAtext.setText(Long.toString(Math.round(lab[1])));
        labBtext.setText(Long.toString(Math.round(lab[2])));
    }

    private void hsvUpdate(double[] xyz) {
        hsvHtext.setText(Long.toString(Math.round(xyz[0])));
        hsvStext.setText(Long.toString(Math.round(xyz[1])));
        hsvVtext.setText(Long.toString(Math.round(xyz[2])));
    }

    public void cmykTextAction(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!lock && !textField.getText().isEmpty() && !textField.getText().equals("-")) {
                lock = true;
                int c = Integer.parseInt(cmykCtext.getText());
                int m = Integer.parseInt(cmykMtext.getText());
                int y = Integer.parseInt(cmykYtext.getText());
                int k = Integer.parseInt(cmykKtext.getText());
                double[] cmyk = handler.checkCmyk(new double[]{c, m, y, k});
                double[] rgb = handler.checkRgb(handler.cmykToRgb(c / 100d, m / 100d, y / 100d, k / 100d));
                double[] xyz = handler.checkXyz(handler.rgbToXyz(rgb[0], rgb[1], rgb[2]));
                double[] lab = handler.checkLab(handler.xyzToLab(xyz[0], xyz[1], xyz[2]));
                double[] hsv = handler.checkHsv(handler.rgbToHsv(rgb[0], rgb[1], rgb[2]));
                cmykUpdate(cmyk);
                labUpdate(lab);
                hsvUpdate(hsv);
                updateColor(rgb);
                updateSliders();
                lock = false;
            }
        });
    }

    public void labTextAction(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!lock && !textField.getText().isEmpty() && !textField.getText().equals("-")) {
                lock = true;
                int l = Integer.parseInt(labLtext.getText());
                int a = Integer.parseInt(labAtext.getText());
                int b = Integer.parseInt(labBtext.getText());
                double[] lab = handler.checkLab(new double[]{l, a, b});
                double[] xyz = handler.checkXyz(handler.labToXyz(l, a, b));
                double[] rgb = handler.checkRgb(handler.xyzToRgb(xyz[0], xyz[1], xyz[2]));
                double[] cmyk = handler.checkCmyk(handler.rgbToCmyk(rgb[0], rgb[1], rgb[2]));
                double[] hsv = handler.checkHsv(handler.rgbToHsv(rgb[0], rgb[1], rgb[2]));
                cmykUpdate(cmyk);
                labUpdate(lab);
                hsvUpdate(hsv);
                updateColor(rgb);
                updateSliders();
                lock = false;
            }
        });
    }

    public void hsvTextAction(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!lock && !textField.getText().isEmpty() && !textField.getText().equals("-")) {
                lock = true;
                int h = Integer.parseInt(hsvHtext.getText());
                int s = Integer.parseInt(hsvStext.getText());
                int v = Integer.parseInt(hsvVtext.getText());
                double[] hsv = handler.checkHsv(new double[]{h, s, v});
                double[] rgb = handler.checkRgb(handler.hsvToRgb(h, s, v));
                double[] xyz = handler.checkXyz(handler.rgbToXyz(rgb[0], rgb[1], rgb[2]));
                double[] cmyk = handler.checkCmyk(handler.rgbToCmyk(rgb[0], rgb[1], rgb[2]));
                double[] lab = handler.checkLab(handler.xyzToLab(xyz[0], xyz[1], xyz[2]));
                cmykUpdate(cmyk);
                labUpdate(lab);
                hsvUpdate(hsv);
                updateColor(rgb);
                updateSliders();
                lock = false;
            }
        });
    }

    public void sliderValueToText(Slider slider, TextField textField) {
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            textField.setText(Integer.toString(Math.round(newValue.floatValue())));
        });
    }

    private void updateColor(double[] rgb) {
        bar.setValue(new Color(rgb[0] / 255d, rgb[1] / 255d, rgb[2] / 255d, 1));
        colorField.setFill(bar.getValue());
    }

    private void cmykSlider(Slider slider) {
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
    }

    private void labSlider(Slider slider) {
        if (slider.equals(labL)) {
            slider.setMin(0);
            slider.setMax(100);
        } else {
            slider.setMin(-128);
            slider.setMax(128);
        }
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
    }

    private void hsvSlider(Slider slider) {
        slider.setMin(0);
        if (slider.equals(hsvH)) {
            slider.setMax(360);
        } else if (slider.equals(hsvS)) {
            slider.setMax(100);
        } else {
            slider.setMax(100);
        }
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
    }

    private void updateSliders() {
        cmykC.setValue(Double.parseDouble(cmykCtext.getText()));
        cmykM.setValue(Double.parseDouble(cmykMtext.getText()));
        cmykY.setValue(Double.parseDouble(cmykYtext.getText()));
        cmykK.setValue(Double.parseDouble(cmykKtext.getText()));

        labL.setValue(Double.parseDouble(labLtext.getText()));
        labA.setValue(Double.parseDouble(labAtext.getText()));
        labB.setValue(Double.parseDouble(labBtext.getText()));

        hsvH.setValue(Double.parseDouble(hsvHtext.getText()));
        hsvS.setValue(Double.parseDouble(hsvStext.getText()));
        hsvV.setValue(Double.parseDouble(hsvVtext.getText()));
    }

    private void cmykText(TextField textField) {
        textField.setTextFormatter(new TextFormatter<>(
                change -> (change.getControlNewText().matches("[0-9]*")) ? change : null));
    }

    private void labText(TextField textField) {
        if (textField.equals(labLtext)) {
            textField.setTextFormatter(new TextFormatter<>(
                    change -> (change.getControlNewText().matches("[0-9]*")) ? change : null));
        } else {
            textField.setTextFormatter(new TextFormatter<>(
                    change -> (change.getControlNewText().matches("[-]?[0-9]*")) ? change : null));
        }
    }

    private void hsvText(TextField textField) {
        textField.setTextFormatter(new TextFormatter<>(
                change -> (change.getControlNewText().matches("[0-9]*")) ? change : null));
    }

    @FXML
    void initialize() {
        barAction(new ActionEvent());

        cmykSlider(cmykC);
        cmykSlider(cmykM);
        cmykSlider(cmykY);
        cmykSlider(cmykK);

        labSlider(labL);
        labSlider(labA);
        labSlider(labB);

        hsvSlider(hsvH);
        hsvSlider(hsvS);
        hsvSlider(hsvV);

        sliderValueToText(cmykC, cmykCtext);
        sliderValueToText(cmykM, cmykMtext);
        sliderValueToText(cmykY, cmykYtext);
        sliderValueToText(cmykK, cmykKtext);

        sliderValueToText(labL, labLtext);
        sliderValueToText(labA, labAtext);
        sliderValueToText(labB, labBtext);

        sliderValueToText(hsvH, hsvHtext);
        sliderValueToText(hsvS, hsvStext);
        sliderValueToText(hsvV, hsvVtext);

        cmykTextAction(cmykCtext);
        cmykTextAction(cmykMtext);
        cmykTextAction(cmykYtext);
        cmykTextAction(cmykKtext);

        hsvTextAction(hsvHtext);
        hsvTextAction(hsvStext);
        hsvTextAction(hsvVtext);

        labTextAction(labLtext);
        labTextAction(labAtext);
        labTextAction(labBtext);

        cmykText(cmykCtext);
        cmykText(cmykMtext);
        cmykText(cmykYtext);
        cmykText(cmykKtext);

        labText(labLtext);
        labText(labAtext);
        labText(labBtext);

        hsvText(hsvHtext);
        hsvText(hsvStext);
        hsvText(hsvVtext);
    }
}
