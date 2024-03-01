package gg.clouke.facerec.loss;

import gg.clouke.ContextVector;

/**
 * @author Clouke
 * @since 01.03.2024 19:40
 * © face-recognition - All Rights Reserved
 */
public class OptK implements LossFunction {
  @Override
  public double compute(ContextVector probabilities) {
    double sumPowK = 0.0;

    int dim = probabilities.dimension();
    for (int k = 0; k < dim; k++) {
      double prob = probabilities.get(k);
      sumPowK += Math.pow(prob, k);
    }

    double prod = 2.0 / (sumPowK * dim) - Math.log(sumPowK);
    return prod / dim;
  }
}
