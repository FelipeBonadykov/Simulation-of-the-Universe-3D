package stuff_on_earth;
import static space.MainSpace.Earth;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import space.MainSpace;

public class StuffOnEarth{
	
	private Group shape;
	public Group getShape() {
		return shape;
	}
	public void setShape(Group shape) {
		this.shape=shape;
	}
	private Sphere aura = new Sphere(0);
	public Sphere getAura() {
		return aura;
	}
	private PathTransition movement;
	public PathTransition getMovement() {
		return movement;
	}
	public double distanceFromTheCenterOfPlanet, upsfift;
	private StackPane auraPane = new StackPane();
	public StackPane getAuraPane () {
		return auraPane;
	}
	
	public StuffOnEarth(String name, String extension, float distanceFromTheCenter, float size, int angle, int offset) {
		shape = Shape3dConstructor.getShape3D(name, extension);
		shape.getTransforms().addAll
		(new Rotate(angle, Rotate.Y_AXIS), new Rotate(90, Rotate.Z_AXIS), new Scale(size,size, size));
		
		rotate(offset, shape);
		move(distanceFromTheCenter, angle, offset, aura);
	
	    bindOnEarth(auraPane);
        auraPane.translateZProperty().bind(Earth.translateZProperty().add(upsfift=-Math.sin(Math.toRadians(angle))));
        auraPane.getChildren().add(aura);
        MainSpace.root.getChildren().add(auraPane);
	    	
	    var stuffPane = new StackPane();
        stuffPane.translateXProperty().bind(aura.translateXProperty());
        stuffPane.translateYProperty().bind(aura.translateYProperty());
        stuffPane.translateZProperty().bind(aura.translateZProperty());
        stuffPane.getChildren().addAll(shape);
        auraPane.getChildren().addAll(stuffPane);
		
	}
	
	public StuffOnEarth(float distanceFromTheCenter, double offset, Node mesh) {
		shape = new Group();
		shape.getChildren().add(mesh);
		mesh.getTransforms().add(new Rotate(90, Rotate.Z_AXIS));
		
		rotate(0, shape);
		move(distanceFromTheCenter, 0, offset, shape);
		
		var stuffPane = new StackPane();
	    bindOnEarth(stuffPane);
	    stuffPane.getChildren().addAll(shape);
	    MainSpace.root.getChildren().add(stuffPane);
	}
	
	public StuffOnEarth(StuffOnEarth apartment, Pane cameraPane) {
		bindOnEarth(cameraPane);
		cameraPane.getChildren().get(0).getTransforms().clear();
	    cameraPane.getChildren().get(0).getTransforms().addAll
	    (new Translate(-625, -900, -350), new Rotate(90, Rotate.X_AXIS));
	    int offset=190+(int)(apartment.getMovement().getCurrentTime().toSeconds())*10;
	    rotate(offset, cameraPane.getChildren().get(0));
	    move(1f, 0, offset, cameraPane.getChildren().get(0));
	}
	
	private void bindOnEarth(Pane pane) {
		pane.translateXProperty().bind(Earth.translateXProperty());
	    pane.translateYProperty().bind(Earth.translateYProperty());
	    pane.translateZProperty().bind(Earth.translateZProperty());
	}
	private void rotate(double offset, Node node) {
		var rotate = new RotateTransition(new Duration(360_00), node);
		rotate.setAxis(Rotate.Z_AXIS);
		rotate.setByAngle(360);
		rotate.setFromAngle(offset);
		rotate.setInterpolator(Interpolator.LINEAR);
		rotate.setCycleCount(Timeline.INDEFINITE);
		rotate.play();
	}
	private void move(float distanceFromTheCenter, int angle, double offset, Node mesh) {
		var movement = new PathTransition(new Duration(360_00), 
				new Circle(distanceFromTheCenterOfPlanet=distanceFromTheCenter*Math.cos(Math.toRadians(angle))), mesh);
		movement.setInterpolator(Interpolator.LINEAR);
		movement.setCycleCount(Animation.INDEFINITE);
		movement.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);
		movement.playFrom(new Duration(offset*100));
		this.movement = movement;
	}
}
