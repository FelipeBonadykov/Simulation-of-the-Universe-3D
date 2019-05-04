package space;
import static space.enumSpaceObjects.SUN;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class SpaceObject{
	public static Sphere create(enumSpaceObjects object) {
		var shape = new Sphere(object.radius);
		shape.setCullFace(CullFace.NONE);
		shape.setMaterial(object.material);
		return shape;
	}
	public static Sphere getSky() {
		var sky = new Sphere(2500);
		sky.setCullFace(CullFace.FRONT);
		sky.getTransforms().add(new Rotate(90, Rotate.X_AXIS));
		sky.setMaterial(new PhongMaterial(Color.BLACK, null, null,null, new Image("file:files/textures/stars.jpg")));
		return sky;
	}
	public SpaceObject move(enumSpaceObjects object, Node spaceObject) {
		if(object==SUN)return this;
		var move = new PathTransition(new Duration(1800/object.angleYear), new Circle(object.distance), spaceObject);
		move.setInterpolator(Interpolator.LINEAR);
		move.setCycleCount(Animation.INDEFINITE);
		move.play();
		return this;
	}
	public SpaceObject rotate(double duration, Node spaceObject) {
		spaceObject.getTransforms().add(new Rotate(90, Rotate.X_AXIS));
		var rotate = new RotateTransition(new Duration(duration), spaceObject);
		rotate.setAxis(Rotate.Z_AXIS);
		rotate.setByAngle(360);
		rotate.setInterpolator(Interpolator.LINEAR);
		rotate.setCycleCount(Timeline.INDEFINITE);
		rotate.play();
		return this;
	}
}
