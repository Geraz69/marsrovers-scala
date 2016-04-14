/**
 * Created by gerardo.mendez on 4/14/16.
 */

import java.io.*;
import java.util.*;

public class MarsRovers {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        if(args.length != 1) {
            throw new IllegalArgumentException("Please specify a path to a file containing the data as the first argument");
        }
        BufferedReader br = new BufferedReader(new FileReader(args[0]));
        String line = null;
        Integer lineNumber = 0, width = 0, height = 0;
        Position current = null;

        LinkedHashMap<Position,String> initialPositions = new LinkedHashMap<Position,String>();

        while ((line = br.readLine()) != null) {
            if(lineNumber == 0) {
                String[] size = line.split(" ");
                if(size.length != 2) {
                    throw new IllegalArgumentException("Size information in the first line of the input is not well formatted");
                }
                width = Integer.parseInt(size[0]);
                height = Integer.parseInt(size[1]);
            } else if(lineNumber % 2 == 1){ // first line, rover's initial position, inverse than 0 since we droped the first line
                String[] position = line.split(" ");
                if(position.length != 3)
                    throw new IllegalArgumentException("Rover position information is not well formated");
                current = new Position(Integer.parseInt(position[0]),Integer.parseInt(position[1]), position[2]);
            } else { // second line, we compliment
                initialPositions.put(current, line);
                current = null; // empty current so we can check if there are missing lines
            }
            lineNumber++;
        }
        if (current != null) throw new IllegalArgumentException("Input is incomplete, set of instructions not found for last rover");

        Plateau plateau = new Plateau(width, height, initialPositions);

        for(Position position: plateau.getFinalPositions()) {
            System.out.println(position);
        }

    }
}

class Position {

    public static Position change(Position previous, Character change){
        switch (change) {
            case 'L':
                return new Position(previous.x, previous.y, Direction.valueOf(previous.d.getNumber() - 1));
            case 'R':
                return new Position(previous.x, previous.y, Direction.valueOf(previous.d.getNumber() + 1));
            case 'M':
                switch (previous.d.getNumber()) {
                    case 0:
                        return new Position(previous.x, previous.y + 1, previous.d);
                    case 1:
                        return new Position(previous.x + 1, previous.y, previous.d);
                    case 2:
                        return new Position(previous.x, previous.y - 1, previous.d);
                    case 3:
                        return new Position(previous.x - 1, previous.y, previous.d);
                }
            default:
                throw new NoSuchElementException("Unrecognised instruction");
        }
    }

    private Integer x;
    private Integer y;
    private Direction d;

    public Position (Integer x, Integer y, Direction d){
        this.x = x;
        this.y = y;
        this.d = d;
    }

    public Integer getX(){
        return this.x;
    }

    public Integer getY(){
        return this.y;
    }

    public Direction getD(){
        return this.d;
    }

    public Position (Integer x, Integer y, String d){
        this(x, y, Direction.valueOf(d));
    }
    @Override
    public boolean equals (Object other) {
        if(other instanceof Position) {
            Position otherPosition = (Position)other;
            return otherPosition.x == this.x && otherPosition.y == this.y && otherPosition.d.equals(this.d);
        } else return false;
    }
    @Override
    public int hashCode () {
        return x * y * d.hashCode();
    }

    @Override
    public String toString(){
        return this.x + " " + this.y + " " + this.d.getLetter();
    }
}

enum Direction {

    N(0), E(1), S(2), W(3);
    private Integer number;

    private Direction(Integer number){
        this.number = number;
    }

    public String getLetter() { return this.name(); }
    public Integer getNumber() { return this.number; }

    public static Direction valueOf(Integer number){
        Integer index = Direction.values().length + (number % Direction.values().length);
        // Applying modulo twice for when the number is negative
        return Direction.values()[index % Direction.values().length];
    }
}

class Plateau {
    private Integer x;
    private Integer y;
    private LinkedHashMap<Position,String> initialPositions;
    private HashSet<Position> finalPositions;

    public Plateau (Integer x, Integer y, LinkedHashMap<Position,String> initialPositions){
        if (x < 0 || y < 0) throw new IllegalArgumentException ("Incorrect size for Plateau");
        this.x = x;
        this.y = y;
        this. initialPositions = initialPositions;
        this.calculateFinalPositions();
    }

    public HashSet<Position> getFinalPositions(){
        return this.finalPositions;
    }

    private void calculateFinalPositions(){
        finalPositions = new HashSet<Position>();
        finalPositions.addAll(initialPositions.keySet());
        for (Map.Entry<Position, String> entry : initialPositions.entrySet()) {
            Position position = entry.getKey();
            String instructions = entry.getValue();
            finalPositions.remove(position);
            for(int x = 0; x < instructions.length(); x ++){
                position = Position.change(position, instructions.charAt(x));
                if (position.getY() < 0 || position.getY() > this.y || position.getX() < 0 || position.getX() > this.x) {
                    throw new IllegalStateException("Rover is out of bounds");
                }
                // Fail if rover arrived at the same spot that other rover and we are tracking collisions
                if (finalPositions.contains(position)) {
                    throw new IllegalStateException("Rover just crashed with another rover");
                }
            }
            finalPositions.add(position);
        }
    }
}