package agents;

/**
 * Created by mec on 2015-12-07.
 */
public abstract class CirclerMethodTemplate implements WeaponStrategy {

    public void behave(){
        //TODO: build seeker template
    }
    protected abstract void move();
    protected abstract boolean doSomethingElse();

}
