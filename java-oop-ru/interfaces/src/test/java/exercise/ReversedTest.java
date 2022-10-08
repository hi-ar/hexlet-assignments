package exercise;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ReversedTest {
    @Test
    void revBasicTest() {
        assertThat(new ReversedSequence("abc").toString()).isEqualTo("cba");
        assertThat(new ReversedSequence("abc").length()).isEqualTo(3);
        assertThat(new ReversedSequence("abc").charAt(0)).isEqualTo('c');
        assertThat(new ReversedSequence("abc").subSequence(0, 2)).isEqualTo("cb");
    }
}
