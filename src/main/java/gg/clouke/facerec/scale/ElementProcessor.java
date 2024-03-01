package gg.clouke.facerec.scale;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author Clouke
 * @since 20.02.2024 20:02
 * © face-recognition - All Rights Reserved
 */
public interface ElementProcessor<T> {

  @Nonnull
  List<T> elements();

  default int size() {
    return elements().size();
  }

}
