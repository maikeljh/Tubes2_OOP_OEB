package Testing;
import Core.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MemberTesting {
    @Test
    public void testCalculatePoint() {
        Member member = new Member();
        member.setId(1);
        member.setPoint(0);
        member.calculatePoint(200000);
        assertEquals(2000, member.getPoint());
    }

    @Test
    public void testCalculateDiscount() {
        Member member = new Member();
        member.setId(1);
        member.setPoint(3000);
        assertEquals(197000, (int) member.calculateDiscount(200000, true));
    }
}