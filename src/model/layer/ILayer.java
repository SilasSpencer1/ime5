package model.layer;

import model.filter.IFilter;
import model.image.IImage;
import model.image.Image;

/**
 * Represents a Layer which has a name, image associated with it, and a visibility setting. It is a
 * part of a multi-layered image.
 */
public interface ILayer {

  /**
   * Sets the image of this layer to be the given image.
   *
   * @throws IllegalArgumentException if the given image is null.
   */
  void setImage(IImage image) throws IllegalArgumentException;

  void flippedImageH();

  void flippedImageV();

  void grayscaleBlueComponent();





  /**
   * Changes the current visibility to be the given visibility.
   */
  void setVisibility(boolean isVisible);

  /**
   * Gets this layer's name.
   *
   * @return this layer's name
   */
  String getName();

  /**
   * Gets this layer's image.
   *
   * @return this layer's image
   */
  IImage getImage();

  /**
   * Determines if this layer is visible.
   *
   * @return true if the layer is visible and false if it is invisible
   */
  boolean isVisible();

  void grayscaleRedComponent();

  void grayscaleGreenComponent();

  void grayscaleItensityComponent();

  void brighten(int b);
}
