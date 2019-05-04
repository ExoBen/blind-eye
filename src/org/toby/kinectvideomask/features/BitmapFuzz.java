package org.toby.kinectvideomask.features;

import processing.core.PImage;

import java.util.Random;

import static org.toby.kinectvideomask.UtilitiesAndConstants.BLACK;
import static org.toby.kinectvideomask.UtilitiesAndConstants.WHITE;

class BitmapFuzz extends AbstractFeature {

  private Random rand;

  BitmapFuzz() {
    rand = new Random();
  }

  PImage executeFeature(PImage liveVideo, PImage body, PImage staticBackground) {
    liveVideo.loadPixels();
    int area = (body.width * body.height);
    for (int i = 0; i < area; i++) {
      int willItChange = rand.nextInt(3);
      if (body.pixels[i] == BLACK && willItChange == 0) {
        liveVideo.pixels[i] = (rand.nextInt(2) == 0 ? BLACK : WHITE);
      }
    }
    liveVideo.updatePixels();
    return liveVideo;
  }

}
