package model;


import model.filter.IFilter;
import model.image.IImage;
import model.color.IColorTransformation;


/**
 * Represents an image processing model as well as the actions that it can perform such as applying
 * filters, transforming the colors of an image, and exporting/importing images.
 */
public interface IModel {

  IImage colorTransformation(IImage image, IColorTransformation colorTransformation)
          throws IllegalArgumentException;

  IImage filter(IImage image, IFilter filter) throws IllegalArgumentException;

}