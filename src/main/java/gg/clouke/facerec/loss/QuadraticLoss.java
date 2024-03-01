package gg.clouke.facerec.loss;

import gg.clouke.ContextVector;

/**
 * @author Clouke
 * @since 01.03.2024 17:33
 * © face-recognition - All Rights Reserved
 */
public class QuadraticLoss implements LossFunction {
  @Override
  public double compute(ContextVector probabilities) {
    int dim = probabilities.dimension();
    double sumSquares = 0.0;
    for (int i = 0; i < dim; i++) {
      double prob = probabilities.get(i);
      sumSquares += Math.pow(prob, 2);
    }
    double prod = Math.sqrt(sumSquares / dim);
    return 1.0 - prod;
  }
}
