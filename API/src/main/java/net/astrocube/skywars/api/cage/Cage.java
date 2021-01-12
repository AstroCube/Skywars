package net.astrocube.skywars.api.cage;

import net.astrocube.skywars.api.custom.Customizable;

public interface Cage extends Customizable {

    /**
     * @return stack with the roof to be used at the cage
     */
    String getRoof();

    /**
     * @return stack with the walls to be used at the cage
     */
    String getWalls();

    /**
     * @return stack with the floor to be used at the cage
     */
    String getFloor();

    /**
     * @return string that will be used for stats processing
     */
    String getName();

    /**
     * @return if walls are overlapping floor and walls corners
     */
    boolean isOverlappingCorners();

    /**
     * @return if every extreme corner will be removed
     */
    boolean pointedCorners();

}
