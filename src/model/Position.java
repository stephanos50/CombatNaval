/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

public  class Position implements Comparable<Position> {
    protected int x ; 
    protected int y;
    private static int ix; 
    private static int iy;
    
    public Position(){}
    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }
    public boolean move(Position p){
        setX(p.x);
        setY(p.y);
        return true;
    }
    
    private int useInteger(){
        return x*100 + y*1;
    }
    
    @Override
    public int compareTo(Position o) {
       return this.useInteger() - o.useInteger();
    }
    
    @Override
    public boolean equals(Object o){
        if(o instanceof Position){
            Position that = (Position) o;
            return this.x == that.x && this.y == that.y;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.x;
        hash = 59 * hash + this.y;
        return hash;
    }

    public static int getIx() {
        return ix;
    }

    public static int getIy() {
        return iy;
    }

    public static void setIx(int ix) {
        Position.ix = ix;
    }

    public static void setIy(int iy) {
        Position.iy = iy;
    }
    
    public static void sauvegarder(int x, int y){
        setIx(x);
        setIy(y);
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }   
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    @Override
    public String toString(){
        return x + " " + y;
    }
}
