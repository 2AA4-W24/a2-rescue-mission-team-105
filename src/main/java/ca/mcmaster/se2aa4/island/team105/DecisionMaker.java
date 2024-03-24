package ca.mcmaster.se2aa4.island.team105;

import org.json.JSONArray;
import org.json.JSONObject;
import ca.mcmaster.se2aa4.island.team105.Drone.Actions;
import ca.mcmaster.se2aa4.island.team105.Drone.Drone;
import ca.mcmaster.se2aa4.island.team105.Drone.Limitations;
import ca.mcmaster.se2aa4.island.team105.Enums.Direction;
import ca.mcmaster.se2aa4.island.team105.Map.SubObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// This class extends from 
public class DecisionMaker extends SubObserver implements SearchMethods {
    private final static Logger logger = LogManager.getLogger();
    protected JSONObject decision = new JSONObject();
    private int count = -1;
    private int gridCount = -1;
    private int phase = 0;
    private boolean radar;
    private int state = 0;
    private Direction searchDirection;
    private Direction turnDirection;
    private boolean turnLeft;
    private boolean inOcean; // we scan, if there is land in the biomes array we are not in the ocean
    private boolean foundGround; // if ground is found when we echo
    private int echoRange; // if we echo, the range
    private boolean boxfound;

    @Override
    public void update(String found, int range, JSONArray biomes) {
        this.inOcean = true;
        this.foundGround = (found.equals("GROUND"));
        this.echoRange = range;
        if (biomes != null) {
            for (int i = 0; i < biomes.length(); i++) {
                if (!biomes.getString(i).equals("OCEAN")) {
                    this.inOcean = false;
                }
            }
        }
        logger.info(foundGround);
        logger.info(echoRange);
        logger.info(inOcean);
    }
    // finds the outermost edge of the map
    @Override
    public void findMapEdge(Limitations limitation, Drone drone, Direction direction, Actions action, JSONObject parameter) {
        // for changing directions
        Direction left = drone.leftOrientation(direction, drone);
        Direction right = drone.rightOrientation(direction, drone);
        count++;
        if (phase == 0) {
            logger.info("phase 0");
            if (this.foundGround) {
                phase = 1;
                count = 0;
            }
        } else if (phase == 1) {
            logger.info("phase 1");
            if (this.foundGround) {
                phase = 2;
                count = 0;
            }
        }
        if (radar) {
            if (!this.foundGround) {
                if (phase == 2) {
                    phase = 3;
                    count = 0;
                } else if (phase == 5) {
                    phase = 6;
                    count = 0;
                } else if (phase == 4) {
                    phase = 5;
                    count = 0;
                } else if (phase == 7) {
                    phase = 8;
                    count = 0;
                }
            }
        }
        if (radar && this.foundGround) {
            if (phase == 3) {
                logger.info("phase 3");
                phase = 4;
                count = 0;
            }
        }
        // condition to make sure 180 degree turns cannot happen
        if (limitation.is180DegreeTurn(direction) == false) {
            switch (phase) {
                // echo's all directions
                case 0:
                    logger.info("case 0");
                    if (count % 5 == 0) {
                        decision = action.echo(parameter, drone.orientation(direction, drone));
                    }

                    else if (count % 5 == 1) {
                        decision = action.echo(parameter, left);
                        searchDirection = left;
                    }

                    else if (count % 5 == 2) {
                        decision = action.echo(parameter, right);
                        searchDirection = right;

                    }

                    else if (count % 5 == 3) {
                        decision = action.fly(drone);
                    }

                    else if (count % 5 == 4) {
                        decision = action.scan();
                    }
                    break;
                // echo left and right
                case 1:
                    logger.info("case 1");

                    if (count % 3 == 0) {
                        decision = action.echo(parameter, left);
                        searchDirection = left;
                        turnDirection = drone.rightOrientation(searchDirection, drone);
                        this.turnLeft = true;
                    }

                    else if (count % 3 == 1) {
                        decision = action.echo(parameter, right);
                        searchDirection = right;
                        turnDirection = drone.leftOrientation(searchDirection, drone);
                        this.turnLeft = false;
                    }

                    else if (count % 3 == 2) {
                        decision = action.fly(drone);
                    }

                    // else if (count % 4 == 3) {
                    //     decision = action.scan();
                    // }
                    break;
                
                // perfect u turn
                case 2:
                    logger.info("case 2");

                    if (count < 3) {
                        decision = action.fly(drone);
                    } else if (count < 6) {
                        if (this.turnLeft) {
                            decision = action.heading(parameter, drone.leftOrientation(turnDirection, drone), drone);
                            turnDirection = drone.leftOrientation(turnDirection, drone);
                        } else {
                            decision = action.heading(parameter, drone.rightOrientation(turnDirection, drone), drone);
                            turnDirection = drone.rightOrientation(turnDirection, drone);
                        }
                    } else if (count < 7) {
                        if (this.turnLeft) {
                            turnDirection = drone.rightOrientation(turnDirection, drone);
                            decision = action.heading(parameter, drone.rightOrientation(turnDirection, drone), drone);
                            turnDirection = drone.rightOrientation(turnDirection, drone);

                        } else {
                            turnDirection = drone.leftOrientation(turnDirection, drone);
                            decision = action.heading(parameter, drone.leftOrientation(turnDirection, drone), drone);
                            turnDirection = drone.leftOrientation(turnDirection, drone);
                        }
                    } else {
                        decision = action.heading(parameter, turnDirection, drone);
                        phase = 3;
                        count = 0;
                    }
                    break;
                // flies to echo range then scans
                case 3:
                    logger.info("case 3");
                    if (count <= this.echoRange) {
                        decision = action.fly(drone);
                    } else {
                        phase = 4;
                        this.turnLeft = !this.turnLeft;
                        this.boxfound = true;
                        decision = action.scan();
                    }

                    break;
                default:
                    logger.info("no case found");
            }
        } else {
            logger.info("Bad Command: echo in wrong direction");
        }
    }

    // searches inside the map through progressive scanning
    @Override
    public void gridSearch(Limitations limitation, Drone drone, Direction direction, Actions action,
            JSONObject parameter) {
        gridCount++;
        if (state == 0 && radar) {
            if (this.foundGround) {
                state = 1;
                gridCount = 0;
            }

            else {
                state = 2;
                gridCount = 0;
            }
        } else if (state == 2 && radar) {
            if (!this.foundGround) {
                state = 3;
                gridCount = 0;
            }
        }
        if (state == 4 && radar) {
            if (this.foundGround) {
                state = 1;
                gridCount = 1;
                this.turnLeft = !this.turnLeft;
            } else {
                decision = action.stop();
                return;
            }
        }
        // can't do 180 degree turn
        if (limitation.is180DegreeTurn(direction) == false) {
            switch (state) {
                // echo forward
                case 0:
                    decision = action.echo(parameter, drone.orientation(turnDirection, drone));
                    radar = true;
                    break;
                // flies to the echo position and scans
                case 1:
                    if (gridCount <= this.echoRange) {
                        logger.info("phase 1");
                        decision = action.fly(drone);
                        radar = false;
                    } else {
                        decision = action.scan();
                        state = 0;
                        radar = false;
                    }
                    break;

                // echo in different directions
                case 2:
                    if (gridCount == 0) {
                        decision = action.scan(); // starting heading
                        radar = false;
                    } else if (gridCount % 3 == 0) {
                        if (this.turnLeft) {
                            decision = action.echo(parameter, drone.leftOrientation(turnDirection, drone)); // starting
                                                                                                            // heading

                        } else {
                            decision = action.echo(parameter, drone.rightOrientation(turnDirection, drone)); // starting
                                                                                                             // heading

                        }
                        radar = true;
                    } else if (gridCount % 3 > 0) {
                        decision = action.fly(drone);
                        radar = false;
                    }
                    break;
                
                // perfect u turn
                case 3:
                    radar = false;
                    if (gridCount == 0) {
                        decision = action.fly(drone);
                    } else if (gridCount < 4) {
                        if (this.turnLeft) {
                            decision = action.heading(parameter, drone.rightOrientation(turnDirection, drone), drone);
                            turnDirection = drone.rightOrientation(turnDirection, drone);
                        } else {
                            decision = action.heading(parameter, drone.leftOrientation(turnDirection, drone), drone);
                            turnDirection = drone.leftOrientation(turnDirection, drone);
                        }
                    } else if (gridCount < 5) {
                        decision = action.fly(drone);
                    } else {
                        if (!this.turnLeft) {
                            turnDirection = drone.rightOrientation(turnDirection, drone);
                            decision = action.heading(parameter, drone.rightOrientation(turnDirection, drone), drone);
                            turnDirection = drone.rightOrientation(turnDirection, drone);
                        } else {
                            turnDirection = drone.leftOrientation(turnDirection, drone);
                            decision = action.heading(parameter, drone.leftOrientation(turnDirection, drone), drone);
                            turnDirection = drone.leftOrientation(turnDirection, drone);
                        }
                        gridCount = -1;
                        state = 4;
                    }
                    break;

                case 4:
                    decision = action.echo(parameter, drone.orientation(turnDirection, drone));
                    radar = true;
                    break;

                default:
                    logger.info("no case found");
            }
        }
    }

    // changes decision based on conditions if the box is found, dynamic approach
    // and used in the JSONConfiguration class
    public JSONObject getDecision(Limitations limitation, Drone drone, Direction direction, Actions action,
            JSONObject parameter) {
        if (!this.boxfound) {
            findMapEdge(limitation, drone, direction, action, parameter);
        } else {
            gridSearch(limitation, drone, direction, action, parameter);
        }
        return decision;
    }
}