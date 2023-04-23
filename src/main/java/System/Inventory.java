package System;

import jakarta.xml.bind.annotation.XmlAnyElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Inventory<T> implements Serializable{
    @XmlAnyElement(lax = true)
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
        this.neff++;
    }

    public void removeElement(T element){
        this.list.remove(element);
        this.neff--;
    }
    public void setElement(int idx, T element) { this.list.set(idx, element); }

    public Boolean containsElement(T element){ return this.list.contains(element); }
    public int getElementIdx(T element) { return this.list.indexOf(element); }
}
