package gg.clouke.facerec;

import gg.clouke.ContextVector;

import java.awt.image.BufferedImage;

/**
 * @author Clouke
 * @since 16.02.2024 12:10
 * © face-recognition - All Rights Reserved
 */
public interface Pipeline {
  ContextVector process(BufferedImage image);
}
