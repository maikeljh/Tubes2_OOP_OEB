package Core;

import jakarta.xml.bind.annotation.XmlAnyElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Inventory<T> implements Serializable{
    @XmlAnyElement(lax = true)
    @Getter private List<T> box;
    @Getter @Setter private int neff;

    public Inventory(){
        this.box = new ArrayList<T>();
        this.neff = 0;
    }

    public T getElement(int idx){
        return this.box.get(idx);
    }

    public void addElement(T element) {
        this.box.add(element);
        this.neff++;
    }

    public void removeElement(T element){
        this.box.remove(element);
        this.neff--;
    }
    public void setElement(int idx, T element) { this.box.set(idx, element); }

    public Boolean containsElement(T element){ return this.box.contains(element); }
    public int getElementIdx(T element) { return this.box.indexOf(element); }
}
