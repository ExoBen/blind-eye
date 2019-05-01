package org.toby.kinectvideomask.glitches;

import processing.core.PImage;

abstract class AbstractGlitch {

  abstract PImage executeGlitch(PImage liveVideo, PImage body, int counter);

  abstract boolean stillGlitching();

}
