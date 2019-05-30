package apartment; 

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import com.interactivemesh.jfx.importer.ModelImporter;
import com.interactivemesh.jfx.importer.obj.ObjModelImporter;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class Actor {
	private static Node[] Actor = createActor("man");
	private static boolean canGo=true;
	private static Box aura = new Box(30,30, 100);
	static {
		 aura.translateXProperty().bind(Actor[2].translateXProperty());
		 aura.translateYProperty().bind(Actor[2].translateYProperty());
	 }
	private static Node[] createActor(String person) {
		ModelImporter modelImporter = new ObjModelImporter();
		Node[] mesh=null;
		try {
			modelImporter.read("files/apartment/3d models/0 PEOPLE/"+person+"/"+person+".obj");
			mesh = (Node[]) modelImporter.getImport();
			modelImporter.close();
		} catch (Exception e) {
			System.err.println(" FILE NOT FOUND  "+e.getMessage());
		}
		var scale = new Scale();	
		scale.setX(.6);
	    scale.setY(.6);	
	    scale.setZ(.6);
	    for (var part : mesh) {
	    	part.getTransforms().addAll(new Rotate(90, Rotate.X_AXIS), scale, new Rotate(180, Rotate.Y_AXIS), new Rotate());
		    part.setTranslateX(-30);
		}
		return mesh;
	}
	
	public static Node[] getActor() {
		return Actor;
	}
	//movements
	public static void goAhead(){
		if (NotCollides(0, -10)&canGo) 
		for (var part : Actor) {
			part.getTransforms().remove(part.getTransforms().size()-1);
			part.getTransforms().add(new Rotate(0, Rotate.Y_AXIS));

			var go = new TranslateTransition(new Duration(100), part);
			go.setByY(-10);
			go.setInterpolator(Interpolator.LINEAR);
			go.play();
		}
	}
	public static void goBack (){
		if (NotCollides(0, 10)&canGo) 
		for (var part : Actor){
			part.getTransforms().remove(part.getTransforms().size()-1);
			part.getTransforms().add(new Rotate(180, Rotate.Y_AXIS));
			
			var go = new TranslateTransition(new Duration(100), part);
			go.setByY(10);
			go.setInterpolator(Interpolator.LINEAR);
			go.play();
		}
	}
	public static void goLeft (){ 
		if (NotCollides(-10, 0)&canGo) 
		for (var part : Actor){
			part.getTransforms().remove(part.getTransforms().size()-1);
			part.getTransforms().add(new Rotate(270, Rotate.Y_AXIS));
			
			var go = new TranslateTransition(new Duration(100), part);
			go.setByX(-10);
			go.setInterpolator(Interpolator.LINEAR);
			go.play();
		}
	}
	public static void goRight(){
		if (NotCollides(10, 0)&canGo)
		for (var part : Actor) {
			part.getTransforms().remove(part.getTransforms().size()-1);
			part.getTransforms().add(new Rotate(90, Rotate.Y_AXIS));
			var go = new TranslateTransition(new Duration(100), part);
			go.setByX(10);
			go.setInterpolator(Interpolator.LINEAR);
			go.play();
		}
	}
	
	public static boolean NotCollides(int x, int y) {
		var result = true;
		aura.getTransforms().add(new Translate(x,0,y));
		for (var wall : Apartment.wall) 
			if (aura.getBoundsInParent().intersects(wall.getBoundsInParent())) 
				result = false;
		for (var sofaPart : Apartment.actionstuff.get(0)) 
			if (aura.getBoundsInParent().intersects(sofaPart.getBoundsInParent())) 
				watchTV();
		for (var bedPart : Apartment.actionstuff.get(2)) 
			if (aura.getBoundsInParent().intersects(bedPart.getBoundsInParent())) 
				sleep();
		if (aura.getBoundsInParent().intersects(Apartment.actionstuff.get(3)[0].getBoundsInParent())) 
	     		eat();
		aura.getTransforms().remove(aura.getTransforms().size()-1);
		return result;
	}
	//actions
	private static Node[] actorSitting = createActor("watchTV man");
	public static Node[] getWatchingTV() {
		for (var node : actorSitting) {
			node.setVisible(false);
		    node.setTranslateX(-150);
		    node.setTranslateY(200);
		}
		return actorSitting;
	}
	private static void watchTV() {
		canGo=false;
		for (var node : Actor) 
			node.setVisible(false);
		for (var node : actorSitting) 
			node.setVisible(true);
		Apartment.actionstuff.get(1)[0].setVisible(true);
		new MediaPlayer(new Media(new File("files/apartment/sounds/relaxing.wav").toURI().toString())).play();
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				canGo=true;
				for (var node : Actor) {
					node.setVisible(true);
					node.setTranslateX(-120);
					node.setTranslateY(170);
				}
				for (var node : actorSitting) 
					node.setVisible(false);
				Apartment.actionstuff.get(1)[0].setVisible(false);
			}
		}, 7*1000L);
	}
	
	private static Node[] actorSleeping = createActor("sleep man");
	public static Node[] getSleeping() {
		for (var node : actorSleeping) {
			node.setVisible(false);
		    node.getTransforms().addAll(new Translate(-360, -70, 15), new Rotate(-90, Rotate.Y_AXIS), new Scale(.9,.9,.9));
		}
		return actorSleeping;
	}
	private static void sleep () {
		canGo=false;
		for (var node : Actor) 
			node.setVisible(false);
		for (var node : actorSleeping) 
			node.setVisible(true);
		new MediaPlayer(new Media(new File("files/apartment/sounds/sleeping.wav").toURI().toString())).play();
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				canGo=true;
				for (var node : Actor) {
					node.setVisible(true);
					node.setTranslateX(70);
				}
				for (var node : actorSleeping) 
					node.setVisible(false);
			}
		}, 10*1000L);
	}

	private static void eat() {
		canGo=false;
		Apartment.actionstuff.get(3)[0].setVisible(true);
		new MediaPlayer(new Media(new File("files/apartment/sounds/eating.wav").toURI().toString())).play();
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				canGo=true;
				for (var node : Actor) {
					node.setTranslateX(-30);
					node.setTranslateY(-160);
				}
				Apartment.actionstuff.get(3)[0].setVisible(false);
			}
		}, 5*1000L);
	}
}
