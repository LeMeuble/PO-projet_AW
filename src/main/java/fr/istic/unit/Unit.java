package fr.istic.unit;

import fr.istic.Config;
import fr.istic.DisplayUtil;
import fr.istic.MiniWars;
import fr.istic.PathUtil;
import fr.istic.game.Player;
import fr.istic.map.Case;
import fr.istic.map.Coordinate;
import fr.istic.map.Grid;
import fr.istic.terrain.Property;
import fr.istic.unit.type.Cruiser;
import fr.istic.unit.type.Submarine;
import fr.istic.util.OptionSelector;
import fr.istic.weapon.MeleeWeapon;
import fr.istic.weapon.RangedWeapon;
import fr.istic.weapon.Weapon;
import fr.istic.weather.Weather;

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
 * @author PandaLunatique
 * @author LeMeuble
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

    private final Coordinate coordinate;

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
        this.coordinate = new Coordinate(-1, -1);
        this.energy = this.getType().getEnergy();

    }

    /**
     * Transforme une chaine de caractere en une nouvelle instance d'unite.
     * Fonction appelee dans {@link main.parser.MapParser#parseMap(MapMetadata)}
     *
     * @param string La chaine caractere a fr.istic.parser.
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
     * @return Les coordonnees de l'unite
     */
    public Coordinate getCoordinate() {
        return this.coordinate.clone();
    }

    /**
     * Redefinit les coordonnees de l'unite, a l'aide d'un objet coordonnee
     *
     * @param coordinate Les nouvelles coordonnees
     */
    public void setCoordinate(Coordinate coordinate) {
        this.coordinate.setX(coordinate.getX());
        this.coordinate.setY(coordinate.getY());
    }

    /**
     * Redefinit les coordonnees de l'unite, a l'aide de deux entiers
     *
     * @param x La coordonnee X
     * @param y La coordonnee Y
     */
    public void setCoordinate(int x, int y) {
        this.coordinate.setX(x);
        this.coordinate.setY(y);
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
     * Obtenir le nombre de points de vie de l'unite arrondi a l'entier superieur.
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
        this.energy = Math.max(0, Math.min(energy, this.getType().getEnergy()));
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
     * Verifie si une la distance se situe a portee de l'arme de l'unite
     *
     * @param distance La distance
     *
     * @return true si la distance est a portee, false sinon
     */
    public boolean isDistanceReachable(double distance) {

        for (Weapon weapon : weapons) {
            if (weapon.getMinReach() <= distance && weapon.getMaxReach() >= distance) {
                return true;
            }
        }
        return false;

    }

    /**
     * Inflige des degats a l'unite.
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

    /**
     * Permet a cette unite d'en attaquer une autre.
     * Choisit automatiquement l'arme la plus efficace contre la cible.
     *
     * @param target La cible a attaquer
     */
    public void attack(Unit target) {

        final Weapon bestWeapon = this.bestWeaponAgainst(target);

        // S'il existe une arme pouvant toucher la cible
        if (bestWeapon != null) {

            Coordinate targetCoordinates = target.getCoordinate();
            float terrainDefense = MiniWars.getInstance().getCurrentGame().getGrid().getCase(targetCoordinates).getTerrain().getTerrainCover();  // Inflige les degats

            // Si la cible est une unite terrestre
            if (target instanceof OnFoot || target instanceof Motorized) {
                // On actualise les degats en fonction de la defense du fr.istic.terrain
                target.damageBy(this.calculateDamage(bestWeapon.getMultiplierOn(target)) * (1 - terrainDefense));
            }
            else {
                target.damageBy(this.calculateDamage(bestWeapon.getMultiplierOn(target)));
            }

            bestWeapon.setAmmo(bestWeapon.getAmmo() - 1);

            // Si l'arme est une arme de corps a corps
            if (!(bestWeapon instanceof RangedWeapon)) {

                // La cible riposte si elle est toujours en vie (et si elle en a la possibilite)
                if (target.isAlive()) {

                    Weapon targetBestWeapon = target.bestWeaponAgainst(this);

                    if (targetBestWeapon != null) {
                        this.damageBy(target.calculateDamage(targetBestWeapon.getMultiplierOn(this)));
                        targetBestWeapon.setAmmo(targetBestWeapon.getAmmo() - 1);
                    }
                }
            }
        }
    }

    /**
     * Permet a cette unite d'en attaquer une autre.
     * Choisit automatiquement l'arme a distance la plus efficace contre la cible
     *
     * @param target La cible a attaquer
     */
    @Deprecated
    public void rangedAttack(Unit target) {

        final Weapon bestWeapon = this.bestWeaponAgainst(target);

        // Si il existe une arme a distance pouvant toucher la cible
        if (bestWeapon != null && bestWeapon instanceof RangedWeapon) {

            Coordinate targetCoordinates = target.getCoordinate();
            float terrainDefense = MiniWars.getInstance().getCurrentGame().getGrid().getCase(targetCoordinates).getTerrain().getTerrainCover();  // Inflige les degats

            float weatherModifier = 0;
            if (MiniWars.getInstance().getCurrentGame().getWeather() == Weather.HEAVY_WIND) {
                weatherModifier = 0.2f;
            }

            // Si la cible est une unite terrestre
            if (target instanceof OnFoot || target instanceof Motorized) {
                // On actualise les degats en fonction de la defense du fr.istic.terrain et de la meteo
                target.damageBy(this.calculateDamage(bestWeapon.getMultiplierOn(target)) * (1 - terrainDefense) * (1 - weatherModifier));
            }
            else {
                target.damageBy(this.calculateDamage(bestWeapon.getMultiplierOn(target) * (1 - weatherModifier)));
            }

            bestWeapon.setAmmo(bestWeapon.getAmmo() - 1);
            // La cible ne peut pas riposter
        }

    }

    /**
     * Verifie si l'unite
     *
     * @param target
     *
     * @return
     */
    public boolean canAttack(Unit target) {

        // Pas d'attaque contre soi-meme
        if (this.getOwner() == target.getOwner()) return false;

        final Weapon bestWeapon = this.bestWeaponAgainst(target);

        // Si une arme peut toucher la cible
        if (bestWeapon != null) {
            // Si cette arme a des munitions
            // Si cette arme a des munitions
            if (bestWeapon.hasAmmo()) {

                Grid grid = MiniWars.getInstance().getCurrentGame().getGrid();

                // Si la case de la cible n'est pas dans le brouillard de guerre
                if (!grid.getCase(target.getCoordinate()).isFoggy()) {

                    // Si cette unite est un sous-marin en plongee
                    if (target instanceof Submarine && ((Submarine) target).canSurface()) {
                        // Si l'unite courante est un autre sous-marin ou un croiseur, elle peut toucher ce sous-marin
                        return this instanceof Submarine || this instanceof Cruiser;
                    }
                    // Sinon, l'unite courante peut toucher la cible
                    else {
                        return true;
                    }
                }

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
     * @return La meilleure arme utilisable
     *
     * @see Weapon
     */
    public Weapon bestWeaponAgainst(Unit unit) {

        Weapon bestWeapon = null;
        final int d = this.coordinate.distance(unit.coordinate);
        // Cherche parmi toutes les armes de l'unite courante
        for (Weapon weapon : this.weapons) {

            if (weapon instanceof RangedWeapon && d > 1 || weapon instanceof MeleeWeapon && d == 1) {

                // Si l'arme est utilisable contre l'unite cible
                if (weapon.canBeUsedOn(unit) && weapon.hasAmmo()) {

                    // Si l'arme est plus efficace que l'arme courante ou si l'arme courante n'est pas definie
                    if (bestWeapon == null || weapon.getMultiplierOn(unit) > bestWeapon.getMultiplierOn(unit)) {
                        bestWeapon = weapon;
                    }

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

    /**
     * @return true si la liste d'armes de l'unite n'est pas vide
     */
    public boolean hasAnyWeapon() {
        return !this.weapons.isEmpty();
    }

    /**
     * @return true si l'unite a au moins une arme de corps a corps
     */
    public boolean hasMeleeWeapon() {

        for (Weapon weapon : weapons) {
            if (weapon instanceof MeleeWeapon) {
                return true;
            }
        }
        return false;

    }

    /**
     * @return true si l'unite a au moins une arme de distance
     */
    public boolean hasRangeWeapon() {

        for (Weapon weapon : weapons) {
            if (weapon instanceof RangedWeapon) {
                return true;
            }
        }
        return false;

    }

    public String toString() {

        return this.getClass().getSimpleName();

    }

    /**
     * Renvoie un selecteur d'options, contenant les options disponibles pour l'unite
     *
     * @param currentCase La case courante
     * @param contextGrid La grille dans laquelle l'unite peut evoluer
     *
     * @return Un selecteur d'options
     */
    public OptionSelector<UnitAction> getAvailableActions(Case currentCase, Grid contextGrid) {

        final OptionSelector<UnitAction> actions = new OptionSelector<>();
        actions.addOption(UnitAction.WAIT, true);
        actions.addOption(UnitAction.MOVE, !this.hasMoved());

        final int minRange = this.getMinWeaponRange();
        final int maxRange = this.getMaxWeaponRange();

        List<Case> cases = contextGrid.getCasesAround(currentCase.getCoordinate(), minRange, maxRange);

        boolean adjacentEnemy = false;
        boolean inRangeEnemy = false;

        // On verifie la presence d'ennemis sur les cases adjacentes a l'unite
        for (Case aCase : cases) {

            Unit unit = aCase.getUnit();
            if (unit != null) {
                if (unit.getOwner() != this.getOwner()) {

                    if(this.canAttack(unit)) {
                        int d = aCase.distance(currentCase);

                        // Si la case n'est qu'a une case de distance
                        if (d == 1) {
                            // On verifie si l'unite a une arme de corps a corps
                            adjacentEnemy = this.hasMeleeWeapon();
                        }
                        // Si la case est a portee
                        else if (d >= minRange && d <= maxRange) {
                            // On verifie si l'unite a une arme de distance, pouvant toucher l'unite cible
                            inRangeEnemy = this.hasRangeWeapon() && this.isDistanceReachable(d);
                        }
                    }
                }
            }
        }

        // On ajoute les possibilites d'attaque
        actions.addOption(UnitAction.ATTACK, adjacentEnemy);
        actions.addOption(UnitAction.RANGED_ATTACK, inRangeEnemy && !this.hasMoved);
        //Todo: a revoir (fog, bonne arme) (canAttack)

        final List<Case> adjacentCases = contextGrid.getAdjacentCases(currentCase);
        boolean anyEmptyTransport = false;

        // On liste les transports allies, non pleins et acceptant notre unite, autour de nous
        for (Case adjacentCase : adjacentCases) {

            Unit adjacentUnit = adjacentCase.getUnit();
            if (adjacentUnit != null && adjacentUnit.getOwner() == this.getOwner()) {
                if (adjacentUnit instanceof Transport && !((Transport) adjacentUnit).isFull()) {
                    anyEmptyTransport = ((Transport) adjacentUnit).accept(this);
                }
            }

        }

        actions.addOption(UnitAction.GET_IN, anyEmptyTransport);

        return actions;

    }

    /**
     * Verifie si l'unite peut se deplacer vers la case, en focntion de la meteo
     *
     * @param c La case de destination
     * @param w La meteo
     *
     * @return true si l'unite peut se deplacer, false sinon
     */
    public boolean canMoveTo(Case c, Weather w) {

        return !c.hasUnit() || (c.hasUnit() && c.getUnit().getOwner() == this.getOwner());

    }

    /**
     * Ravitaille cette unite, en reinitialisant : les munitions et l'energie
     */
    public void supply() {

        for (Weapon weapon : weapons) {
            weapon.reload();
        }

        this.energy = this.getType().getEnergy();

    }

    /**
     * Repare l'unite courante entre 0 et {@link Config#UNIT_MAX_HEALTH_RECOVERY} points de vie
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

        return (int) Math.floor(this.getType().getPrice() * (1d / Config.UNIT_MAX_HEALTH));

    }

    public String getFile(int frame) {

        return PathUtil.getUnitIdleFacingPath(this.getType(), this.getOwner(), this.getFacing(), !this.hasPlayed(), frame);

    }

    /**
     * @return La direction dans laquelle l'unite regarde
     */
    public UnitFacing getFacing() {
        return this.facing;
    }

    /**
     * Definit la direction dans laquelle l'unite regarde
     *
     * @param facing Une direction
     */
    public void setFacing(UnitFacing facing) {
        this.facing = facing;
    }

    /**
     * Cette methode s'occupe du rendu le l'unite.
     *
     * @param pixelX            Position en pixel x de l'unite.
     * @param pixelY            Position en pixel y de l'unite.
     * @param frame             Frame actuelle de l'unite.
     * @param width             Taille (largeur) de l'unite en pixel.
     * @param height            Taille (hauteur) de l'unite en pixel.
     * @param displayIndicators Indique s'il est necessaire d'afficher les indicateurs de l'unites.
     */
    public void render(double pixelX, double pixelY, int frame, double width, double height, boolean displayIndicators) {

        if (!MiniWars.getInstance().isPlaying()) return;

        double offsetDivider = (this instanceof OnFoot) ? 4.0d : 5.0d;
        double indicatorX = pixelX + Config.PIXEL_PER_CASE / offsetDivider;
        double indicatorBisX = pixelX - Config.PIXEL_PER_CASE / offsetDivider;
        double indicatorY = pixelY - Config.PIXEL_PER_CASE / offsetDivider;

        if (this.isAlive()) {

            final Player player = MiniWars.getInstance().getCurrentGame().getCurrentPlayer();
            final Grid grid = MiniWars.getInstance().getCurrentGame().getGrid();

            // Rendu propres aux sous-marins
            if (this instanceof Submarine && ((Submarine) this).canSurface()) {

                final Case center = grid.getCase(this.getCoordinate());

                boolean display = this.getOwner() == player.getType();
                display = display || grid.getAdjacentCases(center)
                        .stream()
                        .anyMatch(c -> c.hasUnit() && c.getUnit().getOwner() == player.getType());

                if (display) {
                    DisplayUtil.drawPicture(pixelX, pixelY, this.getFile(frame), width, height);
                    DisplayUtil.drawPicture(indicatorBisX, indicatorY, PathUtil.getIndicatorPath(!this.hasPlayed, "submarine"), width / 3, height / 3);
                }

            }

            else {
                DisplayUtil.drawPicture(pixelX, pixelY, this.getFile(frame), width, height);
            }

            // Rendu des indicateurs de capacite d'un transport
            if (this instanceof Transport) {
                Transport transport = (Transport) this;
                if (transport.isFull()) {
                    DisplayUtil.drawPicture(indicatorBisX, indicatorY, PathUtil.getIndicatorPath(!this.hasPlayed, "locker"), width / 3, height / 3);
                }
                else if (transport.isCarryingUnit()) {
                    DisplayUtil.drawPicture(indicatorBisX, indicatorY, PathUtil.getIndicatorPath(!this.hasPlayed, "non_full"), width / 3, height / 3);
                }
            }

            // Rendu des indicateurs des unites (energie, munition, vie)
            if (displayIndicators) {

                if (this.getHealth() < Unit.MAX_HEALTH) {

                    DisplayUtil.drawPicture(indicatorX, indicatorY, PathUtil.getHealthPath((int) Math.floor(this.getHealth()), !this.hasPlayed()), width / 3, height / 3);

                }

                if (this.getOwner() == player.getType()) {

                    boolean isLowAmmo = this.getWeapons().stream()
                            .anyMatch(weapon -> weapon.getAmmo() <= weapon.getDefaultAmmo() * Config.UNIT_LOW_AMMO_THRESHOLD);
                    boolean noAmmo = this.getWeapons().stream()
                            .allMatch(weapon -> weapon.getAmmo() == 0);

                    boolean displayLowAmmo = ((isLowAmmo && (frame % 2 == 0)) || noAmmo) && this.hasAnyWeapon();

                    if (isLowAmmo) indicatorY += Config.PIXEL_PER_CASE / 3;
                    if (displayLowAmmo) {

                        DisplayUtil.drawPicture(indicatorX, indicatorY, PathUtil.getIndicatorPath("ammo"), width / 3, height / 3);
                    }

                    boolean isLowEnergy = this.getEnergy() <= this.getType().getEnergy() * Config.UNIT_LOW_FUEL_THRESHOLD;
                    boolean noEnergy = this.getEnergy() == 0;

                    boolean displayLowEnergy = (isLowEnergy && (frame % 2 == 0)) || noEnergy;

                    if (displayLowEnergy) {
                        indicatorY += Config.PIXEL_PER_CASE / 3;
                        DisplayUtil.drawPicture(indicatorX, indicatorY, PathUtil.getIndicatorPath("fuel"), width / 3, height / 3);
                    }
                }
            }
        }
    }

    public abstract UnitType getType();

    public abstract int getDailyEnergyConsumption();

    public abstract int getMovementCostTo(Case destination, Weather weather);


}
