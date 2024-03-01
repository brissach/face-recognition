package gg.clouke.facerec.scale;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Clouke
 * @since 01.03.2024 14:29
 * © face-recognition - All Rights Reserved
 */
public class BatchIterator<T> implements Iterable<Batch<T>> {

  private final List<Batch<T>> batches;

  public BatchIterator(List<Batch<T>> batches) {
    this.batches = batches;
  }

  public void iterate(Consumer<Batch<T>> action) {
    for (Batch<T> b : this)
      action.accept(b);
  }

  public int size() {
    return batches.size();
  }

  @Override @Nonnull
  public Iterator<Batch<T>> iterator() {
    return batches.iterator();
  }
}
