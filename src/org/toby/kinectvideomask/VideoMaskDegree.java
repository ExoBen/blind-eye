package org.toby.kinectvideomask;

import org.toby.kinectvideomask.base.BaseLoader;
import org.toby.kinectvideomask.features.FeatureLoader;
import org.toby.kinectvideomask.glitches.GlitchLoader;
import processing.core.*;
import processing.sound.*;
import KinectPV2.*;

import java.util.ArrayList;
import java.util.Random;

import static org.toby.kinectvideomask.Utilities.BLACK;
import static org.toby.kinectvideomask.Utilities.LEFT_OFFSET;

public class VideoMaskDegree extends PApplet {

  public static final int KINECT_WIDTH = 434;
  public static final int KINECT_HEIGHT = 360;

  private BaseLoader base;
  private FeatureLoader feature;
  private boolean currentlyFeaturing = false;
  private GlitchLoader glitch;
  private boolean currentlyGlitching = false;

  private TextOverlay textOverlay;
  private KinectPV2 kinect;
  private long timeBegin;
  private long timeOfLastFeature;
  private SoundFile clickBuzz;
  private PImage staticBackground;
  private Random rand;
  private PImage outputVideo;

  public static void main(String[] args) {
    PApplet.main("org.toby.kinectvideomask.VideoMaskDegree");
  }

  public void settings() {
    fullScreen();
  }

  public void setup() {
    String background = "F:/OneDrive - University of Dundee/Year 4/Kinect Video Mask/kinect-video-mask/resources/bg.png";
    String vhsFont = "F:/OneDrive - University of Dundee/Year 4/Kinect Video Mask/kinect-video-mask/resources/vcr.ttf";

    staticBackground = loadImage(background);
    staticBackground.resize(1302, 1080);
    rand = new Random();
    base = new BaseLoader();
    feature = new FeatureLoader();
    glitch = new GlitchLoader();
    textOverlay = new TextOverlay(this);
    timeBegin = System.currentTimeMillis();
    timeOfLastFeature = timeBegin;
    setUpKinect(this);
    setUpSounds();
    textFont(createFont(vhsFont, 48));
  }

  @SuppressWarnings("unchecked")
  public void draw() {
    background(0);
    long currentTime = System.currentTimeMillis() - timeBegin;
    PImage cropBody = kinect.getBodyTrackImage().get(39, 32, KINECT_WIDTH, KINECT_HEIGHT);
    PImage body = Upscaler.upscaler(cropBody, KINECT_WIDTH*KINECT_HEIGHT);
    PImage liveVideo = kinect.getColorImage().get(309, 0, 1302, 1080);
    ArrayList<PImage> bodyList = kinect.getBodyTrackUser();

    int random = rand.nextInt(200);
    long timeSince = System.currentTimeMillis() - timeOfLastFeature;
    boolean toFeature = (timeSince > 4000 && rand.nextInt(100) == 0) || timeSince > 6000;

    if (toFeature || currentlyFeaturing || random == 0 || currentlyGlitching) {
      if (toFeature || currentlyFeaturing) {
        //featuring
        outputVideo = feature.executeFeature(liveVideo, body, staticBackground);
        currentlyFeaturing = feature.isCurrentlyFeaturing();
        if (!clickBuzz.isPlaying()) {
          clickBuzz.play();
        }
        timeOfLastFeature = System.currentTimeMillis();
      } else {
        outputVideo = liveVideo;
      }
      if (random == 0 || currentlyGlitching) {
        //glitching
        outputVideo = glitch.executeGlitch(outputVideo, body, bodyList);
        currentlyGlitching = glitch.isCurrentlyGlitching();
      }
    } else {
      //basing
      outputVideo = base.executeBase(liveVideo, body, staticBackground, bodyList);
    }
    image(outputVideo, LEFT_OFFSET, 0);
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

  public void mousePressed() {
    if (mouseButton == LEFT) {
      outputVideo.save("resources/bg.png");
      String background = "F:/OneDrive - University of Dundee/Year 4/Kinect Video Mask/kinect-video-mask/resources/bg.png";
      staticBackground = loadImage(background);
    } else if (mouseButton == RIGHT) {
      exit();
    }
  }
}