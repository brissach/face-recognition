package gg.clouke.facerec;

import gg.clouke.ContextVector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * @author Clouke
 * @since 16.02.2024 12:10
 * © face-recognition - All Rights Reserved
 */
public class PipelineBuilder<O> {

  private final List<Function<O, O>> stages = new ArrayList<>();

  public PipelineBuilder<O> from(Collection<Function<O, O>> stages) {
    this.stages.addAll(stages);
    return this;
  }

  public PipelineBuilder<O> from(PipelineBuilder<O> builder) {
    stages.addAll(builder.stages);
    return this;
  }

  public PipelineBuilder<O> addStage(Function<O, O> stage) {
    stages.add(stage);
    return this;
  }

  @SafeVarargs
  public final PipelineBuilder<O> addStages(Function<O, O>... functions) {
    stages.addAll(Arrays.asList(functions));
    return this;
  }

  public Pipeline<O> build(Encoder<O> encoder) {
    return new Pipeline<O>() {
      @Override
      public ContextVector process(O o) {
        O out = null;
        for (Function<O, O> stage : stages)
          out = stage.apply(o);
        return encoder.encode(out);
      }

      @Override
      public Encoder<O> encoder() {
        return encoder;
      }
    };
  }

}
