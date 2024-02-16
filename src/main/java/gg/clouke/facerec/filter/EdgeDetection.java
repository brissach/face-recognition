package gg.clouke.facerec.filter;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

/**
 * @author Clouke
 * @since 28.01.2024 13:13
 * © face-recognition - All Rights Reserved
 */
public class EdgeDetection implements ImageFilter {

  @Override
  public BufferedImage apply(BufferedImage image) {
    int w = image.getWidth();
    int h = image.getHeight();

    BufferedImage edgeImage = new BufferedImage(w, h, TYPE_INT_RGB);

    for (int y = 1; y < h - 1; y++) {
      for (int x = 1; x < w - 1; x++) {
        int[][] pMatrix = new int[3][3];

        for (int i = -1; i <= 1; i++) {
          for (int j = -1; j <= 1; j++)
            pMatrix[i + 1][j + 1] = image.getRGB(x + i, y + j) & 0xFF;
        }

        int gx = (-pMatrix[0][0] + pMatrix[0][2]
          - 2 * pMatrix[1][0] + 2 * pMatrix[1][2]
          - pMatrix[2][0] + pMatrix[2][2]);

        int gy = (-pMatrix[0][0] - 2 * pMatrix[0][1] - pMatrix[0][2]
          + pMatrix[2][0] + 2 * pMatrix[2][1] + pMatrix[2][2]);

        int gradMag = (int) Math.sqrt(gx * gx + gy * gy);
        int normMag = Math.min(255, Math.max(0, gradMag));
        Color colorized = new Color(
          normMag,
          normMag,
          normMag
        );

        edgeImage.setRGB(
          x,
          y,
          colorized.getRGB()
        );
      }
    }

    return edgeImage;
  }

}
