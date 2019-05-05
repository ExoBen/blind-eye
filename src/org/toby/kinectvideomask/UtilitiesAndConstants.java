package org.toby.kinectvideomask;

import processing.core.PImage;

public class UtilitiesAndConstants {

  public static final int BLACK = -16777216;
  public static final int WHITE = -1;
  public static final int LEFT_OFFSET = 309;
  public static final int LEFT_DISPLAY_OFFSET = 289;
  public static final int RIGHT_LENGTH = 1611;
  public static final int RIGHT_DISPLAY_OFFSET = 1591;
  public static final int MAIN_WIDTH = 1302;
  public static final int MAIN_HEIGHT = 1080;

  public static final int KINECT_WIDTH = 434;
  public static final int KINECT_HEIGHT = 360;

  public UtilitiesAndConstants() {}

  public static PImage twoTone(PImage body, int foreground, int background) {
    PImage mask = new PImage(body.width, body.height);
    int bodyUpscaledSize = (body.width * body.height);
    for (int i = 0; i < bodyUpscaledSize; i++) {
      if (body.pixels[i] == BLACK) {
        mask.pixels[i] = foreground;
      } else {
        mask.pixels[i] = background;
      }
    }
    return mask;
  }

}
