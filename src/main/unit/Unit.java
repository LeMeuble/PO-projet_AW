package main.unit;

import main.game.Player;
import main.map.MapMetadata;
import main.terrain.Property;
import main.terrain.Terrain;
import main.util.OptionSelector;
import main.weapon.RangedWeapon;
import main.weapon.Weapon;
import main.weather.Weather;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe abstraite representant une unite
 *
 * @author Tristan LECONTE--DENIS
 * @author GRAVOT Lucien
 */
public abstract class Unit {

    public static final int MAX_HEALTH = 10;
    private static final double HELICOPTER_SNOWY_MOVEMENT_MULTIPLIER = 2 / 3d;


    protected final Player.Type owner;
    private double health;
    private List<Weapon> weapons;
    private int fuel;
    private boolean hasPlayed;
    private boolean hasMoved;
    private boolean isAlive;

    /**
     * Constructeur d'une unite
     * Initialise toutes les valeurs par defaut,
     *
     * @param owner
     */
    public Unit(Player.Type owner) {

        this.owner = owner;
        this.health = MAX_HEALTH;
        this.weapons = new ArrayList<>();
        this.fuel = 100;
        this.hasPlayed = false;
        this.hasMoved = false;
        this.isAlive = true;

    }

    /**
     * Trnasforme une string en une nouvelle instance d'unite.
     * Fonction appelee dans {@link main.parser.MapParser#parseMap(MapMetadata)}
     * @param string La string a parser
     * @return Une nouvelle instance de l'unite presente sur la case si il en a une, null sinon
     */
    public static Unit parse(String string) {

        if (string == null) return null;

        String format = string.replace(" ", "");
        if (format.startsWith("[") && format.endsWith("]")) {

            String[] split = format.substring(1, format.length() - 1).split(";");

            if (split.length == 2 && !split[0].equals(".")) {

                final String unitSpan = split[0].trim();
                final String ownerSpan = split[1].trim();

                UnitType type = UnitType.fromCharacter(unitSpan.charAt(0));
                Player.Type owner = !ownerSpan.equals(".") ? Player.Type.fromValue(Integer.parseInt(ownerSpan)) : null;

                if (type != null && owner != null) return type.newInstance(owner);

            }

        }
        return null;

    }


    /**
     * @return Le joueur proprietaire de l'unite
     */
    public Player.Type getOwner() {
        return this.owner;
    }

    /**
     * @return La vie de l'unite
     */
    public double getHealth() {
        return this.health;
    }

    /**
     * Definit la vie de l'unite
     *
     * @param health La nouvelle valeur de la vie
     */
    public void setHealth(double health) {
        this.health = health;
    }

    /**
     * Inflige des degats à l'unite
     *
     * @param damage Le nombre de degats a infliger
     */
    public void damageBy(double damage) {
        this.health -= damage;
        // Todo : Dégats en fonction de l'unité (El famoso Problème Pour Les Nous Du Futur) (aka ceux qui relisent ce commentaire)
        // Todo : Se rendre compte que ces gens, c'est vous
        // Todo : Enfin, nous, mais vous saisissez l'idée
        // Si l'unite n'a plus de vie, on la tue
        if (this.health <= 0.0d) {
            this.isAlive = false;
            this.health = 0.0d;
        }
    }

    // Todo : Commentaire de ça et ameliorer (shitty code v2)
    public int getMovementPoint(Weather weather) {

        if (this instanceof Flying && weather == Weather.SNOWY) {
            return (int) (this.getType().getMovementPoint() * HELICOPTER_SNOWY_MOVEMENT_MULTIPLIER);
        }

        return this.getType().getMovementPoint();
    }

    /**
     * Ajoute une arme dans la liste des armes de l'unite
     *
     * @param weapon L'arme a ajouter
     */
    public void addWeapon(Weapon weapon) {
        this.weapons.add(weapon);
    }

    /**
     * @return Le fuel de l'unite
     */
    public int getFuel() {
        return this.fuel;
    }

    /**
     * Definit le fuel de l'unite
     *
     * @param fuel La nouvelle valeur du fuel
     */
    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    /**
     * @return true si l'unite a joue, false sinon
     */
    public boolean hasPlayed() {
        return this.hasPlayed;
    }

    /**
     * Definit si l'unite a joue ou non
     *
     * @param hasPlayed Un boolean
     */
    public void setHasPlayed(boolean hasPlayed) {
        this.hasPlayed = hasPlayed;
    }

    /**
     * @return true si l'unite a bouge, false sinon
     */
    public boolean hasMoved() {
        return this.hasMoved;
    }

    /**
     * Definit si l'unite a bouge ou non
     *
     * @param hasMoved Un boolean
     */
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    /**
     * @return true si l'unite est en vie, false sinon
     */
    public boolean isAlive() {
        return this.isAlive;
    }

    /**
     * Definit si l'unite est vivant ou non
     *
     * @param alive Un boolean
     */
    public void setAlive(boolean alive) {
        this.isAlive = alive;
    }

    /**
     * Reinitialise les valeurs d'actions de l'unite
     */
    public void prepareForNextTurn() {
        this.hasPlayed = false;
        this.hasMoved = false;
    }

    /**
     * Permet a cette unite d'en attaquer une autre
     * Choisit automatiquement l'arme la plus efficace contre la cible
     *
     * @param target La cible a attaquer
     */
    public void attack(Unit target) {

        UnitType targetType = target.getType();
        Weapon bestWeapon = this.bestWeaponAgainst(targetType);
        // Todo : Verifier si c'est les bonnes fonctions qui sont appelees
        target.damageBy(this.calculateDamage(bestWeapon.getMultiplierOn(targetType)));

        if(!(bestWeapon instanceof RangedWeapon)) {

            Weapon targetBestWeapon = target.bestWeaponAgainst(this.getType());
            this.damageBy(target.calculateDamage(targetBestWeapon.getMultiplierOn(this.getType())));

        }

    }

    /**
     * Calcule les degats infligees a la cible
     *
     * @param multiplier Le multiplicateur de degats
     * @return Le nombre de degats infliges
     */
    public double calculateDamage(float multiplier) {
        return multiplier * Math.ceil(this.health);
    }

    /**
     * Choisit la meilleure arme de l'unite courante contre l'unite cible
     *
     * @param unitType Le type de l'unite cible
     * @return La meilleure arme utilisable
     */
    public Weapon bestWeaponAgainst(UnitType unitType) {

        Weapon bestWeapon = null;
        // Cherche parmi toutes les armes de l'unite courante
        for (Weapon w : this.weapons) {

            //Si pas de meilleure arme, erifie si l'arme peut être utilisee contre la cible
            if (bestWeapon == null && w.canBeUsedOn(unitType)) {
                bestWeapon = w;
            }

            // Sinon, verifie si l'arme peut être utilisee sur la cible, et si son multiplicateur est plus eleve que l'arme deja selectionne
            if (w.canBeUsedOn(unitType) && w.getMultiplierOn(unitType) > bestWeapon.getMultiplierOn(unitType)) {
                bestWeapon = w;
            }

        }
        return bestWeapon;
    }

    /**
     * Capture une propriete
     * @param property La propriete a capturer
     */
    public void capture(Property property) {

        // Seules les unitees a pied peuvent capturer une propriete
        if(this instanceof OnFoot) {

            double currentDefense = property.getDefense();
            double newDefense = Math.ceil(currentDefense - this.getHealth());

            property.setDefense(newDefense);

            // Si la defense de la propriete passe en dessous de 0, elle se fait capturer
            if (property.getDefense() <= 0) {

                property.setOwner(this.getOwner());
                property.setDefense(5);

            }

            // L'attaque d'une propriete compte comme un tour complet
            this.setHasPlayed(true);
        }

    }

    // Todo : De la doc pour des abstracts ?

    public String toString() {

        return this.getClass().getSimpleName();

    }

    public OptionSelector<UnitAction> getAvailableActions() {
        OptionSelector<UnitAction> actions = new OptionSelector<>();
        actions.addOption(UnitAction.WAIT, true);
        actions.addOption(UnitAction.MOVE, !this.hasMoved());

        return actions;
    }

    public abstract UnitType getType();

    public abstract int getMinReach();

    public abstract int getMaxReach();

    public abstract String getFile(int frame);

    public abstract boolean canMoveTo(Terrain destination, Weather weather);

    public abstract int getMovementCostTo(Terrain destination, Weather weather);

}
