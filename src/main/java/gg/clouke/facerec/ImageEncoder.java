package gg.clouke.facerec;

import gg.clouke.ContextVector;

import java.awt.image.BufferedImage;

/**
 * @author Clouke
 * @since 16.02.2024 12:09
 * © face-recognition - All Rights Reserved
 */
public class ImageEncoder implements Encoder<BufferedImage> {

  @Override
  public ContextVector encode(BufferedImage image) {
    int w = image.getWidth();
    int h = image.getHeight();

    double[] ctx = new double[w * h];

    int idx = 0;
    for (int y = 0; y < h; y++) {
      for (int x = 0; x < w; x++) {
        int rgb = image.getRGB(x, y);
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;
        double norm = ((red + green + blue) / 3.0) / 255.0;
        ctx[idx++] = norm;
      }
    }

    return new ContextVector(ctx);
  }
}
