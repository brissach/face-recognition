package gg.clouke.facerec;

import gg.clouke.ContextVector;

import java.awt.image.BufferedImage;

import static gg.clouke.facerec.Functions.*;

/**
 * @author Clouke
 * @since 16.02.2024 12:10
 * © face-recognition - All Rights Reserved
 */
public interface Pipeline<W> {

  static Pipeline<BufferedImage> plain() {
    return builder()
      .addStages(
        EDGE_DETECTION,
        GAUSSIAN_BLUR,
        RESCALE
      )
      .build(new ImageEncoder());
  }

  static PipelineBuilder<BufferedImage> builder() {
    return new PipelineBuilder<>();
  }

  ContextVector process(W w);

  Encoder<W> encoder();

}
