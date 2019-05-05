package org.toby.kinectvideomask.glitches.instances;

import KinectPV2.KinectPV2;
import org.toby.kinectvideomask.glitches.AbstractGlitch;
import processing.core.PConstants;
import processing.core.PImage;

public class BlackAndWhiteVideo extends AbstractGlitch {

  public PImage executeGlitch(PImage liveVideo, PImage body, KinectPV2 kinect) {
    liveVideo.filter(PConstants.THRESHOLD, 0.7f);
    return liveVideo;
  }

}
