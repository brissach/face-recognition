package gg.clouke.facerec.filter;

import java.awt.image.BufferedImage;

/**
 * @author Clouke
 * @since 16.02.2024 12:08
 * © face-recognition - All Rights Reserved
 */
public interface ImageFilter {
  BufferedImage apply(BufferedImage image);
}
