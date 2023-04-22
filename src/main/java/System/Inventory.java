package System;

import java.util.ArrayList;
import java.util.List;

public class Inventory<T> {
    private List<T> list;
    private int neff;

    public Inventory(){
        this.list = new ArrayList<T>();
        this.neff = 0;
    }

    /* Getter */
    public List<T> getList(){
        return this.list;
    }

    public T getElement(int idx){
        return this.list.get(idx);
    }

    public int getNeff(){
        return neff;
    }

    /* Setter */
    public void setNeff(int neff){
        this.neff = neff;
    }

    public void addElement(T element) {
        this.list.add(element);
    }

    public void removeElement(T element){
        this.list.remove(element);
    }
    public void setElement(int idx, T element) { this.list.set(idx, element); }

    public Boolean containsElement(T element){ return this.list.contains(element); }
    public int getElementIdx(T element) { return this.list.indexOf(element); }
}
