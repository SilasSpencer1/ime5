package model;

import model.layer.ILayerModel;


/**
 * Represents an object that gets the current state of a given layer model.
 */
public class LayerModelState implements ILayerModelState {

  private final ILayerModel model;

  /**
   * Constructs an {@code LayerModelState} object which gets the data from this object's model
   * field.
   *
   * @param model the model to retrieve data from
   * @throws IllegalArgumentException if the model is null
   */
  public LayerModelState(ILayerModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null!");
    }

    this.model = model;
  }

}
