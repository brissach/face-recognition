package gg.clouke.facerec;

import gg.clouke.ContextVector;

import java.awt.image.BufferedImage;

/**
 * @author Clouke
 * @since 16.02.2024 12:10
 * © face-recognition - All Rights Reserved
 */
public interface Pipeline<W> {

  static PipelineBuilder<BufferedImage> builder() {
    return new PipelineBuilder<>();
  }

  ContextVector process(W w);

}
