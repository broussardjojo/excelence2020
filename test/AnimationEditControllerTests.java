import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import cs3500.animator.controller.AnimationEditController;
import cs3500.animator.controller.IEditController;
import cs3500.animator.controller.MockController;
import cs3500.animator.controller.myactionevents.MAEAddKeyframe;
import cs3500.animator.controller.myactionevents.MAEDefault;
import cs3500.animator.controller.myactionevents.MAEDeleteKeyframe;
import cs3500.animator.controller.myactionevents.MAEEchoEllipse;
import cs3500.animator.controller.myactionevents.MAEEchoRectangle;
import cs3500.animator.controller.myactionevents.MAEEditKeyframe;
import cs3500.animator.controller.myactionevents.MAERemoveShape;
import cs3500.animator.controller.myactionevents.MAESelectKeyframe;
import cs3500.animator.controller.myactionevents.MAESelectShapeForKeyFrame;
import cs3500.animator.controller.myactionevents.MAESelectShapeForShape;
import cs3500.animator.controller.myactionevents.MyActionEventFast;
import cs3500.animator.controller.myactionevents.MyActionEventLoop;
import cs3500.animator.controller.myactionevents.MyActionEventPlay;
import cs3500.animator.controller.myactionevents.MyActionEventRestart;
import cs3500.animator.controller.myactionevents.MyActionEventSlow;

import org.junit.Test;


/**
 * This is a test class for the AnimationEditControllerClass.
 */
public class AnimationEditControllerTests {

  @Test
  public void testLoop() {
    MockController mock = new MockController();
    MyActionEventLoop loop = new MyActionEventLoop();
    assertEquals(null, mock.getCommandPressed());
    mock.actionPerformed(loop);
    assertEquals("loop command performed", mock.getCommandPressed());
  }


  @Test
  public void testPlay() {
    MockController mock = new MockController();
    MyActionEventPlay play = new MyActionEventPlay();
    assertEquals(null, mock.getCommandPressed());
    mock.actionPerformed(play);
    assertEquals("play command performed", mock.getCommandPressed());

  }


  @Test
  public void testSlowDown() {
    MockController mock = new MockController();
    MyActionEventSlow slow = new MyActionEventSlow();
    assertEquals(null, mock.getCommandPressed());
    mock.actionPerformed(slow);
    assertEquals("slow down command performed", mock.getCommandPressed());
  }

  @Test
  public void testFastForward() {
    MockController mock = new MockController();
    MyActionEventFast fast = new MyActionEventFast();
    assertEquals(null, mock.getCommandPressed());
    mock.actionPerformed(fast);
    assertEquals("fast forward command performed", mock.getCommandPressed());
  }


  @Test
  public void testRestart() {
    MockController mock = new MockController();
    MyActionEventRestart restart = new MyActionEventRestart();
    assertEquals(null, mock.getCommandPressed());
    mock.actionPerformed(restart);
    assertEquals("restart command performed", mock.getCommandPressed());
  }


  @Test
  public void testEchoEllipse() {
    MockController mock = new MockController();
    MAEEchoEllipse echoE = new MAEEchoEllipse();
    assertEquals(null, mock.getCommandPressed());
    mock.actionPerformed(echoE);
    assertEquals("echo ellipse command performed", mock.getCommandPressed());
  }


  @Test
  public void testEchoRectangle() {
    MockController mock = new MockController();
    MAEEchoRectangle echoR = new MAEEchoRectangle();
    assertEquals(null, mock.getCommandPressed());
    mock.actionPerformed(echoR);
    assertEquals("echo rectangle command performed", mock.getCommandPressed());
  }

  @Test
  public void testSelectShapeForShape() {
    MockController mock = new MockController();
    MAESelectShapeForShape sfs = new MAESelectShapeForShape();
    assertEquals(null, mock.getCommandPressed());
    mock.actionPerformed(sfs);
    assertEquals("select shape for shape command performed", mock.getCommandPressed());
  }

  @Test
  public void testSelectShapeForKeyframe() {
    MockController mock = new MockController();
    MAESelectShapeForKeyFrame sfkf = new MAESelectShapeForKeyFrame();
    assertEquals(null, mock.getCommandPressed());
    mock.actionPerformed(sfkf);
    assertEquals("select shape for keyframe command performed", mock.getCommandPressed());
  }

  @Test
  public void testRemoveShape() {
    MockController mock = new MockController();
    MAERemoveShape removeS = new MAERemoveShape();
    assertEquals(null, mock.getCommandPressed());
    mock.actionPerformed(removeS);
    assertEquals("remove shape command performed", mock.getCommandPressed());
  }

  @Test
  public void testAddKeyframe() {
    MockController mock = new MockController();
    MAEAddKeyframe akf = new MAEAddKeyframe();
    assertEquals(null, mock.getCommandPressed());
    mock.actionPerformed(akf);
    assertEquals("add key frame command performed", mock.getCommandPressed());
  }

  @Test
  public void testSelectKeyframe() {
    MockController mock = new MockController();
    MAESelectKeyframe skf = new MAESelectKeyframe();
    assertEquals(null, mock.getCommandPressed());
    mock.actionPerformed(skf);
    assertEquals("select key frame command performed", mock.getCommandPressed());
  }

  @Test
  public void testDeleteKeyframe() {
    MockController mock = new MockController();
    MAEDeleteKeyframe dfk = new MAEDeleteKeyframe();
    assertEquals(null, mock.getCommandPressed());
    mock.actionPerformed(dfk);
    assertEquals("delete key frame command performed", mock.getCommandPressed());
  }

  @Test
  public void testEditKeyframe() {
    MockController mock = new MockController();
    MAEEditKeyframe ekf = new MAEEditKeyframe();
    assertEquals(null, mock.getCommandPressed());
    mock.actionPerformed(ekf);
    assertEquals("edit key frame command performed", mock.getCommandPressed());
  }

  @Test
  public void testDefault() {
    MockController mock = new MockController();
    MAEDefault d = new MAEDefault();
    assertEquals(null, mock.getCommandPressed());
    mock.actionPerformed(d);
    assertEquals("unknown command", mock.getCommandPressed());
  }


  @Test
  public void testSetAndGetTickRate() {
    IEditController editC = new AnimationEditController();
    editC.setTickRate(10);
    assertEquals(10, editC.getTickRate());
  }

  @Test
  public void testSetTickRateBad() {
    try {
      IEditController editC = new AnimationEditController();
      editC.setTickRate(-10);
      assertEquals(-10, editC.getTickRate());
      fail("did not catch exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Tick rate cannot be negative.", e.getMessage());
    }

  }
}
