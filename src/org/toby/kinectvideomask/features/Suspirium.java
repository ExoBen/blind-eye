package org.toby.kinectvideomask.features;

import org.toby.kinectvideomask.UtilitiesAndConstants;
import processing.core.PImage;

class Suspirium extends AbstractFeature {

  PImage executeFeature(PImage liveVideo, PImage body, PImage staticBackground) {
    return UtilitiesAndConstants.twoTone(body, -65135,-16738306);
  }

}
