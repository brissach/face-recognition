package gg.clouke.facerec;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import java.util.List;

/**
 * @author Clouke
 * @since 29.02.2024 12:18
 * © face-recognition - All Rights Reserved
 */
public class DataSet {

  private final List<BufferedImage> samples;
  private final List<BufferedImage> validation;
  private final double splitRatio;

  public DataSet(List<BufferedImage> images, double splitRatio) {
    samples = new ArrayList<>();
    validation = new ArrayList<>();
    this.splitRatio = splitRatio;

    int size = images.size();
    int numSamples = (int) (size * splitRatio);
    for (int i = 0; i < size; i++) {
      if (i < numSamples) samples.add(images.get(i));
      else validation.add(images.get(i));
    }
  }

  public DataSet(List<BufferedImage> samples, List<BufferedImage> validation) {
    double x = samples.size();
    double y = validation.size();
    this.samples = samples;
    this.validation = validation;
    splitRatio = x / y;
  }

  public DataSet(List<BufferedImage> images) {
    this(images, 0.8);
  }

  public List<BufferedImage> samples() {
    return samples;
  }

  public List<BufferedImage> validation() {
    return validation;
  }

  public double splitRatio() {
    return splitRatio;
  }

}

