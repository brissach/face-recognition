package gg.clouke.facerec.loss;

/**
 * @author Clouke
 * @since 01.03.2024 17:48
 * © face-recognition - All Rights Reserved
 */
public enum LossFunctions {
  CROSS_ENTROPY(new CrossEntropy()),
  KL_DIVERGENCE(new KLDivergence()),
  MEAN(new MeanLoss()),
  QUADRATIC(new QuadraticLoss()),
  OPT_K(new OptK());

  private final LossFunction function;

  LossFunctions(LossFunction function) {
    this.function = function;
  }

  public LossFunction function() {
    return function;
  }

  public static LossFunctions from(String name) {
    return valueOf(name.toUpperCase());
  }
}
