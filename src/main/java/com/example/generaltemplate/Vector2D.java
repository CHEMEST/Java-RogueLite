package com.example.generaltemplate;

public record Vector2D(double x, double y) {

    @Override
    public String toString(){
        return "(" + x + ", " + y + ")";
    }

    public Vector2D subtract(Vector2D other) {
        double dx = other.x - x;
        double dy = other.y - y;
        return new Vector2D(dx, dy);
    }
    public static Vector2D multiply(Vector2D vec1, Vector2D vec2) {
        return new Vector2D(vec1.x() * vec2.x(), vec1.y() * vec2.y());
    }

    public static Vector2D divide(Vector2D vec1, Vector2D vec2){
        return new Vector2D(vec1.x() / vec2.x(), vec1.y() / vec2.y());
    }

    public static Vector2D add(Vector2D vec1, Vector2D vec2){
        return new Vector2D(vec1.x() + vec2.x(), vec1.y() + vec2.y());
    }

    public Vector2D rotate(double degrees) {
        double angleRad = Math.toRadians(degrees);
//        System.out.println(angleRad);
        double cosAngle = Math.cos(angleRad);
        double sinAngle = Math.sin(angleRad);
//        System.out.println(cosAngle + " " + sinAngle);


//         Rotate the point around the origin (0, 0)
        double newX = x * cosAngle - y * sinAngle;
        double newY = x * sinAngle + y * cosAngle;

        return new Vector2D(newX, newY);
    }
    public Vector2D directionTo(Vector2D other) {
        Vector2D difference = other.subtract(this);
        double magnitude = Math.sqrt(difference.x * difference.x + difference.y * difference.y);
        if (magnitude == 0) {
            return new Vector2D(0, 0);
        }
        double dx = difference.x / magnitude;
        double dy = difference.y / magnitude;
        return new Vector2D(dx, dy);
    }

    public static Vector2D random(double minX, double minY, double maxX , double maxY) {
        double randomX = minX + Math.random() * (maxX - minX);
        double randomY = minY + Math.random() * (maxY - minY);

        return new Vector2D(randomX, randomY);
    }


}
