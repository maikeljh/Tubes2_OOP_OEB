package Testing;
import System.*;
import javafx.scene.image.Image;
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

    public void testCalculateDiscount() {
        Member member = new Member();
        member.setId(1);
        member.setPoint(3000);
        member.calculateDiscount()
    }
}