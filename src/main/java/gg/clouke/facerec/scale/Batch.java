package gg.clouke.facerec.scale;

import java.util.List;

/**
 * @author Clouke
 * @since 18.02.2024 11:10
 * © face-recognition - All Rights Reserved
 */
public class Batch<T> {

  private final List<T> elements;

  public Batch(List<T> elements) {
    this.elements = elements;
  }

  public List<T> elements() {
    return elements;
  }

  public int size() {
    return elements.size();
  }

  @Override
  public String toString() {
    return "Batch{" +
      "elements=" + elements +
      '}';
  }

  @Override
  public int hashCode() {
    return elements.hashCode();
  }
}
