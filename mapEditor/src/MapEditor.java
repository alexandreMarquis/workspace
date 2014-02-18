
public class MapEditor {
	public static void main(String[] args) {
		MapModel m = new MapModel();
		MapView v = new MapView(m);
		MapController c = new MapController(m);
		v.addListner(c);
		MapFrame f = new MapFrame(v,c);
	}
}
