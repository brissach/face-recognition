package gg.clouke.facerec.loss;

import gg.clouke.ContextVector;

/**
 * @author Clouke
 * @since 01.03.2024 17:16
 * © face-recognition - All Rights Reserved
 */
public interface LossFunction {
  double compute(ContextVector probabilities);
}
