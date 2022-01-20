package it.unibs.pajc;

 public class Vector2d {

    private double x;
    private double y;

    public Vector2d()
    {
        this.setX(0);
        this.setY(0);
    }

    public Vector2d(double x, double y)
    {
        this.setX(x);
        this.setY(y);
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getX() {
        return x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public void set(double x, double y)
    {
        this.setX(x);
        this.setY(y);
    }


    public double dot(Vector2d v2)
    {
        double result = 0.0D;
        result = this.getX() * v2.getX() + this.getY() * v2.getY();
        return result;
    }

    public double getLength()
    {
        return Math.sqrt(getX()*getX() + getY()*getY());
    }

    public double getDistance(Vector2d v2)
    {
        return  Math.sqrt((v2.getX() - getX()) * (v2.getX() - getX()) + (v2.getY() - getY()) * (v2.getY() - getY()));
    }


    public Vector2d add(Vector2d v2)
    {
        Vector2d result = new Vector2d();
        result.setX(getX() + v2.getX());
        result.setY(getY() + v2.getY());
        return result;
    }

    public Vector2d subtract(Vector2d v2)
    {
        Vector2d result = new Vector2d();
        result.setX(this.getX() - v2.getX());
        result.setY(this.getY() - v2.getY());
        return result;
    }

    public Vector2d multiply(double scaleFactor)
    {
        Vector2d result = new Vector2d();
        result.setX(this.getX() * scaleFactor);
        result.setY(this.getY() * scaleFactor);
        return result;
    }

    public Vector2d normalize()
    {
        double len = getLength();
        if (len != 0.0f)
        {
            this.setX(this.getX() / len);
            this.setY(this.getY() / len);
        }
        else
        {
            this.setX(0.0f);
            this.setY(0.0f);
        }

        return this;
    }

    public String toString()
    {
        return "X: " + getX() + " Y: " + getY();
    }


}
