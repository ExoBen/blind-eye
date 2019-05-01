package org.toby.kinectvideomask;

import KinectPV2.KinectPV2;
import processing.core.PApplet;

import java.text.SimpleDateFormat;
import java.util.Date;

class TextOverlay extends VideoMaskDegree {

  private PApplet parent;

  TextOverlay(PApplet p) {
    parent = p;
  }

  void textOverlay(long currentTime, KinectPV2 kinect) {
    parent.fill(255);
    parent.text("Time: " + intoSeconds(currentTime) + "s", 50, 170);
    parent.text("FPS: " + floor(parent.frameRate), 50, 220);
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy\nHH:mm:ss");
    String dateTime = sdf.format(new Date());
    parent.text(dateTime, 1000, 700);

    parent.text(kinect.getBodyTrackUser().size(), 50, 120);
    parent.text("REC", 50, 70);
    if (floor(currentTime/1000) % 2 == 0) {
      parent.fill(255, 0, 0);
      parent.stroke(255, 0, 0);
      parent.rect(150, 39, 16, 24);
      parent.rect(146, 43, 24, 16);
    }
  }

  private int intoSeconds(long millis) {
    return floor(millis/1000);
  }

}
