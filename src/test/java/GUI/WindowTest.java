package GUI;

import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class WindowTest {

    private Window window;

    @Before
    public void setup(){
        window = new Window(0, 0, 10, 10, "Window name");
    }

    @Test
    public void checkNumberOfReturnedParagraphs() {
        // given
        String text = "Line 1\nParagraph 1\n\nLine 4\nParagraph 2\n\nLine 7\nLine 8\nParagraph 3";

        // when
        List<String> content = window.setContent(text);

        // then
        Assert.assertEquals(3, content.size());
    }

    @Test
    public void checkNumberOfReturnedParagraphsWithoutDivision() {
        // given
        String text = "Line 1\nParagraph 1\n\nLine 4\nParagraph 2\n\nLine 7\nLine 8\nParagraph 3";

        // when
        List<String> content = window.setContent(text);

        // then
        Assert.assertEquals(3, content.size());
    }

    @Test
    public void checkNumberOfReturnedParagraphsWithMoreSpace() {
        // given
        String text = "Line 1\nParagraph 1\n\n\nLine 4\nParagraph 2\n\n\n\nLine 7\nLine 8\nParagraph 3";

        // when
        List<String> content = window.setContent(text);

        // then
        Assert.assertEquals(3, content.size());
    }

    @Test
    public void checkNumberOfReturnedParagraphsWithNoSpace(){
        // given
        String text = "Line 1\nParagraph 1";

        // when
        List<String> content = window.setContent(text);

        // then
        Assert.assertEquals(1, content.size());
    }

    @Test
    public void checkNumberOfReturnedParagraphsWithJustSpace(){
        // given
        String text = "\n\n\n";

        // when
        List<String> content = window.setContent(text);

        // then
        Assert.assertEquals(0, content.size());
    }

    @Test
    public void checkNumberOfReturnedParagraphsWithEmptyContent(){
        // given
        String text = "";

        // when
        List<String> content = window.setContent(text);

        // then
        Assert.assertEquals(0, content.size());
    }
}