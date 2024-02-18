package gg.clouke.facerec.scale;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Clouke
 * @since 18.02.2024 11:24
 * © face-recognition - All Rights Reserved
 */
public interface SplitImage {

  static List<BufferedImage> split(BufferedImage image, int splitX, int splitY) {
    List<BufferedImage> parts = new ArrayList<>();
    int w = image.getWidth();
    int h = image.getHeight();

    for (int y = 0; y < h; y += splitY) {
      for (int x = 0; x < w; x += splitX) {
        int subW = Math.min(splitX, w - x);
        int subH = Math.min(splitY, h - y);
        BufferedImage subImage = image.getSubimage(x, y, subW, subH);
        parts.add(subImage);
      }
    }

    return parts;
  }
}
