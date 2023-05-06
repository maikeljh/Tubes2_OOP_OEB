package System;

import jakarta.xml.bind.annotation.XmlAnyElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@XmlRootElement
public class Inventory<T> implements Serializable{
    @XmlAnyElement(lax = true)
    private List<T> list;
    private int neff;

    public Inventory(){
        this.list = new ArrayList<T>();
        this.neff = 0;
    }

    public T getElement(int idx){
        return this.list.get(idx);
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
