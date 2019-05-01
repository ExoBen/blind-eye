package org.toby.kinectvideomask;

import processing.core.PImage;

public class Utilities {

  public static final int BLACK = -16777216;
  public static final int WHITE = -1;

  public Utilities() {}

  public static PImage twoTone(PImage bodyUpscaled, int foreground, int background) {
    PImage mask = new PImage(bodyUpscaled.width, bodyUpscaled.height);
    int bodyUpscaledSize = (bodyUpscaled.width * bodyUpscaled.height);
    for (int i = 0; i < bodyUpscaledSize; i++) {
      if (bodyUpscaled.pixels[i] == BLACK) {
        mask.pixels[i] = foreground;
      } else {
        mask.pixels[i] = background;
      }
    }
    return mask;
  }

}
