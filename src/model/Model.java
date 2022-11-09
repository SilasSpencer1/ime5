package model;


import model.color.IColorTransformation;
import model.filter.IFilter;
import model.image.IImage;

public class Model implements IModel {

  @Override
  public IImage colorTransformation(IImage image, IColorTransformation colorTransformation)
          throws IllegalArgumentException {
    if (image == null || colorTransformation == null) {
      throw new IllegalArgumentException("One or more arguments is null!");
    }

    return colorTransformation.apply(image);
  }

  @Override
  public IImage filter(IImage image, IFilter filter) throws IllegalArgumentException {
    if (image == null || filter == null) {
      throw new IllegalArgumentException("One or more arguments is null!");
    }

    return filter.apply(image);
  }
}