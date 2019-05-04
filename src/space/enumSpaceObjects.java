package space;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;

public enum enumSpaceObjects { 
	SUN    (0,  25,1/65,     null , null, null,"sun", Color.WHITE),
	MERCURY(1, .40,   2,  "merury", null, null, null, Color.WHITE),
	VENUS  (2, .90,   4,   "venus", null, null, null, Color.WHITE),
	EARTH  (3,   1,   7,   "earth", null, null, null, Color.WHITE),
	MARS   (4,  .5,  10,    "mars", null, null, null, Color.WHITE),
	JUPITER(5,  11,  15, "jupiter", null, null, null, Color.WHITE),
	SATURN (6,  9.5, 20,  "saturn", null, null, null, Color.BEIGE),
	URANUS (7,  3.9, 25,  "uranus", null, null, null, Color.WHITE), 
	NEPTUNE(8,  4.0, 29, "neptune", null, null, null, Color.WHITE);
	
	public final int number;
	public final double radius, distance, angleYear;
	public final PhongMaterial material;
	
	private enumSpaceObjects(int number, double radius, double distance, String map, String specular, String surface, String illuminate, Color color){
		this.number = number;
		this.radius = radius;
		this.distance = distance*65;
		this.angleYear = (9-number)*.001;
		material = new PhongMaterial(
				color, 
				number!=3? new Image("file:files/textures/"+map+".jpg") : new Image(("file:files/textures/Earth/ocean.gif")),
				specular!=null ? new Image("file:files/textures/"+specular+".jpg") : null,   
				surface!=null ? new Image("file:files/textures/"+surface+".jpg") : null,     
				illuminate!=null ? new Image("file:files/textures/"+illuminate+".jpg") : null
			);
	}
	public static final enumSpaceObjects getObject(int index) {
		switch (index) {
		case 0: return SUN;
		case 1: return MERCURY;
		case 2: return VENUS;
		case 3: return EARTH;
		case 4: return MARS;
		case 5: return JUPITER;
		case 6: return SATURN;
		case 7: return URANUS;
		case 8: return NEPTUNE;
		default: throw new IllegalArgumentException("wrong value");
		}
	}
}
