package org.toby.kinectvideomask.base;

import org.toby.kinectvideomask.Upscaler;
import processing.core.PImage;

import java.util.ArrayList;

import static org.toby.kinectvideomask.VideoMaskDegree.KINECT_HEIGHT;
import static org.toby.kinectvideomask.VideoMaskDegree.KINECT_WIDTH;

public class BaseLoader {

  private Ghosting ghost;

  public BaseLoader() {
    ghost = new Ghosting();
  }

  public PImage executeBase(PImage liveVideo, PImage body, PImage staticBackground, ArrayList<PImage> bodyList) {
    PImage outputVideo;

    if (bodyList.size() > 2) {
      PImage upscaledSecondPerson = Upscaler.upscaler(bodyList.get(1), KINECT_WIDTH * KINECT_HEIGHT);
      outputVideo = ghost.executeBase(liveVideo, upscaledSecondPerson, staticBackground);
    } else {
      outputVideo = ghost.executeBase(liveVideo, body, staticBackground);
    }
    return outputVideo;
  }

}

