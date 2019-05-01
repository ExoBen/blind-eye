package org.toby.kinectvideomask;

import org.toby.kinectvideomask.glitches.GlitchLoader;
import processing.core.*;
import processing.sound.*;
import KinectPV2.*;

import java.util.ArrayList;
import java.util.Random;

import static org.toby.kinectvideomask.Utilities.BLACK;

public class VideoMaskDegree extends PApplet {

  private GlitchLoader glitch;
  private TextOverlay textOverlay;
  private Ghosting ghost;
  private KinectPV2 kinect;
  private long timeBegin;
  private boolean currentlyGlitching = false;
  private SoundFile clickBuzz;
  private PImage staticBackground;
  private Random rand;

  public static void main(String[] args) {
    PApplet.main("org.toby.kinectvideomask.VideoMaskDegree");
  }

  public void settings() {
    size(1302, 1080);
  }

  public void setup() {
    String background = "F:/OneDrive - University of Dundee/Year 4/Kinect Video Mask/kinect-video-mask/resources/bg.png";
    String vhsFont = "F:/OneDrive - University of Dundee/Year 4/Kinect Video Mask/kinect-video-mask/resources/vcr.ttf";

    staticBackground = loadImage(background);
    staticBackground.resize(1302, 1080);
    rand = new Random();
    glitch = new GlitchLoader();
    ghost = new Ghosting();
    textOverlay = new TextOverlay(this);
    timeBegin = System.currentTimeMillis();
    setUpKinect(this);
    setUpSounds();
    textFont(createFont(vhsFont, 48));
  }

  @SuppressWarnings("unchecked")
  public void draw() {
    background(0);
    PImage outputVideo;
    long currentTime = System.currentTimeMillis() - timeBegin;
    PImage cropBody = kinect.getBodyTrackImage().get(39, 32, 434, 360);
    int cropBodySize = (cropBody.width * cropBody.height);

    PImage bodyUpscaled = Upscaler.upscaler(cropBody, cropBodySize);
    PImage liveVideo = kinect.getColorImage().get(309, 0, 1302, 1080);
    ArrayList<PImage> bodyTrackList = kinect.getBodyTrackUser();


    if (rand.nextInt(200) == 0 || currentlyGlitching) {
      outputVideo = glitch.executeGlitch(liveVideo, bodyUpscaled);
      currentlyGlitching = glitch.isCurrentlyGlitching();
    } else if (currentTime % 10000 > 8000) {
      if (!clickBuzz.isPlaying()) {
        clickBuzz.play();
      }
      outputVideo = Utilities.twoTone(bodyUpscaled, color(255, 1, 145), color(0, 151, 254));
      //outputVideo = bitmapFuzz(bodyUpscaled, liveVideo);
    } else if (bodyTrackList.size() > 2) {
      PImage upscaledSecondPerson = Upscaler.upscaler(bodyTrackList.get(1), cropBodySize);
      outputVideo = ghost.ghosting(upscaledSecondPerson, liveVideo, staticBackground);
    } else {
      outputVideo = ghost.ghosting(bodyUpscaled, liveVideo, staticBackground);
    }
    image(outputVideo, 0, 0);

    textOverlay.textOverlay(currentTime, kinect);
  }

  // ---------------------------------------------------------------------

  private void setUpSounds() {
    String softFuzzSound = "F:/OneDrive - University of Dundee/Year 4/Kinect Video Mask/kinect-video-mask/resources/vhs.wav";
    String clickBuzzSound = "F:/OneDrive - University of Dundee/Year 4/Kinect Video Mask/kinect-video-mask/resources/clickBuzz.wav";
    SoundFile softFuzz = new SoundFile(this, softFuzzSound);
    softFuzz.loop();
    softFuzz.amp(0.2f); //volume
    clickBuzz = new SoundFile(this, clickBuzzSound);
    clickBuzz.amp(0.4f);
  }

  private void setUpKinect(VideoMaskDegree videoMaskDegree) {
    kinect = new KinectPV2(videoMaskDegree);
    kinect.enableColorImg(true);
    kinect.enableBodyTrackImg(true);
    kinect.init();
  }


  PImage bitmapFuzz(PImage bodyUpscaled, PImage liveVideo) {
    liveVideo.loadPixels();
    int bodyUpscaledSize = (bodyUpscaled.width * bodyUpscaled.height);

    for (int i = 0; i < bodyUpscaledSize; i++) {
      int magicNumber2 = (int) random(0, 4);
      if (bodyUpscaled.pixels[i] == BLACK && magicNumber2 == 1) {
        int magicNumber = (int) random(0, 2);
        liveVideo.pixels[i] = (magicNumber == 1 ? color(0) : color(255));
      }
    }
    liveVideo.updatePixels();
    return liveVideo;
  }

  public void mousePressed() {
    saveFrame("resources/bg.png");
    String background = "F:/OneDrive - University of Dundee/Year 4/Kinect Video Mask/kinect-video-mask/resources/bg.png";
    staticBackground = loadImage(background);
    staticBackground.resize(1302, 1080);
  }
}