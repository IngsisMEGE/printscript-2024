import exceptions.MustEndWithSeparator
import exceptions.SyntacticError
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ExceptionsTest {
    @Test
    fun test001MustEndWithSeparatorTriggersException() {
        assertThrows<MustEndWithSeparator> {
            throw MustEndWithSeparator(Pair(0, 0))
        }
    }

    @Test
    fun test002SyntacticErrorTriggersException() {
        assertThrows<SyntacticError> {
            throw SyntacticError("Error")
        }
    }
}
