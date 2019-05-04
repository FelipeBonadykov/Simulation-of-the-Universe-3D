package stuff_on_earth;
import static space.MainSpace.Earth;
import static space.enumSpaceObjects.EARTH;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import space.MainSpace;

public class StuffOnEarth{
	
	private Node[] shape;
	public Node[] getShape() {
		return shape;
	}
	private Sphere aura = new Sphere(0);
	public Sphere getAura() {
		return aura;
	}
	private PathTransition movement;
	public PathTransition getMovement() {
		return movement;
	}
	public double distanceFromTheCenterOfPlanet, angle;
	private StackPane auraPane = new StackPane();
	public StackPane getAuraPane () {
		return auraPane;
	}
	
	public Duration d;
	public StuffOnEarth(String name, String extension, float distanceFromTheCenter, float size, int angle, int offset) {
		shape =  Shape3dConstructor.getShape3D(name, extension);
		setOnEarth(distanceFromTheCenter, size, angle, offset, shape);
	}
	
	public StuffOnEarth(float distanceFromTheCenter, Shape3D shape) {
		this.shape = new Shape3D[]{shape};
		shape.setCullFace(CullFace.NONE);
		shape.getTransforms().add(new Rotate(90, Rotate.Z_AXIS));
		
		var movement = new PathTransition(new Duration(360_00), new Circle(distanceFromTheCenterOfPlanet=distanceFromTheCenter), shape);
		movement.setInterpolator(Interpolator.LINEAR);
		movement.setCycleCount(Animation.INDEFINITE);
		movement.play();
		this.movement = movement;
		d = movement.getDuration();
		
		var rotate = new RotateTransition(new Duration(360_00), shape);
		rotate.setAxis(Rotate.Z_AXIS);
		rotate.setByAngle(360);
		rotate.setInterpolator(Interpolator.LINEAR);
		rotate.setCycleCount(Timeline.INDEFINITE);
		rotate.play();
		
		var stuffPane = new StackPane();
	    stuffPane.translateXProperty().bind(Earth.translateXProperty());
	    stuffPane.translateYProperty().bind(Earth.translateYProperty());
	    stuffPane.translateZProperty().bind(Earth.translateZProperty());
	    stuffPane.getChildren().addAll(shape);
	    MainSpace.root.getChildren().add(stuffPane);
	}
	private void setOnEarth(float distanceFromTheCenter, float size, int angle, int offset, Node... shape) {
		var movement = new PathTransition(new Duration(360_00), 
			new Circle(distanceFromTheCenterOfPlanet=distanceFromTheCenter*Math.cos(Math.toRadians(angle))), aura);
		movement.setInterpolator(Interpolator.LINEAR);
		movement.setCycleCount(Animation.INDEFINITE);
		movement.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);
		movement.playFrom(d = new Duration(offset*100));
		this.movement = movement;
		
	    auraPane.translateXProperty().bind(Earth.translateXProperty());
	    auraPane.translateYProperty().bind(Earth.translateYProperty());
	    auraPane.translateZProperty().bind(Earth.translateZProperty().add(this.angle=-Math.sin(Math.toRadians(angle))));
	    auraPane.getChildren().add(aura);
	    MainSpace.root.getChildren().add(auraPane);
	
	    for (Node part : shape) {
			part.getTransforms().add(new Rotate(angle, Rotate.Y_AXIS));
			part.getTransforms().add(new Rotate(90, Rotate.Z_AXIS));
			
			part.setScaleX(size);
			part.setScaleY(size);
			part.setScaleZ(size);
			
			var rotate = new RotateTransition(new Duration(360_00), part);
			rotate.setAxis(Rotate.Z_AXIS);
			rotate.setByAngle(360);
			rotate.setFromAngle(offset);
			rotate.setInterpolator(Interpolator.LINEAR);
			rotate.setCycleCount(Timeline.INDEFINITE);
			rotate.play();
		}
		var partPane = new StackPane();
	    partPane.translateXProperty().bind(aura.translateXProperty());
	    partPane.translateYProperty().bind(aura.translateYProperty());
	    partPane.translateZProperty().bind(aura.translateZProperty());
	    partPane.getChildren().addAll(shape);
	    auraPane.getChildren().addAll(partPane);
	}
	
}
