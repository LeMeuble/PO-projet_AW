package main.unit;

import com.sun.istack.internal.Nullable;
import main.game.Player;
import main.map.Case;
import main.map.Grid;
import main.map.MapMetadata;
import main.terrain.Property;
import main.util.OptionSelector;
import main.weapon.MeleeWeapon;
import main.weapon.RangedWeapon;
import main.weapon.Weapon;
import main.weather.Weather;
import ressources.Config;
import ressources.PathUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstraite representant une unite.
 * Il s'agit de la classe mere de toutes les unites du jeu.
 * Une unite est caracterisee par plusieurs attributs :
 * - son proprietaire
 * - son type (non stocke dans cette classe)
 * - son nombre de points de vie
 * - son nombre de points de deplacement (non stocke dans cette classe)
 * - ses armes
 *
 * @author Tristan LECONTE--DENIS
 * @author GRAVOT Lucien
 * @see UnitType
 * @see Weapon
 * @see UnitMovementPoint
 * @see Player.Type
 */
public abstract class Unit {

    public static final float MAX_HEALTH = Config.UNIT_MAX_HEALTH;


    private final Player.Type owner;
    private final List<Weapon> weapons;
    private UnitFacing facing;
    private float health;
    private int energy;
    private boolean hasPlayed;
    private boolean hasMoved;

    /**
     * Constructeur d'une unite.
     * Initialise toutes les valeurs par defaut,
     *
     * @param owner Proprietaire de l'unite
     */
    public Unit(Player.Type owner) {

        this.owner = owner;
        this.weapons = new ArrayList<>();
        this.facing = UnitFacing.random();
        this.health = Unit.MAX_HEALTH;
        this.hasPlayed = false;
        this.hasMoved = false;

    }

    /**
     * Transforme une chaine de caractere en une nouvelle instance d'unite.
     * Fonction appelee dans {@link main.parser.MapParser#parseMap(MapMetadata)}
     *
     * @param string La chaine caractere a parser.
     *
     * @return Une nouvelle instance de l'unite presente sur la case si il en a une, null sinon.
     *
     * @see UnitType
     * @see main.parser.MapParser#parseMap(MapMetadata)
     */
    public static Unit parse(String string) {

        if (string == null) return null;

        // Suppression des espaces potentiellement presents
        String format = string.replace(" ", "");

        // Verification de la validite de la chaine
        if (format.startsWith("[") && format.endsWith("]")) {

            // Separation des differents champs
            String[] split = format.substring(1, format.length() - 1).split(";");

            // Verification du nombre de champs, et recuperation verification si la chaine contient un type d'unite
            if (split.length == 2 && !split[0].equals(".")) {

                // Recuperation du type d'unite et de son proprietaire (chaines de caracteres)
                final String unitSpan = split[0].trim();
                final String ownerSpan = split[1].trim();

                // Verification que le proprietaire est valide (un entier)
                try {
                    // Conversion de la chaine de caracteres en type d'unite et proprietaire
                    UnitType type = UnitType.fromCharacter(unitSpan.charAt(0));
                    Player.Type owner = !ownerSpan.equals(".") ? Player.Type.fromValue(Integer.parseInt(ownerSpan)) : null;

                    if (type != null && owner != null) return type.newInstance(owner);
                }
                catch (NumberFormatException ignored) {
                }

            }

        }
        return null;

    }


    /**
     * Obtenir le proprietaire de l'unite.
     *
     * @return Le joueur proprietaire de l'unite.
     *
     * @see Player.Type
     */
    public Player.Type getOwner() {
        return this.owner;
    }

    /**
     * Obtenir la liste des armes de l'unite.
     *
     * @return La liste des armes de l'unite.
     *
     * @see Weapon
     */
    public List<Weapon> getWeapons() {
        return this.weapons;
    }

    /**
     * Ajoute une arme dans la liste des armes de l'unite.
     *
     * @param weapon L'arme a ajouter.
     *
     * @see Weapon
     */
    public void addWeapon(Weapon weapon) {
        if (weapon != null) this.weapons.add(weapon);
    }

    /**
     * Obtenir le nombre de points de vie de l'unite arrondi à l'entier superieur.
     *
     * @return La vie de l'unite.
     */
    public float getHealth() {
        return (float) Math.ceil(this.health);
    }

    /**
     * Definir la vie de l'unite.
     *
     * @param health La nouvelle valeur de la vie.
     */
    public void setHealth(float health) {
        this.health = Math.max(0, Math.min(health, Unit.MAX_HEALTH));
    }

    public boolean hasEnergy() {
        return this.energy > 0;
    }
    public int getEnergy() {
        return this.energy;
    }

    public void setEnergy(int energy) {
        this.energy = Math.max(energy, this.energy);
    }

    /**
     * Determiner si l'unite a joue.
     *
     * @return true si l'unite a joue, false sinon.
     */
    public boolean hasPlayed() {
        return this.hasPlayed;
    }

    /**
     * Definir si l'unite a joue ou non.
     *
     * @param hasPlayed Un boolean.
     */
    public void setPlayed(boolean hasPlayed) {
        this.hasPlayed = hasPlayed;
    }

    /**
     * Determiner si l'unite a bouge.
     *
     * @return true si l'unite a bouge, false sinon.
     */
    public boolean hasMoved() {
        return this.hasMoved;
    }

    /**
     * Definir si l'unite a bouge ou non.
     *
     * @param hasMoved Un boolean.
     */
    public void setMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    /**
     * Determiner si l'unite est en vie.
     * Cette n'est appellee que pour determiner si l'unite peut etre supprimee de la carte.
     *
     * @return true si l'unite est en vie, false sinon.
     */
    public boolean isAlive() {
        return this.health > 0;
    }

    /**
     * Obtenir la portee maximale de l'unite en consideration toutes
     * les armes qu'elle possede.
     *
     * @return La portee maximale de l'unite, ou -1 si elle n'a pas d'arme.
     *
     * @see Weapon#getMaxReach()
     */
    public int getMaxWeaponRange() {

        int minRange = -1;
        for (Weapon weapon : weapons) {
            if (minRange == -1 || weapon.getMaxReach() < minRange) {
                minRange = weapon.getMaxReach();
            }
        }

        return minRange;

    }

    /**
     * Obtenir la portee minimale de l'unite en consideration toutes
     * les armes qu'elle possede.
     *
     * @return La portee minimale de l'unite, ou -1 si elle n'a pas d'arme.
     *
     * @see Weapon#getMinReach()
     */
    public int getMinWeaponRange() {

        int minRange = -1;
        for (Weapon weapon : weapons) {
            if (minRange == -1 || weapon.getMinReach() < minRange) {
                minRange = weapon.getMinReach();
            }
        }

        return minRange;

    }

    public boolean isDistanceReachable(double distance) {

        for (Weapon weapon : weapons) {
            if (weapon.getMinReach() <= distance && weapon.getMaxReach() >= distance) {
                return true;
            }
        }
        return false;

    }

    /**
     * Inflige des degats à l'unite.
     * La vie de l'unite est reduite de la valeur passee en parametre.
     * Cette methode peut indiquer une unite comme etant morte.
     *
     * @param damage Le nombre de degats a infliger
     */
    public void damageBy(float damage) {

        this.setHealth(this.getHealth() - damage);

    }

    /**
     * Obtenir le nombre de points de mouvement de l'unite selon la meteo.
     *
     * @param weather La meteo courante.
     *
     * @return Le nombre de points de mouvement de l'unite.
     *
     * @see Weather
     * @see UnitMovementPoint
     */
    public int getMovementPoint(Weather weather) {

        final UnitMovementPoint unitMovementPoint = UnitMovementPoint.fromUnitAndWeather(this.getType(), weather);

        return unitMovementPoint != null ? unitMovementPoint.getMovementPoint() : 0;
    }

    public abstract UnitType getType();

    /**
     * Permet a cette unite d'en attaquer une autre.
     * Choisit automatiquement l'arme la plus efficace contre la cible.
     *
     * @param target La cible a attaquer
     */
    public void attack(Unit target) {

        final Weapon bestWeapon = this.bestWeaponAgainst(target);

        if (bestWeapon != null) {

            // Inflige les degats
            target.damageBy(this.calculateDamage(bestWeapon.getMultiplierOn(target)));
            bestWeapon.setAmmo(bestWeapon.getAmmo() - 1);

            if (!(bestWeapon instanceof RangedWeapon)) {

                if(target.isAlive()) {

                    Weapon targetBestWeapon = target.bestWeaponAgainst(this);

                    if (targetBestWeapon != null) {
                        this.damageBy(target.calculateDamage(targetBestWeapon.getMultiplierOn(this)));
                        targetBestWeapon.setAmmo(targetBestWeapon.getAmmo() - 1);
                    }
                }
            }
        }
    }

    public boolean canAttack(Unit target) {

        if(this.getOwner() == target.getOwner()) return false;

        final Weapon bestWeapon = this.bestWeaponAgainst(target);
        System.out.println("bestWeapon = " + bestWeapon);
        if (bestWeapon != null) {

            if (bestWeapon.hasAmmo()) {

                return true;

            }

        }

        return false;
    }

    /**
     * Calcule les degats infligees a la cible
     *
     * @param multiplier Le multiplicateur de degats
     *
     * @return Le nombre de degats infliges
     */
    public float calculateDamage(float multiplier) {
        return (float) (multiplier * Math.ceil(this.health));
    }

    /**
     * Choisir la meilleure arme de l'unite courante contre l'unite cible
     *
     * @param unitType Le type de l'unite cible
     *
     * @return La meilleure arme utilisable
     *
     * @see Weapon
     */
    @Nullable
    public Weapon bestWeaponAgainst(Unit unit) {

        Weapon bestWeapon = null;

        // Cherche parmi toutes les armes de l'unite courante
        for (Weapon weapon : this.weapons) {

            // Si l'arme est utilisable contre l'unite cible
            if (weapon.canBeUsedOn(unit) && weapon.hasAmmo()) {

                // Si l'arme est plus efficace que l'arme courante ou si l'arme courante n'est pas definie
                if (bestWeapon == null || weapon.getMultiplierOn(unit) > bestWeapon.getMultiplierOn(unit)) {
                    bestWeapon = weapon;
                }

            }

        }
        return bestWeapon;
    }

    /**
     * Capture une propriete
     *
     * @param property La propriete a capturer
     */
    public void capture(Property property) {

        // Seules les unitees a pied peuvent capturer une propriete
        if (this instanceof OnFoot) {

            double currentDefense = property.getDefense();
            double newDefense = Math.ceil(currentDefense - this.getHealth());

            property.setDefense(newDefense);

            // Si la defense de la propriete passe en dessous de 0, elle se fait capturer
            if (property.getDefense() <= 0) {

                property.setOwner(this.getOwner());
                property.setDefense(5);

            }

            // L'attaque d'une propriete compte comme un tour complet
            this.setPlayed(true);
        }

    }

    public boolean hasAnyWeapon() {
        return !this.weapons.isEmpty();
    }

    public boolean hasRangeWeapon() {

        for (Weapon weapon : weapons) {
            if (weapon instanceof RangedWeapon) {
                return true;
            }
        }
        return false;

    }

    public boolean hasMeleeWeapon() {

        for (Weapon weapon : weapons) {
            if (weapon instanceof MeleeWeapon) {
                return true;
            }
        }
        return false;

    }

    public String toString() {

        return this.getClass().getSimpleName();

    }

    public OptionSelector<UnitAction> getAvailableActions(Case currentCase, Grid contextGrid) {

        final OptionSelector<UnitAction> actions = new OptionSelector<>();
        actions.addOption(UnitAction.WAIT, true);
        actions.addOption(UnitAction.MOVE, !this.hasMoved());

        final int minRange = this.getMinWeaponRange();
        final int maxRange = this.getMaxWeaponRange();

        List<Case> cases = contextGrid.getCasesAround(currentCase.getCoordinate().getX(), currentCase.getCoordinate().getY(), minRange, maxRange);

        boolean adjacentEnemy = false;
        boolean inRangeEnemy = false;

        for (Case aCase : cases) {

            Unit unit = aCase.getUnit();
            if (unit != null) {
                if (unit.getOwner() != this.getOwner()) {
                    double d = aCase.distance(currentCase);
                    if (d == 1) {
                        adjacentEnemy = this.hasMeleeWeapon();
                    }
                    else if (d >= minRange && d <= maxRange) {
                        inRangeEnemy = this.hasRangeWeapon() && this.isDistanceReachable(d);
                    }
                }

            }
        }

        actions.addOption(UnitAction.ATTACK, adjacentEnemy);
        actions.addOption(UnitAction.RANGED_ATTACK, inRangeEnemy && !this.hasMoved);

        return actions;

    }

    public boolean canMoveTo(Case c, Weather w) {

        if (!c.hasUnit() || (c.hasUnit() && c.getUnit().getOwner() == this.getOwner())) {
            return true;
        }

        return false;

    }

    public void supply() {

        for (Weapon weapon : weapons) {
            weapon.supply();
        }

        this.energy = this.getType().getEnergy();

    }

    /**
     * Repare l'unite courante entre 0 et {@link Config#UNIT_MAX_HEALTH_RECOVERY} points de vie
     */
    public void repair() {

        // TODO: Voir les cas limites

        int addedHealth = (int) Math.min(Config.UNIT_MAX_HEALTH_RECOVERY, Unit.MAX_HEALTH - this.getHealth());
        this.repair(addedHealth);

    }

    /**
     * Repare l'unite courante d'un certain nombre de points de vie
     *
     * @param amount Le nombre de points de vie a ajouter
     */
    public void repair(int amount) {

        this.setHealth(this.getHealth() + amount);

    }

    /**
     * Retourne le prix de reparation d'un point de vie d'une unite
     *
     * @return Le prix de reparation d'un point de vie d'une unite
     *
     * @see UnitType#getPrice()
     */
    public int getRepairCost() {

        return (int) Math.floor(this.getType().getPrice() * Config.UNIT_HEALTH_PRICE_RATIO);

    }

    public String getFile(int frame) {

        return PathUtil.getUnitIdleFacingPath(this.getType(), this.getOwner(), this.getFacing(), !this.hasPlayed(), frame);

    }

    public UnitFacing getFacing() {
        return this.facing;
    }

    public void setFacing(UnitFacing facing) {
        this.facing = facing;
    }

    public abstract int getMovementCostTo(Case destination, Weather weather);


}
