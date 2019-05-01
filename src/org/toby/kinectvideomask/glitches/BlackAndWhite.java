package org.toby.kinectvideomask.glitches;

import processing.core.PImage;
import org.toby.kinectvideomask.Utilities;

import static org.toby.kinectvideomask.Utilities.BLACK;
import static org.toby.kinectvideomask.Utilities.WHITE;

class BlackAndWhite extends AbstractGlitch {

  private boolean currentlyGlitching;

  BlackAndWhite() {}

  PImage executeGlitch(PImage liveVideo, PImage body, int counter) {
    currentlyGlitching = counter <= 10;
    return Utilities.twoTone(body, WHITE, BLACK);
  }

  boolean stillGlitching() {
    return currentlyGlitching;
  }

}
