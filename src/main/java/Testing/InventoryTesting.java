package Testing;

import System.*;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;

public class InventoryTesting {
    @Test
    public void testAddElement() {
        Inventory<Member> members = new Inventory<Member>();
        Member member1 = new Member();
        member1.setName("Marcel");
        member1.setPhoneNumber("260304");
        members.addElement(member1);

        assertEquals("Marcel", members.getElement(0).getName());
        assertEquals("260304", members.getElement(0).getPhoneNumber());
    }

    @Test
    public void testRemoveElement() {
        Inventory<Member> members = new Inventory<Member>();
        Member member1 = new Member();
        member1.setName("Marcel");
        member1.setPhoneNumber("260304");
        members.addElement(member1);

        assertEquals(1, members.getNeff());

        members.removeElement(member1);

        assertEquals(0, members.getNeff());
    }

    @Test
    public void testSetElement() {
        Inventory<Member> members = new Inventory<Member>();
        Member member1 = new Member();
        member1.setName("Marcel");
        member1.setPhoneNumber("260304");
        members.addElement(member1);

        assertEquals("Marcel", members.getElement(0).getName());
        assertEquals("260304", members.getElement(0).getPhoneNumber());

        Member member2 = new Member();
        member2.setName("Uvuvwevwe");
        member2.setPhoneNumber("080203");

        members.setElement(0, member2);

        assertEquals("Uvuvwevwe", members.getElement(0).getName());
        assertEquals("080203", members.getElement(0).getPhoneNumber());
    }

    @Test
    public void testContainElement() {
        Inventory<Member> members = new Inventory<Member>();
        Member member1 = new Member();
        member1.setName("Marcel");
        member1.setPhoneNumber("260304");
        members.addElement(member1);

        assertEquals(true, members.containsElement(member1));
    }

    @Test
    public void testGetElementIdx() {
        Inventory<Member> members = new Inventory<Member>();
        Member member1 = new Member();
        member1.setName("Marcel");
        member1.setPhoneNumber("260304");
        members.addElement(member1);

        assertEquals(0, members.getElementIdx(member1));
    }
}
