package resources;

/**
 * Created by mec on 2015-12-02.
 */
public class CoordPair {
    final int x_coord;
    final int y_coord;

    public CoordPair(int x, int y){
        this.x_coord = x;
        this.y_coord = y;
    }

    public int getY_coord() {
        return y_coord;
    }

    public int getX_coord() {
        return x_coord;
    }
}
