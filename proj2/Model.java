import java.util.ArrayList;
import java.lang.Math;
import java.awt.Point;

class Thing
{
	int x;
	int y;
	int kind;
	double t;

	public static Thing newThing(int x, int y, int kind) {
		if(kind == 9) {
			System.out.println("Turtle");
			return new Jumper(x, y, kind);
		}
		else {
			return new Thing(x, y, kind);
		}
	}

	Thing(Json ob) {
		this.x = (int)ob.getLong("x");
		this.y = (int)ob.getLong("y");
		this.kind = (int)ob.getLong("kind");
		this.t =(double)ob.getDouble("t");
	}

	protected Thing(int x, int y, int kind)
	{
		this.x = x;
		this.y = y;
		this.kind = kind;
	}

	Json marshal(){
		Json j = Json.newObject();
		j.add("x", this.x);
		j.add("y", this.y);
		j.add("kind", this.kind);
		j.add("t", this.t);

		return j;
	}

	Point pos(int t) {
		return new Point(this.x, this.y);
	}
}

class Jumper extends Thing {
	protected Jumper(int x, int y, int kind)
	{
		super(x, y, kind);
	}

	protected Jumper(Json ob) {
		super(ob);
	}

	Point pos(int t) {
		return new Point(this.x, this.y - (int)Math.max(0., 50 * Math.sin(((double)t) / 5)));
	}
}

class Model {
	// int turtle_x;
	// int turtle_y;
	// int dest_x;
	// int dest_y;
	// static int speed = 4;
	ArrayList<Thing> things;
	int itemNum;

	Model()
	{
		// this.turtle_x = 100;
		// this.turtle_y = 100;
		// this.dest_x = 150;
		// this.dest_y = 100;
		this.itemNum = 0;
		this.things = new ArrayList<Thing>();
	}

	public Json marshal() {
		Json map = Json.newObject();
		Json list_of_things = Json.newList();
		map.add("things", list_of_things);
				
		for(Thing t : this.things) {
			list_of_things.add(t.marshal());
		}
		
		return map;
	}

	public Json unmarshal(Json map) {
		things.clear();
		Json list_of_things = map.get("things");

		for(int i = 0; i < list_of_things.size(); i++) {
			if(list_of_things.get(i).getLong("kind") == 9){
				this.things.add(new Jumper(list_of_things.get(i)));
			}
			else{
			this.things.add(new Thing(list_of_things.get(i)));
			}
		}

		return list_of_things; 
	}

	public void load() {
		Json ob = Json.load("map.json");
		unmarshal(ob);
		System.out.println(ob);
	}

	public void update()
	{	}

    public void reset()
    {	}

	public void setDestination(int x, int y) 
	{	}

	public void addThing(int x, int y) {
	    Thing thing = Thing.newThing(x, y, itemNum);
		things.add(thing);
	}

  	public void removeThing(int x, int y) {
		//variable for shortest distance
		double shortest_distance = 100000.0;
		//variable for index of shortest distance
		int shortest_index = -1;

		for(int i = 0; i < things.size(); i++) {
			if((Math.sqrt(Math.pow(x - things.get(i).x, 2) + Math.pow(y - things.get(i).y, 2))) <= shortest_distance) {
				shortest_distance = Math.sqrt(Math.pow(x + View.horizontal - things.get(i).x, 2) + Math.pow(y + View.vertical - things.get(i).y, 2));	
				shortest_index = i;
			}		
		}
		
		if(shortest_index != -1)
			things.remove(things.get(shortest_index));		
	}
}

