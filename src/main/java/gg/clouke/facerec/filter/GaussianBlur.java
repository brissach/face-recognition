package gg.clouke.facerec.filter;

import java.awt.image.BufferedImage;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * @author Clouke
 * @since 18.02.2024 13:09
 * © face-recognition - All Rights Reserved
 */
public class GaussianBlur implements ImageFilter {

  private static final double[][] KERNEL = {
    {0.0625, 0.125, 0.0625},
    {0.125, 0.25, 0.125},
    {0.0625, 0.125, 0.0625}
  };

  @Override
  public BufferedImage apply(BufferedImage image) {
    int width = image.getWidth();
    int height = image.getHeight();
    BufferedImage blur = new BufferedImage(
      width,
      height,
      BufferedImage.TYPE_INT_RGB
    );

    for (int y = 1; y < height - 1; y++) {
      for (int x = 1; x < width - 1; x++) {
        int red = 0;
        int green = 0;
        int blue = 0;
        for (int ky = -1; ky <= 1; ky++) {
          for (int kx = -1; kx <= 1; kx++) {
            int pixel = image.getRGB(x + kx, y + ky);
            int intensity = (pixel >> 16) & 0xFF;
            red += (int) (intensity * KERNEL[ky + 1][kx + 1]);
            green += (int) (intensity * KERNEL[ky + 1][kx + 1]);
            blue += (int) (intensity * KERNEL[ky + 1][kx + 1]);
          }
        }

        int pxl = (clamp(red) << 16) | (clamp(green) << 8) | clamp(blue);
        blur.setRGB(x, y, pxl);
      }
    }

    return blur;
  }

  static int clamp(int value) {
    return max(0, min(value, 255));
  }

}
