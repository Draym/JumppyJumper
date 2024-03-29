package com.andres_k.gameToolsLib.utils.stockage;

/**
 * Created by andres_k on 13/03/2015.
 */
public class Pair<T1, T2> extends Object {
    T1 v1;
    T2 v2;

    public Pair(T1 v1, T2 v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public Pair(Pair<T1, T2> pair){
        if (pair != null) {
            this.copy(pair);
        }
    }

    public void copy(Pair<T1, T2> values) {
        this.v1 = values.getV1();
        this.v2 = values.getV2();
    }

    public T1 getV1() {
        return this.v1;
    }

    public T2 getV2() {
        return this.v2;
    }

    public void setV1(T1 value) {
        this.v1 = value;
    }

    public void setV2(T2 value) {
        this.v2 = value;
    }

    @Override
    public String toString(){
        return new String("v1:" + this.getV1() + " v2:"+this.getV2());
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Pair) {
            if (this.getV1().equals(((Pair) object).getV1())
                    && this.getV2().equals(((Pair) object).getV2())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.v1.hashCode() + this.v2.hashCode();
    }
}