package gg.clouke.facerec.model;

import gg.acai.acava.annotated.Use;
import gg.acai.acava.commons.graph.Graph;
import gg.clouke.ContextVector;
import gg.clouke.ShapingNetwork;
import gg.clouke.ShapingNetworkBuilder;
import gg.clouke.facerec.DataSet;
import gg.clouke.facerec.Pipeline;
import gg.clouke.facerec.loss.LossFunction;
import gg.clouke.facerec.scale.BatchIterator;
import gg.clouke.facerec.scale.Batches;
import gg.clouke.facerec.scale.ElementProcessor;

import java.awt.image.BufferedImage;

/**
 * @author Clouke
 * @since 20.02.2024 20:13
 * © face-recognition - All Rights Reserved
 */
@Use("Build using FaceRecognitionModel#builder()")
public class FaceRecognitionModel {

  public static FaceRecognitionBuilder builder() {
    return new FaceRecognitionBuilder();
  }

  private final Pipeline<BufferedImage> pipeline;
  private final ShapingNetwork network;
  private final LossFunction lossFunction;
  private final Graph<Double> graph;
  private final int epochModulo;
  private final int batchSpliterator;
  private final int progressModulo;

  private int epochs;
  private double minLoss = Double.MAX_VALUE;
  private double maxLoss = Double.MIN_VALUE;
  private double lossDecreaseRate;

  FaceRecognitionModel(FaceRecognitionBuilder builder) {
    ShapingNetwork pretrained = builder.pretrained;
    network = pretrained != null ? pretrained:
      new ShapingNetworkBuilder()
        .connect(builder.layerBuilder.attribute(0))
        .build();

    pipeline = builder.pipeline;
    batchSpliterator = builder.batchSpliterator;
    lossFunction = builder.lossFunction;
    graph = builder.graph;
    epochModulo = builder.epochModulo;
    progressModulo = builder.progressModulo;
  }

  public void train(DataSet set, int epochs) {
    BatchIterator<BufferedImage> s_batches = Batches.split(
      set.samples(),
      batchSpliterator
    );

    BatchIterator<BufferedImage> v_batches = Batches.split(
      set.validation(),
      (int) (batchSpliterator / (1. + (set.splitRatio() / 2)))
    );

    long minTPB = Long.MAX_VALUE, maxTPB = Long.MIN_VALUE;
    for (int epoch = 0; epoch < epochs; epoch++) {
      long timePerBatch = 0L;
      double avgBatchLoss = 0;
      double avgValLoss = 0;
      for (ElementProcessor<BufferedImage> s_batch : s_batches) {
        long pre = tick();
        double batchLoss = forwardPass(s_batch, true);
        long post = tick();
        long diff = post - pre;
        timePerBatch += diff;
        minTPB = Math.min(minTPB, diff);
        maxTPB = Math.max(maxTPB, diff);
        avgBatchLoss += batchLoss;
      }

      for (ElementProcessor<BufferedImage> v_batch : v_batches) {
        double valLoss = forwardPass(v_batch, false);
        avgValLoss += valLoss;
      }

      if (epoch % progressModulo == 0) {
        long timePerSample = timePerBatch / batchSpliterator;
        int total = epoch * batchSpliterator;

        avgBatchLoss /= s_batches.size();
        timePerBatch /= s_batches.size();
        avgValLoss /= v_batches.size();

        boolean displayGraph = false;
        if (graph != null) {
          double scaled_loss = avgBatchLoss * 100.0;
          if (!Double.isNaN(scaled_loss)) {
            graph.addNode(scaled_loss);
            displayGraph = epoch % epochModulo == 0;
          }
        }

        System.out.println("=======================================");
        System.out.println("  epoch=" + epoch + " (" + total + ") / " + batchSpliterator);
        System.out.println("  time/batch=" + timePerBatch + "ms (" + minTPB + "/" + maxTPB + "ms)");
        System.out.println("  time/sample=" + timePerSample + "ms");
        if (!Double.isNaN(avgBatchLoss)) System.out.println("  loss=" + avgBatchLoss + " (" + String.format("%.2f%%", avgBatchLoss * 100) + ")");
        if (!Double.isNaN(avgValLoss)) System.out.println("  val=" + avgValLoss + " (" + String.format("%.2f%%", avgValLoss * 100) + ")");
        System.out.println("=======================================");
        System.out.println();
        if (displayGraph)
          System.out.println(graph.getVisualizer().toString());
      }
    }
  }

  double forwardPass(ElementProcessor<BufferedImage> batch, boolean feed) {
    ContextVector vector = ContextVector.newVector();
    batch.elements()
      .forEach(entry -> {
        if (feed) feed(entry);
        double prob = predict(entry);
        vector.addValueToVector(prob);
      });

    return lossFunction.compute(vector);
  }

  public ShapingNetwork model() {
    return network;
  }

  void feed(BufferedImage sample) {
    network.feed(
      pipeline.process(sample),
      0
    );
  }

  public double predict(BufferedImage image) {
    double[] prediction = network.predict(
      pipeline.process(image)
    );

    return prediction[0];
  }

  public TransferLearningModel transfer(Class<? extends TransferLearningModel> type)
    throws RuntimeException {
      TransferLearningModel tlm;
      try {
        tlm = type.getConstructor(ShapingNetwork.class).newInstance(network);
      } catch (Exception e) {
        throw new RuntimeException(
          "Could not create instance of " + type.getSimpleName() + " - Requires 'ShapingNetwork' in the constructor parameters"
        );
      }
      return tlm;
    }

  long tick() {
    return System.currentTimeMillis();
  }
}
