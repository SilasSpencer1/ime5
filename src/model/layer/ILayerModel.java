package model.layer;

import java.util.List;


import model.color.Grayscale;
import model.color.IColorTransformation;
import model.filter.IFilter;
import model.filter.Sharpening;
import model.image.IImage;


/**
 * Represents a set of layers of images that are able to be added or removed.
 */
public interface ILayerModel {

  /**
   * Creates a layer in a set of layers. If a layer with the given name already exists it will do
   * nothing. If the layer created is the first layer, it becomes the current
   *
   * @param name the name of the layer being created
   * @throws IllegalArgumentException if the given name is null or if a layer already exists with
   *                                  that name
   */
  void createImageLayer(String name) throws IllegalArgumentException;



  /**
   * Loads the given image into the current layer by setting the layer's image to the given image,
   * as long as the image's dimensions are the same as the previous images or if it is the first
   * image to be placed in a layer.
   *
   * @param image the image to be loaded into the current layer
   * @throws IllegalArgumentException if the given image is null
   */
  void loadLayer(IImage image) throws IllegalArgumentException;

  /**
   * Loads/create a multi-layered image using the given imported layers.
   *
   * @param importedLayers the multi-layers to be instantiated
   * @throws IllegalArgumentException if the given list of layers is null.
   */
  void loadAll(List<ILayer> importedLayers) throws IllegalArgumentException;

  /**
   * Sets the given layer based on its name to be the current layer to perform operations on.
   *
   * @param layerName the name of the layer to be operated on
   * @throws IllegalArgumentException if the layer name is null or if no such layer exists
   */
  void setCurrent(String layerName) throws IllegalArgumentException;

  /**
   * Creates a deep copy of the list of layers in this {@code ILayerModel}.
   *
   * @return a deep copy of the list of layers in the model.
   */
  List<ILayer> getLayers();

  /**
   * Returns the current layer, if one exists.
   *
   * @return the current layer
   * @throws IllegalArgumentException if the current layer does not exist
   */
  ILayer getCurrentLayer() throws IllegalArgumentException;


  void colorTransformCurrent(IColorTransformation colorTransformation)
          throws IllegalArgumentException;

  void filterCurrent(IFilter filter) throws IllegalArgumentException;
}


