package org.toby.kinectvideomask.bugs.instances;

import KinectPV2.KinectPV2;
import org.toby.kinectvideomask.UtilitiesAndConstants;
import org.toby.kinectvideomask.bugs.AbstractBug;
import processing.core.PImage;

import static org.toby.kinectvideomask.UtilitiesAndConstants.BLACK;
import static org.toby.kinectvideomask.UtilitiesAndConstants.WHITE;

public class BlackAndWhiteMask extends AbstractBug {

  public BlackAndWhiteMask() {}

  public PImage executeBug(PImage liveVideo, PImage body, PImage staticBackground, KinectPV2 kinect) {
    return UtilitiesAndConstants.twoTone(body, WHITE, BLACK);
  }

}
