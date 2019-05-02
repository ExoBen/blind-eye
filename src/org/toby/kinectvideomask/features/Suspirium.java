package org.toby.kinectvideomask.features;

import org.toby.kinectvideomask.Utilities;
import processing.core.PImage;

class Suspirium extends AbstractFeature {

  PImage executeFeature(PImage liveVideo, PImage body, PImage staticBackground) {
    return Utilities.twoTone(body, -65135,-16738306);
  }

}
