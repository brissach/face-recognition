package gg.clouke.facerec;

import gg.clouke.facerec.filter.EdgeDetection;
import gg.clouke.facerec.filter.GaussianBlur;
import gg.clouke.facerec.filter.ImageFilter;

import java.awt.image.BufferedImage;
import java.util.function.Function;

/**
 * @author Clouke
 * @since 16.02.2024 12:25
 * © face-recognition - All Rights Reserved
 */
public class Functions {

  private static final ImageFilter EDGE_DET_FILTER = new EdgeDetection();
  private static final ImageFilter GAUSSIAN_BLUR_FILTER = new GaussianBlur();

  public static final Function<BufferedImage, BufferedImage> RESCALE = image -> {
    int w = image.getWidth();
    int h = image.getHeight();

    if (!SampleSize.initiated())
      throw new RuntimeException(
        "Set the sample size using SampleSize#width(size) & SampleSize#height(size) before using the rescale function."
      );

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
  public static final Function<BufferedImage, BufferedImage> GAUSSIAN_BLUR = GAUSSIAN_BLUR_FILTER::apply;

}
