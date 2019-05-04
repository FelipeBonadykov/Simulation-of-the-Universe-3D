package stuff_on_earth;

import com.interactivemesh.jfx.importer.ModelImporter;
import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import com.interactivemesh.jfx.importer.tds.TdsModelImporter;

import javafx.scene.Node;
import javafx.scene.shape.Shape3D;

final class Shape3dConstructor {
	static Node[] getShape3D(String name, String extension) {
		ModelImporter modelImporter;
		switch (extension) {
		case "obj":
			modelImporter = new ObjModelImporter();
			break;
		case "3ds":
			modelImporter = new TdsModelImporter();
			break;
		default:
			throw new RuntimeException("You have used an unsupported type");
		}
		Node[] mesh=null;
		try {
			modelImporter.read("files/3d/"+name+"/"+name+"."+extension);//directory of object in relation to the project
			mesh = (Node[]) modelImporter.getImport();
			modelImporter.close();
		} catch (Exception e) {
			System.err.println(" FILE NOT FUND  "+e.getMessage());
		}
		return mesh;
	}
	private Shape3dConstructor(){}
}
