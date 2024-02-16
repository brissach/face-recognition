package gg.clouke.facerec;

import gg.clouke.ContextVector;

/**
 * @author Clouke
 * @since 28.01.2024 13:26
 * © face-recognition - All Rights Reserved
 */
public interface Encoder<W> {
  ContextVector encode(W image);
}
