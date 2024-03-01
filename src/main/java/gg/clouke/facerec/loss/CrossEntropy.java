package gg.clouke.facerec.loss;

import gg.clouke.ContextVector;

/**
 * @author Clouke
 * @since 01.03.2024 17:35
 * © face-recognition - All Rights Reserved
 */
public class CrossEntropy implements LossFunction {
  @Override
  public double compute(ContextVector probabilities) {
    int dim = probabilities.dimension();
    double crossEntropy = 0.0;
    double epsilon = 1e-15;
    for (int i = 0; i < dim; i++) {
      double prob = probabilities.get(i);
      prob = Math.max(epsilon, prob);
      crossEntropy -= Math.log(prob);
    }
    return crossEntropy / dim;
  }
}
