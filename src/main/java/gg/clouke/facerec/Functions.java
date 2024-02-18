package gg.clouke.facerec;

import gg.clouke.facerec.filter.EdgeDetection;

import java.awt.image.BufferedImage;
import java.util.function.Function;

/**
 * @author Clouke
 * @since 16.02.2024 12:25
 * © face-recognition - All Rights Reserved
 */
public class Functions {

  private static final EdgeDetection EDGE_DET_FILTER = new EdgeDetection();

  public static final Function<BufferedImage, BufferedImage> RESCALE = image -> {
    int w = image.getWidth();
    int h = image.getHeight();

    int sampleW = SampleSize.width();
    int sampleH = SampleSize.height();

    if (w != sampleW || h != sampleH)
      image = ImageRescale.scaleImage(
        image,
        sampleW,
        sampleH
      );

    return image;
  };

  public static final Function<BufferedImage, BufferedImage> EDGE_DETECTION = EDGE_DET_FILTER::apply;

}
