package org.toby.blindeye.features.instances;

import KinectPV2.KinectPV2;
import org.toby.blindeye.bugs.AbstractBug;
import processing.core.PConstants;
import processing.core.PImage;

public class Invert extends AbstractBug {

  public PImage executeBug(PImage liveVideo, PImage body, PImage staticBackground, KinectPV2 kinect) {
    liveVideo.filter(PConstants.INVERT);
    return liveVideo;
  }

}
