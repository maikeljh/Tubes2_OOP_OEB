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

    public List<T> getList(){
        return this.list;
    }

    public T getElement(int idx){
        return this.list.get(idx);
    }

    public int getNeff(){
        return neff;
    }

    public void setNeff(int neff){
        this.neff = neff;
    }

    public void addElement(T element) {
        this.list.add(element);
    }

    public void removeElement(T element){
        this.list.remove(element);
    }
}
