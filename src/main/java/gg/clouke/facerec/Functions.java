package gg.clouke.facerec;

import java.awt.image.BufferedImage;
import java.util.function.Function;

/**
 * @author Clouke
 * @since 16.02.2024 12:25
 * © face-recognition - All Rights Reserved
 */
public class Functions {

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

}
