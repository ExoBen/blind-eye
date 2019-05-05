package org.toby.kinectvideomask.glitches.instances;

import KinectPV2.KinectPV2;
import org.toby.kinectvideomask.UtilitiesAndConstants;
import org.toby.kinectvideomask.glitches.AbstractGlitch;
import processing.core.PImage;

import static org.toby.kinectvideomask.UtilitiesAndConstants.BLACK;
import static org.toby.kinectvideomask.UtilitiesAndConstants.WHITE;

public class BlackAndWhiteMask extends AbstractGlitch {

  public BlackAndWhiteMask() {}

  public PImage executeGlitch(PImage liveVideo, PImage body, KinectPV2 kinect) {
    return UtilitiesAndConstants.twoTone(body, WHITE, BLACK);
  }

}
