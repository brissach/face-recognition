package gg.clouke.facerec.model;

import gg.clouke.Layer;

import java.util.Map;

/**
 * @author Clouke
 * @since 18.02.2024 11:45
 * © face-recognition - All Rights Reserved
 */
public interface TransferLearningModel {

  Map<Integer, Layer> transferLayers();

}
