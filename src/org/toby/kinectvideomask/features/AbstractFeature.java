package org.toby.kinectvideomask.features;

import processing.core.PImage;

abstract class AbstractFeature {

  abstract PImage executeFeature(PImage liveVideo, PImage body, PImage staticBackground);

}
