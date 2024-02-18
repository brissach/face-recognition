package gg.clouke.facerec.scale;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Clouke
 * @since 18.02.2024 11:30
 * © face-recognition - All Rights Reserved
 */
public interface Batches {

  static <T> List<Batch<T>> split(List<T> samples, int batchSize) {
    List<Batch<T>> batches = new ArrayList<>();

    int size = samples.size();
    for (int i = 0; i < size; i += Math.min(batchSize, size - i)) {
      Batch<T> batch = new Batch<>(
        samples.subList(
          i, Math.min(i + batchSize, size)
        ));

      batches.add(batch);
    }
    return batches;
  }

}
