package main.weapon;

public abstract class MeleeWeapon extends Weapon {

    public static final int MIN_REACH = 1;
    public static final int MAX_REACH = 1;

    public MeleeWeapon() {
        super();
    }

    @Override
    public int getMinReach() {
        return MeleeWeapon.MIN_REACH;
    }

    @Override
    public int getMaxReach() {
        return MeleeWeapon.MAX_REACH;
    }

}
