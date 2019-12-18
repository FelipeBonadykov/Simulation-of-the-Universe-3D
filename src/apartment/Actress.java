package apartment;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import com.interactivemesh.jfx.importer.tds.TdsModelImporter;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Polyline;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import space.MainSpace;

public class Actress {
	private static Group girl;
	private static Box aura = new Box(30,30, 100);
	private static Pane girlPane = new Pane(createActress());
	private static PathTransition moves = new PathTransition();
	private static boolean canGo=true;

	static {
		girlPane.translateXProperty().bind(aura.translateXProperty());
    	girlPane.translateYProperty().bind(aura.translateYProperty());
    	girlPane.getTransforms().add(new Rotate(0));
		move();
	}
	
	public static Pane getActress() {
		return girlPane;
	}
	
	private static Group createActress() {
		var modelImporter = new TdsModelImporter();
	    try {
	    	modelImporter.read("files/apartment/3d models/0 PEOPLE/girl/girl.3ds");
	    	girl = new Group((Node[]) modelImporter.getImport());
	    	modelImporter.close();
	    } catch (Exception e) {
	    	System.err.println(e.getCause() +"  "+ e.getMessage());
	    }
        girl.getTransforms().addAll(new Rotate(90, Rotate.X_AXIS), new Scale(1.7,1.7,1.7));
        return girl;
	}
	//artificial intelligence
	private static Polyline getPath() {
		var path = new Polyline();
		path.setFill(Color.RED);
		path.getPoints().addAll(new Double[] {
				-30.0, 0.0,    //start
				-30.0,-160.0,  //fridge
				-200.0,-150.0, -200.0,-40.0, //roam kitchen
				-30.0,-40.0,   //center
				-30.0,100.0, -100.0,120.0,  //roam to tv
				-120.0,200.0,  //tv
				-100.0,140.0, -80.0,100.0, -30.0, 60.0, //roam living room and enter bedroom
				 80.0, 40.0, 100.0,-10.0, 
				 130.0, -20.0,//bed
				 30.0, -40.0, 30.0, -140.0, //enter bathroom
			     220.0, -140.0, //bath
			     //return
			     25.0, -170.0,  30.0, -70.0,  80.0, 40.0,  -30.0, 60.0,  -30.0, 0.0
		});
		return path;
	}
	
	private static void move() {
		moves.setDuration(new Duration(360_00));
		moves.setPath(getPath());
		moves.setNode(aura);
		moves.setCycleCount(Animation.INDEFINITE);
		moves.setInterpolator(Interpolator.LINEAR);
		moves.play();
		new Timer().scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if (canGo) {
					var time = Math.round(moves.getCurrentTime().toSeconds());
					if (time==3)  eat();
					if (time==4)  setAngleRotate(90);
					if (time==7)  setAngleRotate(270);
					if (time==10) setAngleRotate(0);
					if (time==11) setAngleRotate(30);
					if (time==12) setAngleRotate(0);
					if (time==14) setAngleRotate(180);
					if (time==15) watchTV();
					if (time==16) setAngleRotate(210);
					if (time==17) setAngleRotate(270);
					if (time==20) girlPane.getTransforms().addAll(new Translate(0,15,-45), new Rotate(90, Rotate.X_AXIS));
					if (time==21) sleep();
					if (time==22) setAngleRotate(120);
					if (time==23) setAngleRotate(180);
					if (time==24) setAngleRotate(270);
					if (time==26) setAngleRotate(90);
					if (time==27) takeShower();
					if (time==29) setAngleRotate(0);
					if (time==31) setAngleRotate(330);
					if (time==33) setAngleRotate(30);
					if (time==35) setAngleRotate(180);
				}
			}
		}, 0,1000);
	}	
	//actions
	private static void setAngleRotate(int angle) {
		girlPane.getTransforms().remove(girlPane.getTransforms().size()-1);
		girlPane.getTransforms().add(new Rotate(angle, Rotate.Z_AXIS));
	}
	
	private static void eat() {
		canGo=false;
		moves.pause();
		Apartment.actionstuff.get(3).setVisible(true);
		if (MainSpace.isPpressed)
			new MediaPlayer(new Media(new File("files/apartment/sounds/eating.wav").toURI().toString())).play();
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				canGo=true;
				Apartment.actionstuff.get(3).setVisible(false);
				moves.play();
				setAngleRotate(-90);
			}
		}, 5*1000L);
	}
	private static void watchTV() {
		canGo=false;
		moves.pause();
		Apartment.actionstuff.get(1).setVisible(true);
		if (MainSpace.isPpressed)
			new MediaPlayer(new Media(new File("files/apartment/sounds/relaxing.wav").toURI().toString())).play();
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				canGo=true;
				Apartment.actionstuff.get(1).setVisible(false);
				moves.play();
			}
		}, 7*1000L);
	}
	private static void sleep() {
		canGo=false;
		moves.pause();
		if (MainSpace.isPpressed)
			new MediaPlayer(new Media(new File("files/apartment/sounds/sleeping.wav").toURI().toString())).play();
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				canGo=true;
				girlPane.getTransforms().remove(girlPane.getTransforms().size()-1);
				girlPane.getTransforms().remove(girlPane.getTransforms().size()-1);
				moves.play();
			}
		}, 10*1000L);
	}
	private static void takeShower() {
		canGo=false;
		moves.pause();
		girl.getChildren().get(0).setVisible(false);
		girl.getChildren().get(1).setVisible(false);
		girl.getChildren().get(2).setVisible(false);
		girl.getChildren().get(4).setVisible(false);
		if (MainSpace.isPpressed)
			new MediaPlayer(new Media(new File("files/apartment/sounds/taking-shower.wav").toURI().toString())).play();
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				canGo=true;
				girl.getChildren().get(0).setVisible(true);
				girl.getChildren().get(1).setVisible(true);
				girl.getChildren().get(2).setVisible(true);
				girl.getChildren().get(4).setVisible(true);
				moves.playFrom(new Duration(27000));
			}
		}, 3*1000L);
	}

}
