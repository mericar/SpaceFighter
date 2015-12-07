package agents;

/**
 * Created by mec on 2015-12-07.
 */
public abstract class SeekerMethodTemplate implements WeaponStrategy {

    public void behave(){
        //TODO: build seeker template
    }
    protected abstract void move();
    protected abstract boolean doSomething();

}
