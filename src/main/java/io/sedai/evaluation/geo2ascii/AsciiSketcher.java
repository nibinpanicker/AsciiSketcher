package io.sedai.evaluation.geo2ascii;

public class AsciiSketcher {

    public static void main(String[] args) {
        System.out.println("ASCII Sketcher");
        System.out.println("================================");
        if (args != null && args.length == 2) {
            try {
                int height = Integer.parseInt(args[0]);
                int width = Integer.parseInt(args[1]);
                new Geo2AsciiController().generateAsciiSketch(width, height);
            } catch (NumberFormatException e) {
                System.err.println("Invalid arguments. Please provide two integers for height and width.");
            }
        } else {
            new Geo2AsciiController().generateAsciiSketch(120, 120);
        }
    }

}