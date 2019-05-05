package org.toby.blindeye.bugs.instances;

import KinectPV2.KinectPV2;
import org.toby.blindeye.bugs.AbstractBug;
import processing.core.PConstants;
import processing.core.PImage;

public class BlackAndWhiteVideo extends AbstractBug {

  public PImage executeBug(PImage liveVideo, PImage body, PImage staticBackground, KinectPV2 kinect) {
    liveVideo.filter(PConstants.THRESHOLD, 0.7f);
    return liveVideo;
  }

}
