package model.layer;

import java.util.ArrayList;
import java.util.List;

import model.IModel;
import model.Model;
import model.color.IColorTransformation;
import model.filter.IFilter;
import model.filter.Sharpening;
import model.image.IImage;


/**
 * Represents a group of layers of {@code IImage} that can add/remove images from the group of
 * layers and apply different image/layer operations such as filtering/color transforming,
 * loading/saving multi-layered images.
 */
public class LayerModel implements ILayerModel {

  private final IModel delegate;
  private final List<ILayer> layers;
  private int currentLayerNum;
  private static int count;

  /**
   * Constructs a {@code Layers} object with an empty ArrayList.
   */
  public LayerModel() {
    this.delegate = new Model();
    this.layers = new ArrayList<>();
    currentLayerNum = -1;
    count = 0;
  }

  /**
   *
   * @param name the name of the layer being created
   * @throws IllegalArgumentException
   */

  @Override
  public void createImageLayer(String name) throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null!");
    }

    if (this.getIndexBasedOnName(name) == -1) {
      if (layers.isEmpty()) {
        currentLayerNum = 0;
      }
      this.layers.add(new Layer(name));
    } else {
      throw new IllegalArgumentException("Layer already exists!");
    }
  }



  /**
   * Produces the number of layers in the multi-layered image.
   */
  private int getNumberOfLayers() {
    return this.layers.size();
  }

  @Override
  public void loadLayer(IImage image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("Image is null");
    }

    if (this.currentLayerNum == -1) {
      throw new IllegalArgumentException("No current layer exists!");
    }

    if (sameDimensions(image)) {
      this.layers.get(currentLayerNum).setImage(image);
    } else {
      throw new IllegalArgumentException("Image(s) are not the same dimension!");
    }
  }

  @Override
  public void loadAll(List<ILayer> importedLayers) throws IllegalArgumentException {
    if (importedLayers == null) {
      throw new IllegalArgumentException("Null layers.");
    }

    boolean layersEmptyBefore = this.layers.isEmpty();

    for (ILayer importedLayer : importedLayers) {
      // adds images if they are all the same dimension
      if (importedLayer.getImage() == null) {
        this.layers.add(importedLayer);
      } else if (this.sameDimensions(importedLayer.getImage())) {
        this.layers.add(importedLayer);
      } else {
        throw new IllegalArgumentException("Image(s) are not the same dimension!");
      }
    }

    if (layersEmptyBefore && this.layers.size() != 0) {
      this.currentLayerNum = 0;
    }
  }

  /**
   * Determines whether or not the given image has the same dimensions as the first image in the
   * list of layers. If there are no layers in the list of layers, return true.
   *
   * @param image the image to be checked if the dimensions are appropriate
   * @return true if the given image has the same dimensions as the first or if the image is the
   *         first layer in the list of layers
   */
  private boolean sameDimensions(IImage image) {
    if (!layers.isEmpty()) {

      if (layers.size() == 1) {
        if (layers.get(0).getImage() == null) {
          return true;
        }

        int firstImageHeight = this.layers.get(0).getImage().getHeight();
        int firstImageWidth = this.layers.get(0).getImage().getWidth();

        return (firstImageWidth == image.getWidth()) && (firstImageHeight == image.getHeight());
      }

      for (ILayer layer : layers) {
        if (layer.getImage() != null) {
          int height1 = layer.getImage().getHeight();
          int width1 = layer.getImage().getHeight();
          int height2 = image.getHeight();
          int width2 = image.getHeight();

          if ((height1 != height2) || (width1 != width2)) {
            return false;
          }
        }
      }
    }
    return true;
  }

  @Override
  public void setCurrent(String layerName) {
    if (layerName == null) {
      throw new IllegalArgumentException("Layer name cannot be null!");
    }

    if (this.getIndexBasedOnName(layerName) != -1) {
      this.currentLayerNum = this.getIndexBasedOnName(layerName);
    } else {
      throw new IllegalArgumentException("Current cannot be set!");
    }
  }

  /**
   * Determines if the blur, gray, sepia, or sharpen can be applied to an image by checking if the
   * current layer has an image and that the layer is visible.
   *
   * @return true if the current layer has an image and that it is visible.
   */
  private boolean canApplyOperation() {
    return (this.currentLayerNum != -1 && this.layers.get(this.currentLayerNum).getImage() != null
        && this.layers.get(this.currentLayerNum).isVisible());
  }



  /**
   * Gets the index of the layer based on the name. Returns -1 if no such layer exists.
   *
   * @param layerName the name of the desired layer
   * @return The index of the desired layer based on its name. Returns -1 if no such layer exists.
   */
  private int getIndexBasedOnName(String layerName) {
    for (int i = 0; i < layers.size(); i++) {
      if (layers.get(i).getName().equals(layerName)) {
        return i;
      }
    }
    return -1;
  }


  @Override
  public List<ILayer> getLayers() {
    List<ILayer> newList = new ArrayList<>();

    for (ILayer iLayer : layers) {
      ILayer layer = new Layer(iLayer.getName());
      layer.setVisibility(iLayer.isVisible());
      if (iLayer.getImage() != null) {
        layer.setImage(iLayer.getImage());
      }

      newList.add(layer);
    }

    return newList;
  }

  @Override
  public ILayer getCurrentLayer() throws IllegalArgumentException {
    if (currentLayerNum < 0 || currentLayerNum > layers.size() - 1) {
      throw new IllegalArgumentException("There is no current layer!");
    }

    return this.layers.get(currentLayerNum);
  }

  @Override
  public void colorTransformCurrent(IColorTransformation colorTransformation)
          throws IllegalArgumentException {
    if (colorTransformation == null) {
      throw new IllegalArgumentException("Filter cannot be null!");
    }

    if (colorTransformation.isGrayscale()) {

    } else if (colorTransformation.isGrayscaleBlue()) {

    } else if (colorTransformation.isGrayscaleGreen()) {

    } else if (colorTransformation.isGrayscaleRed()) {

    } else if (colorTransformation.isGrayscaleLuma()) {

    } else if (colorTransformation.isGrayscaleValue()) {

    } else if (colorTransformation.isGrayscaleIntensity()) {

    } else {
      // Placeholder for nothing
    }

    if (canApplyOperation()) {
      IImage image = this.delegate
              .colorTransformation(this.layers.get(currentLayerNum).getImage(),
                      colorTransformation);
      this.layers.get(this.currentLayerNum).setImage(image);
    } else {
      throw new IllegalArgumentException(
              "Operation cannot be performed! Image must be visible and present.");
    }
  }


  @Override
  public void filterCurrent(IFilter filter) throws IllegalArgumentException {
    if (filter == null) {
      throw new IllegalArgumentException("Filter cannot be null!");
    }

    if (this.currentLayerNum != -1 && canApplyOperation()) {
      IImage image = this.delegate.filter(this.layers.get(currentLayerNum).getImage(), filter);
      this.layers.get(this.currentLayerNum).setImage(image);
    } else {
      throw new IllegalArgumentException(
              "Operation cannot be performed! Image must be visible and present.");
    }
  }

  @Override
  public String toString() {
    StringBuilder newString = new StringBuilder();

    for (int i = 0; i < this.layers.size(); i++) {
      newString.append("Layer #").append(i + 1).append(", ").append(this.layers.get(i).toString())
          .append("\n");
    }

    if (currentLayerNum == -1) {
      return newString + "Number of valid layers created: " + this.getNumberOfLayers()
          + "\nCurrent not yet set.\n";
    } else {
      return newString + "Number of valid layers created: " + this.getNumberOfLayers()
          + "\nCurrent Layer: " + this.layers.get(currentLayerNum).toString() + "\n";
    }
  }


}
