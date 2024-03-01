package gg.clouke.facerec;

import gg.clouke.ContextVector;

import java.awt.image.BufferedImage;

/**
 * @author Clouke
 * @since 28.01.2024 13:26
 * © face-recognition - All Rights Reserved
 */
public interface Encoder<W> {

  static Encoder<BufferedImage> standard() {
    return new ImageEncoder();
  }

  ContextVector encode(W image);
}
