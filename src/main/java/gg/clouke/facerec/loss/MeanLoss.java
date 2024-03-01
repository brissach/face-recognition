package gg.clouke.facerec.loss;

import gg.clouke.ContextVector;

/**
 * @author Clouke
 * @since 01.03.2024 17:30
 * © face-recognition - All Rights Reserved
 */
public class MeanLoss implements LossFunction {
  @Override
  public double compute(ContextVector probabilities) {
    double sum = 0.0;
    int dim = probabilities.dimension();
    for (int i = 0; i < dim; i++)
      sum += probabilities.get(i);
    double prod = sum / dim;
    return 1.0 - prod;
  }
}
