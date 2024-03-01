package gg.clouke.facerec.loss;

import gg.clouke.ContextVector;

/**
 * @author Clouke
 * @since 01.03.2024 17:35
 * © face-recognition - All Rights Reserved
 */
public class KLDivergence implements LossFunction {
  @Override
  public double compute(ContextVector probabilities) {
    int dim = probabilities.dimension();
    double kld = 0.0;
    for (int i = 0; i < dim; i++) {
      double prob = probabilities.get(i);
      kld += prob * Math.log(prob);
    }
    return -kld / dim;
  }
}
