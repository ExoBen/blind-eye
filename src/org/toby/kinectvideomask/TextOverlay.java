package org.toby.kinectvideomask;

import KinectPV2.KinectPV2;
import processing.core.PApplet;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.toby.kinectvideomask.Utilities.LEFT_OFFSET;
import static org.toby.kinectvideomask.Utilities.RIGHT_OFFSET;

class TextOverlay extends VideoMaskDegree {

  private PApplet parent;

  TextOverlay(PApplet p) {
    parent = p;
  }

  void textOverlay(long currentTime, KinectPV2 kinect) {
    parent.fill(255);
    parent.text("Time: " + intoSeconds(currentTime) + "s", LEFT_OFFSET + 50, 170);
    parent.text("FPS: " + floor(parent.frameRate), LEFT_OFFSET + 50, 220);
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy\nHH:mm:ss");
    String dateTime = sdf.format(new Date());
    parent.text(dateTime, RIGHT_OFFSET - 270, 995);

    parent.text(kinect.getBodyTrackUser().size(), LEFT_OFFSET + 50, 120);
    parent.text("REC", LEFT_OFFSET + 50, 70);
    if (floor(currentTime/1000) % 2 == 0) {
      parent.fill(255, 0, 0);
      parent.stroke(255, 0, 0);
      parent.rect(LEFT_OFFSET + 150, 39, 16, 24);
      parent.rect(LEFT_OFFSET + 146, 43, 24, 16);
    }
  }

  private int intoSeconds(long millis) {
    return floor(millis/1000);
  }

}
