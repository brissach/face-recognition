package gg.clouke.facerec.model;

import gg.clouke.ContextVector;
import gg.clouke.Layer;
import gg.clouke.LayerBuilder;
import gg.clouke.PartitionLayer;
import gg.clouke.Shapes;
import gg.clouke.ShapingNetwork;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonList;

/**
 * @author Clouke
 * @since 18.02.2024 11:48
 * © face-recognition - All Rights Reserved
 */
@Deprecated
public class FacialAuthenticationModel implements TransferLearningModel {

  private static final int BASE = 256;
  private final ShapingNetwork network;
  private final int layerIndex;

  public FacialAuthenticationModel(ShapingNetwork model) {
    int target = 0;
    for (Layer layer : model.layers())
      target = Math.max(
        target,
        layer.attribute()
      );

    layerIndex = target + 1;

    model.initializeLayers(
      singletonList(
        new PartitionLayer(
          new LayerBuilder()
            .addLayer(Shapes.MAX, BASE / 2)
            .addLayer(Shapes.MIN, BASE / 2)
            .addLayer(Shapes.LOG_MEAN, BASE * 4)
            .build(),
          // we add the layer at the end of the pretrained layers
          layerIndex
        )
      ));

    network = model;
  }

  Layer authLayer() {
    return network.layers()
      .stream()
      .filter(l -> l.attribute() == layerIndex)
      .findFirst()
      .orElse(null);
  }

  public void feed(ContextVector sample) {
    network.feed(
      sample,
      layerIndex
    );
  }

  @Override
  public Map<Integer, Layer> transferLayers() {
    Map<Integer, Layer> map = new HashMap<>();
    map.put(
      layerIndex,
      authLayer()
    );
    return map;
  }

  @Deprecated
  public double predictAuth(ContextVector vector) {
    Layer recognition = network
      .layers()
      .get(0);

    double face = recognition.predict(vector);
    double w = face * face - 1 / 2.0;

    double auth = direct(vector);
    return 1.0 - auth * w - 0.01;
  }

  public double direct(ContextVector vector) {
    Layer layer = authLayer();
    return layer == null ? -1.0 : layer.predict(vector);
  }
}
