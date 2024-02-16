package gg.clouke.facerec;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

/**
 * @author Clouke
 * @since 28.01.2024 13:09
 * © face-recognition - All Rights Reserved
 */
public class ImageRescale {

  public static BufferedImage scaleImage(BufferedImage img, int tW, int tH) {
    int w = img.getWidth();
    int h = img.getHeight();

    double xScale = (double) tW / w;
    double yScale = (double) tH / h;

    AffineTransform transformer = new AffineTransform();
    transformer.scale(
      xScale,
      yScale
    );

    BufferedImage scaledImage = new BufferedImage(
      tW,
      tH,
      TYPE_INT_RGB
    );

    Graphics2D g2d = scaledImage.createGraphics();

    g2d.drawImage(
      img,
      transformer,
      null
    );

    g2d.dispose(); // close graphics ctx

    return scaledImage;
  }

}
