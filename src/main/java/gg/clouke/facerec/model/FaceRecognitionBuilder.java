package gg.clouke.facerec.model;

import gg.acai.acava.collect.Mutability;
import gg.acai.acava.commons.graph.Graph;
import gg.clouke.LayerBuilder;
import gg.clouke.Shapes;
import gg.clouke.ShapingNetwork;
import gg.clouke.facerec.FaceFeatures;
import gg.clouke.facerec.loss.LossFunction;
import gg.clouke.facerec.Pipeline;
import gg.clouke.facerec.SampleSize;
import gg.clouke.facerec.loss.LossFunctions;

import java.awt.image.BufferedImage;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Clouke
 * @since 28.02.2024 13:03
 * © face-recognition - All Rights Reserved
 */
public class FaceRecognitionBuilder {

  protected Pipeline<BufferedImage> pipeline = Pipeline.plain();
  protected ShapingNetwork pretrained;
  protected LossFunction lossFunction = LossFunctions.CROSS_ENTROPY.function();
  protected LayerBuilder layerBuilder = new LayerBuilder()
    .addLayer(Shapes.LOG_MEAN, 256);
  protected double validationSplit = 0.8;
  protected final Map<FaceFeatures, Map.Entry<Integer, Shapes>> linearFaceFeatures = new HashMap<>();
  protected int stagnationBuffer = -1;
  protected int batchSpliterator = 20;
  protected Graph<Double> graph;
  protected int epochModulo = -1;
  protected int progressModulo = 5;

  public FaceRecognitionBuilder pipeline(Pipeline<BufferedImage> pipeline) {
    this.pipeline = pipeline;
    return this;
  }

  public FaceRecognitionBuilder stagnationBuffer(int n) {
    stagnationBuffer = n;
    return this;
  }

  public FaceRecognitionBuilder validationSplit(double ratio) {
    validationSplit = ratio;
    return this;
  }

  public FaceRecognitionBuilder from(ShapingNetwork model) {
    pretrained = model;
    return this;
  }

  public FaceRecognitionBuilder withLayer(LayerBuilder layerBuilder) {
    this.layerBuilder = layerBuilder;
    return this;
  }

  public FaceRecognitionBuilder lossFunction(LossFunction lossFunction) {
    this.lossFunction = lossFunction;
    return this;
  }

  public FaceRecognitionBuilder lossFunction(LossFunctions lossFunction) {
    return lossFunction(lossFunction.function());
  }

  public FaceRecognitionBuilder lossFunction(String lossFunction) {
    return lossFunction(LossFunctions.from(lossFunction));
  }

  public FaceRecognitionBuilder progressModulo(int mod) {
    progressModulo = mod;
    return this;
  }

  @Deprecated
  public FaceRecognitionBuilder features(int n, Shapes shape, FaceFeatures... capture) {
    n = Math.max(n, 1);
    for (FaceFeatures feature : capture) {
      Map.Entry<Integer, Shapes> entry = new AbstractMap.SimpleEntry<>(n, shape);
      linearFaceFeatures.put(
        feature,
        entry
      );
    }
    return this;
  }

  @Deprecated
  public FaceRecognitionBuilder features(int n, FaceFeatures... capture) {
    return features(n, Shapes.LINEAR, capture);
  }

  @Deprecated
  public FaceRecognitionBuilder withFeature(int nodes, FaceFeatures feature) {
    return features(nodes, feature);
  }

  public FaceRecognitionBuilder batchSpliterator(int batchSize) {
    batchSpliterator = batchSize;
    return this;
  }

  public FaceRecognitionBuilder displayGraph(int epochModulo) {
    graph = Graph.newBuilder()
      .setMutability(Mutability.MUTABLE)
      .build();

    this.epochModulo = Math.max(1, epochModulo);
    return this;
  }

  public FaceRecognitionBuilder sampleSize(int w, int h) {
    SampleSize.width(w);
    SampleSize.height(h);
    return this;
  }

  public FaceRecognitionBuilder sampleSize(int x) {
    return sampleSize(x, x);
  }

  public FaceRecognitionModel build() {
    return new FaceRecognitionModel(this);
  }

}
