package apartment; 

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import com.interactivemesh.jfx.importer.ModelImporter;
import com.interactivemesh.jfx.importer.obj.ObjModelImporter;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class Actor {
	private static Group Actor = createActor("man");
	private static boolean canGo=true;
	private static Box aura = new Box(30,30, 100);
	static {
		 aura.translateXProperty().bind(Actor.translateXProperty());
		 aura.translateYProperty().bind(Actor.translateYProperty());
	 }
	private static Group createActor(String person) {
		ModelImporter modelImporter = new ObjModelImporter();
		Group mesh=null;
		try {
			modelImporter.read("files/apartment/3d models/0 PEOPLE/"+person+"/"+person+".obj");
			mesh = new Group((Node[]) modelImporter.getImport());
			modelImporter.close();
		} catch (Exception e) {
			System.err.println(" FILE NOT FOUND  "+e.getMessage());
		}
	    mesh.getTransforms().addAll
	    (new Rotate(90, Rotate.X_AXIS), new Scale(.6,.6,.6), new Rotate(180, Rotate.Y_AXIS), new Rotate());
		mesh.setTranslateX(-30);
		return mesh;
	}
	
	public static Group getActor() {
		return Actor;
	}
	//movements
	public static void goAhead(){
		if (NotCollides(0, -10)&canGo) 
	    Actor.getTransforms().remove(Actor.getTransforms().size()-1);
	    Actor.getTransforms().add(new Rotate(0, Rotate.Y_AXIS));
        
	    var go = new TranslateTransition(new Duration(100), Actor);
	    go.setByY(-10);
	    go.setInterpolator(Interpolator.LINEAR);
	    go.play();
	}
	public static void goBack (){
		if (NotCollides(0, 10)&canGo) 
	    Actor.getTransforms().remove(Actor.getTransforms().size()-1);
	    Actor.getTransforms().add(new Rotate(180, Rotate.Y_AXIS));
	    
	    var go = new TranslateTransition(new Duration(100), Actor);
	    go.setByY(10);
	    go.setInterpolator(Interpolator.LINEAR);
	    go.play();
	}
	public static void goLeft (){ 
		if (NotCollides(-10, 0)&canGo)
	    Actor.getTransforms().remove(Actor.getTransforms().size()-1);
	    Actor.getTransforms().add(new Rotate(270, Rotate.Y_AXIS));
	    
	    var go = new TranslateTransition(new Duration(100), Actor);
	    go.setByX(-10);
	    go.setInterpolator(Interpolator.LINEAR);
		go.play();
	}
	public static void goRight(){
		if (NotCollides(10, 0)&canGo)
		Actor.getTransforms().remove(Actor.getTransforms().size()-1);
		Actor.getTransforms().add(new Rotate(90, Rotate.Y_AXIS));
		var go = new TranslateTransition(new Duration(100), Actor);
		go.setByX(10);
		go.setInterpolator(Interpolator.LINEAR);
		go.play();
	}
	
	public static boolean NotCollides(int x, int y) {
		var result = true;
		aura.getTransforms().add(new Translate(x,0,y));
		for (var wall : Apartment.wall) 
			if (aura.getBoundsInParent().intersects(wall.getBoundsInParent())) 
				result = false;
		if (aura.getBoundsInParent().intersects(((Node) Apartment.actionstuff.get(0)).getBoundsInParent())) 
			watchTV();
		if (aura.getBoundsInParent().intersects(((Node) Apartment.actionstuff.get(2)).getBoundsInParent())) 
			sleep();
		if (aura.getBoundsInParent().intersects(Apartment.actionstuff.get(3).getBoundsInParent())) 
	    	eat();
		aura.getTransforms().remove(aura.getTransforms().size()-1);
		return result;
	}
	//actions
	private static Group actorSitting = createActor("watchTV man");
	public static Group getWatchingTV() {
		actorSitting.setVisible(false);
		actorSitting.setTranslateX(-150);
		actorSitting.setTranslateY(200);
		return actorSitting;
	}
	private static void watchTV() {
		canGo=false;
		Actor.setVisible(false);
		actorSitting.setVisible(true);
		Apartment.actionstuff.get(1).setVisible(true);
		new MediaPlayer(new Media(new File("files/apartment/sounds/relaxing.wav").toURI().toString())).play();
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				canGo=true;
			    Actor.setVisible(true);
			    Actor.setTranslateX(-120);
			    Actor.setTranslateY(170);
				actorSitting.setVisible(false);
				Apartment.actionstuff.get(1).setVisible(false);
			}
		}, 7*1000L);
	}
	
	private static Group actorSleeping = createActor("sleep man");
	public static Group getSleeping() {
		actorSleeping.setVisible(false);
		actorSleeping.getTransforms().addAll
		(new Translate(-360, -70, 15), new Rotate(-90, Rotate.Y_AXIS), new Scale(.9,.9,.9));
		return actorSleeping;
	}
	private static void sleep () {
		canGo=false;
		Actor.setVisible(false);
		actorSleeping.setVisible(true);
		new MediaPlayer(new Media(new File("files/apartment/sounds/sleeping.wav").toURI().toString())).play();
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				canGo=true;
				Actor.setVisible(true);
				Actor.setTranslateX(70);
				actorSleeping.setVisible(false);
			}
		}, 10*1000L);
	}

	private static void eat() {
		canGo=false;
		Apartment.actionstuff.get(3).setVisible(true);
		new MediaPlayer(new Media(new File("files/apartment/sounds/eating.wav").toURI().toString())).play();
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				canGo=true;
			    Actor.setTranslateX(-30);
			    Actor.setTranslateY(-160);
				Apartment.actionstuff.get(3).setVisible(false);
			}
		}, 5*1000L);
	}
}
